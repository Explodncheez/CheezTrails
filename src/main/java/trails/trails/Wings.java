package trails.trails;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import trails.ParticleTrail;
import trails.listener.TrailListener;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.OrdinaryColor;

public class Wings extends ParticleTrail {
    
    private static boolean x = true, o = false;
    
    public enum WingShape {
        ANGEL(new boolean[][] {
                { x, x, x, o, o, o, o, o, o, o, o, o, x, x, x},
                { o, x, x, x, o, o, o, o, o, o, o, x, x, x, o},
                { o, o, x, x, x, o, o, x, o, o, x, x, x, o, o},
                { o, o, x, x, x, x, x, x, x, x, x, x, x, o, o},
                { o, o, o, x, x, x, x, x, x, x, x, x, o, o, o},
                { o, o, o, x, x, x, x, x, x, x, x, x, o, o, o},
                { o, o, o, o, x, x, x, o, x, x, x, o, o, o, o},
                { o, o, o, o, x, x, o, o, o, x, x, o, o, o, o},
                { o, o, o, x, x, o, o, o, o, o, x, x, o, o, o}
        }),
        DRACONIC(new boolean[][] {
                { o, o, x, o, o, o, o, o, o, o, o, o, o, o, x, o, o},
                { o, o, x, x, o, o, o, o, o, o, o, o, o, x, x, o, o},
                { o, x, x, x, x, o, o, o, o, o, o, o, x, x, x, x, o},
                { o, x, x, x, x, o, o, o, o, o, o, o, x, x, x, x, o},
                { x, x, x, x, x, x, o, o, o, o, o, x, x, x, x, x, x},
                { x, x, x, x, x, x, x, x, o, x, x, x, x, x, x, x, x},
                { x, x, x, x, x, x, x, x, o, x, x, x, x, x, x, x, x},
                { x, x, x, x, o, x, o, o, o, o, o, x, o, x, o, x, x},
                { x, x, o, x, o, o, o, o, o, o, o, o, o, o, o, x, x},
                { x, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, x},
                { x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, x}
        }),
        PIETY(new boolean[][] {
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                { o, x, x, x, o, o, o, o, o, o, o, x, x, x, o},
                { o, o, x, x, x, x, x, x, x, x, x, x, x, o, o},
                { o, o, o, x, x, x, x, x, x, x, x, x, o, o, o},
                { o, o, o, o, x, x, x, x, x, x, x, o, o, o, o},
                { o, o, o, o, o, o, x, x, x, o, o, o, o, o, o}
        });
        
        
        private WingShape(boolean[][] boo) {
            v = new HashSet<Coordinate>();
            
            int yref = boo.length / 2;
            
            for (int i = 0; i < boo.length; i++) {
                boolean[] hoo = boo[i];
                int xref = hoo.length / 2;
                for (int j = 0; j < hoo.length; j++) {
                    if (hoo[j])
                        v.add(new Coordinate(j - xref, yref - i));
                }
            }
        }
        
        private Set<Coordinate> v;
    }
    
    public static class Coordinate {
        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        private double x, y;
    }
    
    public Wings(ParticleInfo info, WingShape shape, ParticleEffects wingeffect, ParticleEffects traileffect, OrdinaryColor wingcolor, OrdinaryColor trailcolor, OrdinaryColor halocolor) {
        super(info);
        this.shape = shape;
        this.wingcolor = wingcolor;
        this.wingeffect = wingeffect;
        this.traileffect = traileffect;
        this.trailcolor = trailcolor;
        this.halocolor = halocolor;
        this.wrq = wingeffect.equals(ParticleEffects.RED_DUST) && wingcolor != null;
        this.trq = traileffect.equals(ParticleEffects.RED_DUST) && trailcolor != null;
    }

    private WingShape shape;
    private ParticleEffects wingeffect, traileffect;
    private boolean wrq, trq;
    private OrdinaryColor wingcolor, trailcolor, halocolor;

    @Override
    public void doMoveEffect(Player p) {
        Vector facing = p.getLocation().getDirection().setY(0);
        Vector cross = facing.crossProduct(new Vector(0, 1, 0)).normalize();
        Location loc = p.getLocation().add(0, 1 + 0.2 * Math.cos(TrailListener.cycle * Math.PI / 6), 0);
        
        for (int i = 0; i < 3; i++) {
            if (trq) traileffect.display(trailcolor, loc.clone().add(cross.getX() * (-1.0 + i) * .25, 0, cross.getZ() * (-1.0 + i) * .25), 256);
            else traileffect.display(0F, 0F, 0F, 0F, 1, loc.clone().add(cross.getX() * (-1.0 + i) * .25, 0, cross.getZ() * (-1.0 + i) * .25), 256);
        }

        if (halocolor != null)
            ParticleEffects.RED_DUST.display(halocolor, p.getLocation().add(0, 2.1, 0), 256);
    }

    @Override
    public void doStandEffect() {
        for (Player p : users) {
            if (p.hasMetadata("trail.standingstill")) {
                Vector facing = p.getLocation().getDirection().setY(0);
                Location loc = p.getLocation().add(facing.getX() * -.3, 1.5, facing.getZ() * -.33);
                
                Vector cross = facing.crossProduct(new Vector(0, 1, 0)).normalize();
                for (Coordinate coord : shape.v) {
                    if (wrq) wingeffect.display(wingcolor, loc.clone().add(cross.getX() * coord.x * 0.2, coord.y * 0.2, cross.getZ() * coord.x * 0.2), 256);
                    else wingeffect.display(0F, 0F, 0F, 0F, 1, loc.clone().add(cross.getX() * coord.x * 0.2, coord.y * 0.2, cross.getZ() * coord.x * 0.2), 256);
                }
            }
        }
        
        if (halocolor != null)
            for (int i = 0; i < 8; i++) {
                double radians = Math.PI * i / 4D;
                double x = Math.cos(radians) * .2, 
                        z = Math.sin(radians) * .2;
                for (Player p : users) {
                    if (p.hasMetadata("trail.standingstill")) {
                        ParticleEffects.RED_DUST.display(halocolor, p.getLocation().add(x, 2.1, z), 256);
                    }
                }
            }
        
    }

}
