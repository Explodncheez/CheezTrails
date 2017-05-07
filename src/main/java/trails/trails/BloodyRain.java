package trails.trails;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.BlockData;;

public class BloodyRain extends ParticleTrail {

	public BloodyRain(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		Location loc = p.getLocation().add(0, 1, 0);
		ParticleEffects.RED_DUST.display(0.2F, 0.2F, 0.2F, 0F, 4, loc, 256);
	}

	@Override
    public void doStandEffect() {
		
		for (Player p : users) {
			if (p.hasMetadata("trail.standingstill")) {
				if (TrailListener.cycle % 4 == 0) {
					Location loc = p.getLocation().add(0,3.0,0);
					ParticleEffects.EXPLODE.display(0.45F, 0F, 0.45F, 0F, 8, loc, 257);
				}
				double x = Math.random() - 0.5;
				double z = Math.random() - 0.5;
	            ParticleEffects.FALLING_DUST.display(new BlockData(Material.REDSTONE_BLOCK, (byte) 0), Vector.getRandom().setX(0).setZ(0), (float) (Math.random() / 10F), p.getLocation().add(x, 3, z), 257);
			}
		}
	}

}
