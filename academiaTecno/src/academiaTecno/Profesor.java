package academiaTecno;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;


class Profesor extends Usuario {
    private List<Curso> cursosCreados;
    private List<Usuario> listaUsuariosGlobal;

    public Profesor(int id, String nombre, String apellido, String correo, String contrasena, List<Usuario> listaUsuarios) {
        super(id, nombre, apellido, correo, contrasena);
        this.cursosCreados = new ArrayList<>();
        this.listaUsuariosGlobal = listaUsuarios;
        cursosCreados.add(new Curso("Bases de Datos Avanzadas", "Avanzado"));
    }

    @Override
    public void mostrarMenu(Foro foro) {
        String opcion;
        boolean continuar = true;

        while (continuar) {
            String menuTexto = "--- Menú Profesor (" + nombre + " " + apellido + ") ---\n\n"
                             + "Gestión de Cursos:\n"
                             + "1. Crear Curso\n"
                             + "2. Ver Cursos Creados\n"
                             + "3. Calificar Estudiante\n"
                             + "4. Crear Examen Final para un Curso\n"
                             + "5. Crear MiniDesafío para una Clase\n"
                             + "6. Agregar Material al Curso\n"
                             + "7. Agregar Material a una Clase\n\n"
                             + "Foro:\n"
                             + "8. Ver Temas del Foro\n"
                             + "9. Ver Mensajes de un Tema\n"
                             + "10. Crear Nuevo Tema\n"
                             + "11. Responder en un Tema\n\n"
                             + "Opciones de Sesión:\n"
                             + "12. Cerrar Sesión";

            opcion = JOptionPane.showInputDialog(null, menuTexto, "Menú Profesor", JOptionPane.PLAIN_MESSAGE);

            if (opcion == null) {
                continuar = false;
                break;
            }

            switch (opcion.trim()) {
                case "1": crearNuevoCurso();
                break;
                case "2": verCursosCreados();
                break;
                case "3": calificarEstudianteInteractivo();
                break;
                case "4": crearExamenFinal();
                break;
                case "5": crearMiniDesafio();
                break;
                case "6": agregarMaterialCurso();
                break;
                case "7": agregarMaterialClase();
                break;
                case "8": verTemasForo(foro);
                break;
                case "9": verTemaEspecifico(foro);
                break;
                case "10": crearNuevoTemaForo(foro, this);
                break;
                case "11": responderTema(foro, this);
                break;
                case "12": continuar = false;
                break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        }
    }

    private void crearNuevoCurso() {
        String nombreCurso = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo curso:", "Crear Curso", JOptionPane.QUESTION_MESSAGE);
         if (nombreCurso != null) {
            String nombreCursoLimpio = nombreCurso.trim();
            if (!nombreCursoLimpio.isEmpty()) {
                String temaCurso = JOptionPane.showInputDialog(null, "Ingrese el tema del curso:", "Tema del Curso", JOptionPane.QUESTION_MESSAGE);
                if (temaCurso != null && !temaCurso.trim().isEmpty()) {
                     Curso nuevoCurso = new Curso(nombreCursoLimpio, temaCurso.trim());
                     cursosCreados.add(nuevoCurso);
                     JOptionPane.showMessageDialog(null, "Curso '" + nombreCursoLimpio + "' creado exitosamente.");
                } else if (temaCurso != null){
                     JOptionPane.showMessageDialog(null, "El tema del curso no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(null, "El nombre del curso no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void verCursosCreados() {
        String cursosStr = "";
        if (cursosCreados.isEmpty()) {
            cursosStr = "No has creado ningún curso.";
        } else {
            for (Curso curso : cursosCreados) {
                cursosStr += "- " + curso.getNombre() + " (" + curso.getTema() + ")\n";
            }
        }
        JOptionPane.showMessageDialog(null, "Cursos Creados:\n" + cursosStr, "Mis Cursos Creados", JOptionPane.INFORMATION_MESSAGE);
    }

    private Curso seleccionarCurso() {
        if (cursosCreados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes cursos creados.");
            return null;
        }
        String[] nombresCursos = new String[cursosCreados.size()];
        for (int i = 0; i < cursosCreados.size(); i++) {
            nombresCursos[i] = cursosCreados.get(i).getNombre();
        }
        String seleccion = (String) JOptionPane.showInputDialog(
            null, "Selecciona un curso:", "Cursos Disponibles",
            JOptionPane.PLAIN_MESSAGE, null, nombresCursos, nombresCursos[0]
        );
        if (seleccion == null) return null;
        for (Curso curso : cursosCreados) {
            if (curso.getNombre().equals(seleccion)) {
                return curso;
            }
        }
        return null;
    }

    private void crearExamenFinal() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;
        if (curso.getExamenFinal() != null) {
             int sobrescribir = JOptionPane.showConfirmDialog(null, "El curso ya tiene examen final.\n¿Reemplazarlo?", "Examen Existente", JOptionPane.YES_NO_OPTION);
             if (sobrescribir != JOptionPane.YES_OPTION) return;
        }
        String tituloExamen = JOptionPane.showInputDialog("Título del examen final para '" + curso.getNombre() + "':");
        if (tituloExamen == null || tituloExamen.trim().isEmpty()) { if (tituloExamen != null) JOptionPane.showMessageDialog(null, "Título vacío."); return; }
        List<Pregunta> preguntas = crearListaPreguntasInteractivamente();
        if (!preguntas.isEmpty()) {
            Examen nuevoExamen = new Examen(tituloExamen.trim(), preguntas);
            curso.asignarExamenFinal(nuevoExamen);
            JOptionPane.showMessageDialog(null, "Examen final creado y asignado a '" + curso.getNombre() + "'.");
        } else { JOptionPane.showMessageDialog(null, "No se agregaron preguntas."); }
    }

    private void crearMiniDesafio() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;
        List<Clase> clases = curso.getClases();
        if (clases == null || clases.isEmpty()) { JOptionPane.showMessageDialog(null, "Curso sin clases."); return; }
        String[] opcionesClases = new String[clases.size()];
        for (int i = 0; i < clases.size(); i++) { opcionesClases[i] = "Clase " + clases.get(i).getNumero(); }
        String seleccionClase = (String) JOptionPane.showInputDialog(null, "Selecciona la clase:", "Seleccionar Clase", JOptionPane.QUESTION_MESSAGE, null, opcionesClases, opcionesClases[0]);
        if (seleccionClase == null) return;
        Clase claseSeleccionada = null;
        try {
             int indiceClase = Integer.parseInt(seleccionClase.replace("Clase ", "").trim());
             for(Clase c : clases) { if (c.getNumero() == indiceClase) { claseSeleccionada = c; break; } }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Error al seleccionar."); return; }
        if (claseSeleccionada == null) { JOptionPane.showMessageDialog(null, "Clase no encontrada."); return; }
        if (claseSeleccionada.getMiniDesafio() != null) {
             int sobrescribir = JOptionPane.showConfirmDialog(null, "La clase ya tiene MiniDesafío.\n¿Reemplazarlo?", "Existente", JOptionPane.YES_NO_OPTION);
             if (sobrescribir != JOptionPane.YES_OPTION) return;
        }
        String tituloDesafio = JOptionPane.showInputDialog("Título del MiniDesafío para " + seleccionClase + ":");
        if (tituloDesafio == null || tituloDesafio.trim().isEmpty()) { if (tituloDesafio != null) JOptionPane.showMessageDialog(null, "Título vacío."); return; }
        List<Pregunta> preguntas = crearListaPreguntasInteractivamente();
        if (!preguntas.isEmpty()) {
            MiniDesafio nuevoDesafio = new MiniDesafio(tituloDesafio.trim(), preguntas);
            claseSeleccionada.asignarMiniDesafio(nuevoDesafio);
            JOptionPane.showMessageDialog(null, "MiniDesafío creado y asignado a " + seleccionClase + ".");
        } else { JOptionPane.showMessageDialog(null, "No se agregaron preguntas."); }
    }

    private List<Pregunta> crearListaPreguntasInteractivamente() {
        List<Pregunta> preguntas = new ArrayList<>();
        boolean seguirAgregando = true;
        while (seguirAgregando) {
            String enunciado = JOptionPane.showInputDialog("Enunciado de la pregunta (o cancelar para terminar):");
            if (enunciado == null || enunciado.trim().isEmpty()) { seguirAgregando = false; break; }
            List<String> opciones = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                String opcionTxt = JOptionPane.showInputDialog("Opción " + (i + 1) + " para:\n'" + enunciado.trim() + "'");
                if (opcionTxt == null) { JOptionPane.showMessageDialog(null, "Creación cancelada."); opciones = null; break; }
                 if (opcionTxt.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "Opción vacía."); i--; continue; }
                opciones.add(opcionTxt.trim());
            }
            if (opciones == null) continue;
            int indiceCorrecto = -1;
            boolean indiceValido = false;
            while (!indiceValido) {
                String correctaStr = JOptionPane.showInputDialog("¿Opción correcta? (1-4)");
                if (correctaStr == null) { JOptionPane.showMessageDialog(null, "Creación cancelada."); indiceCorrecto = -2; break; }
                try {
                    int seleccionUsuario = Integer.parseInt(correctaStr.trim());
                    if (seleccionUsuario >= 1 && seleccionUsuario <= 4) { indiceCorrecto = seleccionUsuario - 1; indiceValido = true; }
                    else { JOptionPane.showMessageDialog(null, "Número entre 1 y 4.", "Error", JOptionPane.WARNING_MESSAGE); }
                } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Número inválido.", "Error", JOptionPane.ERROR_MESSAGE); }
            }
             if (indiceCorrecto == -2) continue;
            preguntas.add(new Pregunta(enunciado.trim(), opciones, indiceCorrecto));
            JOptionPane.showMessageDialog(null, "Pregunta agregada.");
            int continuar = JOptionPane.showConfirmDialog(null, "¿Agregar otra pregunta?", "Continuar", JOptionPane.YES_NO_OPTION);
            if (continuar != JOptionPane.YES_OPTION) { seguirAgregando = false; }
        }
        return preguntas;
    }

    private void calificarEstudianteInteractivo() {
        String idEstudianteStr = JOptionPane.showInputDialog(null, "Ingrese ID del estudiante a calificar:", "Calificar Estudiante", JOptionPane.QUESTION_MESSAGE);
        if (idEstudianteStr == null) return;
        try {
            int idEstudiante = Integer.parseInt(idEstudianteStr.trim());
            Estudiante estudianteEncontrado = null;
            for (Usuario usr : listaUsuariosGlobal) {
                if (usr instanceof Estudiante && usr.getId() == idEstudiante) { estudianteEncontrado = (Estudiante) usr; break; }
            }
            if (estudianteEncontrado != null) {
                String calificacionStr = JOptionPane.showInputDialog(null, "Ingrese la calificación para " + estudianteEncontrado.getNombre() + " " + estudianteEncontrado.getApellido() + ":", "Ingresar Calificación", JOptionPane.QUESTION_MESSAGE);
                if (calificacionStr == null) return;
                try {
                    double calificacion = Double.parseDouble(calificacionStr.trim());
                    estudianteEncontrado.registrarCalificacionExamen(calificacion);
                    JOptionPane.showMessageDialog(null, "Calificación " + calificacion + " registrada para el estudiante ID " + idEstudiante + ".", "Calificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Calificación inválida.", "Error", JOptionPane.ERROR_MESSAGE); }
            } else { JOptionPane.showMessageDialog(null, "Estudiante ID " + idEstudiante + " no encontrado.", "Error", JOptionPane.WARNING_MESSAGE); }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void agregarMaterialCurso() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;
        String titulo = JOptionPane.showInputDialog("Título del material:");
        String tipo = JOptionPane.showInputDialog("Tipo (PDF, Video, etc):");
        String url = JOptionPane.showInputDialog("URL o ubicación:");
        if (titulo != null && !titulo.trim().isEmpty() && tipo != null && !tipo.trim().isEmpty() && url != null && !url.trim().isEmpty()) {
            Material nuevo = new Material(titulo.trim(), tipo.trim(), url.trim());
            curso.agregarMaterialGeneral(nuevo);
            JOptionPane.showMessageDialog(null, "Material agregado al curso '" + curso.getNombre() + "'.");
        } else { JOptionPane.showMessageDialog(null, "Campos requeridos.", "Error", JOptionPane.WARNING_MESSAGE); }
    }

    private void agregarMaterialClase() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;
        List<Clase> clases = curso.getClases();
        if (clases == null || clases.isEmpty()) { JOptionPane.showMessageDialog(null, "Curso sin clases."); return; }
        String[] opcionesClases = new String[clases.size()];
        for (int i = 0; i < clases.size(); i++) { opcionesClases[i] = "Clase " + clases.get(i).getNumero(); }
        String seleccionClase = (String) JOptionPane.showInputDialog(null, "Selecciona la clase:", "Clases del Curso '" + curso.getNombre() + "'", JOptionPane.PLAIN_MESSAGE, null, opcionesClases, opcionesClases[0]);
        if (seleccionClase == null) return;
        Clase claseSeleccionada = null;
        try {
             int indiceClase = Integer.parseInt(seleccionClase.replace("Clase ", "").trim());
             for(Clase c : clases) { if (c.getNumero() == indiceClase) { claseSeleccionada = c; break; } }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Error al seleccionar."); return; }
        if (claseSeleccionada == null) { JOptionPane.showMessageDialog(null, "Error al encontrar clase."); return; }
        String titulo = JOptionPane.showInputDialog("Título del material para la " + seleccionClase + ":");
        String tipo = JOptionPane.showInputDialog("Tipo (PDF, Video, etc):");
        String url = JOptionPane.showInputDialog("URL o ubicación:");
        if (titulo != null && !titulo.trim().isEmpty() && tipo != null && !tipo.trim().isEmpty() && url != null && !url.trim().isEmpty()) {
            Material nuevo = new Material(titulo.trim(), tipo.trim(), url.trim());
            claseSeleccionada.agregarMaterial(nuevo);
            JOptionPane.showMessageDialog(null, "Material agregado a la " + seleccionClase + ".");
        } else { JOptionPane.showMessageDialog(null, "Campos requeridos.", "Error", JOptionPane.WARNING_MESSAGE); }
    }
}
