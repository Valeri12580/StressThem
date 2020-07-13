package com.stressthem.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {
    String message() default "Error!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldType() ;

}
