package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Smoke extends ParticleTrail {

    public Smoke(ParticleInfo info) {
        super(info);
    }

    @Override
    public void doMoveEffect(Player p) {
        Location loc = p.getLocation().add(0, 0.8, 0);
        ParticleEffects.SMOKE.display(0.1F, 0.3F, 0.1F, 0.02F, 5, loc, 256);
    }

    @Override
    public void doStandEffect() {
        if (TrailListener.cycle % 4 != 0) return;

        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Location loc = p.getLocation().add(0,0.2,0);
                ParticleEffects.SMOKE_LARGE.display(0.3F, 0.2F, 0.3F, 0.02F, 3, loc, 256);
            }
        }
    }

}
