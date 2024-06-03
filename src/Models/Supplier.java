package Models;

import java.util.Date;

public class Supplier {
    private int id_supplier;
    private String corporate_reason;
    private String cnpj;
    private String phone_number;
    private Date registration_date;
    private Address address;  // Referência ao objeto Address

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getCorporate_reason() {
        return corporate_reason;
    }

    public void setCorporate_reason(String corporate_reason) {
        this.corporate_reason = corporate_reason;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
}

