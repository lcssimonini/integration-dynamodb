package br.com.vfs.integrationdynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@DynamoDBTable(tableName = "hosts")
public class Host implements Serializable {
    @DynamoDBHashKey
    private String name;
    @DynamoDBAttribute
    private String ip;
    @DynamoDBAttribute
    private int port;
}
