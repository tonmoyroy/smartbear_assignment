package com.smartbear.assignment.controller;


import com.smartbear.assignment.model.Entry;
import com.smartbear.assignment.repository.DirectoryServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DirectoryServiceController {

    @Autowired
    private DirectoryServiceRepository directoryServiceRepository;

    @GetMapping("ping")
    public String ping() throws Exception {

        return "Application is working!";
    }


    @PostMapping("create")
    @Secured("USER")
    public ResponseEntity<Entry> createEntry(@Valid @RequestBody Entry entry) throws Exception {
        Entry newentry = directoryServiceRepository.save(entry);
        return new ResponseEntity<Entry>(newentry, HttpStatus.OK);
    }


    @GetMapping("read")
    public List<Entry> readEntry() throws Exception {
        return directoryServiceRepository.findAll();
    }


    @PutMapping("update/{email}")
    public ResponseEntity<?> updateEntry(@PathVariable String email, @Valid @RequestBody Entry entry) throws Exception {

        Entry oldEntry = directoryServiceRepository.findByEmail(email);
        if (oldEntry != null) {
            if (entry.getName() == null) {
                entry.setName(oldEntry.getName());
            }

            if (entry.getPhone() == null) {
                entry.setPhone(oldEntry.getPhone());
            }

            int status = directoryServiceRepository.updateEntryByEmail(entry.getName(), entry.getPhone(), email);
            if (status == 1) {
                return ResponseEntity.status(HttpStatus.OK).body(email + " Updated Successfully!");
            } else
                throw new RuntimeException("Failed to update for " + email);
        }else{
            throw new RuntimeException("No Such Entry Exists with id " + email);
        }
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<?> deleteEntry(@PathVariable String email) throws Exception {
        Entry item = directoryServiceRepository.findByEmail(email);
        if (!item.equals(null)) {
            directoryServiceRepository.delete(item);
            return ResponseEntity.status(HttpStatus.OK).body(email + " Deleted Successfully!");
        } else
            throw new RuntimeException("No Such Entry Exists with id " + email);
    }
}
