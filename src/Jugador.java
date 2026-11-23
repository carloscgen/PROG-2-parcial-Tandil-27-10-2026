import java.util.ArrayList;
import java.util.List;

public class Jugador extends Elemento implements Comparable<Jugador> {
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

    public void cambiarEstrategia(Estrategia e) {
        this.estrategia = e;
    }

    public int getTemporadaDebut() {
        return this.tempDebut;
    }

    public int getCantidadVictorias() {
        return this.victorias;
    }

    public double getXp() {
        return this.puntoExp;
    }

    public double calcularAura() {
        return estrategia.calcular(this);
    }

    public boolean poseeMov(String mov) {
        return this.movimientos.contains(mov);
    }

    public void agregarMov(String mov) {
        if (!poseeMov(mov)) {
            this.movimientos.add(mov);
        }
    }

    public List<String> getMovimientos() {
        return new ArrayList<>(this.movimientos);
    }

    public List<Jugador> buscar(Condicion c) {
        List<Jugador> lista = new ArrayList<>();
        if (c.cumple(this)) {
            lista.add(this);
        }
        return lista;
    }

    public int compareTo(Jugador j) {
        int resultado = Double.compare(this.calcularAura(), j.calcularAura());
        if (resultado != 0) {
            return resultado;
        }
        resultado = Integer.compare(this.getCantidadVictorias(), j.getCantidadVictorias());
        if (resultado != 0) {
            return resultado;
        }
        return Double.compare(this.getXp(), j.getXp());
    }

}
