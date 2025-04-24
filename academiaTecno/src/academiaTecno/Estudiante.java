package academiaTecno;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

class Estudiante extends Usuario {
	private List<Curso> cursosInscritos;
	private List<Calificacion> historialExamenes;

    public Estudiante(int id, String nombre, String apellido, String correo, String contrasena) {
        super(id, nombre, apellido, correo, contrasena);
        this.cursosInscritos = new ArrayList<>();
        this.historialExamenes = new ArrayList<>();
        // son datos de ejemplo solamente para que no quede vacio el cuadro de joptionpane, si quieren agreguen algo mas

        // Curso básico con una sola clase
        cursosInscritos.add(new Curso("Introducción a la Programación", "Básico"));
        
        // Curso con clases detalladas
        Curso cursoJava = new Curso("Java", "Intermedio");
        cursoJava.agregarClase(new Clase(1, "Variables y Tipos de Datos"));
        cursoJava.agregarClase(new Clase(2, "Condicionales"));
        cursosInscritos.add(cursoJava);
        
        historialExamenes.add(new Calificacion("Examen Inicial", 7.5));
    }

    @Override
    public void mostrarMenu() {
        String opcion;
        boolean continuar = true;

        while (continuar) {
            String menuTexto = String.format(
                "--- Menú Estudiante (%s %s) ---\n" +
                "Ingrese el número de la opción:\n\n" +
                "1. Ver Cursos Inscritos\n" +
                "2. Ver Calificaciones\n" +
                "3. Inscribirse a Nuevo Curso\n" +
                "4. Ver Progreso General\n" +
                "5. Ver Clases de un Curso\n" +
                "6. Cerrar Sesión",
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
                    continuar = false;
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
    	    historialExamenes.add(new Calificacion(nombreEvaluacion.trim(), calificacion));
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
}