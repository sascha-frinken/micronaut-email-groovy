package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext

@CompileStatic
class AwsSesMailCondition implements Condition {

    @Override
    boolean matches(ConditionContext context) {
        (System.getProperty("aws.sourceemail") || System.getenv("AWS_SOURCE_EMAIL")) &&
                (System.getProperty("aws.region") ||  System.getenv("AWS_REGION"))
    }
}