package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Step;

import java.util.Set;

public interface StepService{
    Step saveAndSetToManual(Step step, Manual manual);
    Set<Step> findAll();
    Step findById(Long aLong);
    void delete(Step object);
    void deleteById(Long aLong);
}
