package academiaTecno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

class Foro {

	private List<Tema> temas;
    private int proximoIdTema = 1;

    public Foro() {
        this.temas = new ArrayList<>();
        
        Tema temaInicial = new Tema(proximoIdTema++, "Bienvenida al Foro");
        Mensaje msgInicial = new Mensaje(1, "¡Hola a todos! Este es el foro de la plataforma.", LocalDate.now(), new Administrador(0, "Sistema", "", "sys@foro.com", "", null));
        temaInicial.agregarRespuesta(msgInicial);
        this.temas.add(temaInicial);

        Tema dudaCurso = new Tema(proximoIdTema++, "Dudas sobre Curso de Java");
        Mensaje dudaMsg = new Mensaje(1, "¿Qué requisitos hay para aprobar el curso de Java?", LocalDate.now(), new Estudiante(2, "Juan", "Pérez", "juan@email.com", "1234"));
        dudaCurso.agregarRespuesta(dudaMsg);
        this.temas.add(dudaCurso);

        Tema consejosEstudio = new Tema(proximoIdTema++, "Consejos para estudiar mejor");
        Mensaje consejoMsg = new Mensaje(1, "¿Alguien tiene consejos para organizar el estudio?", LocalDate.now(), new Estudiante(3, "María", "García", "maria@email.com", "5678"));
        consejosEstudio.agregarRespuesta(consejoMsg);
        this.temas.add(consejosEstudio);
    }

    public void crearTema(String titulo, String contenido, Usuario autor) {
        // Si no se proporcionó contenido, lo pedimos al usuario
        if (contenido == null || contenido.trim().isEmpty()) {
            contenido = JOptionPane.showInputDialog(null, "Ingrese el primer mensaje para el tema '" + titulo + "':", "Primer Mensaje", JOptionPane.QUESTION_MESSAGE);
            if (contenido == null || contenido.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se requiere un mensaje para crear el tema.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Tema nuevoTema = new Tema(proximoIdTema++, titulo);
        Mensaje msgInicial = new Mensaje(1, contenido.trim(), LocalDate.now(), autor);
        nuevoTema.agregarRespuesta(msgInicial);
        this.temas.add(nuevoTema);
    }

    public Tema buscarTemaPorId(int id) {
        for (Tema tema : temas) {
            if (tema.getId() == id) {
                return tema;
            }
        }
        return null;
    }

    public String obtenerListaTemasFormateada() {
        String listaStr = "";
        if (temas.isEmpty()) {
            listaStr = "(No hay temas en el foro)";
        } else {
            for (Tema tema : temas) {
                listaStr += tema.toString() + "\n";
            }
        }
        return listaStr;
    }

    public void moderarForo() {
        JOptionPane.showMessageDialog(null, "Accediendo a herramientas de moderación del foro...", "Moderar Foro", JOptionPane.INFORMATION_MESSAGE);
    }

     public void publicarTareaEnForo(String tituloTarea, String contenidoTarea, Profesor profesor) {
         String tituloTema = "Tarea: " + tituloTarea;
         Tema temaTarea = new Tema(proximoIdTema++, tituloTema);
         Mensaje msgTarea = new Mensaje(1, "Instrucciones de la tarea:\n" + contenidoTarea, LocalDate.now(), profesor);
         temaTarea.agregarRespuesta(msgTarea);
         this.temas.add(temaTarea);
         JOptionPane.showMessageDialog(null, "Tarea '" + tituloTarea + "' publicada en el foro.", "Tarea Publicada", JOptionPane.INFORMATION_MESSAGE);
     }
     
     public String mostrarForo() {
    	    if (temas.isEmpty()) {
    	        return "El foro está vacío.";
    	    }

    	    StringBuilder sb = new StringBuilder();
    	    sb.append("--- Foro General ---\n\n");

    	    for (Tema tema : temas) {
    	        sb.append("Tema: ").append(tema.getTitulo()).append("\n");
    	        for (Mensaje m : tema.getRespuestas()) {
    	            sb.append(" - ").append(m.getAutor().getNombre()).append(": ")
    	              .append(m.getContenido()).append(" [").append(m.getFecha()).append("]\n");
    	        }
    	        sb.append("\n");
    	    }

    	    return sb.toString();
    	}
     
     public void responderUltimoTema(String contenido, Usuario autor) {
    	    if (temas.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "No hay temas en el foro.");
    	        return;
    	    }

    	    Tema ultimo = temas.get(temas.size() - 1);
    	    int nuevoId = ultimo.getRespuestas().size() + 1;

    	    Mensaje nuevo = new Mensaje(nuevoId, contenido, LocalDate.now(), autor);
    	    ultimo.agregarRespuesta(nuevo);
    	}
}
