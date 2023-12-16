package com.horux.validations

import com.horux.extensions.isImage
import com.horux.extensions.isNullOrEmpty
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.io.File
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ImageFileValidator::class])
annotation class ImageFile(val message: String = "Invalid image file. Only PNG, JPG, and JPEG files are allowed.",
                           val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Any>> = [])

class ImageFileValidator : ConstraintValidator<ImageFile?, File?> {
    override fun initialize(constraintAnnotation: ImageFile?) {}
    override fun isValid(file: File?, context: ConstraintValidatorContext): Boolean {
        if (file.isNullOrEmpty()) {
            return true // Allow null or empty files (you can adjust this behavior based on your use case)
        }
        return file.isImage()
    }
}



