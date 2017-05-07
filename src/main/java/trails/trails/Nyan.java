package trails.trails;

import org.bukkit.entity.Player;

import trails.ParticleTrail;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.OrdinaryColor;

public class Nyan extends ParticleTrail {

    public Nyan(ParticleInfo info) {
        super(info);
    }
    
    private static OrdinaryColor[] RAINBOW = {
        new OrdinaryColor(255, 1, 1),
        new OrdinaryColor(255, 135, 1),
        new OrdinaryColor(255, 255, 1),
        new OrdinaryColor(1, 255, 1),
        new OrdinaryColor(1, 120, 255),
        new OrdinaryColor(180, 1, 255)
    };

    @Override
    public void doMoveEffect(Player p) {
        for (int i = 0; i < RAINBOW.length; i++)
            for (int j = 0; j < 6; j++)
                ParticleEffects.RED_DUST.display(RAINBOW[i], p.getLocation().add(0, 1.1 - 0.15 * i - 0.025 * j, 0), 256);
    }

    @Override
    public void doStandEffect() {
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                ParticleEffects.MOB_SPELL.display(0.2F, 0.2F, 0.2F, 1.0F, 2, p.getLocation().add(0, 0.3, 0), 256);
            }
        }
    }

}
