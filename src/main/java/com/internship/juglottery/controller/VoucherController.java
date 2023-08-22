package com.internship.juglottery.controller;

import com.internship.juglottery.dto.request.VoucherRequest;
import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.mapper.VoucherMapper;
import com.internship.juglottery.service.AppUserService;
import com.internship.juglottery.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherMapper voucherMapper;
    private final AppUserService appUserService;

    @GetMapping("/new_voucher")
    public String getNewVoucherPage(Model model) {
        model.addAttribute("voucher", new VoucherRequest());
        return "new_voucher";
    }

    @PostMapping("/new_voucher")
    public String createVoucher(@ModelAttribute("voucher") @Valid VoucherRequest voucherRequest,
                                BindingResult bindingResult,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            log.error("Validation error: {}", bindingResult.getAllErrors());
            return "error/voucher_validation_error";
        }

        Long userId = appUserService.getUserIdByEmail(principal.getName());
        voucherRequest.setUserId(userId);
        Voucher voucherEntity = voucherMapper.mapToEntity(voucherRequest);
        voucherService.createVoucher(voucherEntity);
        return "redirect:/lottery/new_voucher?success";
    }
}
