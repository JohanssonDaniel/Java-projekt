package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

/**
 * Created by pierre on 2014-10-18.
 */
public class TriangleIntersect implements ObstacleIntersect {
    
    @Override
    public boolean intersect(int obsLeftSide, int obsTopSide, int obsWidth, int obsHeight,int playerLeftSide, int playerTopSide, int playerWidth, int playerHeight) {
        int obsRightSide = obsWidth + obsLeftSide;
        int obsBottomSide = obsHeight + obsTopSide;
        int playerRightSide = playerWidth + playerLeftSide;
        int playerBottomSide = playerHeight + playerTopSide;

        if (obsRightSide <= 0 || obsBottomSide <= 0) {
            return false;
        }

        //TODO: Här behövs det att fixa så den fungerar i alla lägen, har buggar i sig just nu.
        else if ((playerRightSide >= obsLeftSide && playerLeftSide < obsRightSide) && (playerBottomSide > obsTopSide && playerTopSide < obsBottomSide)) {
	    return true;
	}
        return false;
    }
}
