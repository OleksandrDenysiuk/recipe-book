package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.repository.StepRepository;
import com.portfolio.recipebook.service.StepService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;

    public StepServiceImpl(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public Set<Step> findAll() {
        Set<Step> steps = new HashSet<>();
        stepRepository.findAll().forEach(steps::add);
        return steps;
    }

    @Override
    public Step findById(Long aLong) {
        return stepRepository.findById(aLong).orElse(null);
    }

    @Override
    public void delete(Step object) {
        stepRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        stepRepository.deleteById(aLong);
    }

    @Transactional
    @Override
    public Step saveAndSetToManual(Step step, Manual manual) {
        step.setManual(manual);
        stepRepository.save(step);
        manual.getSteps().add(step);
        return null;
    }
}
