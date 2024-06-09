package Models;

import java.util.Date;

public class Supplier {

    private String name;
    private String cnpj;
    private Date registration_date;
    private Address address; // Referência ao objeto Address
    private User user; // Referência ao objeto User

    // Construtor
    public Supplier( String name, String cnpj, Date registration_date, Address address, User user) {

        this.name = name;
        this.cnpj = cnpj;
        this.registration_date = registration_date;
        this.address = address;
        this.user = user;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
