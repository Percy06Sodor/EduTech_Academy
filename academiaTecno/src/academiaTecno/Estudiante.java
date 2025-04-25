package academiaTecno;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

class Estudiante extends Usuario {
	private List<Curso> cursosInscritos;
	private List<Calificacion> historialExamenes;
	private List<Certificado> certificados;

    public Estudiante(int id, String nombre, String apellido, String correo, String contrasena) {
        super(id, nombre, apellido, correo, contrasena);
        this.cursosInscritos = new ArrayList<>();
        this.historialExamenes = new ArrayList<>();
        this.certificados = new ArrayList<>();
        // son datos de ejemplo solamente para que no quede vacio el cuadro de joptionpane, si quieren agreguen algo mas

        // Curso básico con una sola clase
        cursosInscritos.add(new Curso("Introducción a la Programación", "Básico"));
        
        // Curso con clases detalladas
        Curso cursoJava = new Curso("Java", "Intermedio");
        cursoJava.agregarClase(new Clase(1, "Variables y Tipos de Datos"));
        cursoJava.agregarClase(new Clase(2, "Condicionales"));
        cursosInscritos.add(cursoJava);
        
        historialExamenes.add(
        	    new Calificacion(
        	        new Examen("Examen Inicial", new ArrayList<>()), 
        	        7.5
        	    )
        	);
    }

