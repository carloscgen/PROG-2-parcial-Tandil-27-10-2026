import java.util.ArrayList;
import java.util.List;

public class Jugador extends Elemento {
    private int tempDebut;
    private double puntoExp;
    private List<String> movimientos;
    private int victorias;
    private Estrategia estrategia;

    public Jugador(String nombre, int temp, double puntosExp, int vic, Estrategia estrategia) {
        super(nombre);
        this.tempDebut = temp;
        this.puntoExp = puntosExp;
        this.movimientos = new ArrayList<>();
        this.victorias = vic;
        this.estrategia = estrategia;
    }

    public List<Jugador> buscar(Condicion c) {
        List<Jugador> lista = new ArrayList<>();
        if (c.cumple(this)) {
            lista.add(this);
        }
        return lista;
    }

    public void agregarMov(String mov) {
        this.movimientos.add(mov);
    }

    public boolean poseeMov(String mov) {
        return this.movimientos.contains(mov);
    }

    public double calcularAura() {
        return estrategia.calcular(this);
    }

    public void cambiarEstrategia(Estrategia e) {
        this.estrategia = e;
    }

    public int getTemporadaDebut() {
        return this.tempDebut;
    }

    public double getXp() {
        return this.puntoExp;
    }

    public List<String> getMovimientos() {
        return new ArrayList<>(this.movimientos);
    }

    public int getCantidadVictorias() {
        return this.victorias;
    }
}
