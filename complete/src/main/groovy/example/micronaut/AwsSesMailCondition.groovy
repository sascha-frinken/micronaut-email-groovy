package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext

@CompileStatic
class AwsSesMailCondition implements Condition {

    @Override
    boolean matches(ConditionContext context) {
        return (System.getProperty("AWS_SOURCE_EMAIL") || System.getenv("AWS_SOURCE_EMAIL")) &&
                (System.getProperty("AWS_REGION") ||  System.getenv("AWS_REGION"))
    }
}