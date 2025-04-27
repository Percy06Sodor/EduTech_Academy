package academiaTecno;

import java.util.List;

public abstract class Evaluacion {
    protected String titulo;
    protected List<Pregunta> preguntas;

    public Evaluacion(String titulo, List<Pregunta> preguntas) {
        this.titulo = titulo;
        this.preguntas = preguntas;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public abstract double evaluar(List<Integer> respuestas); // según tipo (auto/manual)
}
