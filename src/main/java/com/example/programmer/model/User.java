package com.example.programmer.model;

import lombok.Data;

@Data
public class User {

    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "Имя : " + name + '\'' + ", Возраст : " + age ;
    }
}

