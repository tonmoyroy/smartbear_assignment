package com.smartbear.assignment.repository;

import com.smartbear.assignment.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DirectoryServiceRepository extends JpaRepository<Entry, Long> {
    Entry findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Entry SET updated_at=now(), name=:name, phone = :phone WHERE email = :email")
    int updateEntryByEmail(@Param("name") String name, @Param("phone") String phone, @Param("email") String email);
}
