package example.micronaut

//tag::clazzwithannotations[]
import groovy.transform.CompileStatic
import groovy.transform.ToString
import javax.validation.Validation
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ToString
@CompileStatic
@EmailConstraints // <1>
class EmailCmd implements Email {
//end::clazzwithannotations[]

    //tag::properties[]
    @NotNull
    @NotBlank
    String recipient

    @NotNull
    @NotBlank
    String subject

    List<String> cc = []
    List<String> bcc = []
    String htmlBody
    String textBody
    String replyTo
    //end::properties[]

}
