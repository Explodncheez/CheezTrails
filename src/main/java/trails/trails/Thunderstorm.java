package trails.trails;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Thunderstorm extends ParticleTrail {

	public Thunderstorm(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, 1, 0);
		ParticleEffects.SPLASH.display(0.2F, 0.2F, 0.2F, 0.1F, 6, loc, 256);
	}

	@Override
    public void doStandEffect() {
		if (TrailListener.cycle % 4 != 0) return;

		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(0,3.0,0);
				ParticleEffects.EXPLODE.display(0.45F, 0F, 0.45F, 0F, 8, loc, 257);
				ParticleEffects.DRIP_WATER.display(0.38F, 0.05F, 0.38F, 1.0F, 4, loc, 257);
				
				if (Math.random() < 0.04) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 0.4F, 1.0F);
					
					Location thunder = p.getLocation().add(Math.random() - 0.5, 3, Math.random() - 0.5);
					Vector direction = Vector.getRandom();
					double ymin = p.getLocation().getY(), v = 20;
					
					while (thunder.getY() > ymin) {
						direction.setX(direction.getX() * (Math.random() < 0.5 ? -1 : 1));
						direction.setZ(direction.getZ() * (Math.random() < 0.5 ? -1 : 1));
						ParticleEffects.FIREWORKS_SPARK.display(0F, 0F, 0F, 0F, 1, thunder, 257);
						thunder.add(direction.getX() / v, Math.abs(direction.getY()) / -v, direction.getZ() / v);
						if (Math.random() < 0.1)
							direction = Vector.getRandom();
					}
				}
			}
		}
	}

}
