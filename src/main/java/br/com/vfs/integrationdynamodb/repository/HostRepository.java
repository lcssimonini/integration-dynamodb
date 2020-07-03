package br.com.vfs.integrationdynamodb.repository;

import br.com.vfs.integrationdynamodb.model.Host;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface HostRepository extends CrudRepository<Host, String> {
    Optional<Host> findById(String id);
}
