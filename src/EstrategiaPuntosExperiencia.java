public class EstrategiaPuntosExperiencia extends Estrategia {
    private int unidades;

    public EstrategiaPuntosExperiencia(int unidades) {
        this.unidades = unidades;
    }

    public double calcular(Jugador j) {
        return this.unidades * j.getXp();
    }
}