package academiaTecno;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Main {

    public static List<Usuario> listaUsuarios = new ArrayList<>();

    public static void main(String[] args) {

        // Usuarios preexistentes
        Estudiante estudianteEjemplo = new Estudiante(1, "Carlos", "Lopez", "carlos.lopez@email.com", "pass123");
        Profesor profesorEjemplo = new Profesor(101, "Laura", "Martinez", "laura.martinez@email.com", "profpass456", listaUsuarios);
        Administrador adminEjemplo = new Administrador(999, "Sistema", "Admin", "admin@sistema.com", "sysadmin789", listaUsuarios);

        listaUsuarios.add(estudianteEjemplo);
        listaUsuarios.add(profesorEjemplo);
        listaUsuarios.add(adminEjemplo);

        // Menú: Iniciar sesión / Registrarse
        String[] opciones = {"Iniciar sesión", "Registrarse", "Salir"};
        int seleccion = JOptionPane.showOptionDialog(null,
                "¿Qué deseas hacer?",
                "EduTech",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion == 0) {
            iniciarSesion(); // 👈 если выбрали "Iniciar sesión"
        } else if (seleccion == 1) {
            RegistroUsuario.registrar(); // 👈 если выбрали "Registrarse"
        }

        System.out.println("\n--- Fin de la aplicación ---");
    }

    // Lógica de inicio de sesión
    public static void iniciarSesion() {
        String correoInput = JOptionPane.showInputDialog(null, "Ingrese su correo electrónico:", "Inicio de Sesión", JOptionPane.QUESTION_MESSAGE);
        String contrasenaInput = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Inicio de Sesión", JOptionPane.QUESTION_MESSAGE);

        Usuario usuarioLogueado = null;

        if (correoInput != null && contrasenaInput != null) {
            for (Usuario user : listaUsuarios) {
                if (user.verificarCredenciales(correoInput, contrasenaInput)) {
                    usuarioLogueado = user;
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Inicio de sesión cancelado.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido/a, " + usuarioLogueado.getNombre() + "!", "Inicio de Sesión Exitoso", JOptionPane.INFORMATION_MESSAGE);
            usuarioLogueado.mostrarMenu();
            usuarioLogueado.cerrarSesion();
        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
        }
    }
}