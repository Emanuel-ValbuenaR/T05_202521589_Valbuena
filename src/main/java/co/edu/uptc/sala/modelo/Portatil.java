package co.edu.uptc.sala.modelo;

/** Un portatil de la sala de sistemas. */
public class Portatil {

    private String codigo;
    private String marca;
    private EstadoPortatil estado;   // PUNTO 1 · cree el enum EstadoPortatil

    /** Un portatil nuevo siempre empieza DISPONIBLE. */
    public Portatil(String codigo, String marca) {
        this(codigo, marca, EstadoPortatil.DISPONIBLE);
    }

    /** Para reconstruirlo desde el archivo, con el estado que tenia. */
    public Portatil(String codigo, String marca, EstadoPortatil estado) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new DatoInvalidoException("El codigo no puede estar vacio.");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new DatoInvalidoException("La marca no puede estar vacia.");
        }
        this.codigo = codigo;
        this.marca = marca;
        this.estado = estado;
    }

    public String getCodigo()         { return codigo; }
    public String getMarca()          { return marca; }
    public EstadoPortatil getEstado() { return estado; }

    /** Solo se presta uno DISPONIBLE. */
    public void prestar() throws EstadoInvalidoException {
        if (estado != EstadoPortatil.DISPONIBLE) {
            throw new EstadoInvalidoException(codigo, estado, "prestar");
        }
        estado = EstadoPortatil.PRESTADO;
    }

    /** Solo se devuelve uno PRESTADO. */
    public void devolver() throws EstadoInvalidoException {
        if (estado != EstadoPortatil.PRESTADO) {
            throw new EstadoInvalidoException(codigo, estado, "devolver");
        }
       estado = EstadoPortatil.DISPONIBLE;
    }

    @Override
    public String toString() {
        return codigo + " | " + marca + " | " + estado.getTexto();
    }
}
