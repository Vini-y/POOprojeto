package Models;

public class User {
    private int id_user;
    private String name;
    private String email;
    private String senha;

    public User(int id_user,String name, String email, String senha) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.senha = senha;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
