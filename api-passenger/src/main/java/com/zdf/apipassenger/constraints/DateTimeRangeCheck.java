package com.zdf.apipassenger.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mrzhang
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataTimeRangeCheckValidator.class)

public @interface DateTimeRangeCheck
{
    String pattern() default "yyyy-MM-dd MM:mm:ss";

    String message() default "输入正确的日期";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
