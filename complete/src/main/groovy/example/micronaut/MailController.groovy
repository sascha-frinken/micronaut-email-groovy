package example.micronaut

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Slf4j
@CompileStatic
@Controller('/mail') // <1>
@Validated // <2>
class MailController {

    EmailService emailService

    MailController( EmailService  emailService) { // <3>
        this.emailService = emailService
    }


    @Post('/send') // <4>
    HttpResponse send(@Body @Valid EmailCmd cmd) { // <5>
        log.info '{}', cmd.toString()
        emailService.send(cmd)
        HttpResponse.ok()  // <6>
    }
}