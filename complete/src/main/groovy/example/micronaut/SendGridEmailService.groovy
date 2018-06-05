package example.micronaut
import com.sendgrid.Personalization
import com.sendgrid.Content
import com.sendgrid.Mail
import com.sendgrid.SendGrid
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.Method
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton // <1>
@Requires(condition = SendGridEmailCondition) // <2>
@Slf4j
@CompileStatic
class SendGridEmailService implements EmailService {

    String apiKey

    String fromEmail

    SendGridEmailService(@Value('${SENDGRID_APIKEY:faa}') String apiKey, // <3>
                         @Value('${SENDGRID_FROM_EMAIL:foo}') String fromEmail) {
        this.apiKey = apiKey
        this.fromEmail = fromEmail
    }


    protected Content contentOfEmail(Email email) {
        if ( email.textBody ) {
            return new Content("text/plain", email.textBody)
        }
        if ( email.htmlBody ) {
            return new Content("text/html", email.htmlBody)
        }
        return null
    }

    @Override
    void send(Email email) {

        Personalization personalization = new Personalization()
        personalization.subject = email.subject

        com.sendgrid.Email to = new com.sendgrid.Email(email.recipient)
        personalization.addTo(to)

        if ( email.getCc() ) {
            for ( String cc : email.getCc() ) {
                com.sendgrid.Email ccEmail = new com.sendgrid.Email()
                ccEmail.email = cc
                personalization.addCc(ccEmail)
            }
        }

        if ( email.getBcc() ) {
            for ( String bcc : email.getBcc() ) {
                com.sendgrid.Email bccEmail = new com.sendgrid.Email()
                bccEmail.email = bcc
                personalization.addBcc(bccEmail)
            }
        }

        Mail mail = new Mail()
        com.sendgrid.Email from = new com.sendgrid.Email()
        from.email = fromEmail
        mail.from = from
        mail.addPersonalization(personalization)
        Content content = contentOfEmail(email)
        mail.addContent(content)

        SendGrid sg = new SendGrid(apiKey)
        Request request = new Request()
        try {
            request.with {
                method = Method.POST
                endpoint = "mail/send"
                body = mail.build()
            }
            Response response = sg.api(request)
            log.info("Status Code: {}", String.valueOf(response.getStatusCode()))
            log.info("Body: {}", response.getBody())
            if ( log.infoEnabled ) {
                response.getHeaders().each { String k, String v ->
                    log.info("Response Header {} => {}", k, v)
                }
            }

        } catch (IOException ex) {
            log.error(ex.getMessage())
        }
    }
}