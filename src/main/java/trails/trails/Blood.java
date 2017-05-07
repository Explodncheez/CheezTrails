package trails.trails;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.BlockData;

public class Blood extends ParticleTrail {
    
    private static double[][] cache = new double[48][3];
    static {
        for (int c = 0; c < 48; c++) {
            double radians = c * Math.PI / 24;
            double q = 1 - ((c > 24 ? c - 24 : c) / 48D);
            cache[c][0] = Math.cos(radians) * q;
            cache[c][1] = Math.sin((radians > Math.PI ? radians - Math.PI : radians) * 0.5);
            cache[c][2] = Math.sin(radians) * q;
        }
    }

	public Blood(ParticleInfo info) {
		super(info);
	}
	
	private static BlockData REDSTONE = new BlockData(Material.REDSTONE_BLOCK, (byte) 0);

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, 0.2, 0);
		ParticleEffects.RED_DUST.display(0.2F, 0.1F, 0.2F, 0F, 3, loc, 256);
	}

	@Override
    public void doStandEffect() {
		double[] vals = cache[TrailListener.cycle];

		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				ParticleEffects.BLOCK_DUST.display(REDSTONE, 0.08F, 0.1F, 0.08F, 0.06F, 2, p.getLocation().add(0, 1, 0), 256);
				ParticleEffects.RED_DUST.display(0.05F, 0.05F, 0.05F, 0F, 3, p.getLocation().add(vals[0], vals[1], vals[2]), 256);
			}
		}
	}

}