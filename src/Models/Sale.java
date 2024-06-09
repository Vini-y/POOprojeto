package Models;

import java.sql.Timestamp;
import java.sql.Date;

public class Sale {
    private int id_sale;
    private Client client; // Referência ao cliente
    private Seller seller; // Referência ao vendedor
    private Timestamp sale_date;
    private Payment payment; // Referência ao tipo de pagamento
    private float total_value;
    private int parcelas;

    // Construtor
    public Sale(int id_sale, Client client, Seller seller, Timestamp sale_date, Payment payment, float total_value, int parcelas) {
        this.id_sale = id_sale;
        this.client = client;
        this.seller = seller;
        this.sale_date = sale_date;
        this.payment = payment;
        this.total_value = total_value;
        this.parcelas = parcelas;
    }

    // Getters e Setters
    public int getId_sale() {
        return id_sale;
    }

    public void setId_sale(int id_sale) {
        this.id_sale = id_sale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Timestamp getSale_date() {
        return sale_date;
    }

    public void setSale_date(Timestamp sale_date) {
        this.sale_date = sale_date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public float getTotal_value() {
        return total_value;
    }

    public void setTotal_value(float total_value) {
        this.total_value = total_value;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }
}
