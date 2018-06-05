package example.micronaut

import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext

class AwsCredentialsProviderCondition implements Condition {
    @Override
    boolean matches(ConditionContext context) {
        (System.getProperty("aws.accesskeyid") || System.getenv("AWS_ACCESS_KEY_ID")) &&
                (System.getProperty("aws.secretkey") ||  System.getenv("AWS_SECRET_KEY"))
    }
}
