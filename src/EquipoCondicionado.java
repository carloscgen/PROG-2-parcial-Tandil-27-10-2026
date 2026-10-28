public class EquipoCondicionado extends Equipo {
    private Condicion condicion;

    public EquipoCondicionado(String nombre, Condicion condicion) {
        super(nombre);
        this.condicion = condicion;
    }

    public void agregarElemento(Elemento e) {
        if (condicion.cumple(e)) {
            super.agregarElemento(e);
        }
    }
}
