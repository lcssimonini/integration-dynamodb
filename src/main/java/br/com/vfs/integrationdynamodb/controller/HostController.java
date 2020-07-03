package br.com.vfs.integrationdynamodb.controller;

import br.com.vfs.integrationdynamodb.model.Host;
import br.com.vfs.integrationdynamodb.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/hosts")
@RequiredArgsConstructor
public class HostController {

    private final HostService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Host> findAll(){
        return service.findAll();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Host find(@PathVariable final String name){
        return service.find(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Host insert(@RequestBody final Host host){
        return service.insert(host);
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable final String name, @RequestBody final Host host){
        service.update(name, host);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String name){
        service.delete(name);
    }
}