    @Override
    public void mostrarMenu() {
        String opcion;
        boolean continuar = true;

        while (continuar) {
        	String menuTexto = String.format(
        		    "--- Menú Estudiante (%s %s) ---\n\n" +
        		    "Gestión de Cursos:\n" +
        		    "1. Ver Cursos Inscritos\n" +
        		    "2. Ver Calificaciones\n" +
        		    "3. Inscribirse a Nuevo Curso\n" +
        		    "4. Ver Progreso General\n" +
        		    "5. Ver Clases de un Curso\n" +
        		    "6. Realizar MiniDesafío de una Clase\n" +
        		    "7. Rendir Examen Final del Curso\n" +
        		    "8. Obtener Certificado\n\n" +
        		    "Foro:\n" +
        		    "9. Ver Foro\n" +
        		    "10. Crear Nuevo Tema\n" +
        		    "11. Responder en un Tema\n\n" +
        		    "Opciones de Sesión:\n" +
        		    "12. Cerrar Sesión",
        		    nombre, apellido
        		);

            opcion = JOptionPane.showInputDialog(null, menuTexto, "Menú Estudiante", JOptionPane.PLAIN_MESSAGE);

            if (opcion == null) {
                continuar = false;
                break;
            }

            switch (opcion.trim()) {
                case "1":
                    verCursosInscritos();
                    break;
                case "2":
                    verCalificaciones();
                    break;
                case "3":
                    inscribirseNuevoCurso();
                    break;
                case "4":
                    verProgresoGeneral();
                    break;
                case "5":
                    verClasesDeCurso(); // metodo nuevo
                    break;
                case "6":
                    realizarMiniDesafio(); // metodo nuevo
                    break;
                case "7":
                    rendirExamenFinal(); // metodo nuevo
                    break;
                case "8":
                    intentarObtenerCertificado(); // metodo nuevo
                    break;
                case "9": 
                	verForo(); 
                	break;
                case "10": 
                	crearTema(); 
                	break;
                case "11": 
                	agregarMensaje(); 
                	break;
                case "12": continuar = false; break;
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
            	    cursosInscritos.add(new Curso(nombreCursoLimpio, temaCurso.trim()));
            	    JOptionPane.showMessageDialog(null, nombre + " se ha inscrito al curso: " + nombreCursoLimpio, "Inscripción Exitosa", JOptionPane.INFORMATION_MESSAGE);
            	}
                JOptionPane.showMessageDialog(null, nombre + " se ha inscrito al curso: " + nombreCursoLimpio, "Inscripción Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(null, "El nombre del curso no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void registrarCalificacionExamen(double calificacion) {
    	String nombreEvaluacion = JOptionPane.showInputDialog(null, "Nombre de la evaluación:", "Nueva Calificación", JOptionPane.QUESTION_MESSAGE);
    	if (nombreEvaluacion != null && !nombreEvaluacion.trim().isEmpty()) {
    		// Paso 1: Crear lista vacía de preguntas
    		List<Pregunta> preguntasVacias = new ArrayList<>();

    		// Paso 2: Crear examen con nombre y preguntas
    		Examen examenEjemplo = new Examen(nombreEvaluacion.trim(), preguntasVacias);

    		// Paso 3: Crear calificación usando el examen
    		Calificacion nueva = new Calificacion(examenEjemplo, calificacion);

    		// Paso 4: Agregar al historial
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
     
     
     private void verClasesDeCurso() {
    	    if (cursosInscritos.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "No estás inscrito en ningún curso.", "Sin Cursos", JOptionPane.INFORMATION_MESSAGE);
    	        return;
    	    }

    	    // Armar lista de opciones
    	    String[] opcionesCurso = new String[cursosInscritos.size()];
    	    for (int i = 0; i < cursosInscritos.size(); i++) {
    	        opcionesCurso[i] = cursosInscritos.get(i).getNombre();
    	    }

    	    String cursoElegido = (String) JOptionPane.showInputDialog(
    	            null,
    	            "Seleccione un curso:",
    	            "Ver Clases",
    	            JOptionPane.PLAIN_MESSAGE,
    	            null,
    	            opcionesCurso,
    	            opcionesCurso[0]
    	    );

    	    if (cursoElegido != null) {
    	        for (Curso curso : cursosInscritos) {
    	            if (curso.getNombre().equals(cursoElegido)) {
    	                List<Clase> clases = curso.getClases();
    	                if (clases.isEmpty()) {
    	                    JOptionPane.showMessageDialog(null, "Este curso no tiene clases registradas.", "Sin Clases", JOptionPane.INFORMATION_MESSAGE);
    	                } else {
    	                    String clasesStr = "";
    	                    for (Clase c : clases) {
    	                        clasesStr += "- " + c.toString() + "\n";
    	                    }
    	                    JOptionPane.showMessageDialog(null, "Clases del curso '" + curso.getNombre() + "':\n\n" + clasesStr, "Contenido del Curso", JOptionPane.INFORMATION_MESSAGE);
    	                }
    	                break;
    	            }
    	        }
    	    }
    	}
     private void realizarMiniDesafio() {
    	    if (cursosInscritos.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "No estás inscrito en ningún curso.");
    	        return;
    	    }

    	    // Elegir curso
    	    Curso curso = seleccionarCurso();
    	    if (curso == null) return;

    	    // Elegir clase
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
    	        null,
    	        "Selecciona la clase:",
    	        "Clases",
    	        JOptionPane.QUESTION_MESSAGE,
    	        null,
    	        opciones,
    	        opciones[0]
    	    );

    	    if (seleccion == null) return;

    	    int indice = Integer.parseInt(seleccion.replace("Clase ", ""));
    	    Clase claseSeleccionada = clases.stream()
    	        .filter(c -> c.getNumero() == indice)
    	        .findFirst()
    	        .orElse(null);

    	    if (claseSeleccionada == null || claseSeleccionada.getMiniDesafio() == null) {
    	        JOptionPane.showMessageDialog(null, "La clase no tiene un MiniDesafío.");
    	        return;
    	    }

    	    MiniDesafio desafio = claseSeleccionada.getMiniDesafio();

    	    List<Integer> respuestas = new ArrayList<>();
    	    for (Pregunta p : desafio.getPreguntas()) {
    	        String resp = JOptionPane.showInputDialog(p.toString());
    	        try {
    	            respuestas.add(Integer.parseInt(resp));
    	        } catch (Exception e) {
    	            respuestas.add(-1); // respuesta inválida
    	        }
    	    }

    	    double nota = desafio.evaluar(respuestas);
    	    historialExamenes.add(new Calificacion(desafio, nota));

    	    JOptionPane.showMessageDialog(null, "Tu nota fue: " + nota);
    	}
      
     private void rendirExamenFinal() {
    	    if (cursosInscritos.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "No estás inscrito en ningún curso.");
    	        return;
    	    }

    	    Curso curso = seleccionarCurso();
    	    if (curso == null || curso.getExamenFinal() == null) {
    	        JOptionPane.showMessageDialog(null, "Este curso no tiene examen final.");
    	        return;
    	    }

    	    Examen examen = curso.getExamenFinal();

    	    List<Integer> respuestas = new ArrayList<>();
    	    for (Pregunta p : examen.getPreguntas()) {
    	        String resp = JOptionPane.showInputDialog(p.toString());
    	        try {
    	            respuestas.add(Integer.parseInt(resp));
    	        } catch (Exception e) {
    	            respuestas.add(-1); // respuesta inválida
    	        }
    	    }

    	    double nota = examen.evaluar(respuestas);
    	    historialExamenes.add(new Calificacion(examen, nota));

    	    JOptionPane.showMessageDialog(null, "Tu nota fue: " + nota);
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
    	        null,
    	        "Selecciona un curso:",
    	        "Cursos Disponibles",
    	        JOptionPane.PLAIN_MESSAGE,
    	        null,
    	        nombresCursos,
    	        nombresCursos[0]
    	    );

    	    if (seleccion == null) return null;

    	    for (Curso curso : cursosInscritos) {
    	        if (curso.getNombre().equals(seleccion)) {
    	            return curso;
    	        }
    	    }

    	    return null;
    	}
     
