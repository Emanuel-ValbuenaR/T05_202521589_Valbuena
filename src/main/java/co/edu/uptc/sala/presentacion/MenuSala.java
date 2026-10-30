package co.edu.uptc.sala.presentacion;

import co.edu.uptc.sala.modelo.DatoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoPortatil;
import co.edu.uptc.sala.modelo.Portatil;
import co.edu.uptc.sala.negocio.PortatilNoEncontradoException;
import co.edu.uptc.sala.negocio.Sala;
import java.io.IOException;
import javax.swing.JOptionPane;

/** Todo lo que ve el usuario, con JOptionPane. */
public class MenuSala {

    private static final String TITULO = "Sala de sistemas";

    private Sala sala;

    public MenuSala(Sala sala) {
        this.sala = sala;
    }

    public void iniciar() {
        try {
            sala.cargar();
        } catch (IOException | DatoInvalidoException e) {
            avisar("No se pudieron cargar los datos: " + e.getMessage());
        }
        String opcion = "";
        while (!opcion.equals("0")) {
            opcion = mostrarMenu();
            if (opcion.equals("1")) { verPortatiles(); }
            else if (opcion.equals("2")) { prestar(); }
            else if (opcion.equals("3")) { devolver(); }
            else if (opcion.equals("4")) { verResumen(); }
            else if (opcion.equals("0")) { guardar(); }
            else { avisar("Esa opcion no existe."); }
        }
    }

    private String mostrarMenu() {
        String menu = "1. Ver portatiles\n"
                + "2. Prestar\n"
                + "3. Devolver\n"
                + "4. Resumen por estado\n"
                + "0. Salir\n\n"
                + "Escriba el numero de la opcion:";
        String opcion = JOptionPane.showInputDialog(null, menu, TITULO, JOptionPane.PLAIN_MESSAGE);
        if (opcion == null) {
            return "0";
        }
        return opcion.trim();
    }

    private String preguntar(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje, TITULO, JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return "";
        }
        return texto.trim();
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.ERROR_MESSAGE);
    }

    private void verPortatiles() {
        StringBuilder texto = new StringBuilder();
        for (Portatil portatil : sala.getPortatiles()) {
            texto.append(portatil).append("\n");
        }
        JOptionPane.showMessageDialog(null, texto.toString(), TITULO, JOptionPane.PLAIN_MESSAGE);
    }

    private void prestar() {
        try {
            String codigo = preguntar("Codigo del portatil:");
            Portatil portatil = sala.buscar(codigo);
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Prestar el " + portatil.getCodigo() + " (" + portatil.getMarca() + ")?",
                    TITULO,
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                portatil.prestar();
                JOptionPane.showMessageDialog(
                        null, "Portatil " + portatil.getCodigo() + " prestado.",
                        TITULO, JOptionPane.PLAIN_MESSAGE);
            }
        } catch (PortatilNoEncontradoException | EstadoInvalidoException e) {
            avisar(e.getMessage());
        }
    }

    private void devolver() {
        try {
            String codigo = preguntar("Codigo del portatil:");
            Portatil portatil = sala.buscar(codigo);
            portatil.devolver();
           JOptionPane.showMessageDialog(
                    null, "Portatil " + portatil.getCodigo() + " devuelto.",
                    TITULO, JOptionPane.PLAIN_MESSAGE);
        } catch (PortatilNoEncontradoException | EstadoInvalidoException e) {
            avisar(e.getMessage());
        }
    }

    private void verResumen() {
        StringBuilder resumen = new StringBuilder();
        for (EstadoPortatil estado : EstadoPortatil.values()) {
            resumen.append(estado.getTexto())
                    .append(": ")
                    .append(sala.contarPorEstado(estado))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, resumen.toString(), TITULO, JOptionPane.PLAIN_MESSAGE);
    }

    private void guardar() {
        try {
            sala.guardar();
        } catch (IOException e) {
            avisar("No Se Pudo guardar: " + e.getMessage());
        }
    }
}
