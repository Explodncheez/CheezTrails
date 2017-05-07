package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Blizzard extends ParticleTrail {

    public Blizzard(ParticleInfo info) {
        super(info);
    }

    @Override
    public void doMoveEffect(Player p) {
        Location loc = p.getLocation().add(0, 1, 0);
        ParticleEffects.SNOW_SHOVEL.display(0.2F, 0.2F, 0.2F, 0.05F, 4, loc, 256);
    }

    @Override
    public void doStandEffect() {
        if (TrailListener.cycle % 4 != 0) return;
        
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Location loc = p.getLocation().add(0,3.0,0);
                ParticleEffects.EXPLODE.display(0.45F, 0F, 0.45F, 0F, 8, loc, 257);
                ParticleEffects.SNOW_SHOVEL.display(0.35F, 0.05F, 0.35F, 0.1F, 4, loc, 257);
            }
        }
    }

}
