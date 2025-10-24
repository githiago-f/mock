package com.example;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private int maximumWeightLimit;
    private List<Load> loads = new ArrayList<>();
    private WeightService weightService;

    public Vehicle() {
        this.maximumWeightLimit = 100;
    }

    public Vehicle(int weightLimit, WeightService weightService) {
        this.maximumWeightLimit = weightLimit;
        this.weightService = weightService;
    }

    public void addWeight(Load load) {
        loads.add(load);
    }

    public boolean checkWeightLimit() {
        int total = loads.stream().mapToInt(Load::getWeight).sum();
        // delega a verificação ao serviço externo
        return weightService.isWeightAllowed(total, maximumWeightLimit);
    }

    public int getMaximumWeightLimit() {
        return maximumWeightLimit;
    }

    public List<Load> getLoads() {
        return loads;
    }
}