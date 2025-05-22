package com.example.contractanalyzer.api;

import com.example.contractanalyzer.dto.ContractDto;
import com.example.contractanalyzer.service.ContractService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService service;

    public ContractController(ContractService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','LEGAL','ADMIN')")
    public List<ContractDto> list() {
        return service.list();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('LEGAL','ADMIN')")
    public ContractDto create(@RequestBody ContractDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LEGAL','ADMIN')")
    public ContractDto update(@PathVariable Long id,
                              @RequestBody ContractDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LEGAL','ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
