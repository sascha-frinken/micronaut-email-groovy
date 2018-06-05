package example.micronaut

//tag::clazzwithannotations[]
import groovy.transform.CompileStatic
import groovy.transform.ToString
import javax.validation.Validation

@ToString
@CompileStatic
@EmailConstraints // <1>
class EmailCmd implements Email {
//end::clazzwithannotations[]

    //tag::properties[]
    String recipient
    String subject
    List<String> cc = []
    List<String> bcc = []
    String htmlBody
    String textBody
    String replyTo
    //end::properties[]

    //tag::hasErrors[]
    boolean hasErrors() {
        Validation.buildDefaultValidatorFactory().validator.validate(this)
    }
    //end::hasErrors[]

}
