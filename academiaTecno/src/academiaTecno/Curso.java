package academiaTecno;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nombre;
    private String tema;
    private List<Clase> clases;

    public Curso(String nombre, String tema) {
        this.nombre = nombre;
        this.tema = tema;
        this.clases = new ArrayList<>();
    }

    public void agregarClase(Clase clase) {
        clases.add(clase);
    }

    public List<Clase> getClases() {
        return clases;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTema() {
        return tema;
    }

    @Override
    public String toString() {
        return nombre + " (" + tema + ")";
    }
}
