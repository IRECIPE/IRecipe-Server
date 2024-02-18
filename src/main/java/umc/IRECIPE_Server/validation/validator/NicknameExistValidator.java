package umc.IRECIPE_Server.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.service.memberService.MemberServiceImpl;
import umc.IRECIPE_Server.validation.annotation.ExistNickname;

@Component
@RequiredArgsConstructor
public class NicknameExistValidator implements ConstraintValidator<ExistNickname, String> {
    private final MemberServiceImpl memberService;

    @Override
    public void initialize(ExistNickname constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Boolean target = memberService.findMemberByNickname(value);

        if (!target){ // 사용 가능한거
            return true;
        }
        else{
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.NICKNAME_ALREADY_EXIST.getMessage()).addConstraintViolation();
            return false;
        }
    }
}
