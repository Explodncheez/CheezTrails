package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.CheezTrails;
import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.OrdinaryColor;

public class DoubleRings extends ParticleTrail {

    public DoubleRings(ParticleInfo info, OrdinaryColor... colors) {
        super(info);
        this.COLORS = colors;
    }

    private OrdinaryColor[] COLORS;
    private static double[] ATAN = new double[48];
    
    static {
        for (int i = 0; i < 48; i++) {
            ATAN[i] = Math.abs(Math.atan((i - 24) * Math.PI / 24));
        }
    }

    @Override
    public void doMoveEffect(Player p) {
        double k = ATAN[TrailListener.cycle];
        Location location = p.getLocation().add(0, 0.05 + k, 0);
        for (int i = 0; i < 4; i++) {
            double offsetx = (Math.random() - 0.5) / 40;
            double offsety = (Math.random() - 0.5) / 25;
            double offsetz = (Math.random() - 0.5) / 40;
            ParticleEffects.RED_DUST.display(COLORS[CheezTrails.random(COLORS.length - 1)], location.add(offsetx, offsety, offsetz), 256);
        }
    }

    @Override
    public void doStandEffect() {
        double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * 0.75;
        double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * 0.75;
        double y = 0.8 + x * 0.8;
        
        for (int j = 0; j < COLORS.length; j++) {
            for (int i = 0; i < 3; i++) {
                double offsetx = (Math.random() - 0.5) / 50;
                double offsety = (Math.random() - 0.5) / 50;
                double offsetz = (Math.random() - 0.5) / 50;
                for (Player p : users) {
                    if (p.hasMetadata("trail.standingstill")) {
                        ParticleEffects.RED_DUST.display(COLORS[j], p.getLocation().add(x + offsetx, y + offsety, z + offsetz), 256);
                        ParticleEffects.RED_DUST.display(COLORS[j], p.getLocation().add(-x + offsetx, y + offsety, -z + offsetz), 256);
                    }
                }
            }
        }
    }

}
