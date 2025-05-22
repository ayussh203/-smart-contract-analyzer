package com.example.contractanalyzer.repository;

import com.example.contractanalyzer.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {}
