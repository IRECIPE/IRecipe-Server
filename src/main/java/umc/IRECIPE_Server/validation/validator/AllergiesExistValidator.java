package umc.IRECIPE_Server.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.validation.annotation.ExistAllergies;

@Component
@RequiredArgsConstructor
public class AllergiesExistValidator implements ConstraintValidator<ExistAllergies, List<Long>> {
    private final AllergyRepository allergyRepository;

    @Override
    public void initialize(ExistAllergies constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        boolean isValid = values.stream()
                .allMatch(allergyRepository::existsById);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ALLERGY_NOT_FOUND.getMessage()).addConstraintViolation();
        }

        return isValid;

    }
}
