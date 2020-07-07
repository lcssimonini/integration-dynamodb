package br.com.vfs.integrationdynamodb.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.amazonaws.regions.Regions.US_EAST_1;

@Profile("aws")
@Configuration
public class DynamoDBConfigAws {

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(US_EAST_1)
                .build();
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

}
