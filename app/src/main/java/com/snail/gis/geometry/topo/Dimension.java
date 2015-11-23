package com.snail.gis.geometry.topo;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/20
 */
public class Dimension
{
    /**
     *  Dimension value of a point (0).
     */
    public final static int P = 0;

    /**
     *  Dimension value of a curve (1).
     */
    public final static int L = 1;

    /**
     *  Dimension value of a surface (2).
     */
    public final static int A = 2;

    /**
     *  Dimension value of the empty geometry (-1).
     */
    public final static int FALSE = -1;

    /**
     *  Dimension value of non-empty geometries (= {P, L, A}).
     */
    public final static int TRUE = -2;

    /**
     *  Dimension value for any dimension (= {FALSE, TRUE}).
     */
    public final static int DONTCARE = -3;

    /**
     * Symbol for the FALSE pattern matrix entry
     */
    public final static char SYM_FALSE = 'F';

    /**
     * Symbol for the TRUE pattern matrix entry
     */
    public final static char SYM_TRUE = 'T';

    /**
     * Symbol for the DONTCARE pattern matrix entry
     */
    public final static char SYM_DONTCARE = '*';

    /**
     * Symbol for the P (dimension 0) pattern matrix entry
     */
    public final static char SYM_P = '0';

    /**
     * Symbol for the L (dimension 1) pattern matrix entry
     */
    public final static char SYM_L = '1';

    /**
     * Symbol for the A (dimension 2) pattern matrix entry
     */
    public final static char SYM_A = '2';

    public static char toDimensionSymbol(int dimensionValue) {
        switch (dimensionValue) {
            case FALSE:
                return SYM_FALSE;
            case TRUE:
                return SYM_TRUE;
            case DONTCARE:
                return SYM_DONTCARE;
            case P:
                return SYM_P;
            case L:
                return SYM_L;
            case A:
                return SYM_A;
        }
        throw new IllegalArgumentException("Unknown dimension value: " + dimensionValue);
    }


    public static int toDimensionValue(char dimensionSymbol) {
        switch (Character.toUpperCase(dimensionSymbol)) {
            case SYM_FALSE:
                return FALSE;
            case SYM_TRUE:
                return TRUE;
            case SYM_DONTCARE:
                return DONTCARE;
            case SYM_P:
                return P;
            case SYM_L:
                return L;
            case SYM_A:
                return A;
        }
        throw new IllegalArgumentException("Unknown dimension symbol: " + dimensionSymbol);
    }
}
