package com.szyperek.lottery.service;

public interface QRService {
    byte[] generateQRCode(String barcodeText);
}
