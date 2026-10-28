public class CondicionMayorPuntos extends Condicion {
    private int puntos;

    public CondicionMayorPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean cumple(Jugador j) {
        return j.getXp() > this.puntos;
    }
}
