package trails.trails;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;

public class Bloop extends ParticleTrail {
    
    private static double[] moveCache = new double[12];
    private static double[][][] standCache = new double[48][2][4];
    static {
        for (int i = 0; i < 12; i++) {
            double radians = i * Math.PI / 6;
            moveCache[i] = Math.cos(radians);
        }
        for (int i = 0; i < 48; i++) {
            double radians = i * Math.PI / 24;
            for (int j = 0; j < 4; j++) {
                double k = Math.PI * j / 2D;
                standCache[i][0][j] = Math.cos(radians + k);
                standCache[i][1][j] = Math.sin(radians + k);
            }
            
        }
    }

	public Bloop(ParticleInfo info) {
		super(info);
	}

	@Override
    public void doMoveEffect(Player p) {
		Vector dir = p.getLocation().getDirection().multiply(-1D);
		Vector cross = dir.setY(0).crossProduct(new Vector(0, 1, 0));
		double d = moveCache[TrailListener.cycle % 12];
		ParticleEffects.END_ROD.display(new Vector(0, 0.15, 0), 1F, p.getLocation().add(cross.getX() * d, 0.1, cross.getZ() * d), 256);
	}

	@Override
    public void doStandEffect() {
		double[][] d = standCache[TrailListener.cycle];
		
		for (int i = 0; i < 4; i++) {
		    double x = d[0][i];
		    double z = d[1][i];
			for (Player p : users) {
				if (p.hasMetadata("trail.standingstill")) {
					ParticleEffects.END_ROD.display(new Vector(0, 0.15, 0), 1.5F, p.getLocation().add(x, 0.1, z), 257);
				}
			}
		}
	}

}
