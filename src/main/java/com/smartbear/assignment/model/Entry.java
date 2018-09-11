package com.smartbear.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "entry")
public class Entry extends DirectoryModel {
    @Id
    @GeneratedValue(generator = "entryID")
    @SequenceGenerator(
            name = "entryID",
            sequenceName = "entry_sequence",
            initialValue = 1000
    )
    @JsonIgnore
    private Long id;

    @NotBlank(message = "Enter user name")
    private String name;

    @NotBlank
    @Size(min = 9, max = 13,  message = "Phone no. must be {min} to {max} characters in length.")
    private String phone;

    @NotBlank
    @Email(message = "Enter a valid email")
    @Column(name = "email", unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
