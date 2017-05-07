package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import utilities.particles.ParticleEffects;

public class Sparkle extends ParticleTrail {

    public Sparkle(ParticleInfo info) {
        super(info);
    }

    @Override
    public void doMoveEffect(Player p) {
        Location loc = p.getLocation().add(0, 0.4, 0);
        ParticleEffects.FIREWORKS_SPARK.display(0.1F, 0.3F, 0.1F, 0.07F, 3, loc, 256);
    }

    @Override
    public void doStandEffect() {
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Location loc = p.getLocation().add(0,0.7,0);
                ParticleEffects.FIREWORKS_SPARK.display(0.2F, 0.4F, 0.2F, 0.07F, 1, loc, 256);
            }
        }
    }

}
