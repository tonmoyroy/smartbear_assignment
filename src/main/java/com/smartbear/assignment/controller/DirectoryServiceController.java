package com.smartbear.assignment.controller;


import com.smartbear.assignment.model.Entry;
import com.smartbear.assignment.model.Response;
import com.smartbear.assignment.repository.DirectoryServiceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Directory Service Controller", produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectoryServiceController {

    @Autowired
    private DirectoryServiceRepository directoryServiceRepository;

    @GetMapping(value = "ping")
    public String ping() throws Exception {
        return "Application is working!";
    }


    @ApiOperation(value = "Returns 201 if User Created Successfully")
    @PostMapping(value = "create", produces = "application/json")
    public ResponseEntity<Response> createEntry(@Valid @RequestBody Entry entry) throws Exception {
        Entry newentry = directoryServiceRepository.save(entry);
        Response eR = new Response();
        eR.setCode(HttpStatus.CREATED.value());
        eR.setDescrition(newentry.getEmail() + " Created Successfully!");
        return new ResponseEntity<Response>(eR, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Returns User Info List")
    @GetMapping(value = "read", produces = "application/json")
    public List<Entry> readEntry() throws Exception {
        return directoryServiceRepository.findAll();
    }


    @ApiOperation(value = "Username, Phone or Email is optional in JSon body. Returns 200 if Updated Successfully")
    @PutMapping(value = "update/{email}", produces = "application/json")
    public ResponseEntity<Response> updateEntry(@PathVariable String email, @Valid @RequestBody Entry entry) throws Exception {

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
                Response eR = new Response();
                eR.setCode(HttpStatus.OK.value());
                eR.setDescrition(email + " Updated Successfully!");
                return new ResponseEntity<Response>(eR, HttpStatus.OK);
            } else
                throw new RuntimeException("Failed to update for " + email);
        } else {
            throw new RuntimeException("No Such Entry Exists with id " + email);
        }
    }

    @ApiOperation(value = "Returns 200 if User Deleted Successfully")
    @DeleteMapping(value = "delete/{email}", produces = "application/json")
    public ResponseEntity<Response> deleteEntry(@PathVariable String email) throws Exception {
        Entry item = directoryServiceRepository.findByEmail(email);
        if (!item.equals(null)) {
            directoryServiceRepository.delete(item);
            Response eR = new Response();
            eR.setCode(HttpStatus.OK.value());
            eR.setDescrition(email + " Deleted Successfully!");
            return new ResponseEntity<Response>(eR, HttpStatus.OK);
        } else
            throw new RuntimeException("No Such Entry Exists with id " + email);
    }
}
