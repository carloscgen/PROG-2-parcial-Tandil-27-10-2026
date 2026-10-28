public class CondicionMayorPuntos extends Condicion {
    private int puntos;

    public CondicionMayorPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean cumple(Elemento e) {
        return e.getXp() > this.puntos;
    }
}
