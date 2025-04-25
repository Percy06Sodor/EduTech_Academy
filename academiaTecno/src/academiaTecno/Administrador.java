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
        permisos.add("gestionUsuarios");
        permisos.add("gestionCursos");

        // Curso de ejemplo para no dejarlo vacío
        cursosCreados.add(new Curso("Administración General", "General"));
    }

    @Override
    public void mostrarMenu() {
        String opcion;
        boolean continuar = true;

        while (continuar) {
            String menuTexto = String.format(
                "--- Menú Administrador (%s %s) ---\n\n" +
                "Gestión del Sistema:\n" +
                "1. Ver Lista de Usuarios\n" +
                "2. Gestionar Cursos (simulado)\n" +
                "3. Moderar Foro (simulado)\n\n" +
                "Gestión de Contenidos:\n" +
                "4. Agregar Material al Curso\n" +
                "5. Agregar Material a una Clase\n\n" +
                "Foro:\n" +
                "6. Ver Foro\n" +
                "7. Crear Nuevo Tema\n" +
                "8. Responder en un Tema\n\n" +
                "Opciones de Sesión:\n" +
                "9. Cerrar Sesión",
                nombre, apellido
            );

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
                    moderarForoSimulado();
                    break;
                case "4":
                    agregarMaterialCurso();
                    break;
                case "5":
                    agregarMaterialClase();
                    break;
                case "6":
                    verForo();
                    break;
                case "7":
                    crearTema();
                    break;
                case "8":
                    agregarMensaje();
                    break;
                case "9":
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
        if (listaUsuariosGlobal.isEmpty()) {
            listaTexto += "No hay usuarios registrados.";
        } else {
            for (Usuario usr : listaUsuariosGlobal) {
                String tipo = usr.getClass().getSimpleName();
                listaTexto += String.format("ID: %d, Nombre: %s %s, Correo: %s, Tipo: %s\n",
                                                usr.getId(), usr.getNombre(), usr.getApellido(), usr.getCorreo(), tipo);
            }
        }
        JOptionPane.showMessageDialog(null, listaTexto, "Gestión de Usuarios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void gestionarCursosSimulado() {
         JOptionPane.showMessageDialog(null, "Función 'Gestionar Cursos' no implementada completamente.", "Gestión de Cursos", JOptionPane.INFORMATION_MESSAGE);
    }

     private void moderarForoSimulado() {
         JOptionPane.showMessageDialog(null, "Función 'Moderar Foro' no implementada.", "Moderar Foro", JOptionPane.INFORMATION_MESSAGE);
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