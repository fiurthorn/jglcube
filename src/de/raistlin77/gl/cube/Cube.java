/*
 *
 */
package de.raistlin77.gl.cube;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import de.raistlin77.gl.cube.tiles.Area;
import de.raistlin77.gl.cube.tiles.Corner;
import de.raistlin77.gl.cube.tiles.Edge;
import de.raistlin77.gl.cube.tiles.Tile;

/**
 * The Class Cube.
 */
public class Cube {

    // @formatter:off
    /*
                 +--+--+--+
                 |10|22|17|
                 +--+--+--+
                 |11|23|16|
                 +--+--+--+
                 | 1| 2| 3|
      +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+
      |10|11| 1| | 1| 2| 3| | 3|16|17| |17|22|10| > 7 10
      +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+
      |12|13| 4| | 4| 5| 6| | 6|18|19| |19|26|12| > 8 11
      +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+
      |14|15| 7| | 7| 8| 9| | 9|20|21| |21|25|14| > 9 12
      +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+
      ^^ ^^ ^^   | 7| 8| 9|
      13 14 15   +--+--+--+
                 |15|24|20|
      16 17 18   +--+--+--+
                 |14|25|21|
                 +--+--+--+
                  ^^ ^^ ^^
                  1  2  3
                  4  5  6.
     */
    // @formatter:on

    private Area ST05, ST13, ST18, ST23, ST24, ST26;

    /** The S t25. */
    private Edge ST02, ST04, ST06, ST08, ST11, ST12, ST15, ST16, ST19, ST20, ST22, ST25;

    /** The S t21. */
    private Corner ST01, ST03, ST07, ST09, ST10, ST14, ST17, ST21;

    /** The T26. */
    private Area T05, T13, T18, T23, T24, T26;

    /** The T25. */
    private Edge T02, T04, T06, T08, T11, T12, T15, T16, T19, T20, T22, T25;

    /** The T21. */
    private Corner T01, T03, T07, T09, T10, T14, T17, T21;

    /** The count moves. */
    private long countMoves;

    /** The solveing. */
    private boolean solveing = false;

    /** The solve list. */
    private List<Byte> solveList;

    /** The show. */
    private boolean show = true;

    private int recDeep = 4;

    /**
     * Instantiates a new cube.
     */
    public Cube() {
        setNew();
        solveing = false;
        solveList = new ArrayList<>();

    }

