package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.ManualRepository;
import com.portfolio.recipebook.service.ManualService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ManualServiceImpl implements ManualService {

    private final ManualRepository manualRepository;

    public ManualServiceImpl(ManualRepository manualRepository) {
        this.manualRepository = manualRepository;
    }

    @Override
    public Set<Manual> findAll() {
        Set<Manual> manuals = new HashSet<>();
        manualRepository.findAll().forEach(manuals::add);
        return manuals;
    }

    @Override
    public Manual findById(Long aLong) {
        return manualRepository.findById(aLong).orElse(null);
    }

    @Override
    public void delete(Manual object) {
        manualRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        manualRepository.deleteById(aLong);
    }

    @Transactional
    @Override
    public Manual saveAndSetToRecipe(Manual manual, Recipe recipe) {
        manual.setRecipe(recipe);
        Manual savedManual = manualRepository.save(manual);
        recipe.setManual(savedManual);

        return savedManual;
    }
}
