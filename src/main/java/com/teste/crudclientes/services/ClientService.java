package com.teste.crudclientes.services;

import com.teste.crudclientes.dto.ClientDTO;
import com.teste.crudclientes.entities.Client;
import com.teste.crudclientes.repositories.ClientRepository;
import com.teste.crudclientes.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        var client = clientRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Cliente não encontrado")
        );
        return new ClientDTO(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        var clients = clientRepository.findAll(pageable);
        return clients.map(ClientDTO::new);
    }


    @Transactional
    public ClientDTO insert(ClientDTO clientDto) {
        var entity = new Client();

        copyDtoToEntity(clientDto, entity);
        clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            // pego a entidade no banco
            var entity = clientRepository.getReferenceById(id);
            // copio os dados do dto para a entidade
            copyDtoToEntity(dto, entity);
            // salvo os novos dados da entidade e atribuo a entidade
            entity = clientRepository.save(entity);
            // retorno o dto com a entidade já atualizada
            return new ClientDTO(entity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional()
    public void delete(Long id) {
        if(!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        clientRepository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }

}
