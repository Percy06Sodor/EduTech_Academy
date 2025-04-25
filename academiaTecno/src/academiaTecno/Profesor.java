package academiaTecno;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
    public void mostrarMenu() {
        String opcion;
        boolean continuar = true;

        while (continuar) {
            String menuTexto = String.format(
                "--- Menú Profesor (%s %s) ---\n\n" +
                "Gestión de Cursos:\n" +
                "1. Crear Curso\n" +
                "2. Ver Cursos Creados\n" +
                "3. Calificar Estudiante\n" +
                "4. Crear Examen Final para un Curso\n" +
                "5. Crear MiniDesafío para una Clase\n" +
                "6. Agregar Material al Curso\n" +
                "7. Agregar Material a una Clase\n\n" +
                "Foro:\n" +
                "8. Ver Foro\n" +
                "9. Crear Nuevo Tema\n" +
                "10. Responder en un Tema\n\n" +
                "Opciones de Sesión:\n" +
                "11. Cerrar Sesión",
                nombre, apellido
            );

            opcion = JOptionPane.showInputDialog(null, menuTexto, "Menú Profesor", JOptionPane.PLAIN_MESSAGE);

            if (opcion == null) {
                continuar = false;
                break;
            }

            switch (opcion.trim()) {
                case "1":
                    crearNuevoCurso();
                    break;
                case "2":
                    verCursosCreados();
                    break;
                case "3":
                    calificarEstudianteInteractivo();
                    break;
                case "4":
                    crearExamenFinal();
                    break;
                case "5":
                    crearMiniDesafio();
                    break;
                case "6":
                    agregarMaterialCurso();
                    break;
                case "7":
                    agregarMaterialClase();
                    break;
                case "8":
                    verForo();
                    break;
                case "9":
                    crearTema();
                    break;
                case "10":
                    agregarMensaje();
                    break;
                case "11":
                    continuar = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        }
    }

    private void crearNuevoCurso() {
        String nombreCurso = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo curso:", "Crear Curso", JOptionPane.QUESTION_MESSAGE);
        String temaCurso = JOptionPane.showInputDialog(null, "Ingrese el tema del curso:", "Crear Curso", JOptionPane.QUESTION_MESSAGE);

        if (nombreCurso != null && temaCurso != null &&
            !nombreCurso.equals("") && !temaCurso.equals("")) {

            Curso nuevo = new Curso(nombreCurso, temaCurso);
            cursosCreados.add(nuevo);

            JOptionPane.showMessageDialog(null, "Curso '" + nombreCurso + "' creado exitosamente.");
        }
    }

    private void verCursosCreados() {
        if (cursosCreados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No has creado ningún curso.");
            return;
        }

        String cursosStr = "";
        for (Curso curso : cursosCreados) {
            cursosStr += "- " + curso.getNombre() + " (" + curso.getTema() + ")\n";
        }

        JOptionPane.showMessageDialog(null, "Cursos Creados:\n" + cursosStr);
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
            null,
            "Selecciona un curso:",
            "Cursos Disponibles",
            JOptionPane.PLAIN_MESSAGE,
            null,
            nombresCursos,
            nombresCursos[0]
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

        String titulo = JOptionPane.showInputDialog("Título del examen:");
        if (titulo == null || titulo.equals("")) return;

        List<Pregunta> preguntas = new ArrayList<Pregunta>();
        boolean seguir = true;

        while (seguir) {
            String enunciado = JOptionPane.showInputDialog("Escribe la pregunta:");
            if (enunciado == null || enunciado.equals("")) break;

            List<String> opciones = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                String opcion = JOptionPane.showInputDialog("Opción " + i + ":");
                if (opcion != null) opciones.add(opcion);
            }

            int indiceCorrecto = -1;
            boolean entradaValida = false;

            while (!entradaValida) {
                String correcta = JOptionPane.showInputDialog("¿Cuál es la opción correcta? (0–3)");
                try {
                    indiceCorrecto = Integer.parseInt(correcta);
                    if (indiceCorrecto >= 0 && indiceCorrecto < opciones.size()) {
                        entradaValida = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "El número debe estar entre 0 y 3.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debes ingresar un número válido.");
                }
            }

            preguntas.add(new Pregunta(enunciado, opciones, indiceCorrecto));

            int continuar = JOptionPane.showConfirmDialog(null, "¿Agregar otra pregunta?", "Continuar", JOptionPane.YES_NO_OPTION);
            seguir = (continuar == JOptionPane.YES_OPTION);
        }

        Examen nuevo = new Examen(titulo, preguntas);
        curso.asignarExamenFinal(nuevo);

        JOptionPane.showMessageDialog(null, "Examen creado exitosamente.");
    }
    
    private void crearMiniDesafio() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;

        List<Clase> clases = curso.getClases();
        if (clases.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este curso no tiene clases.");
            return;
        }

        String[] opcionesClases = new String[clases.size()];
        for (int i = 0; i < clases.size(); i++) {
            opcionesClases[i] = "Clase " + clases.get(i).getNumero();
        }

        String seleccion = (String) JOptionPane.showInputDialog(
            null,
            "Selecciona la clase:",
            "Clases",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcionesClases,
            opcionesClases[0]
        );

        if (seleccion == null) return;
        int indice = Integer.parseInt(seleccion.replace("Clase ", ""));
        Clase claseSeleccionada = clases.stream()
            .filter(c -> c.getNumero() == indice)
            .findFirst()
            .orElse(null);

        if (claseSeleccionada == null) return;

        String titulo = JOptionPane.showInputDialog("Título del MiniDesafío:");
        if (titulo == null || titulo.equals("")) return;

        List<Pregunta> preguntas = new ArrayList<Pregunta>();
        boolean seguir = true;

        while (seguir) {
            String enunciado = JOptionPane.showInputDialog("Escribe la pregunta:");
            if (enunciado == null || enunciado.equals("")) break;

            List<String> opcionesPregunta = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                String opcion = JOptionPane.showInputDialog("Opción " + i + ":");
                if (opcion != null) opcionesPregunta.add(opcion);
            }

            int indiceCorrecto = -1;
            boolean entradaValida = false;

            while (!entradaValida) {
                String correcta = JOptionPane.showInputDialog("¿Cuál es la opción correcta? (0–3)");
                try {
                    indiceCorrecto = Integer.parseInt(correcta);
                    if (indiceCorrecto >= 0 && indiceCorrecto < opcionesPregunta.size()) {
                        entradaValida = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "El número debe estar entre 0 y 3.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debes ingresar un número válido.");
                }
            }

            preguntas.add(new Pregunta(enunciado, opcionesPregunta, indiceCorrecto));

            int continuar = JOptionPane.showConfirmDialog(null, "¿Agregar otra pregunta?", "Continuar", JOptionPane.YES_NO_OPTION);
            seguir = (continuar == JOptionPane.YES_OPTION);
        }

        MiniDesafio nuevo = new MiniDesafio(titulo, preguntas);
        claseSeleccionada.asignarMiniDesafio(nuevo);

        JOptionPane.showMessageDialog(null, "MiniDesafío creado exitosamente.");
    }

    private void calificarEstudianteInteractivo() {
        String idEstudianteStr = JOptionPane.showInputDialog(null, "Ingrese el ID del estudiante a calificar:", "Calificar Estudiante", JOptionPane.QUESTION_MESSAGE);
        if (idEstudianteStr == null) return;

        try {
            int idEstudiante = Integer.parseInt(idEstudianteStr.trim());
            Estudiante estudianteEncontrado = null;

            for (Usuario usr : listaUsuariosGlobal) {
                if (usr instanceof Estudiante && usr.getId() == idEstudiante) {
                    estudianteEncontrado = (Estudiante) usr;
                    break;
                }
            }

            if (estudianteEncontrado != null) {
                String calificacionStr = JOptionPane.showInputDialog(null, "Ingrese la calificación para " + estudianteEncontrado.getNombre() + " " + estudianteEncontrado.getApellido() + ":", "Ingresar Calificación", JOptionPane.QUESTION_MESSAGE);
                if (calificacionStr == null) return;

                try {
                    double calificacion = Double.parseDouble(calificacionStr.trim());
                    estudianteEncontrado.registrarCalificacionExamen(calificacion);
                    JOptionPane.showMessageDialog(null, "Calificación " + calificacion + " registrada para el estudiante ID " + idEstudiante + ".", "Calificación Exitosa", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "La calificación ingresada no es un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con el ID " + idEstudiante + ".", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID ingresado no es un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarMaterialCurso() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;

        String titulo = JOptionPane.showInputDialog("Título del material:");
        String tipo = JOptionPane.showInputDialog("Tipo (PDF, Video, etc):");
        String url = JOptionPane.showInputDialog("URL o ubicación:");

        if (titulo != null && tipo != null && url != null &&
            !titulo.equals("") && !tipo.equals("") && !url.equals("")) {
            
            Material nuevo = new Material(titulo, tipo, url);
            curso.agregarMaterialGeneral(nuevo);

            JOptionPane.showMessageDialog(null, "Material agregado al curso.");
        }
    }
    
    private void agregarMaterialClase() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;

        List<Clase> clases = curso.getClases();
        if (clases.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este curso no tiene clases.");
            return;
        }

        String[] opciones = new String[clases.size()];
        for (int i = 0; i < clases.size(); i++) {
            opciones[i] = "Clase " + clases.get(i).getNumero();
        }

        String seleccion = (String) JOptionPane.showInputDialog(
            null, "Selecciona la clase:",
            "Clases del Curso",
            JOptionPane.PLAIN_MESSAGE,
            null, opciones, opciones[0]
        );

        if (seleccion == null) return;
        int indice = Integer.parseInt(seleccion.replace("Clase ", ""));
        Clase clase = clases.stream().filter(c -> c.getNumero() == indice).findFirst().orElse(null);
        if (clase == null) return;

        String titulo = JOptionPane.showInputDialog("Título del material:");
        String tipo = JOptionPane.showInputDialog("Tipo (PDF, Video, etc):");
        String url = JOptionPane.showInputDialog("URL o ubicación:");

        if (titulo != null && tipo != null && url != null &&
            !titulo.equals("") && !tipo.equals("") && !url.equals("")) {
            
            Material nuevo = new Material(titulo, tipo, url);
            clase.agregarMaterial(nuevo);

            JOptionPane.showMessageDialog(null, "Material agregado a la clase.");
        }
    }
    
    private void verForo() {
        String contenido = Main.foroGeneral.mostrarForo();
        JOptionPane.showMessageDialog(null, contenido, "Foro General", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void crearTema() {
        String titulo = JOptionPane.showInputDialog("Título del nuevo tema:");
        String contenido = JOptionPane.showInputDialog("Contenido del primer mensaje:");

        if (titulo != null && contenido != null &&
            !titulo.equals("") && !contenido.equals("")) {
            
            Main.foroGeneral.crearTema(titulo, contenido, this);
            JOptionPane.showMessageDialog(null, "Tema creado exitosamente.");
        }
    }
    
    private void agregarMensaje() {
        String contenido = JOptionPane.showInputDialog("Contenido de tu respuesta:");

        if (contenido != null && !contenido.equals("")) {
            Main.foroGeneral.responderUltimoTema(contenido, this);
            JOptionPane.showMessageDialog(null, "Mensaje agregado exitosamente.");
        }
    }
}