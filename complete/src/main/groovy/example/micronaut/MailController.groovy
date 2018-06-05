package example.micronaut

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Slf4j
@CompileStatic
@Controller('/mail') // <1>
class MailController {

    EmailService emailService

    MailController( EmailService  emailService) { // <2>
        this.emailService = emailService
    }

    @Post('/send') // <3>
    HttpResponse send(@Body EmailCmd cmd) {
        if ( cmd.hasErrors() ) {
            return HttpResponse.badRequest()
        }
        log.info '{}', cmd.toString()
        emailService.send(cmd)
        HttpResponse.ok()  // <5>
    }
}