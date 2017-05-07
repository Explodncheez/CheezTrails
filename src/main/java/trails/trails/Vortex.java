package trails.trails;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Vortex extends ParticleTrail {

    public Vortex(ParticleInfo info) {
        super(info);
    }
    
    private static double[][] CACHE = new double[96][3];
    private boolean extension;
    
    static {
        for (int i = 0; i < 96; i++) {
            double k = i * Math.PI / 48;
            double r = Math.sin(3 * k) * 1.4;
            CACHE[i][0] = r * Math.sin(k);
            CACHE[i][1] = Math.abs(Math.sin(1.5 * k));
            CACHE[i][2] = r * Math.cos(k);
        }
    }

    @Override
    public void doMoveEffect(Player p) {
        double k = TrailListener.cycle * Math.PI / 6;
        Vector dir = p.getLocation().getDirection().multiply(-1D);
        Vector cross = dir.setY(0).crossProduct(new Vector(0, 1, 0));
        ParticleEffects.FLAME.display(0F, 0F, 0F, 0F, 1, p.getLocation().add(cross.getX() * Math.cos(k), 0.1, cross.getZ() * Math.cos(k)), 256);
        
    }

    @Override
    public void doStandEffect() {
        if (TrailListener.cycle == 0)
            extension = !extension;
        
        int j = TrailListener.cycle + (extension ? 48 : 0);
        double x = CACHE[j][0];
        double y = CACHE[j][1];
        double z = CACHE[j][2];

        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                ParticleEffects.FLAME.display(0F, 0F, 0F, 0F, 1, p.getLocation().add(x, y, z), 256);
            }
        }
    }

}
