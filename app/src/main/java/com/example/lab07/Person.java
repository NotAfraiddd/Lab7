package com.example.lab07;

public class Person {
    private int id;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Person(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Person() {
    }
}
