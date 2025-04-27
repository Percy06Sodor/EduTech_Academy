package academiaTecno;

public class Material {
    private String titulo;
    private String tipo; // por ejemplo: PDF, Video, Link
    private String url;

    public Material(String titulo, String tipo, String url) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return tipo + ": " + titulo + " (" + url + ")";
    }
}
