package br.com.hmv.services.validation.administrativo.funcionario.criacao;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FuncionarioInsertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface FuncionarioInsertValid {
    String message() default "Validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
