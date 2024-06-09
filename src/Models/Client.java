package Models;

public class Client {
    private Person person; // Referência ao objeto Person

    // Construtor
    public Client(Person person) {
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
