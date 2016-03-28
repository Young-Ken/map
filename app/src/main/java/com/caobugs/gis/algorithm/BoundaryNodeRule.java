package com.caobugs.gis.algorithm;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public interface BoundaryNodeRule
{
    boolean isInBoundary(int boundaryCount);

    public static final BoundaryNodeRule MOD2_BOUNDARY_RULE = new Mod2BoundaryNodeRule();

    public static final BoundaryNodeRule ENDPOINT_BOUNDARY_RULE = new EndPointBoundaryNodeRule();

    public static final BoundaryNodeRule OGC_SFS_BOUNDARY_RULE = MOD2_BOUNDARY_RULE;

    public static class Mod2BoundaryNodeRule implements BoundaryNodeRule
    {
        @Override
        public boolean isInBoundary(int boundaryCount)
        {
            return boundaryCount % 2 == 1;
        }
    }

    public static class EndPointBoundaryNodeRule implements BoundaryNodeRule
    {
        @Override
        public boolean isInBoundary(int boundaryCount)
        {
            return boundaryCount > 0;
        }
    }
}
