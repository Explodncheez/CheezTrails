package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Shine extends ParticleTrail {

	public Shine(ParticleInfo info) {
		super(info);
	}
	
	@Override
    public void doMoveEffect(Player p) {
		ParticleEffects.FIREWORKS_SPARK.display(0.1F, 0.3F, 0.1F, 0.01F, 1, p.getLocation().add(0, 0.9, 0), 256);
	}

	@Override
    public void doStandEffect() {
		double k = TrailListener.cycle * Math.PI / 24;
		double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1];
		double y = 1.2 + Math.cos(24 * k);
		double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0];
		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(x, y, z);
				ParticleEffects.FIREWORKS_SPARK.display(0F, 0F, 0F, 0F, 1, loc, 256);
				ParticleEffects.FIREWORKS_SPARK.display(0.05F, 0.05F, 0.05F, 0.05F, 1, p.getLocation().add(0, 0.1, 0), 256);
			}
		}
	}

}
