public class CondicionMovimiento extends Condicion {
    private String movimiento;

    public CondicionMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public boolean cumple(Jugador j) {
        return j.poseeMov(this.movimiento);
    }
}
