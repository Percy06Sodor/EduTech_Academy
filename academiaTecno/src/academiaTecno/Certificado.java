package academiaTecno;

import java.time.LocalDate;

public class Certificado {
    private Curso curso;
    private LocalDate fechaEmision;

    public Certificado(Curso curso) {
        this.curso = curso;
        this.fechaEmision = LocalDate.now();
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    @Override
    public String toString() {
        return "Certificado de: " + curso.getNombre() + " (emitido: " + fechaEmision + ")";
    }
}
