package academiaTecno;

public class Calificacion {
    private Evaluacion evaluacion;
    private double nota;

    public Calificacion(Evaluacion evaluacion, double nota) {
        this.evaluacion = evaluacion;
        this.nota = nota;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return evaluacion.getTitulo() + ": " + nota;
    }
}
