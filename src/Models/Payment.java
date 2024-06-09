package Models;

public class Payment {
    private int id_pay;
    private String type;

    // Construtor
    public Payment(int id_pay, String type) {
        this.id_pay = id_pay;
        this.type = type;
    }

    // Getters e Setters
    public int getId_pay() {
        return id_pay;
    }

    public void setId_pay(int id_pay) {
        this.id_pay = id_pay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
