package trails.trails;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Music extends ParticleTrail {

	public Music(ParticleInfo info) {
		super(info);
	}
	
	private static double[] CACHE = new double[48];
	static {
	    for (int i = 0; i < 48; i++) {
	        CACHE[i] = 1.2 + 0.5 * Math.cos(i * Math.PI / 6);
	    }
	}

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, CACHE[TrailListener.cycle], 0);
		ParticleEffects.NOTE.display(0F, 0F, 0F, 2.0F, 1, loc, 256);
	}

	@Override
    public void doStandEffect() {
		if (TrailListener.cycle % 5 != 0) return;

		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc = p.getLocation().add(0,0.5,0);
				ParticleEffects.NOTE.display(0.4F, 0.5F, 0.4F, 1.0F, 2, loc, 256);
			}
		}
	}

}
