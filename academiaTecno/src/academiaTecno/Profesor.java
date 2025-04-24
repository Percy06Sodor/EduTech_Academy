package academiaTecno;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

class Profesor extends Usuario {
	private List<String> cursosCreados;
    private List<Usuario> listaUsuariosGlobal;

    public Profesor(int id, String nombre, String apellido, String correo, String contrasena, List<Usuario> listaUsuarios) {
        super(id, nombre, apellido, correo, contrasena);
        this.cursosCreados = new ArrayList<>();
        this.listaUsuariosGlobal = listaUsuarios;
        cursosCreados.add("Bases de Datos Avanzadas");
    }

    @Override
    public void mostrarMenu() {
         String opcion;
         boolean continuar = true;

         while(continuar) {
             String menuTexto = String.format(
                "--- Menú Profesor (%s %s) ---\n" +
                "Ingrese el número de la opción:\n\n" +
                "1. Crear Curso\n" +
                "2. Ver Cursos Creados\n" +
                "3. Calificar Estudiante\n" +
                "4. Cerrar Sesión",
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
        if (nombreCurso != null) {
             String nombreCursoLimpio = nombreCurso.trim();
            if (!nombreCursoLimpio.isEmpty()) {
                cursosCreados.add(nombreCursoLimpio);
                JOptionPane.showMessageDialog(null, "Curso '" + nombreCursoLimpio + "' creado exitosamente.", "Curso Creado", JOptionPane.INFORMATION_MESSAGE);
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
            for(String curso : cursosCreados) {
                cursosStr += "- " + curso + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, "Cursos Creados:\n" + cursosStr, "Mis Cursos Creados", JOptionPane.INFORMATION_MESSAGE);
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
}