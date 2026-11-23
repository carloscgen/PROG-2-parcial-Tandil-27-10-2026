import java.util.ArrayList;
import java.util.List;

public class Equipo extends Elemento {
    private List<Elemento> elementos;

    public Equipo(String nombre) {
        super(nombre);
        this.elementos = new ArrayList<>();
    }

    public int getTemporadaDebut() {
        if (elementos.isEmpty()) {
            return 0;
        }
        int anioAntiguo = elementos.get(0).getTemporadaDebut();
        for (Elemento elemento : elementos) {
            int temp = elemento.getTemporadaDebut();
            if (temp < anioAntiguo) {
                anioAntiguo = temp;
            }
        }
        return anioAntiguo;
    }

    public int getCantidadVictorias() {
        int total = 0;
        for (Elemento elemento : elementos) {
            total += elemento.getCantidadVictorias();
        }
        return total;
    }

    public double getXp() {
        if (elementos.isEmpty()) {
            return 0;
        }
        return elementos.get(0).getXp();
    }

    public double calcularAura() {
        if (elementos.isEmpty()) {
            return 0;
        }
        double valor = 0;

        for (Elemento elemento : elementos) {
            valor += elemento.calcularAura();

        }
        return valor / elementos.size(); // SIN ANIDAMIENTOS
    }

    public boolean poseeMov(String mov) {
        for (Elemento elemento : elementos) {
            if (elemento.poseeMov(mov)) {
                return true;
            }
        }
        return false;
    }

    public List<Jugador> buscar(Condicion c) {
        List<Jugador> lista = new ArrayList<>();
        for (Elemento elemento : elementos) {
            lista.addAll(elemento.buscar(c));
        }
        return lista;
    }

    public void agregarElemento(Elemento e) {
        this.elementos.add(e);
    }

}
