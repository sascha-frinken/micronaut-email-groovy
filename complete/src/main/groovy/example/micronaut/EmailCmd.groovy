package example.micronaut

import groovy.transform.CompileStatic
import groovy.transform.ToString
import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ToString
@CompileStatic
//tag::clazzwithannotations[]
@Introspected
@EmailConstraints
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
