package umc.IRECIPE_Server.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.service.MemberService;
import umc.IRECIPE_Server.validation.annotation.ExistNickname;
import umc.IRECIPE_Server.validation.annotation.ExistPersonalId;

@Component
@RequiredArgsConstructor
public class PersonalIdExistValidator implements ConstraintValidator<ExistPersonalId, String> {
    private final MemberService memberService;

    @Override
    public void initialize(ExistPersonalId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Boolean target = memberService.findMemberByPersonalId(value);

        if (!target){ // 사용 가능한거
            return true;
        }
        else{
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_ALREADY_EXIST.getMessage()).addConstraintViolation();
            return false;
        }
    }
}
