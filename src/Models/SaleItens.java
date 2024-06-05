package Models;

public class SaleItens {
    private int id_sale_itens;
    private Sale sale;  // Referência ao objeto Sale
    private Product product;  // Referência ao objeto Product
    private int quantity;

    public int getId_sale_itens() {
        return id_sale_itens;
    }

    public void setId_sale_itens(int id_sale_itens) {
        this.id_sale_itens = id_sale_itens;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

