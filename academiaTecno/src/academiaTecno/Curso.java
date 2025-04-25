package academiaTecno;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nombre;
    private String tema;
    private List<Clase> clases;
    private Examen examenFinal;
    private List<Material> materialesGenerales;

    public Curso(String nombre, String tema) {
        this.nombre = nombre;
        this.tema = tema;
        this.clases = new ArrayList<>();
        this.materialesGenerales = new ArrayList<>();
    }

    public void agregarClase(Clase clase) {
        clases.add(clase);
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void asignarExamenFinal(Examen examen) {
        this.examenFinal = examen;
    }

    public Examen getExamenFinal() {
        return examenFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTema() {
        return tema;
    }

    public void agregarMaterialGeneral(Material material) {
        materialesGenerales.add(material);
    }

    public List<Material> getMaterialesGenerales() {
        return materialesGenerales;
    }

    @Override
    public String toString() {
        return nombre + " (" + tema + ")";
    }
}