public class CondicionMenorIgualPuntos extends Condicion {
    private int puntos;

    public CondicionMenorIgualPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean cumple(Elemento e) {
        return e.getXp() <= this.puntos;
    }
}
