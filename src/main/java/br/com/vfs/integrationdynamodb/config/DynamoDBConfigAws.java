package br.com.vfs.integrationdynamodb.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.amazonaws.regions.Regions.US_EAST_1;

@Profile("aws")
@Configuration
@EnableDynamoDBRepositories(basePackages = "br.com.vfs.integrationdynamodb.repository")
public class DynamoDBConfigAws {

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(US_EAST_1)
                .build();
    }
}
