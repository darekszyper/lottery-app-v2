package com.internship.juglottery.event;

import com.internship.juglottery.service.impl.EmailSenderService;
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
