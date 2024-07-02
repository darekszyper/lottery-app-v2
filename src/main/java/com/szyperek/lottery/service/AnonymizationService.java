package com.szyperek.lottery.service;

public interface AnonymizationService {

    String anonymizeEmail(String email);

    void cronAnonymize();
}
