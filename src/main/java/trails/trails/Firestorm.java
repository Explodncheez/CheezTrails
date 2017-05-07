package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Firestorm extends ParticleTrail {

	public Firestorm(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		ParticleEffects.LAVA.display(0.1F, 0.05F, 0.1F, 0.1F, 1, p.getLocation(), 256);
	}

	@Override
    public void doStandEffect() {
		if (TrailListener.cycle % 4 != 0) return;

		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(0,3.0,0);
				ParticleEffects.SMOKE_LARGE.display(0.45F, 0F, 0.45F, 0F, 8, loc, 257);
				ParticleEffects.DRIP_LAVA.display(0.38F, 0.05F, 0.38F, 1.0F, 4, loc, 257);
			}
		}
	}

}
