package trails.trails;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.ItemData;

public class Cookie extends ParticleTrail {

	public Cookie(ParticleInfo info) {
		super(info);
	}
	
	private static ItemData[] LEL = { new ItemData(Material.CAKE, (byte) 0), new ItemData(Material.COOKIE, (byte) 0) };

	@Override
    public void doMoveEffect(Player p) {
		ParticleEffects.ITEM_CRACK.display(LEL[1], 0.1F, 0.1F, 0.1F, 0.02F, 2, p.getLocation().add(0, 0.2, 0), 256);
	}

	@Override
    public void doStandEffect() {
		double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * 0.75;
		double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * 0.75;
		double y = 0.8 + x * 0.8;
		
		for (Player p : users)
			if (p.hasMetadata("trail.standingstill")) {
				for (ItemData data : LEL) {
					ParticleEffects.ITEM_CRACK.display(data, 0.1F, 0.1F, 0.1F, 0.02F, 3, p.getLocation().add(x, y, z), 256);
					ParticleEffects.ITEM_CRACK.display(data, 0.1F, 0.1F, 0.1F, 0.02F, 3, p.getLocation().add(-x, y, -z), 256);
				}
			}
	}

}
