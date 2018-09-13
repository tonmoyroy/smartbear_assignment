package com.smartbear.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="name", nullable=false)
    //@JsonProperty(value ="name",required = true)
    private String name;

    @Size(min = 9, max = 13,  message = "Phone no. must be @JsonProperty(value = \"name\"){min} to {max} characters in length.")
    @Column(name="phone", nullable= true)
    //@JsonProperty(value ="phone")
    private String phone;

    @Email
    @Column(name = "email", nullable=false, unique = true)
    //@JsonProperty(value = "email", required = true)
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
