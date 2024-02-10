package umc.IRECIPE_Server.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import umc.IRECIPE_Server.validation.validator.NicknameExistValidator;
import umc.IRECIPE_Server.validation.validator.PersonalIdExistValidator;

@Documented
@Constraint(validatedBy = PersonalIdExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistPersonalId {

    String message() default "이미 가입된 회원입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}