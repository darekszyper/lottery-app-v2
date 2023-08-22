package com.internship.juglottery.service;

public interface AnonymizationService {

    String anonymizeEmail(String email);

    void cronAnonymize();
}
