package com.internship.juglottery.controller;

import com.internship.juglottery.service.QRService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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

    @GetMapping("{lotteryId}")
    public void showQRImage(@PathVariable Long lotteryId, HttpServletResponse response) throws IOException {
        byte[] bytes = qrService.generateQRCode("http://localhost:8080/register/" + lotteryId);
        response.setContentType("png");

        InputStream is = new ByteArrayInputStream(bytes);
        IOUtils.copy(is, response.getOutputStream());
    }
}
