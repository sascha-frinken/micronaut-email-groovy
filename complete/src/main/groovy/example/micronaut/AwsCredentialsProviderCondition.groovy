package example.micronaut

import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext

class AwsCredentialsProviderCondition implements Condition {
    @Override
    boolean matches(ConditionContext context) {
        return (System.getProperty("AWS_ACCESS_KEY_ID") || System.getenv("AWS_ACCESS_KEY_ID")) &&
                (System.getProperty("AWS_SECRET_KEY") ||  System.getenv("AWS_SECRET_KEY"))
    }
}
