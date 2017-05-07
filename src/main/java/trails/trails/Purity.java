package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Purity extends ParticleTrail {

	public Purity(ParticleInfo info) {
		super(info);
	}
	
	private boolean reverse = false;

	@Override
    public void doMoveEffect(Player p) {
		ParticleEffects.SNOWBALL.display(0.15F, 0.05F, 0.15F, 0F, 3, p.getLocation().add(0, 0.1, 0), 256);
	}

	@Override
    public void doStandEffect() {
		if (TrailListener.cycle == 0) {
			reverse = !reverse;
			if (reverse) {
				for (Player p : users) {
					if (p.hasMetadata("trail.standingstill")) {
						ParticleEffects.FIREWORKS_SPARK.display(0.12F, 0.05F, 0.12F, 0.1F, 6, p.getLocation().add(0, 2.6, 0), 256);
						ParticleEffects.SNOW_SHOVEL.display(0.12F, 0.05F, 0.12F, 0.12F, 12, p.getLocation().add(0, 2.7, 0), 256);
					}
				}
				return;
			}
		}
		
		double q = 1.5 * (reverse ? TrailListener.cycle / 48D : 1 - (TrailListener.cycle / 48D));
		double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * q;
		double y = TrailListener.cycle / 48D;
		double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * q;
		double yy = (reverse ? 1 - y : y) * 2.5;
		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(z, yy, x);
				ParticleEffects.SNOW_SHOVEL.display(0.03F, 0.03F, 0.03F, 0.03F, 3, loc, 256);
			}
		}
	}

}
