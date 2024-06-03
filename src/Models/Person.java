package Models;

import java.util.Date;

public class Person {
    private int id_person;
    private String last_name;
    private String cpf;
    private Date birth_date;
    private String phone_number;
    private Date registration_date;
    private Address address;  // Referência ao objeto Address
}
