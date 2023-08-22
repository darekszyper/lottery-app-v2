package com.internship.juglottery.service.impl;

import com.internship.juglottery.repository.ParticipantRepo;
import com.internship.juglottery.service.AnonymizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnonymizationServiceImpl implements AnonymizationService {

    private final ParticipantRepo participantRepo;

    @Override
    public String anonymizeEmail(String email) {
        String[] split = email.split("@");
        int length = split[0].length();

        if (length > 2) {
            return email.replaceAll("(?<=.).(?=[^@]*?.@)", "*");
        } else {
            StringBuilder anonymizedEmail = new StringBuilder();
            anonymizedEmail.append(split[0].substring(0, length - 1)).append("*@").append(split[1]);
            return anonymizedEmail.toString();
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void cronAnonymize() {
        List<String> idsAndEmails = participantRepo.extractIdsAndEmails();

        for (String idAndEmail : idsAndEmails) {
            String[] splitResult = idAndEmail.split(",");
            participantRepo.editEmails(Long.valueOf(splitResult[0]), anonymizeEmail(splitResult[1]));
        }
    }
}
