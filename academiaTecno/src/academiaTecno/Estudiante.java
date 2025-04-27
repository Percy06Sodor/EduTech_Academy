package academiaTecno;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;

class Estudiante extends Usuario {
    private List<Curso> cursosInscritos;
    private List<Calificacion> historialExamenes;
    private List<Certificado> certificados;

    public Estudiante(int id, String nombre, String apellido, String correo, String contrasena) {
        super(id, nombre, apellido, correo, contrasena);
        this.cursosInscritos = new ArrayList<>();
        this.historialExamenes = new ArrayList<>();
        this.certificados = new ArrayList<>();

        //Datos de ejemplo para que no se vea vacia
        Curso cursoIntro = new Curso("Introducción a la Programación", "Básico");
        this.cursosInscritos.add(cursoIntro);

        Curso cursoJava = new Curso("Java", "Intermedio");
        cursoJava.agregarClase(new Clase(1, "Variables y Tipos de Datos"));
        cursoJava.agregarClase(new Clase(2, "Condicionales"));
        this.cursosInscritos.add(cursoJava);

        List<Pregunta> preguntasEjemplo = new ArrayList<>();
        Examen examenEjemplo = new Examen("Examen Inicial", preguntasEjemplo);
        this.historialExamenes.add(new Calificacion(examenEjemplo, 7.5));
    }

