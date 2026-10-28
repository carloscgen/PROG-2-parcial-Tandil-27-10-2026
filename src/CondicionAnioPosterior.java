public class CondicionAnioPosterior extends Condicion {
    private int anio;

    public CondicionAnioPosterior(int anio) {
        this.anio = anio;
    }

    public boolean cumple(Jugador j) {
        return j.getTemporadaDebut() > this.anio;
    }
}
