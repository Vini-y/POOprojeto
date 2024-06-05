package Models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Sale {
    private int id_sale;
    private Client client;  // Referência ao objeto Client
    private Seller seller;  // Referência ao objeto Seller
    private Date sale_date;
    private ArrayList <SaleItens> itens;
    private Pay payment;  // Referência ao objeto Pay
    private float total_value;
    private int parcelas;

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

    public Date getSale_date() {
        return sale_date;
    }

    public void setSale_date(Date sale_date) {
        this.sale_date = sale_date;
    }

    public ArrayList<SaleItens> getItens() {
        return itens;
    }

    public void setItens(ArrayList<SaleItens> itens) {
        this.itens = itens;
    }

    public Pay getPayment() {
        return payment;
    }

    public void setPayment(Pay payment) {
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
