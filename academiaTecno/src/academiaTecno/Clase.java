package academiaTecno;

import java.util.ArrayList;
import java.util.List;

public class Clase {
    private int numero;
    private String contenido;
    private MiniDesafio miniDesafio;
    private List<Material> materiales;

    public Clase(int numero, String contenido) {
        this.numero = numero;
        this.contenido = contenido;
        this.materiales = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public String getContenido() {
        return contenido;
    }

    public void asignarMiniDesafio(MiniDesafio miniDesafio) {
        this.miniDesafio = miniDesafio;
    }

    public MiniDesafio getMiniDesafio() {
        return miniDesafio;
    }

    public void agregarMaterial(Material material) {
        materiales.add(material);
    }

    public List<Material> getMateriales() {
        return materiales;
    }

    @Override
    public String toString() {
        return "Clase " + numero + ": " + contenido;
    }
}
