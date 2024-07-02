package com.szyperek.lottery.controller;

import com.szyperek.lottery.dto.request.LotteryRequest;
import com.szyperek.lottery.dto.response.FinishedLotteryResponse;
import com.szyperek.lottery.dto.response.LotteryResponse;
import com.szyperek.lottery.dto.response.ParticipantResponse;
import com.szyperek.lottery.dto.response.VoucherResponse;
import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Voucher;
import com.szyperek.lottery.event.VouchersSentEvent;
import com.szyperek.lottery.exception.LotteryNotActiveException;
import com.szyperek.lottery.mapper.LotteryMapper;
import com.szyperek.lottery.mapper.ParticipantMapper;
import com.szyperek.lottery.mapper.VoucherMapper;
import com.szyperek.lottery.service.AppUserService;
import com.szyperek.lottery.service.LotteryService;
import com.szyperek.lottery.service.ParticipantService;
import com.szyperek.lottery.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lottery")
@RequiredArgsConstructor
@Slf4j
public class LotteryController {

    private final LotteryMapper lotteryMapper;
    private final VoucherMapper voucherMapper;
    private final ParticipantMapper participantMapper;
    private final LotteryService lotteryService;
    private final VoucherService voucherService;
    private final ParticipantService participantService;
    private final AppUserService appUserService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping
    public String showUserMainPage(Model model, Principal principal) {
        Long userId = appUserService.getUserIdByEmail(principal.getName());
        List<LotteryResponse> lotteryResponses = lotteryService.getAllLotteriesAssignedToUser(userId).stream()
                .map(lotteryMapper::mapToResponse)
                .collect(Collectors.toList());

        model.addAttribute("lotteries", lotteryResponses);
        return "user_main_page";
    }

    @PostMapping("/manage")
    public String deleteLottery(@RequestParam("eventId") Long eventId, Model model) {
        participantService.removeParticipantId(eventId);
        voucherService.removeVoucherId(eventId);
        lotteryService.deleteLottery(eventId);

        model.addAttribute("eventId", eventId);
        return "success/delete_lottery_success";
    }

    @GetMapping("/manage")
    public String getNewManageLotteryPage(Model model, Principal principal) {
        Long userId = appUserService.getUserIdByEmail(principal.getName());
        List<LotteryResponse> lotteryResponses = lotteryService.getAllLotteriesAssignedToUser(userId).stream()
                .map(lotteryMapper::mapToResponse)
                .collect(Collectors.toList());

        model.addAttribute("lotteries", lotteryResponses);
        return "manage_lottery";
    }

    @GetMapping("/qr_code")
    public String startLottery(@RequestParam("eventId") Long eventId, Model model) {
        try {
            lotteryService.changeLotteryStatusToActive(eventId);
        } catch (LotteryNotActiveException exception) {
            log.error(exception.getMessage());
            return "error/lottery_not_active";
        }
        model.addAttribute("eventId", eventId);
        int participantCount = participantService.getConfirmedEmailCount(eventId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("participantCount", participantCount);

        return "qr_code";
    }

    @PostMapping("/winners/{eventId}")
    public String finishLotteryAndShowWinners(@PathVariable("eventId") Long eventId, Model model) {
        try {
            List<ParticipantResponse> winners = lotteryService.pickWinners(eventId).stream()
                    .map(participantMapper::mapToParticipantResponse)
                    .toList();
            model.addAttribute("winners", winners);
            eventPublisher.publishEvent(new VouchersSentEvent(eventId));
            return "winners";
        } catch (LotteryNotActiveException exception) {
            log.error(exception.getMessage());
            return "error/lottery_not_active";
        }
    }

    @GetMapping("/create")
    public String getCreateEventPage(Model model, Principal principal) {
        Long userId = appUserService.getUserIdByEmail(principal.getName());
        List<VoucherResponse> voucherResponses = voucherService.getAllNotUsedVouchersAssignedToUser(userId).stream()
                .map(voucherMapper::mapToVoucherResponse).toList();

        model.addAttribute("vouchers", voucherResponses);
        model.addAttribute("lottery", new LotteryRequest());
        return "new_event";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute("lottery") @Valid LotteryRequest lotteryRequest,
                              BindingResult bindingResult,
                              Principal principal) {
        if (bindingResult.hasErrors()) {
            log.error("Validation error: {}", bindingResult.getAllErrors());
            return "error/lottery_validation_error";
        }

        Long userId = appUserService.getUserIdByEmail(principal.getName());
        lotteryRequest.setUserId(userId);

        List<Voucher> vouchers = voucherService.getAllVouchersById(lotteryRequest.getVoucherId());
        Lottery lottery = lotteryMapper.mapToEntity(lotteryRequest);
        lotteryService.createLotteryWithAssignedVouchers(lottery, vouchers);
        return "redirect:/lottery";
    }

    @GetMapping("/previous_winners")
    public String displayPreviousWinners(Model model, Principal principal) {
        Long userId = appUserService.getUserIdByEmail(principal.getName());

        List<FinishedLotteryResponse> lotteries = lotteryService.getAllFinishedLotteriesAssignedToUser(userId).stream()
                .map(lotteryMapper::mapToFinishedLotteryResponse).toList();
        model.addAttribute("lotteries", lotteries);
        return "previous_winners";
    }
}