     public void obtenerCertificado(Curso curso) {
    	    // Ya tiene certificado
    	    for (Certificado c : certificados) {
    	        if (c.getCurso().equals(curso)) {
    	            JOptionPane.showMessageDialog(null, "Ya tienes un certificado para este curso.");
    	            return;
    	        }
    	    }

    	    // Validar que el curso tenga clases
    	    if (curso.getClases().isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "Este curso no tiene clases, no se puede emitir certificado.");
    	        return;
    	    }

    	    // Buscar calificación del examen final
    	    boolean aprobado = false;

    	    for (Calificacion cal : historialExamenes) {
    	        if (cal.getEvaluacion() instanceof Examen &&
    	            curso.getExamenFinal() != null &&
    	            cal.getEvaluacion().equals(curso.getExamenFinal()) &&
    	            cal.getNota() >= 6.0) {

    	            aprobado = true;
    	            break;
    	        }
    	    }

    	    if (aprobado) {
    	        Certificado nuevo = new Certificado(curso);
    	        certificados.add(nuevo);
    	        JOptionPane.showMessageDialog(null, "¡Felicidades! Se ha emitido tu certificado:\n" + nuevo);
    	    } else {
    	        JOptionPane.showMessageDialog(null, "No has aprobado el examen final. No se puede emitir certificado.");
    	    }
    	}
     
     private void intentarObtenerCertificado() {
    	    if (cursosInscritos.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "No estás inscrito en ningún curso.");
    	        return;
    	    }

    	    String[] opcionesCurso = new String[cursosInscritos.size()];
    	    for (int i = 0; i < cursosInscritos.size(); i++) {
    	        opcionesCurso[i] = cursosInscritos.get(i).getNombre();
    	    }

    	    String seleccion = (String) JOptionPane.showInputDialog(
    	        null,
    	        "Selecciona el curso:",
    	        "Obtener Certificado",
    	        JOptionPane.PLAIN_MESSAGE,
    	        null,
    	        opcionesCurso,
    	        opcionesCurso[0]
    	    );

    	    if (seleccion == null) return;

    	    for (Curso curso : cursosInscritos) {
    	        if (curso.getNombre().equals(seleccion)) {
    	            obtenerCertificado(curso);
    	            return;
    	        }
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