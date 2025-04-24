package academiaTecno;

import javax.swing.JOptionPane;

abstract class Usuario {
	private int id;
    protected String nombre;
    protected String apellido;
    private String correo;
    private String contrasena;

    // Constructor
    public Usuario(int id, String nombre, String apellido, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public boolean verificarCredenciales(String correoIngresado, String contrasenaIngresada) {
        return this.correo.equalsIgnoreCase(correoIngresado) && this.contrasena.equals(contrasenaIngresada);
    }

    public abstract void mostrarMenu();

    public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void cerrarSesion() {
        JOptionPane.showMessageDialog(null, "Sesión cerrada para: " + nombre + " " + apellido, "Cerrar Sesión", JOptionPane.INFORMATION_MESSAGE);
    }
}
