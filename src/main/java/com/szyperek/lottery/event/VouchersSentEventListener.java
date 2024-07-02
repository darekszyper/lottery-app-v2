package com.szyperek.lottery.event;

import com.szyperek.lottery.service.impl.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VouchersSentEventListener {

    private final EmailSenderService emailSenderService;

    @EventListener
    public void onApplicationEvent(VouchersSentEvent event) {
        emailSenderService.sendVouchersToWinners(event.lotteryId());
    }
}
