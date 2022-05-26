package com.example.cognito.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Log4j2
@Configuration
public class AWSConfig {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Bean
    public CognitoIdentityProviderClient createAmazonCognito() {
        log.info("createAmazonCognito Bean");
        return CognitoIdentityProviderClient.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(awsCredentials())
                .build();
    }

    @Bean
    public AwsCredentialsProvider awsCredentials() {
        return () -> new AwsCredentials() {
			@Override
			public String accessKeyId() {
				return accessKey;
			}

			@Override
			public String secretAccessKey() {
				return secretKey;
			}
		};
    }
}
