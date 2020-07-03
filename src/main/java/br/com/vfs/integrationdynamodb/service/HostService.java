package br.com.vfs.integrationdynamodb.service;

import br.com.vfs.integrationdynamodb.errors.ConflictHostException;
import br.com.vfs.integrationdynamodb.errors.HostNotFoundException;
import br.com.vfs.integrationdynamodb.model.Host;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class HostService {

    private final Set<Host> hosts = new HashSet<>();

    @PostConstruct
    public void createBasicHosts(){
        hosts.add(Host.builder().name("vinicius1").ip("127.0.0.1").port(80).build());
        hosts.add(Host.builder().name("vinicius2").ip("127.0.0.2").port(80).build());
        hosts.add(Host.builder().name("vinicius3").ip("127.0.0.3").port(443).build());
        hosts.add(Host.builder().name("vinicius4").ip("127.0.0.4").port(443).build());
        hosts.add(Host.builder().name("vinicius5").ip("10.0.0.1").port(80).build());
        hosts.add(Host.builder().name("vinicius6").ip("10.0.0.2").port(80).build());
        hosts.add(Host.builder().name("vinicius7").ip("10.0.0.3").port(80).build());
        hosts.add(Host.builder().name("vinicius8").ip("10.0.0.4").port(443).build());
    }

    public Set<Host> findAll(){
        return hosts;
    }

    public Host find(final String name){
        return hosts
                .stream()
                .filter(host -> host.getName().equals(name))
                .findFirst()
                .orElseThrow(()-> new HostNotFoundException("host not found"));
    }

    public Host insert(final Host host){
        if(hosts.contains(host))
            throw new ConflictHostException("new host existed");
        hosts.add(host);
        return host;
    }

    public void update(String name, final Host host){
        if(hosts.stream().map(Host::getName).noneMatch(name::equals))
            throw new HostNotFoundException("host not found");
        hosts.add(host);
    }

    public void delete(final String name){
        final Host host = find(name);
        hosts.remove(host);
    }
}
