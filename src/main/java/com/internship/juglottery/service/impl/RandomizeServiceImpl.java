package com.internship.juglottery.service.impl;

import com.internship.juglottery.service.RandomizeService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomizeServiceImpl implements RandomizeService {
    @Override
    public int randomize(int numberOfParticipants) {
        Random random = new Random();

        return random.ints(0, numberOfParticipants)
                .limit(3)
                .sum() % numberOfParticipants;
    }
}
