package br.com.vfs.integrationdynamodb.service;

import br.com.vfs.integrationdynamodb.errors.ConflictHostException;
import br.com.vfs.integrationdynamodb.errors.HostNotFoundException;
import br.com.vfs.integrationdynamodb.model.Host;
import br.com.vfs.integrationdynamodb.repository.HostRepository;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostService {

    private static final Set<Host> INITIAL_HOSTS = Set.of(
            Host.builder().name("vinicius1").ip("127.0.0.1").port(80).build(),
        Host.builder().name("vinicius2").ip("127.0.0.2").port(80).build(),
        Host.builder().name("vinicius3").ip("127.0.0.3").port(443).build(),
        Host.builder().name("vinicius4").ip("127.0.0.4").port(443).build(),
        Host.builder().name("vinicius5").ip("10.0.0.1").port(80).build(),
        Host.builder().name("vinicius6").ip("10.0.0.2").port(80).build(),
        Host.builder().name("vinicius7").ip("10.0.0.3").port(80).build(),
        Host.builder().name("vinicius8").ip("10.0.0.4").port(443).build());
    private final HostRepository repository;
    private final AmazonDynamoDB amazonDynamoDB;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void createTable() throws InterruptedException {
        final var dynamoDB = new DynamoDB(amazonDynamoDB);
        final var attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("name").withAttributeType("S"));
        final var keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH));
        //exclude table
        final var existingTable = dynamoDB.getTable("hosts");
        if(Objects.nonNull(existingTable)){
            existingTable.delete();
            existingTable.waitForDelete();
        }
        //include table
        final var request = new CreateTableRequest()
                .withTableName("hosts")
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(5L));

        final var table = dynamoDB.createTable(request);
        table.waitForActive();
        //include registers
        INITIAL_HOSTS.forEach(host -> {
                    try {
                        table.putItem(Item.fromJSON(objectMapper.writeValueAsString(host)));
                    } catch (JsonProcessingException e) {
                        log.error("error ao converter host", e);
                    }
                });
    }

    public Set<Host> findAll(){
        final var set = new HashSet<Host>();
        repository.findAll().forEach(set::add);
        return set;
    }

    public Host find(final String name){
        return repository.findById(name)
                .orElseThrow(()-> new HostNotFoundException("host not found"));
    }

    public Host insert(final Host host){
        if(findAll().contains(host))
            throw new ConflictHostException("new host existed");
        return repository.save(host);
    }

    public void update(String name, final Host host){
        final Host exist = find(name);
        exist.setName(host.getName());
        exist.setIp(host.getIp());
        exist.setPort(host.getPort());
        repository.save(exist);
    }

    public void delete(final String name){
        final Host host = find(name);
        repository.delete(host);
    }
}
