public class App {
    private static int fallas = 0;

    public static void main(String[] args) throws Exception {
        // Aura de cada tipo de jugador
        Jugador newbie = new Jugador("Newbie", 2021, 10, 3, new EstrategiaVictoria(500));
        Jugador tryhard = new Jugador("Tryhard", 2019, 4, 1, new EstrategiaPuntosExperiencia(800));
        Jugador pro = new Jugador("Pro", 2023, 4, 2,
                new EstrategiaSuma(new EstrategiaVictoria(1000), new EstrategiaPuntosExperiencia(500)));

        verificar("Newbie, 3 victorias", 1500, newbie.calcularAura());
        verificar("Tryhard, XP 4", 3200, tryhard.calcularAura());
        verificar("Pro, 2 victorias y XP 4", 4000, pro.calcularAura());

        // Cambio de estrategia en cualquier momento
        newbie.cambiarEstrategia(new EstrategiaSuma(new EstrategiaVictoria(1000), new EstrategiaPuntosExperiencia(500)));
        verificar("Newbie con estrategia Pro (3 vic, XP 10)", 8000, newbie.calcularAura());
        newbie.cambiarEstrategia(new EstrategiaVictoria(500));
        verificar("Newbie vuelve a su estrategia", 1500, newbie.calcularAura());

        // Equipo plano: promedio de 1500 y 3200
        Equipo plano = new Equipo("Plano");
        plano.agregarElemento(newbie);
        plano.agregarElemento(tryhard);
        verificar("Equipo plano, promedio del aura", 2350, plano.calcularAura());
        verificar("Equipo plano, suma de victorias", 4, plano.getCantidadVictorias());
        verificar("Equipo plano, XP del primero", 10, plano.getXp());
        verificar("Equipo plano, temporada mas antigua", 2019, plano.getTemporadaDebut());

        // Arbol de dos niveles: promedio de directos (no de todos los jugadores)
        Jugador d = new Jugador("D", 2022, 4, 2,
                new EstrategiaSuma(new EstrategiaVictoria(1000), new EstrategiaPuntosExperiencia(500)));
        Jugador e = new Jugador("E", 2024, 1, 3, new EstrategiaVictoria(500));
        Equipo sub = new Equipo("Sub");
        sub.agregarElemento(tryhard); // 3200
        sub.agregarElemento(d); // 4000
        sub.agregarElemento(e); // 1500
        Equipo arbol = new Equipo("Arbol");
        arbol.agregarElemento(newbie); // 1500
        arbol.agregarElemento(sub); // promedio 2900
        verificar("Subequipo, promedio de sus 3", 2900, sub.calcularAura());
        verificar("Arbol, promedio de directos (1500 y 2900)", 2200, arbol.calcularAura());
        verificar("Arbol, victorias de todos los niveles", 3 + 1 + 2 + 3, arbol.getCantidadVictorias());
        verificar("Arbol, temporada mas antigua de todos los niveles", 2019, arbol.getTemporadaDebut());

        // Movimientos de actitud
        newbie.agregarMov("Six-seven");
        e.agregarMov("Chin touch");
        verificar("Arbol posee Chin touch (anidado)", 1, arbol.poseeMov("Chin touch") ? 1 : 0);
        verificar("Arbol posee Six-seven (directo)", 1, arbol.poseeMov("Six-seven") ? 1 : 0);
        verificar("Arbol no posee Mewing", 0, arbol.poseeMov("Mewing") ? 1 : 0);

        // Equipo vacio
        Equipo vacio = new Equipo("Vacio");
        verificar("Equipo vacio, aura", 0, vacio.calcularAura());
        verificar("Equipo vacio, victorias", 0, vacio.getCantidadVictorias());

        System.out.println(fallas == 0 ? "\nTodo OK" : "\nFallas: " + fallas);
    }

    private static void verificar(String caso, double esperado, double real) {
        boolean ok = Math.abs(esperado - real) < 1e-9;
        if (!ok) {
            fallas++;
        }
        System.out.println((ok ? "OK    " : "FALLA ") + caso + " -> esperado " + esperado + ", real " + real);
    }
}
