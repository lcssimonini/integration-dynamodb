package br.com.vfs.integrationdynamodb.service;

import br.com.vfs.integrationdynamodb.errors.ConflictHostException;
import br.com.vfs.integrationdynamodb.errors.HostNotFoundException;
import br.com.vfs.integrationdynamodb.model.Host;
import br.com.vfs.integrationdynamodb.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository repository;

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
