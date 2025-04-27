package academiaTecno;

import javax.swing.JOptionPane;


public class RegistroUsuario {

    public static void registrar() {
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String apellido = JOptionPane.showInputDialog("Apellido:");
        String correo = JOptionPane.showInputDialog("Correo:");
        String password = JOptionPane.showInputDialog("Contraseña:");

        if (nombre != null && apellido != null && correo != null && password != null) {
            int nuevoId = Main.listaUsuarios.size() + 1;

            Estudiante nuevoEstudiante = new Estudiante(nuevoId, nombre, apellido, correo, password);
            Main.listaUsuarios.add(nuevoEstudiante);

            JOptionPane.showMessageDialog(null, "Estudiante registrado exitosamente: " + nombre + " " + apellido);
        } else {
            JOptionPane.showMessageDialog(null, "Registro cancelado.");
        }
    }
}
