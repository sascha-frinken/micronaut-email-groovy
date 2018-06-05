package example.micronaut

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton // <1>
@Requires(condition = AwsCredentialsProviderCondition) // <2>
@CompileStatic
class AwsCredentialsProviderService implements AWSCredentialsProvider {

    String accessKey
    String secretKey

    AwsCredentialsProviderService(@Value('${AWS_ACCESS_KEY_ID:none}') String accessKeyEnv, // <3>
                                  @Value('${AWS_SECRET_KEY:none}') String secretKeyEnv,
                                  @Value('${aws.accesskeyid:none}') String accessKeyProp,
                                  @Value('${aws.secretkey:none}') String secretKeyProp) {
        this.accessKey = accessKeyEnv != null && !accessKeyEnv.equals("none") ? accessKeyEnv : accessKeyProp;
        this.secretKey = secretKeyEnv != null && !secretKeyEnv.equals("none")  ? accessKeyEnv : secretKeyProp;
    }

    @Override
    AWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey)
    }

    @Override
    void refresh() {

    }
}