package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Step;

public interface StepService extends CrudService<Step, Long> {
    Step saveAndSetToManual(Step step, Manual manual);
}
