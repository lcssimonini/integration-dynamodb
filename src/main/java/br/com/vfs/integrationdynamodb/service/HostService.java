package br.com.vfs.integrationdynamodb.service;

import br.com.vfs.integrationdynamodb.errors.ConflictHostException;
import br.com.vfs.integrationdynamodb.errors.HostNotFoundException;
import br.com.vfs.integrationdynamodb.model.Host;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostService {

    private final DynamoDBMapper mapper;

    public Set<Host> findAll(){
        return new HashSet<>(mapper.scan(Host.class, new DynamoDBScanExpression()));
    }

    public Host find(final String name, final String ip) {
        return Optional.ofNullable(mapper.load(Host.class, name, ip))
                .orElseThrow(() -> new HostNotFoundException("host not found"));
    }

    public Set<Host> findByName(final String name) {
        final var expression = new DynamoDBQueryExpression<Host>().withHashKeyValues(Host.builder().name(name).build());
        final var query = mapper.query(Host.class, expression);
        return new HashSet<>(query);
    }

    public Set<Host> findByIp(final String ip) {
        final var eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(ip));
        final var expression = new DynamoDBScanExpression().withFilterExpression("ip = :val1").withExpressionAttributeValues(eav);
        final var scan = mapper.scan(Host.class, expression);
        return new HashSet<>(scan);
    }

    public Host insert(final Host host){
        if(findAll().contains(host))
            throw new ConflictHostException("new host existed");
        mapper.save(host);
        return host;
    }

    public void update(final String name, final String ip, final Host host){
        final Host exist = find(name, ip);
        exist.setName(host.getName());
        exist.setIp(host.getIp());
        exist.setPort(host.getPort());
        mapper.save(exist);
    }

    public void delete(final String name, final String ip){
        final Host host = find(name, ip);
        mapper.delete(host);
    }
}
