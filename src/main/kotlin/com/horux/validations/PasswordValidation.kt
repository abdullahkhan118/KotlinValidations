package com.horux.validations

import com.horux.extensions.inRange
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Pattern
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [PasswordValidator::class])
annotation class Password(val message: String = "Invalid password", val minLength: Int = 8, val maxLength: Int = 20, val minUppercase: Int = 1, val minLowercase: Int = 1, val minDigits: Int = 1, val minSpecialChars: Int = 1, val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Any>> = [])

class PasswordValidator : ConstraintValidator<Password, String?> {
    private var minLength = 0
    private var maxLength = 0
    private var minUppercase = 0
    private var minLowercase = 0
    private var minDigits = 0
    private var minSpecialChars = 0
    override fun initialize(constraintAnnotation: Password) {
        minLength = constraintAnnotation.minLength
        maxLength = constraintAnnotation.maxLength
        minUppercase = constraintAnnotation.minUppercase
        minLowercase = constraintAnnotation.minLowercase
        minDigits = constraintAnnotation.minDigits
        minSpecialChars = constraintAnnotation.minSpecialChars
    }

    override fun isValid(password: String?, context: ConstraintValidatorContext): Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }
        if (password.inRange(minLength, maxLength)) {
            return false
        }
        var uppercaseCount = 0
        var lowercaseCount = 0
        var digitCount = 0
        var specialCharCount = 0
        for (ch in password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                uppercaseCount++
            } else if (Character.isLowerCase(ch)) {
                lowercaseCount++
            } else if (Character.isDigit(ch)) {
                digitCount++
            } else {
                specialCharCount++
            }
        }
        return uppercaseCount >= minUppercase && lowercaseCount >= minLowercase && digitCount >= minDigits && specialCharCount >= minSpecialChars &&
                pattern.matcher(password).matches()
    }

    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$)"
        private val pattern = Pattern.compile(PASSWORD_PATTERN)
    }
}




