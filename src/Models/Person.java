package Models;

import java.util.Date;


public class Person {
    private String last_name;
    private String cpf;
    private Date birth_date;
    private Date registration_date;
    private String phone_number;
    private Address address;
    private User user;

    // Construtor
    public Person(String last_name, String cpf, Date birth_date,Date registration_date, String phone_number, Address address, User user) {
        this.last_name = last_name;
        this.cpf = cpf;
        this.birth_date = birth_date;
        this.registration_date = registration_date;
        this.phone_number = phone_number;
        this.address = address;
        this.user = user;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