    /**
     * Sets the new.
     */
    public void setNew() {
        T01 = new Corner(new Tile.Order[] { Tile.Order.ABOVE, Tile.Order.AHEAD, Tile.Order.LEFT },
                new Tile.Color[] { Tile.Color.WHITE, Tile.Color.RED, Tile.Color.BLUE });
        T02 = new Edge(new Tile.Order[] { Tile.Order.ABOVE, Tile.Order.AHEAD },
                new Tile.Color[] { Tile.Color.WHITE, Tile.Color.RED });
        T03 = new Corner(new Tile.Order[] { Tile.Order.ABOVE, Tile.Order.AHEAD, Tile.Order.RIGHT },
                new Tile.Color[] { Tile.Color.WHITE, Tile.Color.RED, Tile.Color.GREEN });
        T04 = new Edge(new Tile.Order[] { Tile.Order.AHEAD, Tile.Order.LEFT },
                new Tile.Color[] { Tile.Color.RED, Tile.Color.BLUE });
        T05 = new Area(new Tile.Order[] { Tile.Order.AHEAD }, new Tile.Color[] { Tile.Color.RED });
        T06 = new Edge(new Tile.Order[] { Tile.Order.AHEAD, Tile.Order.RIGHT },
                new Tile.Color[] { Tile.Color.RED, Tile.Color.GREEN });
        T07 = new Corner(new Tile.Order[] { Tile.Order.AHEAD, Tile.Order.BELOW, Tile.Order.LEFT },
                new Tile.Color[] { Tile.Color.RED, Tile.Color.YELLOW, Tile.Color.BLUE });
        T08 = new Edge(new Tile.Order[] { Tile.Order.AHEAD, Tile.Order.BELOW },
                new Tile.Color[] { Tile.Color.RED, Tile.Color.YELLOW });
        T09 = new Corner(new Tile.Order[] { Tile.Order.AHEAD, Tile.Order.BELOW, Tile.Order.RIGHT },
                new Tile.Color[] { Tile.Color.RED, Tile.Color.YELLOW, Tile.Color.GREEN });
        T10 = new Corner(new Tile.Order[] { Tile.Order.ABOVE, Tile.Order.LEFT, Tile.Order.BEHIND },
                new Tile.Color[] { Tile.Color.WHITE, Tile.Color.BLUE, Tile.Color.ORANGE });
        T11 = new Edge(new Tile.Order[] { Tile.Order.LEFT, Tile.Order.ABOVE },
                new Tile.Color[] { Tile.Color.BLUE, Tile.Color.WHITE });
        T12 = new Edge(new Tile.Order[] { Tile.Order.BEHIND, Tile.Order.LEFT },
                new Tile.Color[] { Tile.Color.ORANGE, Tile.Color.BLUE });
        T13 = new Area(new Tile.Order[] { Tile.Order.LEFT }, new Tile.Color[] { Tile.Color.BLUE });
        T14 = new Corner(new Tile.Order[] { Tile.Order.LEFT, Tile.Order.BELOW, Tile.Order.BEHIND },
                new Tile.Color[] { Tile.Color.BLUE, Tile.Color.YELLOW, Tile.Color.ORANGE });
        T15 = new Edge(new Tile.Order[] { Tile.Order.BELOW, Tile.Order.LEFT },
                new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.BLUE });
        T16 = new Edge(new Tile.Order[] { Tile.Order.ABOVE, Tile.Order.RIGHT },
                new Tile.Color[] { Tile.Color.WHITE, Tile.Color.GREEN });
        T17 = new Corner(new Tile.Order[] { Tile.Order.ABOVE, Tile.Order.RIGHT, Tile.Order.BEHIND },
                new Tile.Color[] { Tile.Color.WHITE, Tile.Color.GREEN, Tile.Color.ORANGE });
        T18 = new Area(new Tile.Order[] { Tile.Order.RIGHT }, new Tile.Color[] { Tile.Color.GREEN });
        T19 = new Edge(new Tile.Order[] { Tile.Order.RIGHT, Tile.Order.BEHIND },
                new Tile.Color[] { Tile.Color.GREEN, Tile.Color.ORANGE });
        T20 = new Edge(new Tile.Order[] { Tile.Order.RIGHT, Tile.Order.BELOW },
                new Tile.Color[] { Tile.Color.GREEN, Tile.Color.YELLOW });
        T21 = new Corner(new Tile.Order[] { Tile.Order.BELOW, Tile.Order.RIGHT, Tile.Order.BEHIND },
                new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.GREEN, Tile.Color.ORANGE });
        T22 = new Edge(new Tile.Order[] { Tile.Order.BEHIND, Tile.Order.ABOVE },
                new Tile.Color[] { Tile.Color.ORANGE, Tile.Color.WHITE });
        T23 = new Area(new Tile.Order[] { Tile.Order.ABOVE }, new Tile.Color[] { Tile.Color.WHITE });
        T24 = new Area(new Tile.Order[] { Tile.Order.BELOW }, new Tile.Color[] { Tile.Color.YELLOW });
        T25 = new Edge(new Tile.Order[] { Tile.Order.BEHIND, Tile.Order.BELOW },
                new Tile.Color[] { Tile.Color.ORANGE, Tile.Color.YELLOW });
        T26 = new Area(new Tile.Order[] { Tile.Order.BEHIND }, new Tile.Color[] { Tile.Color.ORANGE });
    }

    /**
     * Move.
     *
     * @param move
     *             the move
     */
    public void move(byte move) {
        // Ruft die Funktion in abhängikeit von der drehrichtung die F. SW(...)
        // auf
        countMoves++;
        if (solveing) {
            // KC->drawCube(QP); // wenn FLoes gesetzt Würfen neu zeichnen
            // cout << r << "|"<<flush;
            solveList.add(move);
        }
        // übergibt der Funktion SW die Teile zum Tauschen
        // zuerst ecken dann kanten
        switch (move) {
            case 1:
                SW(T01, T04, T07, T15, T14, T12, T10, T11, move);
                break;
            case 2:
                SW(T02, T05, T08, T24, T25, T26, T22, T23, move);
                break;
            case 3:
                SW(T03, T06, T09, T20, T21, T19, T17, T16, move);
                break;
            case 4:
                SW(T10, T11, T14, T12, T07, T15, T01, T04, move);
                break;
            case 5:
                SW(T22, T23, T25, T26, T08, T24, T02, T05, move);
                break;
            case 6:
                SW(T17, T16, T21, T19, T09, T20, T03, T06, move);
                break;
            case 7:
                SW(T03, T02, T01, T11, T10, T22, T17, T16, move);
                break;
            case 8:
                SW(T06, T05, T04, T13, T12, T26, T19, T18, move);
                break;
            case 9:
                SW(T09, T08, T07, T15, T14, T25, T21, T20, move);
                break;
            case 10:
                SW(T17, T16, T10, T22, T01, T11, T03, T02, move);
                break;
            case 11:
                SW(T19, T18, T12, T26, T04, T13, T06, T05, move);
                break;
            case 12:
                SW(T21, T20, T14, T25, T07, T15, T09, T08, move);
                break;
            case 13:
                SW(T01, T02, T03, T06, T09, T08, T07, T04, move);
                break;
            case 14:
                SW(T16, T18, T20, T24, T15, T13, T11, T23, move);
                break;
            case 15:
                SW(T17, T19, T21, T25, T14, T12, T10, T22, move);
                break;
            case 16:
                SW(T07, T04, T09, T08, T03, T06, T01, T02, move);
                break;
            case 17:
                SW(T11, T23, T15, T13, T20, T24, T16, T18, move);
                break;
            case 18:
                SW(T10, T22, T14, T12, T21, T25, T17, T19, move);
                break;
            default:
                break;
        }

        if (isShow()) {
            System.out.println(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Dice.
     */
    public void dice() {
        // initialisiert den Generator mit der PID und Laufzeit des Programmes
        Random r = new Random();
        // anzahl der Dreheungen auf NULL
        countMoves = 0;
        int z = 0;
        // anzah der schritte ver verwürfelung
        int y = 1000 + r.nextInt(100000);

        for (long x = 0; x < y; x++) {
            z = 1 + r.nextInt(18);
            // drehen mit schritt x
            move((byte) z);
        }
    }

    /**
     *
     */
    public void solve2() {
        solveing = true;
        countMoves = 0;

        solveList.clear();
        solve_rec(recDeep);
        solveList.clear();

        solveing = false;
    }

    private void solve_rec(int deep) {
        Counter c = new Counter(0);
        for (byte turn = 1; turn <= 18; turn++) {
            solve_rec(c, --deep, turn);
        }
    }

    private void solve_rec(Counter c, int deep, byte turn) {
        if (deep >= 0) {
            // if ( (c.value() % 10000) == 0 )

            c.inc();
            System.out.format("%s ::: deep(%s):turn(%s)%n", c, StringUtils.rightPad("", recDeep - deep, '-'), turn);
            move(turn);

            // if ( isSolved() ) {
            // return;
            // } else {
            for (byte i = 1; i <= 18; i++) {
                solve_rec(c, deep - 1, i);
            }
            // }
            move(antiTurn(turn));
        }
    }

    @SuppressWarnings("unused")
    private boolean isSolved() {
        return false;
    }

    /**
     * Solve.
     */
    public void solve() {
        solveing = true;
        countMoves = 0;

        solveList.clear();

        solveList.add((byte) 20);
        F1E();
        solveList.add((byte) 21);
        while (T05.FaRT(Tile.Order.AHEAD) != Tile.Color.RED) {
            move((byte) 11);
        }
        K1E(Tile.Color.RED); // Kanten der 1. Ebene
        solveList.add((byte) 22);
        E1E(Tile.Color.BLUE);
        solveList.add((byte) 23);
        while (T05.FaRT(Tile.Order.AHEAD) != Tile.Color.RED) {
            move((byte) 8);
        }
        solveList.add((byte) 24);
        K2E(Tile.Color.RED);
        solveList.add((byte) 25);
        move((byte) 1);
        move((byte) 2);
        move((byte) 3);
        move((byte) 3);
        move((byte) 2);
        move((byte) 1);
        E3();
        solveList.add((byte) 25);
        move((byte) 1);
        move((byte) 2);
        move((byte) 3);
        move((byte) 3);
        move((byte) 2);
        move((byte) 1);

        solveList.add((byte) 30);

        System.out.println(String.format("%s Züge", countMoves));
        System.out.println(String.format("%s Lösungszüge", solveList.size() - 11));
        System.out.println(StringUtils.join(solveList, "-"));

        boolean change;
        int count;

        do {
            change = false;

            /*
             * Parse the solve list to clear 4 same turns
             * ****************************
             */
            for (int i = 0; i < (solveList.size() - 4); i++) {
                count = 0;
                for (int j = 0; j < 4; j++) {
                    if (solveList.get(i).equals(solveList.get(i + j))) {
                        count++;
                    }
                }
                if (count == 4) {
                    System.out.println(String.format("4er Paar gefunden. %s", i));
                    change = true;
                    solveList.remove(i);
                    solveList.remove(i);
                    solveList.remove(i);
                    solveList.remove(i);
                }
            }

            /*
             * Parse the solve list to clear turn and following anti turn
             * ************
             */
            for (int i = 0; i < (solveList.size() - 1); i++) {
                if (solveList.get(i).equals(antiTurn(solveList.get(i + 1)))) {
                    System.out.println(String.format("Zug / Gegenzug Paar gefunden. %s", i));
                    change = true;
                    solveList.remove(i);
                    solveList.remove(i);
                }
            }

            /*
             * Parse the solve list to change 3 at one line into 1 to the other
             * side *
             */
            for (int i = 0; i < (solveList.size() - 3); i++) {
                count = 0;
                for (int j = 0; j < 3; j++) {
                    if (solveList.get(i).equals(solveList.get(i + j))) {
                        count++;
                    }
                }
                if (count == 3) {
                    byte a = (byte) antiTurn(solveList.get(i));
                    System.out.println(String.format("tausche 3er Kobi durch Gegenzug. %s", i));
                    change = true;
                    solveList.remove(i);
                    solveList.remove(i);
                    solveList.set(i, a);
                }
            }

        } while (change);

        System.out.println(String.format("%s Lösungszüge", solveList.size() - 11));
        System.out.println(StringUtils.join(solveList, "-"));

        solveing = false;
    }

    /**
     * Anti turn.
     *
     * @param move
     *             the move
     * @return the byte
     */
    private byte antiTurn(byte move) {
        switch (move) {
            case 1:
            case 2:
            case 3:
            case 7:
            case 8:
            case 9:
            case 13:
            case 14:
            case 15:
                return (byte) (move + 3);
            // break;
            case 4:
            case 5:
            case 6:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
                return (byte) (move - 3);
            // break;
            default:
                break;
        }
        return 0;
    }

    /**
     * F1 e.
     */
    public void F1E() {
        Tile.Color[] q = new Tile.Color[] { Tile.Color.WHITE };
        // drehet die weiße fläche nach oben
        if (T24.TRi(q)) {
            move((byte) 2); // von unten nach vorne
        }
        if (T13.TRi(q)) {
            move((byte) 17); // von links nach oben
        } else if (T05.TRi(q)) {
            move((byte) 2); // von vorne nach oben
        } else if (T18.TRi(q)) {
            move((byte) 14); // von rechts -"-
        } else if (T26.TRi(q)) {
            move((byte) 5); // von hinten -"-
        }
    }

    /**
     * K1 e.
     *
     * @param c
     *          the c
     */
    private void K1E(Tile.Color c) {
        Tile.Color[] qc = new Tile.Color[] { Tile.Color.WHITE, c };
        for (int i = 0; i < 4; i++) { // für 4Kanten
            // nach mitte vorne unten drehen
            if (!((T02.TRi(qc)) && (T02.FaRT(Tile.Order.ABOVE) == Tile.Color.WHITE))) {
                if (T02.TRi(qc))
                    move((byte) 16);
                if (T11.TRi(qc))
                    move((byte) 4);
                if (T16.TRi(qc))
                    move((byte) 6);
                if (T22.TRi(qc))
                    move((byte) 18);
                if (T12.TRi(qc))
                    move((byte) 8);
                if (T19.TRi(qc))
                    move((byte) 11);
                if (T06.TRi(qc))
                    move((byte) 16);
                if (T04.TRi(qc))
                    move((byte) 13);
                if (T15.TRi(qc))
                    move((byte) 9);
                if (T25.TRi(qc))
                    move((byte) 12);
                if (T20.TRi(qc))
                    move((byte) 12);
                // die kante wird in richtiger lage ein gereiht
                if (T08.FaRT(Tile.Order.AHEAD) != qc[1]) {
                    move((byte) 9);
                    move((byte) 5);
                    move((byte) 12);
                    move((byte) 2);
                } else {
                    move((byte) 13);
                    move((byte) 13);
                }
            }
            if (qc[1] == Tile.Color.RED)
                qc[1] = Tile.Color.GREEN;
            else if (qc[1] == Tile.Color.GREEN)
                qc[1] = Tile.Color.ORANGE;
            else if (qc[1] == Tile.Color.ORANGE)
                qc[1] = Tile.Color.BLUE;
            else if (qc[1] == Tile.Color.BLUE)
                qc[1] = Tile.Color.RED;
            move((byte) 10);
        }
    }

    /**
     * E1 e.
     *
     * @param c
     *          the c
     */
    private void E1E(Tile.Color c) {
        // cColor q[1]=qc, q[2];
        Tile.Color[] q = new Tile.Color[] { Tile.Color.WHITE, c, Tile.Color.BLACK };
        for (int x = 0; x < 4; x++) {// 4mal für alle ecken
            // berechnet aus der übergeben farbe die zwei benötigeten
            // und die neuen
            if (q[1] == Tile.Color.BLUE) {
                q[1] = Tile.Color.RED;
                q[2] = Tile.Color.BLUE;
            } else if (q[1] == Tile.Color.RED) {
                q[1] = Tile.Color.GREEN;
                q[2] = Tile.Color.RED;
            } else if (q[1] == Tile.Color.GREEN) {
                q[1] = Tile.Color.ORANGE;
                q[2] = Tile.Color.GREEN;
            } else if (q[1] == Tile.Color.ORANGE) {
                q[1] = Tile.Color.BLUE;
                q[2] = Tile.Color.ORANGE;
            }
            if (!((T01.TRi(q)) && (T01.FaRT(Tile.Order.ABOVE) == Tile.Color.WHITE))) {
                // dreht die gesucht Kante von ihren Position nach vorne unten
                // links
                if (T01.TRi(q)) {
                    move((byte) 4);
                    move((byte) 9);
                    move((byte) 1);
                    move((byte) 12);
                } else if (T03.TRi(q)) {
                    move((byte) 6);
                    move((byte) 12);
                    move((byte) 3);
                } else if (T17.TRi(q)) {
                    move((byte) 18);
                    move((byte) 12);
                    move((byte) 15);
                    move((byte) 12);
                } else if (T10.TRi(q)) {
                    move((byte) 15);
                    move((byte) 9);
                    move((byte) 18);
                } else if (T14.TRi(q)) {
                    move((byte) 9);
                } else if (T09.TRi(q)) {
                    move((byte) 12);
                } else if (T21.TRi(q)) {
                    move((byte) 12);
                    move((byte) 12);
                }
                // anschießend wird die Kanten, in abhängikeit ob weiß voren
                // unten oder
                // links ist die Ecke eingereiht
                if (T07.FaRT(Tile.Order.AHEAD) == Tile.Color.WHITE) {
                    move((byte) 9);
                    move((byte) 4);
                    move((byte) 12);
                    move((byte) 1);
                } else if (T07.FaRT(Tile.Order.LEFT) == Tile.Color.WHITE) {
                    move((byte) 12);
                    move((byte) 13);
                    move((byte) 9);
                    move((byte) 16);
                } else {
                    move((byte) 2);
                    move((byte) 3);
                    move((byte) 9);
                    move((byte) 13);
                    move((byte) 13);
                    move((byte) 12);
                    move((byte) 5);
                    move((byte) 6);
                }
            }
            move((byte) 10); // Eben 1 nach rechts
        }
    }

    /**
     * K2 e.
     *
     * @param c
     *          the c
     */
    private void K2E(Tile.Color c) {
        Tile.Color[] q = new Tile.Color[] { c, Tile.Color.BLACK };
        for (int x = 0; x < 4; x++) { // für alle 4 Kanten
            // berechnet aus der über geben farbe die zwei benötigeten
            // und die neuen
            if (q[0] == Tile.Color.BLUE) {
                q[0] = Tile.Color.RED;
                q[1] = Tile.Color.BLUE;
            } else if (q[0] == Tile.Color.RED) {
                q[0] = Tile.Color.GREEN;
                q[1] = Tile.Color.RED;
            } else if (q[0] == Tile.Color.GREEN) {
                q[0] = Tile.Color.ORANGE;
                q[1] = Tile.Color.GREEN;
            } else if (q[0] == Tile.Color.ORANGE) {
                q[0] = Tile.Color.BLUE;
                q[1] = Tile.Color.ORANGE;
            }
            // dreht die gesuchte Kanten von ihren Position nach mitte unten
            // vorne
            if ((T06.TRi(q)) && (T06.FaRT(Tile.Order.AHEAD) == q[1]))
                ;
            else if (T04.TRi(q)) {
                move((byte) 4);
                move((byte) 12);
                move((byte) 1);
                move((byte) 12);
                move((byte) 13);
                move((byte) 9);
                move((byte) 16);
                move((byte) 9);
                move((byte) 9);
            } else if (T06.TRi(q)) {
                move((byte) 6);
                move((byte) 9);
                move((byte) 3);
                move((byte) 9);
                move((byte) 16);
                move((byte) 12);
                move((byte) 13);
                move((byte) 9);
                move((byte) 9);
            } else if (T19.TRi(q)) {
                move((byte) 18);
                move((byte) 12);
                move((byte) 15);
                move((byte) 9);
                move((byte) 3);
                move((byte) 9);
                move((byte) 6);
                move((byte) 9);
            } else if (T12.TRi(q)) {
                move((byte) 15);
                move((byte) 12);
                move((byte) 18);
                move((byte) 12);
                move((byte) 1);
                move((byte) 9);
                move((byte) 4);
                move((byte) 12);
            } else if (T20.TRi(q)) {
                move((byte) 12);
            } else if (T25.TRi(q)) {
                move((byte) 9);
                move((byte) 9);
            } else if (T15.TRi(q)) {
                move((byte) 9);
            }
            // dreht die Kanten unten mitte in die position vorne rechts
            // mittlere Ebene
            // in abhängigkeit der Lage der Farben
            if (T08.FaRT(Tile.Order.AHEAD) == q[1] && T08.FaRT(Tile.Order.BELOW) == q[0]) {
                move((byte) 12);
                move((byte) 6);
                move((byte) 9);
                move((byte) 3);
                move((byte) 9);
                move((byte) 16);
                move((byte) 12);
                move((byte) 13);
            } else if (T08.FaRT(Tile.Order.AHEAD) == q[0] && T08.FaRT(Tile.Order.BELOW) == q[1]) {
                move((byte) 9);
                move((byte) 9);
                move((byte) 16);
                move((byte) 12);
                move((byte) 13);
                move((byte) 12);
                move((byte) 6);
                move((byte) 9);
                move((byte) 3);
            }
            // Ebenen oben und mitte nach rechts
            move((byte) 11);
        }
    }

    /**
     * E3.
     */
    private void E3() {
        solveList.add((byte) 26);
        // solange drehen bis orange/gelb vorne ist
        while (!T02.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.ORANGE })) {
            move((byte) 10);
        }
        // wenn Gelb/Blau hinten ist nach links bringen
        if (T22.TRi(new Tile.Color[] { Tile.Color.BLUE, Tile.Color.YELLOW })) {
            move((byte) 7);
            K3E();
            move((byte) 10);
        }
        // wenn Gelb/Balu rechts ist nach links bringen
        else if (T16.TRi(new Tile.Color[] { Tile.Color.BLUE, Tile.Color.YELLOW })) {
            move((byte) 7);
            move((byte) 7);
            K3E();
            move((byte) 10);
            K3E();
            move((byte) 10);
        }
        // wenn grün/gelb und rot/gelb vertauscht sind tauschen
        if (T22.TRi(new Tile.Color[] { Tile.Color.GREEN, Tile.Color.YELLOW })
                && T16.TRi(new Tile.Color[] { Tile.Color.RED, Tile.Color.YELLOW })) {
            move((byte) 7);
            move((byte) 7);
            K3E();
            move((byte) 10);
            move((byte) 10);
        }

        solveList.add((byte) 27);
        // dreht evrl. die gelbe Farbe nach oben
        for (int x = 0; x < 4; x++) { // für alle 4 Kanten
            if (T16.FaRT(Tile.Order.ABOVE) != Tile.Color.YELLOW) { // wenn
                                                                   // gelb
                                                                   // nicht
                                                                   // oben
                                                                   // ist,
                for (int y = 0; y < 4; y++) { // muß dieser Zug muß 4x pro
                                              // Kante durchgeführt werden
                    move((byte) 3);
                    move((byte) 8);
                }
            }
            // nach rechts drehen
            move((byte) 10);
        }
        // sucht die ecke Gelb/Blau/Oranage und bringt sie nach vorne links
        // an ihre Endposition um dann die Ecke nach hinten links zudrehen

        solveList.add((byte) 28);
        // von Postione hiten links
        if (T10.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.BLUE, Tile.Color.ORANGE })) {
            move((byte) 7);
            E3E();
            move((byte) 10);
        }
        // von positon hinten rechts
        else if (T17.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.BLUE, Tile.Color.ORANGE })) {
            move((byte) 10);
            E3E();
            move((byte) 7);
            E3E();
        }
        // von Postione vorne rechts
        else if (T03.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.BLUE, Tile.Color.ORANGE })) {
            E3E();
        }

        // nun die Gelb/Grün/Orange Ecke auf ihre postion bringen
        // von hinten rechts nach vorne rechts
        if (T17.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.GREEN, Tile.Color.ORANGE })) {
            move((byte) 10);
            E3E();
            move((byte) 7);
        }
        // von hiten links nach vornen rechts
        else if (T10.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.GREEN, Tile.Color.ORANGE })) {
            move((byte) 10);
            move((byte) 10);
            E3E();
            move((byte) 7);
            move((byte) 7);
            move((byte) 10);
            E3E();
            move((byte) 7);
        }
        // falls die rot/grün/gelbe mit der gelb/blau/roten Ecke getauscht ist
        // tauschen
        if (T10.TRi(new Tile.Color[] { Tile.Color.YELLOW, Tile.Color.GREEN, Tile.Color.RED })) {
            move((byte) 10);
            move((byte) 10);
            E3E();
            move((byte) 7);
            move((byte) 7);
        }

        solveList.add((byte) 29);
        for (int x = 0; x < 4; x++) { // dreht bei allen 4 Ecken die Gelbe
                                      // Seite nach oben
            for (int z = 0; z < 2; z++) { // diser zug muß bis zu 2mal durch
                                          // gefürt werden
                if (T03.FaRT(Tile.Order.ABOVE) != Tile.Color.YELLOW) {
                    move((byte) 3);
                    move((byte) 13);
                    move((byte) 6);
                    move((byte) 16);
                    move((byte) 3);
                    move((byte) 13);
                    move((byte) 6);
                    move((byte) 16);
                }
            }
            move((byte) 10); // nach rechts drehen
        }
    }

    /**
     * K3 e.
     */
    private void K3E() {
        // dieser zug tasuch die KAnten vorne und links
        move((byte) 10);
        move((byte) 16);
        move((byte) 3);
        move((byte) 10);
        move((byte) 6);
        move((byte) 7);
        move((byte) 13);
    }

    /**
     * E3 e.
     */
    private void E3E() {
        // dieser zug vertausch die Ecken vorne links und rechts
        move((byte) 16);
        move((byte) 9);
        move((byte) 16);
        move((byte) 16);
        move((byte) 9);
        move((byte) 9);
        move((byte) 16);
        move((byte) 16);
        move((byte) 12);
        move((byte) 13);
    }

    /**
     * SW tausch die Teile in Folgender Reihenfolge 1 < 2 < 3 < 4 \>>>>>>>>>>>^
     * die gechied mit hilfe der Funktion swap(...) in anhängingkeit der
     * Drehrichtung
     *
     * @param c1
     *             the c1
     * @param e1
     *             the e1
     * @param c2
     *             the c2
     * @param e2
     *             the e2
     * @param c3
     *             the c3
     * @param e3
     *             the e3
     * @param c4
     *             the c4
     * @param e4
     *             the e4
     * @param move
     *             the move
     */
    private void SW(Corner c1, Edge e1, Corner c2, Edge e2, Corner c3, Edge e3, Corner c4, Edge e4, byte move) {
        Corner ctmp = c1.dup();
        Edge etmp = e1.dup();

        c1.swap(c2, move);
        e1.swap(e2, move);

        e2.swap(e3, move);
        c2.swap(c3, move);

        e3.swap(e4, move);
        c3.swap(c4, move);

        e4.swap(etmp, move);
        c4.swap(ctmp, move);
    }

    /**
     * SW.
     *
     * @param e1
     *             the e1
     * @param a1
     *             the a1
     * @param e2
     *             the e2
     * @param a2
     *             the a2
     * @param e3
     *             the e3
     * @param a3
     *             the a3
     * @param e4
     *             the e4
     * @param a4
     *             the a4
     * @param move
     *             the move
     */
    private void SW(Edge e1, Area a1, Edge e2, Area a2, Edge e3, Area a3, Edge e4, Area a4, byte move) {
        Edge etmp = e1.dup();
        Area atmp = a1.dup();

        e1.swap(e2, move);
        a1.swap(a2, move);

        e2.swap(e3, move);
        a2.swap(a3, move);

        e3.swap(e4, move);
        a3.swap(a4, move);

        e4.swap(etmp, move);
        a4.swap(atmp, move);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        char O1 = Tile.colorChar(T10.FaRT(Tile.Order.ABOVE));
        char O2 = Tile.colorChar(T22.FaRT(Tile.Order.ABOVE));
        char O3 = Tile.colorChar(T17.FaRT(Tile.Order.ABOVE));
        char O4 = Tile.colorChar(T11.FaRT(Tile.Order.ABOVE));
        char O5 = Tile.colorChar(T23.FaRT(Tile.Order.ABOVE));
        char O6 = Tile.colorChar(T16.FaRT(Tile.Order.ABOVE));
        char O7 = Tile.colorChar(T01.FaRT(Tile.Order.ABOVE));
        char O8 = Tile.colorChar(T02.FaRT(Tile.Order.ABOVE));
        char O9 = Tile.colorChar(T03.FaRT(Tile.Order.ABOVE));

        char U1 = Tile.colorChar(T07.FaRT(Tile.Order.BELOW));
        char U2 = Tile.colorChar(T08.FaRT(Tile.Order.BELOW));
        char U3 = Tile.colorChar(T09.FaRT(Tile.Order.BELOW));
        char U4 = Tile.colorChar(T15.FaRT(Tile.Order.BELOW));
        char U5 = Tile.colorChar(T24.FaRT(Tile.Order.BELOW));
        char U6 = Tile.colorChar(T20.FaRT(Tile.Order.BELOW));
        char U7 = Tile.colorChar(T14.FaRT(Tile.Order.BELOW));
        char U8 = Tile.colorChar(T25.FaRT(Tile.Order.BELOW));
        char U9 = Tile.colorChar(T21.FaRT(Tile.Order.BELOW));

        char L1 = Tile.colorChar(T10.FaRT(Tile.Order.LEFT));
        char L2 = Tile.colorChar(T11.FaRT(Tile.Order.LEFT));
        char L3 = Tile.colorChar(T01.FaRT(Tile.Order.LEFT));
        char L4 = Tile.colorChar(T12.FaRT(Tile.Order.LEFT));
        char L5 = Tile.colorChar(T13.FaRT(Tile.Order.LEFT));
        char L6 = Tile.colorChar(T04.FaRT(Tile.Order.LEFT));
        char L7 = Tile.colorChar(T14.FaRT(Tile.Order.LEFT));
        char L8 = Tile.colorChar(T15.FaRT(Tile.Order.LEFT));
        char L9 = Tile.colorChar(T07.FaRT(Tile.Order.LEFT));

        char R1 = Tile.colorChar(T03.FaRT(Tile.Order.RIGHT));
        char R2 = Tile.colorChar(T16.FaRT(Tile.Order.RIGHT));
        char R3 = Tile.colorChar(T17.FaRT(Tile.Order.RIGHT));
        char R4 = Tile.colorChar(T06.FaRT(Tile.Order.RIGHT));
        char R5 = Tile.colorChar(T18.FaRT(Tile.Order.RIGHT));
        char R6 = Tile.colorChar(T19.FaRT(Tile.Order.RIGHT));
        char R7 = Tile.colorChar(T09.FaRT(Tile.Order.RIGHT));
        char R8 = Tile.colorChar(T20.FaRT(Tile.Order.RIGHT));
        char R9 = Tile.colorChar(T21.FaRT(Tile.Order.RIGHT));

        char V1 = Tile.colorChar(T01.FaRT(Tile.Order.AHEAD));
        char V2 = Tile.colorChar(T02.FaRT(Tile.Order.AHEAD));
        char V3 = Tile.colorChar(T03.FaRT(Tile.Order.AHEAD));
        char V4 = Tile.colorChar(T04.FaRT(Tile.Order.AHEAD));
        char V5 = Tile.colorChar(T05.FaRT(Tile.Order.AHEAD));
        char V6 = Tile.colorChar(T06.FaRT(Tile.Order.AHEAD));
        char V7 = Tile.colorChar(T07.FaRT(Tile.Order.AHEAD));
        char V8 = Tile.colorChar(T08.FaRT(Tile.Order.AHEAD));
        char V9 = Tile.colorChar(T09.FaRT(Tile.Order.AHEAD));

        char H1 = Tile.colorChar(T17.FaRT(Tile.Order.BEHIND));
        char H2 = Tile.colorChar(T22.FaRT(Tile.Order.BEHIND));
        char H3 = Tile.colorChar(T10.FaRT(Tile.Order.BEHIND));
        char H4 = Tile.colorChar(T19.FaRT(Tile.Order.BEHIND));
        char H5 = Tile.colorChar(T26.FaRT(Tile.Order.BEHIND));
        char H6 = Tile.colorChar(T12.FaRT(Tile.Order.BEHIND));
        char H7 = Tile.colorChar(T21.FaRT(Tile.Order.BEHIND));
        char H8 = Tile.colorChar(T25.FaRT(Tile.Order.BEHIND));
        char H9 = Tile.colorChar(T14.FaRT(Tile.Order.BEHIND));

        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format("               + %s + %s + %s +\n", O1, O2, O3));
        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format("               + %s + %s + %s +\n", O4, O5, O6));
        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format("               + %s + %s + %s +\n", O7, O8, O9));
        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format(" +---+---+---+ +---+---+---+ +---+---+---+ +---+---+---+\n"));
        sb.append(String.format(" + %s + %s + %s + + %s + %s + %s + + %s + %s + %s + + %s + %s + %s +\n", L1, L2, L3,
                V1, V2, V3, R1, R2, R3, H1, H2, H3));
        sb.append(String.format(" +---+---+---+ +---+---+---+ +---+---+---+ +---+---+---+\n"));
        sb.append(String.format(" + %s + %s + %s + + %s + %s + %s + + %s + %s + %s + + %s + %s + %s +\n", L4, L5, L6,
                V4, V5, V6, R4, R5, R6, H4, H5, H6));
        sb.append(String.format(" +---+---+---+ +---+---+---+ +---+---+---+ +---+---+---+\n"));
        sb.append(String.format(" + %s + %s + %s + + %s + %s + %s + + %s + %s + %s + + %s + %s + %s +\n", L7, L8, L9,
                V7, V8, V9, R7, R8, R9, H7, H8, H9));
        sb.append(String.format(" +---+---+---+ +---+---+---+ +---+---+---+ +---+---+---+\n"));
        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format("               + %s + %s + %s +\n", U1, U2, U3));
        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format("               + %s + %s + %s +\n", U4, U5, U6));
        sb.append(String.format("               +---+---+---+\n"));
        sb.append(String.format("               + %s + %s + %s +\n", U7, U8, U9));
        sb.append(String.format("               +---+---+---+\n"));

        return sb.toString();
    }

    /**
     * The main method.
     *
     * @param args
     *             the arguments
     */
    public static void main(String[] args) {
        Cube c = new Cube();

        c.setShow(false);
        c.dice();
        c.saveState();
        c.solve();
        c.restoreState();
        c.setShow(true);

        int i = 1;
        for (Byte b : c.getSolveList()) {
            System.out.println(String.format("Zug: %d", i));
            c.move(b);
            i++;
        }

    }

    /**
     * Save state.
     */
    public void saveState() {
        ST01 = T01.dup();
        ST02 = T02.dup();
        ST03 = T03.dup();
        ST04 = T04.dup();
        ST05 = T05.dup();
        ST06 = T06.dup();
        ST07 = T07.dup();
        ST08 = T08.dup();
        ST09 = T09.dup();
        ST10 = T10.dup();
        ST11 = T11.dup();
        ST12 = T12.dup();
        ST13 = T13.dup();
        ST14 = T14.dup();
        ST15 = T15.dup();
        ST16 = T16.dup();
        ST17 = T17.dup();
        ST18 = T18.dup();
        ST19 = T19.dup();
        ST20 = T20.dup();
        ST21 = T21.dup();
        ST22 = T22.dup();
        ST23 = T23.dup();
        ST24 = T24.dup();
        ST25 = T25.dup();
        ST26 = T26.dup();
    }

    /**
     * Restore state.
     */
    public void restoreState() {
        T01 = ST01.dup();
        T02 = ST02.dup();
        T03 = ST03.dup();
        T04 = ST04.dup();
        T05 = ST05.dup();
        T06 = ST06.dup();
        T07 = ST07.dup();
        T08 = ST08.dup();
        T09 = ST09.dup();
        T10 = ST10.dup();
        T11 = ST11.dup();
        T12 = ST12.dup();
        T13 = ST13.dup();
        T14 = ST14.dup();
        T15 = ST15.dup();
        T16 = ST16.dup();
        T17 = ST17.dup();
        T18 = ST18.dup();
        T19 = ST19.dup();
        T20 = ST20.dup();
        T21 = ST21.dup();
        T22 = ST22.dup();
        T23 = ST23.dup();
        T24 = ST24.dup();
        T25 = ST25.dup();
        T26 = ST26.dup();
    }

    /**
     * Sets the show.
     *
     * @param show
     *             the new show
     */
    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * Checks if is show.
     *
     * @return true, if is show
     */
    public boolean isShow() {
        return show;
    }

    // public void setSolveList(List<Byte> solveList) {
    // this.solveList = solveList;
    // }

    /**
     * Gets the solve list.
     *
     * @return the solve list
     */
    public List<Byte> getSolveList() {
        return solveList;
    }

}
