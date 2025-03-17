package com.ex.api_clientes.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ex.api_clientes.model.Cliente;
import com.ex.api_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<ClienteFormRequest> salvar(@RequestBody ClienteFormRequest request) {

        Cliente cliente = request.toModel();

        clienteRepository.save(cliente);

        System.out.println(cliente);

        return ResponseEntity.ok(ClienteFormRequest.fromModel(cliente));
    }

    @GetMapping
    public List<ClienteFormRequest> getLista(){
        return clienteRepository.findAll().stream().map( ClienteFormRequest::fromModel ).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClienteFormRequest> getById(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(ClienteFormRequest::fromModel)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody ClienteFormRequest request) {

        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = request.toModel();
        cliente.setId(id);
        clienteRepository.save(cliente);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> atualizarParcialmente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = clienteExistente.get();

        updates.forEach((campo, valor) -> {
            switch (campo) {
                case "nome" -> cliente.setNome((String) valor);
                case "email" -> cliente.setEmail((String) valor);
                case "telefone" -> cliente.setTelefone((String) valor);
                case "endereco" -> cliente.setEndereco((String) valor);
                case "cpf" -> cliente.setCpf((String) valor);
                case "nascimento" -> cliente.setNascimento(LocalDate.parse((String) valor));
            }
        });

        clienteRepository.save(cliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClienteFormRequest>> getByName(@PathVariable String nome) {
        List<Cliente> clientes = clienteRepository.findByNomeContainingIgnoreCase(nome);
        List<ClienteFormRequest> clientesResponse = clientes.stream()
                .map(ClienteFormRequest::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientesResponse);
    }
}
