package academiaTecno;

import java.util.List;

public class Pregunta {
    private String enunciado;
    private List<String> opciones;
    private int respuestaCorrecta; // índice en la lista de opciones

    public Pregunta(String enunciado, List<String> opciones, int respuestaCorrecta) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public boolean esCorrecta(int indiceSeleccionado) {
        return this.respuestaCorrecta == indiceSeleccionado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(enunciado + "\n");
        for (int i = 0; i < opciones.size(); i++) {
            sb.append(i).append(". ").append(opciones.get(i)).append("\n");
        }
        return sb.toString();
    }
}
