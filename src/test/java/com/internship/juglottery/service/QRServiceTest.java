package com.internship.juglottery.service;

import com.internship.juglottery.service.impl.QRServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QRServiceTest {

    QRService qrService = new QRServiceImpl();

    @Test
    @DisplayName("Should return byte array")
    void shouldReturnByteArray() {
        // given
        String url = "http://localhost:8080/register/1";

        // when
        byte[] bytes = qrService.generateQRCode(url);

        // then
        assertInstanceOf(byte[].class, bytes);
    }
}