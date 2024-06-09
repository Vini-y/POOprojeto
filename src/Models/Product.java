package Models;

public class Product {
    private int id_product;
    private String description;
    private int quantity;
    private float price;
    private Supplier supplier; // Referência ao objeto Supplier

    // Construtor
    public Product( String description, int quantity, float price, Supplier supplier) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
    }

    // Getters e Setters
    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
