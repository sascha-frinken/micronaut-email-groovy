package example.micronaut


import io.micronaut.context.annotation.Factory
import io.micronaut.validation.validator.constraints.ConstraintValidator

import javax.inject.Singleton

@Factory
class EmailConstraintsFactory {

    @Singleton
    ConstraintValidator<EmailConstraints, EmailCmd> emailBodyValidator() {
        return { value, annotationMetadata, context ->
            value && (value.textBody || value.htmlBody)
        }
    }
}
