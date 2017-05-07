package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.OrdinaryColor;

public class Secret extends ParticleTrail {

    public Secret(ParticleInfo info) {
        super(info);
    }
    

    @Override
    public void doMoveEffect(Player p) {
        ParticleEffects.CLOUD.display(0.15F, 0.05F, 0.15F, 0.05F, 3, p.getLocation().add(0, 0.8, 0), 256);
    }
    
    private static OrdinaryColor
    s = new OrdinaryColor(245, 234, 186),
    t = new OrdinaryColor(245, 186, 237);

    @Override
    public void doStandEffect() {
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Vector dir = p.getLocation().getDirection();
                for (int i = 0 ; i < 3; i++)
                    for (double d = 0; d < 0.8; d+= 0.1) {
                        Location loc = p.getLocation().add(dir.getX() * (0.25 + d), 0.7, dir.getZ() * (0.25 + d));
                        ParticleEffects.RED_DUST.display(d > 0.6 ? t : s, loc, 256);
                    }
        
                Location loc = p.getLocation().add(dir.getX() * 0.3, 0, dir.getZ() * 0.3);
                Vector cross = dir.setY(0).crossProduct(new Vector(0, 1, 0)).normalize();
                for (int xx = -1; xx <= 1; xx += 2) {
                    for (int i = 0; i < 4; i++) {
                        double x = (Math.random() - 0.5) / 4,
                            y = (Math.random() - 0.5) / 4,
                            z = (Math.random() - 0.5) / 4;
                        ParticleEffects.RED_DUST.display(s, loc.clone().add(cross.getX() * (0.25 * xx) + x, 0.5 + y, cross.getZ() * (0.25 * xx) + z), 256);
                    }
                }
            }
        }
    }

}
