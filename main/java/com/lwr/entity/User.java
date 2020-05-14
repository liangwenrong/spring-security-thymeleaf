package com.lwr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user1")
public class User {
    @Id
    @Column(length = 20)
//    @GeneratedValue(strategy=GenerationType.AUTO)
    public String id;
    
    @Column(name = "name", length = 20)
    public String name;
    
    @Column(name = "password", length = 20)
    public String password;
    
    @Transient
    public Integer gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", gender=" + gender + "]";
    }


}
