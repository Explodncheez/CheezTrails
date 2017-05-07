package trails.trails;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.BlockData;
import utilities.particles.ParticleEffects.ItemData;

public class Sugarcane extends ParticleTrail {

	public Sugarcane(ParticleInfo info) {
		super(info);
	}
	
	private static ItemData SUGARCANE = new ItemData(Material.SUGAR_CANE, (byte) 0);
	private static BlockData CANE_BLOCK = new BlockData(Material.SUGAR_CANE_BLOCK, (byte) 0);

	@Override
    public void doMoveEffect(Player p) {
		ParticleEffects.ITEM_CRACK.display(SUGARCANE, 0.1F, 0.1F, 0.1F, 0.02F, 2, p.getLocation().add(0, 0.2, 0), 256);
	}

	@Override
    public void doStandEffect() {
		double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * 0.75;
		double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * 0.75;
		double y = 0.8 + x * 0.8;

		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				Location loc1 = p.getLocation().add(x, y, z), loc2 = p.getLocation().add(-x, y, -z);
				ParticleEffects.ITEM_CRACK.display(SUGARCANE, 0.1F, 0.1F, 0.1F, 0.02F, 3, loc1, 256);
				ParticleEffects.ITEM_CRACK.display(SUGARCANE, 0.1F, 0.1F, 0.1F, 0.02F, 3, loc2, 256);
				ParticleEffects.BLOCK_DUST.display(CANE_BLOCK, 0.1F, 0.1F, 0.1F, 0.02F, 2, loc1, 256);
				ParticleEffects.BLOCK_DUST.display(CANE_BLOCK, 0.1F, 0.1F, 0.1F, 0.02F, 2, loc2, 256);
			}
		}
	}

}
