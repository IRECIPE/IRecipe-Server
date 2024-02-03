package umc.IRECIPE_Server.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.service.MemberService;
import umc.IRECIPE_Server.validation.annotation.ExistAllergies;
import umc.IRECIPE_Server.validation.annotation.ExistNickname;

@Component
@RequiredArgsConstructor
public class NicknameExistValidator implements ConstraintValidator<ExistNickname, String> {
    private final MemberService memberService;

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
