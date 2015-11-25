package com.snail.gis.enumeration;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/20
 */
public class Dimension
{
    /**
     *  点
     */
    public final static int P = 0;

    /**
     *  线
     */
    public final static int L = 1;

    /**
     *  面
     */
    public final static int A = 2;

    /**
     *  空Geometry
     */
    public final static int FALSE = -1;

    /**
     *   不是空的 geometries (= {P, L, A}).
     */
    public final static int TRUE = -2;

    /**
     * 代表 (= {FALSE, TRUE}).
     */
    public final static int DONTCARE = -3;

    /**
     *  FALSE
     */
    public final static char SYM_FALSE = 'F';

    /**
     * TRUE
     */
    public final static char SYM_TRUE = 'T';

    /**
     * DONTCARE
     */
    public final static char SYM_DONTCARE = '*';

    public final static char SYM_P = '0';

    public final static char SYM_L = '1';

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
        throw new IllegalArgumentException("未知: " + dimensionValue);
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
        throw new IllegalArgumentException("未知 ：" + dimensionSymbol);
    }
}
