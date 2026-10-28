import java.util.Comparator;

// OPCION 2 (NO es necesaria para el examen, esta solo como otra forma de hacerlo).
// Es el mismo criterio del enunciado que implementa compareTo en Jugador (aura, luego
// victorias, luego XP), pero escrito como Comparator en una clase aparte. Si se resolviera
// el parcial con esta opcion, Jugador no implementaria Comparable y se ordenaria con
// Collections.sort(lista, new ComparadorPorMejorJugador()).
public class ComparadorPorMejorJugador implements Comparator<Jugador> {

    public int compare(Jugador a, Jugador b) {
        int resultado = Double.compare(a.calcularAura(), b.calcularAura());
        if (resultado != 0) {
            return resultado;
        }
        resultado = Integer.compare(a.getCantidadVictorias(), b.getCantidadVictorias());
        if (resultado != 0) {
            return resultado;
        }
        return Double.compare(a.getXp(), b.getXp());
    }
}
