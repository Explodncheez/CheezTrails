package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Emerald extends ParticleTrail {

	public Emerald(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, 1 + 0.5 * Math.cos(TrailListener.cycle * Math.PI / 6), 0);
		ParticleEffects.VILLAGER_HAPPY.display(0F, 0F, 0F, 0F, 2, loc, 256);
	}

	@Override
    public void doStandEffect() {
		if (TrailListener.cycle % 2 == 0) return;
		
		double k = TrailListener.cycle * Math.PI / 24;
		double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * 0.85, x2 = Math.cos(k + Math.PI / 2) * 0.85;
		double y = 0.7 + PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] / 1.5;
		double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * 0.85, z2 = Math.sin(k + Math.PI / 2) * 0.85;
		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				ParticleEffects.VILLAGER_HAPPY.display(0F, 0F, 0F, 2.0F, 1, p.getLocation().add(x, y, z), 256);
				ParticleEffects.VILLAGER_HAPPY.display(0F, 0F, 0F, 2.0F, 1, p.getLocation().add(-z2, y, -x2), 256);
			}
		}
	}

}
