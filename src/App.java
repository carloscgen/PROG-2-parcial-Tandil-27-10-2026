import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    private static int fallas = 0;

    public static void main(String[] args) throws Exception {
        // Aura de cada tipo de jugador
        Jugador newbie = new Jugador("Newbie", 2021, 10, 3, new EstrategiaVictoria(500));
        Jugador tryhard = new Jugador("Tryhard", 2019, 4, 1, new EstrategiaPuntosExperiencia(800));
        Jugador pro = new Jugador("Pro", 2023, 4, 2,
                new EstrategiaSuma(new EstrategiaVictoria(1000), new EstrategiaPuntosExperiencia(500)));

        Jugador lurker = new Jugador("Lurker", 2024, 4, 2,
                new EstrategiaSuma(new EstrategiaVictoria(300), new EstrategiaPuntosExperiencia(200)));
        verificar("Lurker, 2 victorias y XP 4 (300 x 2 + 200 x 4)", 1400, lurker.calcularAura());

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

        // Listado con condiciones (el arbol de arriba: newbie 2021 / sub[tryhard 2019, d 2022, e 2024])
        Jugador crack = new Jugador("Crack", 2023, 600000, 1, new EstrategiaVictoria(1));
        sub.agregarElemento(crack);
        verificar("Buscar temporada > 2020 (newbie, d, e, crack)", 4,
                arbol.buscar(new CondicionAnioPosterior(2020)).size());
        verificar("Buscar XP > 500.000 (solo crack)", 1,
                arbol.buscar(new CondicionMayorPuntos(500000)).size());
        verificar("Buscar Chin touch y temporada > 2022 (solo e)", 1,
                arbol.buscar(new CondicionAND(new CondicionMovimiento("Chin touch"),
                        new CondicionAnioPosterior(2022))).size());
        verificar("Buscar Chin touch y temporada > 2024 (nadie)", 0,
                arbol.buscar(new CondicionAND(new CondicionMovimiento("Chin touch"),
                        new CondicionAnioPosterior(2024))).size());
        verificar("El listado no incluye equipos (todos jugadores, 5 en total)", 5,
                arbol.buscar(new CondicionAnioPosterior(0)).size());

        // Equipo de Temporada Alta: solo temporada posterior a 2025
        EquipoCondicionado alta = new EquipoCondicionado("Alta", new CondicionAnioPosterior(2025));
        alta.agregarElemento(jugador("A2026", 2026, 100));
        alta.agregarElemento(jugador("A2025", 2025, 100));
        alta.agregarElemento(jugador("A2024", 2024, 100));
        verificar("Temporada Alta: solo entra 2026", 1, cantidadJugadores(alta));

        // Equipo Novatos: no mas de 10.000 XP
        EquipoCondicionado novatos = new EquipoCondicionado("Novatos", new CondicionMenorIgualPuntos(10000));
        novatos.agregarElemento(jugador("N9999", 2024, 9999));
        novatos.agregarElemento(jugador("N10000", 2024, 10000));
        novatos.agregarElemento(jugador("N10001", 2024, 10001));
        verificar("Novatos: entran 9.999 y 10.000, no 10.001", 2, cantidadJugadores(novatos));
        Equipo chico = new Equipo("Chico");
        chico.agregarElemento(jugador("C5000", 2024, 5000));
        Equipo grande = new Equipo("Grande");
        grande.agregarElemento(jugador("G20000", 2024, 20000));
        novatos.agregarElemento(chico);
        novatos.agregarElemento(grande);
        verificar("Novatos: acepta equipo con XP 5.000 y rechaza el de 20.000", 3, cantidadJugadores(novatos));

        // Equipo Zootopia: solo elementos que puedan hacer "Zorro guapo"
        EquipoCondicionado zootopia = new EquipoCondicionado("Zootopia", new CondicionMovimiento("Zorro guapo"));
        Jugador zorro = jugador("Zorro", 2024, 100);
        zorro.agregarMov("Zorro guapo");
        zootopia.agregarElemento(zorro);
        zootopia.agregarElemento(jugador("Sin movimiento", 2024, 100));
        verificar("Zootopia: solo entra el que hace Zorro guapo", 1, cantidadJugadores(zootopia));
        Equipo conZorro = new Equipo("ConZorro");
        Jugador otroZorro = jugador("OtroZorro", 2024, 100);
        otroZorro.agregarMov("Zorro guapo");
        conZorro.agregarElemento(jugador("Comun", 2024, 100));
        conZorro.agregarElemento(otroZorro);
        Equipo sinZorro = new Equipo("SinZorro");
        sinZorro.agregarElemento(jugador("Comun2", 2024, 100));
        zootopia.agregarElemento(conZorro);
        zootopia.agregarElemento(sinZorro);
        verificar("Zootopia: acepta el equipo donde alguien hace Zorro guapo, rechaza el otro", 3,
                cantidadJugadores(zootopia));

        // Comparacion de jugadores: aura, luego victorias, luego XP
        EstrategiaVictoria porVictoria = new EstrategiaVictoria(100);
        Jugador flojo = new Jugador("flojo", 2020, 1, 1, porVictoria);
        Jugador fuerte = new Jugador("fuerte", 2020, 1, 5, porVictoria);
        Jugador igual = new Jugador("igual", 2020, 1, 1, porVictoria);
        Jugador masXp = new Jugador("masXp", 2020, 9, 1, porVictoria);
        Jugador masVictorias = new Jugador("masVictorias", 2020, 1, 2, new EstrategiaPuntosExperiencia(100));
        verificar("Compara: mayor aura gana", 1, Math.signum(fuerte.compareTo(flojo)));
        verificar("Compara: menor aura pierde", -1, Math.signum(flojo.compareTo(fuerte)));
        verificar("Compara: aura igual, mas XP gana", -1, Math.signum(flojo.compareTo(masXp)));
        verificar("Compara: empate total", 0, flojo.compareTo(igual));
        // mismo aura (100) pero con mas victorias: 2 victorias vs 1
        verificar("Compara: aura igual, mas victorias gana", 1, Math.signum(masVictorias.compareTo(flojo)));
        // el aura manda aunque tenga menos victorias y menos XP
        Jugador pocaVictoria = new Jugador("pocaVictoria", 2020, 10, 1, new EstrategiaPuntosExperiencia(1000)); // aura 10000
        Jugador muchaVictoria = new Jugador("muchaVictoria", 2020, 10, 9, new EstrategiaVictoria(1)); // aura 9
        verificar("Compara: mas aura gana aunque tenga menos victorias (1 vic y aura 10000 vs 9 vic y aura 9)", 1,
                Math.signum(pocaVictoria.compareTo(muchaVictoria)));

        // Uso de compareTo: comparar dos jugadores y ordenar una lista
        System.out.println("\nfuerte vs flojo: "
                + (fuerte.compareTo(flojo) > 0 ? fuerte.getNombre() : flojo.getNombre()) + " es mejor");

        List<Jugador> lista = new ArrayList<>();
        lista.add(flojo);
        lista.add(fuerte);
        lista.add(masXp);
        Collections.sort(lista);
        System.out.println("De peor a mejor: " + nombres(lista));
        Collections.sort(lista, Collections.reverseOrder());
        System.out.println("De mejor a peor: " + nombres(lista));
        System.out.println("El mejor de todos: " + Collections.max(lista).getNombre());
        verificar("Ordenar: el mejor queda primero con reverseOrder", 1, lista.get(0) == fuerte ? 1 : 0);
        verificar("Collections.max devuelve al mejor", 1, Collections.max(lista) == fuerte ? 1 : 0);

        // OPCION 2 (NO es necesaria para el examen, esta solo como otra forma de hacerlo):
        // ordenar con un Comparator en vez de con compareTo. Ver ComparadorPorTemporada.
        ComparadorPorTemporada porTemporada = new ComparadorPorTemporada();
        List<Jugador> variados = new ArrayList<>();
        variados.add(lurker); // temporada 2024
        variados.add(pro); // temporada 2023
        variados.add(newbie); // temporada 2021
        variados.add(tryhard); // temporada 2019
        Collections.sort(variados, porTemporada);
        System.out.println("\nPor temporada de debut (Comparator): " + nombres(variados));
        verificar("Comparator: el mas antiguo queda primero", 1, variados.get(0) == tryhard ? 1 : 0);
        verificar("Comparator: el mas nuevo queda ultimo", 1, variados.get(3) == lurker ? 1 : 0);

        // OPCION 2 con el criterio del enunciado (aura, victorias, XP): tiene que dar el mismo
        // orden que compareTo.
        ComparadorPorMejorJugador porMejor = new ComparadorPorMejorJugador();
        verificar("Comparator del enunciado: mayor aura gana", 1, Math.signum(porMejor.compare(fuerte, flojo)));
        verificar("Comparator del enunciado: mas XP gana con aura y victorias iguales", -1,
                Math.signum(porMejor.compare(flojo, masXp)));
        verificar("Comparator del enunciado: empate total", 0, porMejor.compare(flojo, igual));
        List<Jugador> conComparable = new ArrayList<>(List.of(flojo, fuerte, masXp, masVictorias));
        List<Jugador> conComparator = new ArrayList<>(conComparable);
        Collections.sort(conComparable);
        Collections.sort(conComparator, porMejor);
        System.out.println("\nCon Comparable: " + nombres(conComparable));
        System.out.println("Con Comparator: " + nombres(conComparator));
        verificar("Comparable y Comparator ordenan igual", 1, conComparable.equals(conComparator) ? 1 : 0);

        System.out.println(fallas == 0 ? "\nTodo OK" : "\nFallas: " + fallas);
    }

    private static Jugador jugador(String nombre, int temporada, double xp) {
        return new Jugador(nombre, temporada, xp, 1, new EstrategiaVictoria(1));
    }

    private static List<String> nombres(List<Jugador> jugadores) {
        List<String> nombres = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            nombres.add(jugador.getNombre());
        }
        return nombres;
    }

    private static int cantidadJugadores(Equipo equipo) {
        return equipo.buscar(new CondicionAnioPosterior(0)).size();
    }

    private static void verificar(String caso, double esperado, double real) {
        boolean ok = Math.abs(esperado - real) < 1e-9;
        if (!ok) {
            fallas++;
        }
        System.out.println((ok ? "OK    " : "FALLA ") + caso + " -> esperado " + esperado + ", real " + real);
    }
}
