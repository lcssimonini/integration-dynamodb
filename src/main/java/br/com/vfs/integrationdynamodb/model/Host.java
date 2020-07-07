package br.com.vfs.integrationdynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "hosts")
public class Host implements Serializable {
    @DynamoDBHashKey(attributeName = "name")
    private String name;
    @DynamoDBRangeKey(attributeName = "ip")
    private String ip;
    @DynamoDBAttribute
    private int port;
}
