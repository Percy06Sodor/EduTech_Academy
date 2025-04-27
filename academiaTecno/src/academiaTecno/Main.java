package academiaTecno;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Main {

    public static List<Usuario> listaUsuarios = new ArrayList<>();
    public static Foro foroGeneral = new Foro();

    public static void main(String[] args) {
        cargarUsuariosDeEjemplo();

        //Chicos tuve que agregar un while para repetir el Login y desactive el registro para usarla cuando tengamos la base de dates
        boolean salir = false;
        while (!salir) {
            String[] opciones = {"Iniciar sesión", "Registrarse", "Salir"};
            int seleccion = JOptionPane.showOptionDialog(null, "¿Qué deseas hacer?", "EduTech - Academia Tecno", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    opciones,
                    opciones[0]);

            if (seleccion == 0) {
                iniciarSesion();
            } else if (seleccion == 1) {
                 // RegistroUsuario.registrar(); // Desactivado por el momento
                 JOptionPane.showMessageDialog(null,"Funcionalidad 'Registrarse' aún no implementada.");
            } else if (seleccion == 2 || seleccion == JOptionPane.CLOSED_OPTION) {
                salir = true;
                JOptionPane.showMessageDialog(null, "Saliendo de la aplicación...", "Adiós", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        System.out.println("\n--- Fin de la aplicación ---");
    }
    
    //los ususarios de ejemplo los deje como estaban
    public static void cargarUsuariosDeEjemplo() {
        Estudiante estudianteEjemplo = new Estudiante(1, "Carlos", "Lopez", "carlos.lopez@email.com", "contraseña123");
        Profesor profesorEjemplo = new Profesor(101, "Laura", "Martinez", "laura.martinez@email.com", "acamandoyo123", listaUsuarios);
        Administrador adminEjemplo = new Administrador(999, "Sistema", "Admin", "admin@sistema.com", "soyadmin999", listaUsuarios);

        listaUsuarios.add(estudianteEjemplo);
        listaUsuarios.add(profesorEjemplo);
        listaUsuarios.add(adminEjemplo);
    }
    
    public static void iniciarSesion() {
        String correoInput = JOptionPane.showInputDialog(null, "Ingrese su correo electrónico:", "Inicio de Sesión", JOptionPane.QUESTION_MESSAGE);
        if (correoInput == null) {
             JOptionPane.showMessageDialog(null, "Inicio de sesión cancelado.", "Cancelado", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String contrasenaInput = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Inicio de Sesión", JOptionPane.QUESTION_MESSAGE);
        if (contrasenaInput == null) {
             JOptionPane.showMessageDialog(null, "Inicio de sesión cancelado.", "Cancelado", JOptionPane.WARNING_MESSAGE);
             return;
        }

        Usuario usuarioLogueado = null;

        for (Usuario user : listaUsuarios) {
            if (user.verificarCredenciales(correoInput, contrasenaInput)) {
                usuarioLogueado = user;
                break;
            }
        }

        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido/a, " + usuarioLogueado.getNombre() + "!", "Inicio de Sesión Exitoso", JOptionPane.INFORMATION_MESSAGE);
            usuarioLogueado.mostrarMenu(foroGeneral);
            usuarioLogueado.cerrarSesion();

        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
        }
    }
}