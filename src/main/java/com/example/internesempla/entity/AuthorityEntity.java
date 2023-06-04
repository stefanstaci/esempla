package com.example.internesempla.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "authority")
public class AuthorityEntity {
    @Id
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    public AuthorityEntity() {
    }

    public AuthorityEntity(String name, Collection<UserEntity> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }
}
