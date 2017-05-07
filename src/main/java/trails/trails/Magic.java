package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import utilities.particles.ParticleEffects;

public class Magic extends ParticleTrail {

	public Magic(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, 0.4, 0);
		ParticleEffects.WITCH_MAGIC.display(0.1F, 0.3F, 0.1F, 0F, 3, loc, 256);
	}

	@Override
    public void doStandEffect() {
		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(0,0.2,0);
				ParticleEffects.WITCH_MAGIC.display(0.2F, 0.4F, 0.2F, 0.001F, 1, loc, 256);
			}
		}
	}

}
