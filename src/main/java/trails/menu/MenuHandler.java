package trails.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import trails.CheezTrails;
import trails.ParticleTrail;
import trails.listener.TrailHandler;

public class MenuHandler {
    
    public static void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, "[ §1Particle Effects §0]");
        for (ParticleTrail trail : ParticleTrail.getTrails().values()) {
            if (trail.getSlot() > -1)
                inv.setItem(trail.getSlot(), trail.getIcon(p));
        }
        
        if (TrailHandler.getActiveTrail(p) != null) {
            int slot = TrailHandler.getActiveTrail(p).getSlot();
            if (slot >= 0)
                inv.getItem(slot).addUnsafeEnchantment(CheezTrails.glow, 1);
        }
        
        p.openInventory(inv);
    }
    
}
