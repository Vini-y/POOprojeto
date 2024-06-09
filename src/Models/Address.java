package Models;

public class Address {
    private int address_id;
    private String city;
    private String state;
    private String country;
    private String address;
    private String address_number;

    // Construtor
    public Address(String city, String state, String country, String address, String address_number) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.address_number = address_number;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_number() {
        return address_number;
    }

    public void setAddress_number(String address_number) {
        this.address_number = address_number;
    }
}
