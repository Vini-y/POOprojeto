package Models;

public class Seller {
    private Person person; // Referência ao objeto Person

    // Construtor
    public Seller(Person person) {
        this.person = person;
    }

    // Getter e Setter
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
