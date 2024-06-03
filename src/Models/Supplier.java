package Models;

import java.util.Date;

public class Supplier {
    private int id_supplier;
    private String corporate_reason;
    private String cnpj;
    private String phone_number;
    private Date registration_date;
    private Address address;  // Referência ao objeto Address
}

