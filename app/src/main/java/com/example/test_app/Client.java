package com.example.test_app;

public class Client {
    public String id;
    public String name;
    public String gender;
    public String birthDate;

    public Client() { // default constructor in order to use it store value- will be used later.

    }

    public Client( String name, String birthDate,String gender,String id) { // default constructor
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
    }
    public String toString(){
        return "Name: "+this.name + " Gender: "+this.gender+" Birthdate: "+this.birthDate;
    }

}
