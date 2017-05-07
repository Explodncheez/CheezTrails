package trails;

public class PositionCache {
    
    // 2D circle with radius of 1
    public static final double[][] NORMAL_CIRCLE = new double[48][2];
    
    static {
        for (int i = 0; i < 48; i++) {
            double k = i * Math.PI / 24;
            NORMAL_CIRCLE[i][0] = Math.cos(k);
            NORMAL_CIRCLE[i][1] = Math.sin(k);
        }
    }

}
