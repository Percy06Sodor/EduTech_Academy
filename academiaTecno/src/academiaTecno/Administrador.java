package academiaTecno;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

class Administrador extends Usuario {
	private List<String> permisos;
    private List<Usuario> listaUsuariosGlobal;

    public Administrador(int id, String nombre, String apellido, String correo, String contrasena, List<Usuario> listaUsuarios) {
        super(id, nombre, apellido, correo, contrasena);
        this.permisos = new ArrayList<>();
        this.listaUsuariosGlobal = listaUsuarios;
        permisos.add("gestionUsuarios");
        permisos.add("gestionCursos");
    }

    @Override
    public void mostrarMenu() {
        String opcion;
        boolean continuar = true;

        while(continuar) {
            String menuTexto = String.format(
                "--- Menú Administrador (%s %s) ---\n" +
                "Ingrese el número de la opción:\n\n" +
                "1. Gestionar Usuarios (Ver Lista)\n" +
                "2. Gestionar Cursos (Simulado)\n" +
                "3. Moderar Foro (Simulado)\n" +
                "4. Cerrar Sesión",
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
}