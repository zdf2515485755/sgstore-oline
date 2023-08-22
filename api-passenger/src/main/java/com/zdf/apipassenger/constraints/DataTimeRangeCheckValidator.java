package com.zdf.apipassenger.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author mrzhang
 */
public class DataTimeRangeCheckValidator implements ConstraintValidator<DateTimeRangeCheck, Object>
{
    @Override
    public void initialize(DateTimeRangeCheck dateTimeRangeCheck)
    {
        ConstraintValidator.super.initialize(dateTimeRangeCheck);

    }
    @Override
    public boolean isValid(Object paramDate, ConstraintValidatorContext constraintValidatorContext)
    {
        LocalDateTime dateValue = null;
        DateTimeRangeCheck dateTimeRangeCheck = null;

        if (paramDate instanceof LocalDateTime)
        {
            dateValue = (LocalDateTime) paramDate;
        }

        if (paramDate instanceof String) {
            dateValue = LocalDateTime.parse((String) paramDate, DateTimeFormatter.ofPattern(dateTimeRangeCheck.pattern()));
        }

        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(dateValue);
    }

}
