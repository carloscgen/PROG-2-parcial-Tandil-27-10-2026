public class CondicionAnioPosterior extends Condicion {
    private int anio;

    public CondicionAnioPosterior(int anio) {
        this.anio = anio;
    }

    public boolean cumple(Elemento e) {
        return e.getTemporadaDebut() > this.anio;
    }
}
