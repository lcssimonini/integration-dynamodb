package br.com.vfs.integrationdynamodb.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration
//@EnableDynamoDBRepositories(basePackages = "br.com.vfs.integrationdynamodb.repository")
public class DynamoDBConfigLocal {
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.region}")
    private String amazonDynamoDBRegion;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(amazonAWSCredentialsProvider())
                .withEndpointConfiguration(amazonAwsClientBuilder())
                .build();
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig());
    }

    private DynamoDBMapperConfig dynamoDBMapperConfig(){
        return DynamoDBMapperConfig.builder()
//                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
//                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
//                .withTableNameOverride(null)
//                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
                .build();
    }

    private AwsClientBuilder.EndpointConfiguration amazonAwsClientBuilder() {
        return new AwsClientBuilder
                .EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion);
    }

    private AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }
}
