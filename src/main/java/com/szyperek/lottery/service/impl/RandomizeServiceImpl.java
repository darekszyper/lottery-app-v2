package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.service.RandomizeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RandomizeServiceImpl implements RandomizeService {
    @Override
    public List<Integer> randomize(int range, int amountOfNumbers) {
        List<Integer> participantsIndexes = IntStream.rangeClosed(0, range)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> winnersIndexes = new ArrayList<>();
        Random random = new Random();

        while (winnersIndexes.size() < amountOfNumbers) {
            if (range == 0) {
                winnersIndexes.add(participantsIndexes.get(0));
                participantsIndexes.remove(0);
            } else {
                int winnerIndex = random.ints(0, range)
                        .limit(3)
                        .sum() % range;
                winnersIndexes.add(participantsIndexes.get(winnerIndex));
                participantsIndexes.remove(winnerIndex);
                range--;
            }
        }

        return winnersIndexes;
    }

}
