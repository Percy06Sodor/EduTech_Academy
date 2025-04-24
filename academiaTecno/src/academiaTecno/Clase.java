package academiaTecno;

public class Clase {
    private int numero;
    private String contenido;

    public Clase(int numero, String contenido) {
        this.numero = numero;
        this.contenido = contenido;
    }

    public int getNumero() {
        return numero;
    }

    public String getContenido() {
        return contenido;
    }

    @Override
    public String toString() {
        return "Clase " + numero + ": " + contenido;
    }
}
