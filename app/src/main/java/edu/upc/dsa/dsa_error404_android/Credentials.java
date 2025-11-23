package edu.upc.dsa.dsa_error404_android;

public class Credentials {

    private String nombre;
    private String password;
    private String email; // 1. Nuevo campo añadido

    public Credentials() {
    }

    // 2. Constructor actualizado para incluir el email
    public Credentials(String nombre, String password, String email) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 3. Nuevos Getter y Setter para el email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
