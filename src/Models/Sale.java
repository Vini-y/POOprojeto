package Models;

import java.util.Date;

public class Sale {
    private int id_sale;
    private Client client;  // Referência ao objeto Client
    private Seller seller;  // Referência ao objeto Seller
    private Date sale_date;
    private Pay payment;  // Referência ao objeto Pay
    private float total_value;
    private int parcelas;
}
