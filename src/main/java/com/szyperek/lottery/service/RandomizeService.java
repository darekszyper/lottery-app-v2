package com.szyperek.lottery.service;

import java.util.List;

public interface RandomizeService {
    List<Integer> randomize(int range, int amountOfNumbers);
}
