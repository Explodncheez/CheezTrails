package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class InLove extends ParticleTrail {

	public InLove(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, 1, 0);
		ParticleEffects.RED_DUST.display(0F, 0F, 0F, 0F, 1, loc, 256);
	}

	@Override
    public void doStandEffect() {
		if (TrailListener.cycle % 4 != 0) return;

		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(0 , 2.0 , 0);
				ParticleEffects.HEART.display(0F, 0F, 0F, 1.0F, 1, loc, 257);
			}
		}
	}

}