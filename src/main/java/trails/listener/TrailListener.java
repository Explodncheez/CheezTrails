package trails.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import trails.CheezTrails;
import trails.ParticleTrail;
import trails.menu.MenuEditor;

public class TrailListener implements Listener {
	
	private static final FixedMetadataValue metadata = new FixedMetadataValue(CheezTrails.getInstance(), 1);
	public static int cycle = 0;
	public static int delay = 1;
	
	private BukkitTask runnable;
	
	public TrailListener(int cycleTicks) {
		a(cycleTicks);
		b();
	}
	
	public static void a(int i) {
	    if (i <= 0)
	       i = 1;
	    delay = i;
	}
	
	public void b() {
		if (runnable != null)
			runnable.cancel();
		
		runnable = new BukkitRunnable() {
		    @Override
			public void run() {
			    if (cycle % delay == 0) {
    				for (ParticleTrail trail : TrailHandler.UsedTrails.keySet()) {
    					trail.doStandEffect();
    				}
    				for (Player p : TrailHandler.ScrubPlayers.keySet()) {
    					if (cycle % 12 == 0) {
    					    if ((!p.hasMetadata("trail.movement") || p.getMetadata("trail.movement").get(0).asString().equals(stringify(p.getLocation())))) {
    					        p.setMetadata("trail.standingstill", metadata);
    					    } else {
    					        p.setMetadata("trail.movement", new FixedMetadataValue(CheezTrails.getInstance(), stringify(p.getLocation())));
    					    }
    					}
    				}
			    }
				cycle = ++cycle % 48;
			}
		}.runTaskTimer(CheezTrails.getInstance(), 5L, 1L);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent e) {
		ParticleTrail scrub = TrailHandler.getActiveTrail(e.getPlayer());
		if (scrub != null && (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ() || e.getFrom().getY() != e.getTo().getY())) {
			scrub.doMoveEffect(e.getPlayer());
			if (e.getPlayer().hasMetadata("trail.standingstill"))
				e.getPlayer().removeMetadata("trail.standingstill", CheezTrails.getInstance());
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("[ §1Particle Effects §0]")) {
			e.setCancelled(true);
			
			if (e.getCurrentItem() != null && e.getClickedInventory() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				Inventory inv = e.getClickedInventory();
				int slot = e.getSlot();
				if (e.getCurrentItem().containsEnchantment(CheezTrails.glow)) {
					e.getCurrentItem().removeEnchantment(CheezTrails.glow);
					TrailHandler.removeTrail(p);
				} else if (TrailHandler.setActiveTrail(p, ParticleTrail.get(slot), false)) {
					ParticleTrail trail = TrailHandler.getActiveTrail(p);
					if (trail != null)
						inv.getItem(trail.getSlot()).removeEnchantment(CheezTrails.glow);
					inv.getItem(slot).addUnsafeEnchantment(CheezTrails.glow, 1);
					p.closeInventory();
				}
			}
		} else if (e.getInventory().getName().equals("[ §1Effect Menu Editor §0]")) {
			Player p = (Player) e.getWhoClicked();
			
			e.setCancelled(true);
			MenuEditor me = MenuEditor.get(p);
			if (me != null) {
				me.handle(e);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equals("[ §1Effect Menu Editor §0]")) {
			final Player p = (Player) e.getPlayer();
			p.getInventory().clear();
			MenuEditor me = MenuEditor.remove(p);
			if (me != null) {
				me.restore(p);
				
				new BukkitRunnable() {
					@Override
                    public void run() {
						if (p.isValid())
							p.updateInventory();
					}
				}.runTaskLater(CheezTrails.getInstance(), 2L);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		TrailHandler.r(e.getPlayer());
	}
	
	private static String stringify(Location loc) {
		return loc.getX() + "," + loc.getY() + "," + loc.getZ();
	}

}
