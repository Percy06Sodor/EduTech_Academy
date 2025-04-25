package academiaTecno;

import java.util.List;

public class MiniDesafio extends Evaluacion {

    public MiniDesafio(String titulo, List<Pregunta> preguntas) {
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
        return (double) correctas / preguntas.size() * 10;
    }
}

