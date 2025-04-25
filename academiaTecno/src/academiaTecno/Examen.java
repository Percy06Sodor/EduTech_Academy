package academiaTecno;

import java.util.List;

public class Examen extends Evaluacion {

    public Examen(String titulo, List<Pregunta> preguntas) {
        super(titulo, preguntas);
    }

    @Override
    public double evaluar(List<Integer> respuestas) {
        int correctas = 0;
        for (int i = 0; i < preguntas.size(); i++) {
            if (i < respuestas.size() && preguntas.get(i).esCorrecta(respuestas.get(i))) {
                correctas++;
            }
        }
        return (double) correctas / preguntas.size() * 10; // escala de 0 a 10
    }
}
