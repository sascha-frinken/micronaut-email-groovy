package example.micronaut

import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext

class SendGridEmailCondition implements Condition {

    @Override
    boolean matches(ConditionContext context) {
        (System.getProperty("sendgrid.apikey") || System.getenv("SENDGRID_APIKEY")) &&
                (System.getProperty("sendgrid.fromemail") ||  System.getenv("SENDGRID_FROM_EMAIL"))
    }
}
