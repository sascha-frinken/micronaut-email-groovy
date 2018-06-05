package example.micronaut

import groovy.transform.CompileStatic

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@CompileStatic
class EmailConstraintsValidator implements ConstraintValidator<EmailConstraints, EmailCmd> {

    @Override
    boolean isValid(EmailCmd email, ConstraintValidatorContext context) {
        email && (email.textBody || email.htmlBody) && email.subject && email.recipient
    }
}
