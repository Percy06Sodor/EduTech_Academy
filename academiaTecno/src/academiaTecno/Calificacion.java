package academiaTecno;

public class Calificacion {
    private String nombreEvaluacion;
    private double nota;

    public Calificacion(String nombreEvaluacion, double nota) {
        this.nombreEvaluacion = nombreEvaluacion;
        this.nota = nota;
    }

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return nombreEvaluacion + ": " + nota;
    }
}
