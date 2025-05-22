package com.example.contractanalyzer.service;

import com.example.contractanalyzer.dto.ContractDto;
import com.example.contractanalyzer.model.Contract;
import com.example.contractanalyzer.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository repo;

    public ContractService(ContractRepository repo) {
        this.repo = repo;
    }

    public List<ContractDto> list() {
        return repo.findAll()
                   .stream()
                   .map(this::toDto)
                   .toList();
    }

    @Transactional
    public ContractDto create(ContractDto dto) {
        Contract saved = repo.save(toEntity(dto));
        return toDto(saved);
    }

    @Transactional
    public ContractDto update(Long id, ContractDto dto) {
        Contract existing = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contract", id));
        existing.setTitle(dto.getTitle());
        existing.setBody(dto.getBody());
        Contract updated = repo.save(existing);
        return toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Contract", id);
        }
        repo.deleteById(id);
    }

    // --- mapping helpers ---
    private ContractDto toDto(Contract c) {
        ContractDto dto = new ContractDto();
        dto.setId(c.getId());
        dto.setTitle(c.getTitle());
        dto.setBody(c.getBody());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }

    private Contract toEntity(ContractDto dto) {
        Contract c = new Contract();
        c.setTitle(dto.getTitle());
        c.setBody(dto.getBody());
        // createdAt is auto-set in the entity
        return c;
    }
}
