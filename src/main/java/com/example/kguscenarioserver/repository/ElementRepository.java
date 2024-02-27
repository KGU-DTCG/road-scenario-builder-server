package com.example.kguscenarioserver.repository;

import com.example.kguscenarioserver.entity.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepository extends JpaRepository<Element,Long> {
}
