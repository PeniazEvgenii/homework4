package ru.aston.hometask.validator.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidationContext {
    private final List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {
        errors.add(error);
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
