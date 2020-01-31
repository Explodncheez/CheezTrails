package trails.trails;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import trails.PositionCache;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.BlockData;

public class Gamebreaker extends ParticleTrail {

    public Gamebreaker(ParticleInfo info) {
        super(info);
    }
    
    private static BlockData BLACK = new BlockData(Material.BLACK_WOOL, (byte) 0);
    private static BlockData MAGENTA = new BlockData(Material.MAGENTA_WOOL, (byte) 0);
    
    private static ParticleEffects[] random = {
    		ParticleEffects.FIREWORKS_SPARK,
    		ParticleEffects.VILLAGER_HAPPY,
    		ParticleEffects.VILLAGER_ANGRY,
    		ParticleEffects.RED_DUST,
    		ParticleEffects.HEART,
    		ParticleEffects.FLAME,
    		ParticleEffects.SMOKE,
    		ParticleEffects.END_ROD,
    		ParticleEffects.SPELL,
    		ParticleEffects.EXPLODE
    };

    private static Set<Player> halt = new HashSet<>();
    private static Set<Player> spasm = new HashSet<>();
    private static Map<Player, ParticleEffects> randoms = new HashMap<>();

    @Override
    public void doMoveEffect(Player p) {
        ParticleEffects.BLOCK_DUST.display(BLACK, 0.1F, 0.1F, 0.1F, 0.02F, 2, p.getLocation().add(0, 0.2, 0), 256);
        ParticleEffects.BLOCK_DUST.display(MAGENTA, 0.1F, 0.1F, 0.1F, 0.02F, 2, p.getLocation().add(0, 0.2, 0), 256);
    }

    @Override
    public void doStandEffect() {
        double x = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][1] * 0.75;
        double z = PositionCache.NORMAL_CIRCLE[TrailListener.cycle][0] * 0.75;
        double y = 0.6;

        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
            	boolean spas = spasm.contains(p);
            	if (spas && Math.random() < 0.2) {
            		spasm.remove(p);
            	}
            	
            	if (halt.contains(p)) {
            		if (Math.random() < 0.1) {
            			halt.remove(p);
            		}
            		continue;
            	}

            	double yy = y + (spas ? (Math.random() - 0.5) : 0);
                Location loc1 = p.getLocation().add(x, yy, z), loc2 = p.getLocation().add(-x, yy, -z);
                
            	ParticleEffects replace = randoms.get(p);
            	if (replace == null) {
	                ParticleEffects.BLOCK_DUST.display(BLACK, 0.1F, 0.1F, 0.1F, 0.01F, 3, loc1, 256);
	                ParticleEffects.BLOCK_DUST.display(BLACK, 0.1F, 0.1F, 0.1F, 0.01F, 3, loc2, 256);
	                ParticleEffects.BLOCK_DUST.display(MAGENTA, 0.1F, 0.1F, 0.1F, 0.01F, 2, loc1, 256);
	                ParticleEffects.BLOCK_DUST.display(MAGENTA, 0.1F, 0.1F, 0.1F, 0.01F, 2, loc2, 256);
            	} else {
            		replace.display(0.1f, 0.1f, 0.1f, 0.02f, 6, loc1, 256);
            		replace.display(0.1f, 0.1f, 0.1f, 0.02f, 6, loc2, 256);
            		
            		if (Math.random() < 0.12) {
            			randoms.remove(p);
            		}
            	}

            	if (Math.random() < 0.02) {
            		spasm.add(p);
            	}
            	
            	if (Math.random() < 0.02) {
            		halt.add(p);
            	} else if (Math.random() < 0.01) {
            		randoms.put(p, utilities.Utils.get(random));
            	}
            
                if (Math.random() < 0.01) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.4F, 1.0F);
                    
                    Location thunder = p.getLocation().add(Math.random() - 0.5, 3, Math.random() - 0.5);
                    Vector direction = Vector.getRandom();
                    double ymin = p.getLocation().getY(), v = 20;
                    
                    ParticleEffects.EXPLODE.display(0.2f, 0.2f, 0.2f, 0.05f, 24, thunder, 256);
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
