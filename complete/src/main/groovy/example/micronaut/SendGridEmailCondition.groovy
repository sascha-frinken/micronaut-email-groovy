package example.micronaut

import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext

class SendGridEmailCondition implements Condition {

    @Override
    boolean matches(ConditionContext context) {
        return (System.getProperty("SENDGRID_APIKEY") || System.getenv("SENDGRID_APIKEY")) &&
                (System.getProperty("SENDGRID_FROM_EMAIL") ||  System.getenv("SENDGRID_FROM_EMAIL"))
    }
}
