package academiaTecno;

import javax.swing.JOptionPane;
import java.time.LocalDate;

abstract class Usuario {
    private int id;
    protected String nombre;
    protected String apellido;
    private String correo;
    private String contrasena;

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
    
    public abstract void mostrarMenu(Foro foro);

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

    protected void verTemasForo(Foro foro) {
        String temasStr = foro.obtenerListaTemasFormateada();
        JOptionPane.showMessageDialog(null, "--- Temas del Foro ---\n" + temasStr, "Foro", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void verTemaEspecifico(Foro foro) {
        String idTemaStr = JOptionPane.showInputDialog(null, "Ingrese el ID del tema que desea ver:", "Ver Tema", JOptionPane.QUESTION_MESSAGE);
        if (idTemaStr == null)
        	return;
        //use lo que vimos en clase, agregue la excepción NumberFormatException e y el mensaje para que usen un numero entero, no lo saquen.
        try {
            int idTema = Integer.parseInt(idTemaStr.trim());
            Tema tema = foro.buscarTemaPorId(idTema);

            if (tema != null) {
                String mensajesStr = tema.obtenerMensajesFormateados();
                JOptionPane.showMessageDialog(null, "--- Tema: " + tema.getTitulo() + " (ID: " + tema.getId() + ") ---\n" + mensajesStr, "Detalle del Tema", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún tema con el ID " + idTema + ".", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID ingresado ('" + idTemaStr + "') no es un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void responderTema(Foro foro, Usuario autor) {
        String idTemaStr = JOptionPane.showInputDialog(null, "Ingrese el ID del tema al que desea responder:", "Responder Tema", JOptionPane.QUESTION_MESSAGE);
        if (idTemaStr == null)
        	return;

        try {
            int idTema = Integer.parseInt(idTemaStr.trim());
            Tema tema = foro.buscarTemaPorId(idTema);

            if (tema != null) {
                String contenidoMensaje = JOptionPane.showInputDialog(null, "Ingrese su respuesta para el tema '" + tema.getTitulo() + "':", "Escribir Respuesta", JOptionPane.QUESTION_MESSAGE);
                if (contenidoMensaje != null) {
                     String contenidoLimpio = contenidoMensaje.trim();
                     if (!contenidoLimpio.isEmpty()) {
                         Mensaje nuevoMensaje = new Mensaje(tema.getRespuestas().size() + 1, contenidoLimpio, LocalDate.now(), autor);

                         tema.agregarRespuesta(nuevoMensaje);
                         JOptionPane.showMessageDialog(null, "Respuesta añadida exitosamente al tema '" + tema.getTitulo() + "'.", "Respuesta Enviada", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                         JOptionPane.showMessageDialog(null, "El contenido de la respuesta no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún tema con el ID " + idTema + ".", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID ingresado ('" + idTemaStr + "') no es un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

     protected void crearNuevoTemaForo(Foro foro, Usuario autor) {
        String tituloTema = JOptionPane.showInputDialog(null, "Ingrese el título para el nuevo tema:", "Crear Tema", JOptionPane.QUESTION_MESSAGE);
        if (tituloTema != null) {
            String tituloLimpio = tituloTema.trim();
            if (!tituloLimpio.isEmpty()) {
                String primerMensaje = JOptionPane.showInputDialog(null, "Ingrese el primer mensaje para el tema '" + tituloLimpio + "':", "Primer Mensaje", JOptionPane.QUESTION_MESSAGE);
                if (primerMensaje != null) {
                    String mensajeLimpio = primerMensaje.trim();
                    if (!mensajeLimpio.isEmpty()) {
                        foro.crearTema(tituloLimpio, mensajeLimpio, autor);
                        JOptionPane.showMessageDialog(null, "Intento de creación de tema '" + tituloLimpio + "' realizado.", "Tema Creado", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                         JOptionPane.showMessageDialog(null, "El primer mensaje no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                 JOptionPane.showMessageDialog(null, "El título del tema no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
