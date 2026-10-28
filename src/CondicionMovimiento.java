public class CondicionMovimiento extends Condicion {
    private String movimiento;

    public CondicionMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public boolean cumple(Elemento e) {
        return e.poseeMov(this.movimiento);
    }
}
