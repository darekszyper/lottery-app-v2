package com.internship.juglottery.controller;

import com.internship.juglottery.service.ParticipantService;
import com.internship.juglottery.service.QRService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/qr_code/image")
@RequiredArgsConstructor
public class QRController {

    private final QRService qrService;
    private final SimpMessagingTemplate template;
    private final ParticipantService participantService;

    @GetMapping("{lotteryId}")
    public void showQRImage(@PathVariable Long lotteryId,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        String hostName = request.getRequestURL().toString().replace("/qr_code/image/" + lotteryId, "");
        byte[] bytes = qrService.generateQRCode(hostName + "/register/" + lotteryId);
        response.setContentType("image/png");

        InputStream is = new ByteArrayInputStream(bytes);
        IOUtils.copy(is, response.getOutputStream());
        template.convertAndSend("/topic/participantCount", participantService.getConfirmedEmailCount(lotteryId));
    }
}
