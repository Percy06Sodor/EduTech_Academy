package academiaTecno;

import java.util.ArrayList;
import java.util.List;

class Tema {

	private int id;
    private String titulo;
    private List<Mensaje> respuestas;

    public Tema(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.respuestas = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public List<Mensaje> getRespuestas() { return respuestas; }

    public void agregarRespuesta(Mensaje mensaje) {
        this.respuestas.add(mensaje);
    }

    public String obtenerMensajesFormateados() {
        String mensajesStr = "";
        if (respuestas.isEmpty()) {
            mensajesStr = "  (No hay respuestas en este tema aún)";
        } else {
            for (Mensaje msg : respuestas) {
                mensajesStr += msg.toString() + "\n";
            }
        }
        return mensajesStr;
    }

    public String toString() {
        return "ID: " + id + " - Título: " + titulo + " (" + respuestas.size() + " respuestas)";
    }
}
