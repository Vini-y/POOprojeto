package Models;

public class SaleItem {
    private int id_sale_item;
    private Sale sale; // Referência à venda
    private Product product; // Referência ao produto
    private int quantity;
    private float total_value;

    // Construtor
    public SaleItem(int id_sale_item, Sale sale, Product product, int quantity, float total_value) {
        this.id_sale_item = id_sale_item;
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.total_value = total_value;
    }

    // Getters e Setters
    public int getId_sale_item() {
        return id_sale_item;
    }

    public void setId_sale_item(int id_sale_item) {
        this.id_sale_item = id_sale_item;
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

    public float getTotal_value() {
        return total_value;
    }

    public void setTotal_value(float total_value) {
        this.total_value = total_value;
    }
}
