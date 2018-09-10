package com.smartbear.assignment.repository;

import com.smartbear.assignment.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectoryServiceRepository extends JpaRepository<Entry, Long> {
}
