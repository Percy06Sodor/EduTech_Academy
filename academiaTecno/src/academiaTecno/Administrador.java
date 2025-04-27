package academiaTecno;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


class Administrador extends Usuario {
    private List<String> permisos;
    private List<Usuario> listaUsuariosGlobal;
    private List<Curso> cursosCreados;

    public Administrador(int id, String nombre, String apellido, String correo, String contrasena, List<Usuario> listaUsuarios) {
        super(id, nombre, apellido, correo, contrasena);
        this.permisos = new ArrayList<>();
        this.listaUsuariosGlobal = listaUsuarios;
        this.cursosCreados = new ArrayList<>();
        this.permisos.add("gestionUsuarios");
        this.permisos.add("gestionCursos");
    }

    @Override
    public void mostrarMenu(Foro foro) {
        String opcion;
        boolean continuar = true;

        while (continuar) {
            String menuTexto = "--- Menú Administrador (" + nombre + " " + apellido + ") ---\n\n"
                             + "Gestión del Sistema:\n"
                             + "1. Ver Lista de Usuarios\n"
                             + "2. Gestionar Cursos (simulado)\n"
                             + "3. Moderar Foro (simulado)\n\n"
                             + "Gestión de Contenidos:\n"
                             + "4. Agregar Material al Curso\n"
                             + "5. Agregar Material a una Clase\n\n"
                             + "Foro:\n"
                             + "6. Ver Temas del Foro\n"
                             + "7. Ver Mensajes de un Tema\n"
                             + "8. Crear Nuevo Tema\n"
                             + "9. Responder en un Tema\n\n"
                             + "Opciones de Sesión:\n"
                             + "10. Cerrar Sesión";

            opcion = JOptionPane.showInputDialog(null, menuTexto, "Menú Administrador", JOptionPane.PLAIN_MESSAGE);

            if (opcion == null) {
                continuar = false;
                break;
            }

            switch (opcion.trim()) {
                case "1":
                    gestionarUsuariosInteractivo();
                    break;
                case "2":
                    gestionarCursosSimulado();
                    break;
                case "3":
                    moderarForoSimulado(foro);
                    break;
                case "4":
                    agregarMaterialCurso();
                    break;
                case "5":
                    agregarMaterialClase();
                    break;
                case "6":
                    verTemasForo(foro);
                    break;
                case "7":
                    verTemaEspecifico(foro);
                    break;
                case "8":
                    crearNuevoTemaForo(foro, this);
                    break;
                case "9":
                    responderTema(foro, this);
                    break;
                case "10":
                    continuar = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        }
    }

    private void gestionarUsuariosInteractivo() {
        String listaTexto = "--- Lista de Usuarios ---\n";
        if (listaUsuariosGlobal == null || listaUsuariosGlobal.isEmpty()) {
            listaTexto += "No hay usuarios registrados o no se tiene acceso.";
        } else {
            for (Usuario usr : listaUsuariosGlobal) {
                String tipo = usr.getClass().getSimpleName();
                listaTexto += "ID: " + usr.getId() + ", Nombre: " + usr.getNombre() + " " + usr.getApellido() + ", Correo: " + usr.getCorreo() + ", Tipo: " + tipo + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, listaTexto, "Gestión de Usuarios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void gestionarCursosSimulado() {
         JOptionPane.showMessageDialog(null, "Función 'Gestionar Cursos' no implementada completamente.", "Gestión de Cursos", JOptionPane.INFORMATION_MESSAGE);
         // Por completar
    }

    private void moderarForoSimulado(Foro foro) {
         foro.moderarForo();
         //Por completar
         // JOptionPane.showMessageDialog(null, "Función 'Moderar Foro' no implementada.", "Moderar Foro", JOptionPane.INFORMATION_MESSAGE);
    }

    private Curso seleccionarCurso() {
         if (cursosCreados == null || cursosCreados.isEmpty()) {
             JOptionPane.showMessageDialog(null, "No hay cursos creados/gestionados por este administrador.");
             return null;
         }

         String[] nombresCursos = new String[cursosCreados.size()];
         for (int i = 0; i < cursosCreados.size(); i++) {
             nombresCursos[i] = cursosCreados.get(i).getNombre();
         }

         String seleccion = (String) JOptionPane.showInputDialog(null,
             "Selecciona un curso:",
             "Cursos Disponibles",
             JOptionPane.PLAIN_MESSAGE, null,
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
    //Por completar
    private void agregarMaterialCurso() {
        Curso curso = seleccionarCurso();
        if (curso == null)
        	return;

        String titulo = JOptionPane.showInputDialog("Título del material:");
        String tipo = JOptionPane.showInputDialog("Tipo (PDF, Video, etc):");
        String url = JOptionPane.showInputDialog("URL o ubicación:");

        if (titulo != null && !titulo.trim().isEmpty() &&
            tipo != null && !tipo.trim().isEmpty() &&
            url != null && !url.trim().isEmpty()) {
            Material nuevo = new Material(titulo.trim(), tipo.trim(), url.trim());
            curso.agregarMaterialGeneral(nuevo);

            JOptionPane.showMessageDialog(null, "Material agregado al curso '" + curso.getNombre() + "'.");
        } else {
             JOptionPane.showMessageDialog(null, "Todos los campos son requeridos.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void agregarMaterialClase() {
        Curso curso = seleccionarCurso();
        if (curso == null) return;

        List<Clase> clases = curso.getClases();
        if (clases == null || clases.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este curso no tiene clases definidas.");
            return;
        }

        String[] opcionesClases = new String[clases.size()];
        for (int i = 0; i < clases.size(); i++) {
            opcionesClases[i] = "Clase " + clases.get(i).getNumero();
        }

        String seleccionClase = (String) JOptionPane.showInputDialog(
            null, "Selecciona la clase:",
            "Clases del Curso '" + curso.getNombre() + "'",
            JOptionPane.PLAIN_MESSAGE,
            null, opcionesClases, opcionesClases[0]
        );

        if (seleccionClase == null) return;
        Clase claseSeleccionada = null;
        for(Clase c : clases) {
            if (seleccionClase.equals("Clase " + c.getNumero())) {
                claseSeleccionada = c;
                break;
            }
        }

        if (claseSeleccionada == null) {
             JOptionPane.showMessageDialog(null, "Error al encontrar la clase seleccionada.");
             return;
        }


        // Pide datos del material
        String titulo = JOptionPane.showInputDialog("Título del material para la " + seleccionClase + ":");
        String tipo = JOptionPane.showInputDialog("Tipo (PDF, Video, etc):");
        String url = JOptionPane.showInputDialog("URL o ubicación:");

        if (titulo != null && !titulo.trim().isEmpty() &&
            tipo != null && !tipo.trim().isEmpty() &&
            url != null && !url.trim().isEmpty()) {

            Material nuevo = new Material(titulo.trim(), tipo.trim(), url.trim());
            claseSeleccionada.agregarMaterial(nuevo);

            JOptionPane.showMessageDialog(null, "Material agregado a la " + seleccionClase + ".");
        } else {
             JOptionPane.showMessageDialog(null, "Todos los campos son requeridos.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}