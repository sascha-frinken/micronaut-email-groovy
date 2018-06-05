package example.micronaut
import com.amazonaws.services.simpleemail.model.SendEmailResult
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.Message
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import javax.inject.Singleton


@Singleton // <1>
@Requires(beans = AwsCredentialsProviderService) // <2>
@Requires(condition = AwsSesMailCondition)  // <3>
@Primary // <4>
@Slf4j
@CompileStatic
class AwsSesMailService implements EmailService {

    String awsRegion

    String sourceEmail

    AwsCredentialsProviderService awsCredentialsProviderService

    AwsSesMailService(@Value('${AWS_REGION:none}') String awsRegionEnv, // <5>
                      @Value('${AWS_SOURCE_EMAIL:none}') String sourceEmailEnv,
                      @Value('${aws.region:none}') String awsRegionProp,
                      @Value('${aws.sourceemail:none}') String sourceEmailProp,
                      AwsCredentialsProviderService awsCredentialsProviderService
    ) {
        this.awsRegion = awsRegionEnv != null && !awsRegionEnv.equals("none") ? awsRegionEnv : awsRegionProp
        this.sourceEmail = sourceEmailEnv != null && !sourceEmailEnv.equals("none") ? sourceEmailEnv : sourceEmailProp
        this.awsCredentialsProviderService = awsCredentialsProviderService
    }

    private Body bodyOfEmail(Email email) {
        if (email.htmlBody) {
            Content htmlBody = new Content().withData(email.htmlBody)
            return new Body().withHtml(htmlBody)
        }
        if (email.textBody) {
            Content textBody = new Content().withData(email.textBody)
            return new Body().withHtml(textBody)
        }
        new Body()
    }

    @Override
    void send(Email email) {

        if ( !awsCredentialsProviderService ) {
            log.warn("AWS Credentials provider not configured")
            return
        }

        Destination destination = new Destination().withToAddresses(email.recipient)
        if ( email.getCc() ) {
            destination = destination.withCcAddresses(email.getCc())
        }
        if ( email.getBcc() ) {
            destination = destination.withBccAddresses(email.getBcc())
        }
        Content subject = new Content().withData(email.getSubject())
        Body body = bodyOfEmail(email)
        Message message = new Message().withSubject(subject).withBody(body)

        SendEmailRequest request = new SendEmailRequest()
                .withSource(sourceEmail)
                .withDestination(destination)
                .withMessage(message)

        if ( email.getReplyTo() ) {
            request = request.withReplyToAddresses()
        }

        try {
            log.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...")

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(awsCredentialsProviderService)
                    .withRegion(awsRegion)
                    .build()

            SendEmailResult sendEmailResult = client.sendEmail(request)
            log.info("Email sent! {}", sendEmailResult.toString())

        } catch (Exception ex) {
            log.warn("The email was not sent.")
            log.warn("Error message: {}", ex.message)
        }
    }
}