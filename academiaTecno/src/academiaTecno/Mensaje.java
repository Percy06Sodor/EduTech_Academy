package academiaTecno;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Mensaje {

	private int id;
    private String contenido;
    private LocalDate fecha;
    private Usuario autor;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Mensaje(int id, String contenido, LocalDate fecha, Usuario autor) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
    }

    public int getId() { return id; }
    public String getContenido() { return contenido; }
    public LocalDate getFecha() { return fecha; }
    public Usuario getAutor() { return autor; }


    public String toString() {
        return "  [" + fecha.format(FORMATO_FECHA) + "] "
               + autor.getNombre() + " " + autor.getApellido()
               + " (" + autor.getClass().getSimpleName() + "):"
               + "\n    "
               + contenido;
    }
}

