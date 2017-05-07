package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import utilities.particles.ParticleEffects;

public class Knowledge extends ParticleTrail {

    public Knowledge(ParticleInfo info) {
        super(info);
    }

    @Override
    public void doMoveEffect(Player p) {
        Location loc = p.getLocation().add(0, 1.2, 0);
        ParticleEffects.ENCHANTMENT_TABLE.display(0.1F, 0.1F, 0.1F, 0F, 4, loc, 256);
    }

    @Override
    public void doStandEffect() {
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Location loc = p.getLocation().add(0, 1.2, 0);
                ParticleEffects.ENCHANTMENT_TABLE.display(0F, 0F, 0F, 1.0F, 3, loc, 256);
            }
        }
    }

}
