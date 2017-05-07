package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Heart extends ParticleTrail {

    public Heart(ParticleInfo info) {
        super(info);
    }
    
    private boolean reverse = false;

    @Override
    public void doMoveEffect(Player p) {
        if (TrailListener.cycle % 2 != 0)
            ParticleEffects.HEART.display(0F, 0F, 0F, 1.0F, 1, p.getLocation().add(0, 1.8, 0), 256);
        ParticleEffects.RED_DUST.display(0.1F, 0.3F, 0.1F, 0F, 3, p.getLocation().add(0, 1.5, 0), 256);
    }

    @Override
    public void doStandEffect() {
        if (TrailListener.cycle % 2 != 0) return;
        
        if (TrailListener.cycle == 0)
            reverse = !reverse;
        
        double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * 0.8;
        double y = TrailListener.cycle / 32D;
        double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * 0.8;
        double yy = 0.1 + (reverse ? 1 - y : y);
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Location loc = p.getLocation().add(x, yy, z);
                ParticleEffects.HEART.display(0F, 0F, 0F, 1.0F, 1, loc, 256);
            }
        }
    }

}
