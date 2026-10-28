import java.util.List;

public abstract class Elemento {
    private String nombre;

    public Elemento(String nombre) {
        this.nombre = nombre;
    }

    public abstract double calcularAura();

    public abstract boolean poseeMov(String mov);

    public abstract int getTemporadaDebut();

    public abstract int getCantidadVictorias();

    public abstract double getXp();

    public String getNombre() {
        return this.nombre;
    }

    public abstract List<Jugador> buscar(Condicion c);
}
