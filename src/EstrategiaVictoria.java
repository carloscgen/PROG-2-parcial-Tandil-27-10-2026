public class EstrategiaVictoria extends Estrategia {
    private int unidades;

    public EstrategiaVictoria(int unidades) {
        this.unidades = unidades;
    }

    public double calcular(Jugador j) {
        return this.unidades * j.getCantidadVictorias();
    }
}
