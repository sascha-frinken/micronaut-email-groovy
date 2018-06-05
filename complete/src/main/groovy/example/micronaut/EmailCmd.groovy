package example.micronaut

import groovy.transform.CompileStatic
import groovy.transform.ToString

import javax.validation.Validation

@ToString
@CompileStatic
@EmailConstraints // <1>
class EmailCmd implements Email {

    String recipient

    String subject

    List<String> cc = []
    List<String> bcc = []

    String htmlBody
    String textBody
    String replyTo

    boolean hasErrors() {
        Validation.buildDefaultValidatorFactory().validator.validate(this)
    }
}
