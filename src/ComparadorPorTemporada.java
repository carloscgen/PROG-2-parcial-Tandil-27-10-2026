import java.util.Comparator;

// OPCION 2 (NO es necesaria para el examen, esta solo como otra forma de hacerlo).
// Un Comparator es un criterio de comparacion que vive en una clase aparte de Jugador.
// Sirve cuando hay varios criterios intercambiables (uno por clase). El enunciado de este
// parcial pide un solo mecanismo y siempre el mismo, por eso la solucion es Comparable
// (compareTo en Jugador), y esta clase no forma parte de la solucion.
public class ComparadorPorTemporada implements Comparator<Jugador> {

    public int compare(Jugador a, Jugador b) {
        return Integer.compare(a.getTemporadaDebut(), b.getTemporadaDebut());
    }
}
