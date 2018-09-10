package com.smartbear.assignment.controller;


import com.smartbear.assignment.exception.ResourceNotFoundException;
import com.smartbear.assignment.model.Entry;
import com.smartbear.assignment.repository.DirectoryServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DirectoryServiceController {

    @Autowired
    private DirectoryServiceRepository directoryServiceRepository;

    @GetMapping("/ping")
    public String ping() throws Exception{

        return "Application is working!";
    }


    @PostMapping("create")
    @Secured("USER")
    public ResponseEntity<Entry> createEntry(@Valid @RequestBody Entry entry) throws Exception {
        Entry newentry = directoryServiceRepository.save(entry);
        return new ResponseEntity<Entry>(entry, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public Entry updateEntry(@PathVariable Long entryid, @Valid @RequestBody Entry entry) throws Exception{

        return directoryServiceRepository.findById(entryid).map(var -> {
            var.setName(entry.getName());
            var.setPhone(entry.getPhone());
            var.setEmail(entry.getEmail());
            return directoryServiceRepository.save(var);
        }).orElseThrow(() -> new ResourceNotFoundException("No Such Entry Exists with id " + entryid));
    }
}