    @Override
    public void mostrarMenu(Foro foro) {
        String opcion;
        boolean continuar = true;

        while (continuar) {
            String menuTexto = "--- Menú Estudiante (" + nombre + " " + apellido + ") ---\n\n"
                             + "Gestión de Cursos:\n"
                             + "1. Ver Cursos Inscritos\n"
                             + "2. Ver Calificaciones\n"
                             + "3. Inscribirse a Nuevo Curso\n"
                             + "4. Ver Progreso General\n"
                             + "5. Ver Clases de un Curso\n"
                             + "6. Realizar MiniDesafío de una Clase\n"
                             + "7. Rendir Examen Final del Curso\n"
                             + "8. Obtener Certificado\n\n"
                             + "Foro:\n"
                             + "9. Ver Temas del Foro\n"
                             + "10. Ver Mensajes de un Tema\n"
                             + "11. Crear Nuevo Tema\n"
                             + "12. Responder en un Tema\n\n"
                             + "Opciones de Sesión:\n"
                             + "13. Cerrar Sesión";

            opcion = JOptionPane.showInputDialog(null, menuTexto, "Menú Estudiante", JOptionPane.PLAIN_MESSAGE);

            if (opcion == null) {
                continuar = false;
                break;
            }

            switch (opcion.trim()) {
                case "1": verCursosInscritos();
                break;
                case "2": verCalificaciones();
                break;
                case "3": inscribirseNuevoCurso();
                break;
                case "4": verProgresoGeneral();
                break;
                case "5": verClasesDeCurso();
                break;
                case "6": realizarMiniDesafio();
                break;
                case "7": rendirExamenFinal();
                break;
                case "8": intentarObtenerCertificado();
                break;
                case "9": verTemasForo(foro);
                break;
                case "10": verTemaEspecifico(foro);
                break;
                case "11": crearNuevoTemaForo(foro, this);
                break;
                case "12": responderTema(foro, this);
                break;
                case "13": continuar = false;
                break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        }
    }

    private void verCursosInscritos() {
        String cursosStr = "";
        if (cursosInscritos.isEmpty()) {
            cursosStr = "No estás inscrito en ningún curso.";
        } else {
            for (Curso curso : cursosInscritos) {
                cursosStr += "- " + curso.getNombre() + " (" + curso.getTema() + ")\n";
            }
        }
        JOptionPane.showMessageDialog(null, "Cursos Inscritos:\n" + cursosStr, "Mis Cursos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verCalificaciones() {
        String califStr = "";
        if (historialExamenes.isEmpty()) {
            califStr = "No tienes calificaciones registradas.";
        } else {
            for (Calificacion calificacion : historialExamenes) {
                califStr += "- " + calificacion.toString() + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, "Historial de Calificaciones:\n" + califStr, "Mis Calificaciones", JOptionPane.INFORMATION_MESSAGE);
    }

     private void inscribirseNuevoCurso() {
        String nombreCurso = JOptionPane.showInputDialog(null, "Ingrese el nombre del curso al que desea inscribirse:", "Inscribir Curso", JOptionPane.QUESTION_MESSAGE);
        if (nombreCurso != null) {
            String nombreCursoLimpio = nombreCurso.trim();
            if (!nombreCursoLimpio.isEmpty()) {
                String temaCurso = JOptionPane.showInputDialog(null, "Ingrese el tema del curso:", "Tema del Curso", JOptionPane.QUESTION_MESSAGE);
                if (temaCurso != null && !temaCurso.trim().isEmpty()) {
                    Curso nuevoCurso = new Curso(nombreCursoLimpio, temaCurso.trim());
                    cursosInscritos.add(nuevoCurso);
                    JOptionPane.showMessageDialog(null, nombre + " se ha inscrito al curso: " + nombreCursoLimpio, "Inscripción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else if (temaCurso != null){
                     JOptionPane.showMessageDialog(null, "El tema del curso no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(null, "El nombre del curso no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void registrarCalificacionExamen(double calificacion) {
        String nombreEvaluacion = JOptionPane.showInputDialog(null, "Nombre de la evaluación:", "Nueva Calificación", JOptionPane.QUESTION_MESSAGE);
        if (nombreEvaluacion != null && !nombreEvaluacion.trim().isEmpty()) {
            List<Pregunta> preguntasVacias = new ArrayList<>();
            Examen examenEjemplo = new Examen(nombreEvaluacion.trim(), preguntasVacias);
            Calificacion nueva = new Calificacion(examenEjemplo, calificacion);
            historialExamenes.add(nueva);
        }
    }

    private void verProgresoGeneral() {
        String progreso = "";
        progreso += "--- Progreso General de " + nombre + " ---\n\n";
        progreso += "Cursos Inscritos:\n";
        if (cursosInscritos.isEmpty()) {
            progreso += "- Ninguno\n";
        } else {
             for (Curso curso : cursosInscritos) {
                 progreso += "- " + curso.getNombre() + " (" + curso.getTema() + ")\n";
             }
        }
        progreso += "\nHistorial de Calificaciones:\n";
        if (historialExamenes.isEmpty()) {
            progreso += "- Ninguno\n";
        } else {
             for (Calificacion calificacion : historialExamenes) {
                 progreso += "- " + calificacion.toString() + "\n";
             }
        }
        JOptionPane.showMessageDialog(null, progreso, "Mi Progreso", JOptionPane.INFORMATION_MESSAGE);
    }

    private Curso seleccionarCurso() {
        if (cursosInscritos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No estás inscrito en ningún curso.");
            return null;
        }
        String[] nombresCursos = new String[cursosInscritos.size()];
        for (int i = 0; i < cursosInscritos.size(); i++) {
            nombresCursos[i] = cursosInscritos.get(i).getNombre();
        }
        String seleccion = (String) JOptionPane.showInputDialog(
            null, "Selecciona un curso:", "Cursos Disponibles",
            JOptionPane.PLAIN_MESSAGE, null, nombresCursos, nombresCursos[0]
        );
        if (seleccion == null) return null;
        for (Curso curso : cursosInscritos) {
            if (curso.getNombre().equals(seleccion)) {
                return curso;
            }
        }
        return null;
    }

    private void verClasesDeCurso() {
        Curso curso = seleccionarCurso();
        if (curso != null) {
            List<Clase> clases = curso.getClases();
            if (clases == null || clases.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este curso no tiene clases registradas.", "Sin Clases", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String clasesStr = "";
                for (Clase c : clases) {
                    clasesStr += "- " + c.toString() + "\n";
                }
                JOptionPane.showMessageDialog(null, "Clases del curso '" + curso.getNombre() + "':\n\n" + clasesStr, "Contenido del Curso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void realizarMiniDesafio() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;
        List<Clase> clases = curso.getClases();
        if (clases == null || clases.isEmpty()) { JOptionPane.showMessageDialog(null, "Este curso no tiene clases."); return; }
        String[] opcionesClases = new String[clases.size()];
        for (int i = 0; i < clases.size(); i++) { opcionesClases[i] = "Clase " + clases.get(i).getNumero(); } // Asume getNumero()
        String seleccionClase = (String) JOptionPane.showInputDialog(null, "Selecciona la clase:", "Clases", JOptionPane.QUESTION_MESSAGE, null, opcionesClases, opcionesClases[0]);
        if (seleccionClase == null) return;
        Clase claseSeleccionada = null;
        try {
             int indiceClase = Integer.parseInt(seleccionClase.replace("Clase ", "").trim());
             for(Clase c : clases) { if (c.getNumero() == indiceClase) { claseSeleccionada = c; break; } }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Error al seleccionar la clase."); return; }
        if (claseSeleccionada == null) { JOptionPane.showMessageDialog(null, "Clase no encontrada."); return; }
        MiniDesafio desafio = claseSeleccionada.getMiniDesafio();
        if (desafio == null) { JOptionPane.showMessageDialog(null, "La clase no tiene MiniDesafío."); return; }
        List<Pregunta> preguntasDesafio = desafio.getPreguntas();
        if (preguntasDesafio == null || preguntasDesafio.isEmpty()) { JOptionPane.showMessageDialog(null, "El MiniDesafío no tiene preguntas."); return; }
        List<Integer> respuestasUsuario = new ArrayList<>();
        for (Pregunta p : preguntasDesafio) {
            String respStr = JOptionPane.showInputDialog(p.toString());
            int respuestaInt = -1;
            if (respStr != null) {
                 try { respuestaInt = Integer.parseInt(respStr.trim()); }
                 catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Respuesta inválida...\nSe marcará como incorrecta.", "Error", JOptionPane.WARNING_MESSAGE); }
            } else { JOptionPane.showMessageDialog(null, "Respuesta cancelada...\nSe marcará como incorrecta.", "Cancelado", JOptionPane.WARNING_MESSAGE); }
            respuestasUsuario.add(respuestaInt);
        }
        double nota = desafio.evaluar(respuestasUsuario);
        historialExamenes.add(new Calificacion(desafio, nota));
        JOptionPane.showMessageDialog(null, "MiniDesafío completado. Tu nota fue: " + nota);
    }

    private void rendirExamenFinal() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;
        Examen examen = curso.getExamenFinal();
        if (examen == null) { JOptionPane.showMessageDialog(null, "Este curso no tiene examen final asignado."); return; }
        List<Pregunta> preguntasExamen = examen.getPreguntas();
         if (preguntasExamen == null || preguntasExamen.isEmpty()) { JOptionPane.showMessageDialog(null, "El examen final no tiene preguntas."); return; }
        List<Integer> respuestasUsuario = new ArrayList<>();
        for (Pregunta p : preguntasExamen) {
            String respStr = JOptionPane.showInputDialog(p.toString());
            int respuestaInt = -1;
            if (respStr != null) {
                 try { respuestaInt = Integer.parseInt(respStr.trim()); }
                 catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Respuesta inválida...\nSe marcará como incorrecta.", "Error", JOptionPane.WARNING_MESSAGE); }
            } else { JOptionPane.showMessageDialog(null, "Respuesta cancelada...\nSe marcará como incorrecta.", "Cancelado", JOptionPane.WARNING_MESSAGE); }
            respuestasUsuario.add(respuestaInt);
        }
        double nota = examen.evaluar(respuestasUsuario);
        historialExamenes.add(new Calificacion(examen, nota));
        JOptionPane.showMessageDialog(null, "Examen final completado. Tu nota fue: " + nota);
    }

    private void intentarObtenerCertificado() {
        Curso curso = seleccionarCurso();
        if (curso != null) {
            obtenerCertificado(curso);
        }
    }

    public void obtenerCertificado(Curso curso) {
        for (Certificado c : certificados) {
            if (c.getCurso().equals(curso)) {
                JOptionPane.showMessageDialog(null, "Ya tienes un certificado para '" + curso.getNombre() + "'.\n" + c.toString());
                return;
            }
        }
        Examen examenFinal = curso.getExamenFinal();
        if (examenFinal == null) { JOptionPane.showMessageDialog(null, "Curso sin examen final definido."); return; }
        boolean aprobado = false;
        double notaAprobatoria = 6.0;
        for (Calificacion cal : historialExamenes) {
            if (cal.getEvaluacion() != null && cal.getEvaluacion().equals(examenFinal) && cal.getNota() >= notaAprobatoria) {
                aprobado = true;
                break;
            }
        }
        if (aprobado) {
            Certificado nuevoCertificado = new Certificado(curso);
            certificados.add(nuevoCertificado);
            JOptionPane.showMessageDialog(null, "¡Felicidades! Certificado emitido para '" + curso.getNombre() + "':\n" + nuevoCertificado.toString()); // Asume toString()
        } else {
            JOptionPane.showMessageDialog(null, "No has aprobado el examen final para '" + curso.getNombre() + "' (Nota mínima: " + notaAprobatoria + ").");
        }
    }
}
