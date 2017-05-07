package trails.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import trails.CheezTrails;
import trails.ParticleTrail;
import trails.listener.TrailHandler;

public class MenuEditor {

    private static final ItemStack
    empty = CheezTrails.ConstructItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8, "§dClick > §fSelect This Slot", new String[] {"§7This allows you to customize the /trails menu.", "§7Select two slots to swap their positions!", "§7Click the Emerald to save the layout!"}),
    blank = CheezTrails.ConstructItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15, "", new String[] {}),
    save = CheezTrails.ConstructItemStack(Material.EMERALD, 1, (short) 0, "§a§lSave Layout", new String[] {"§7Saves this layout as the menu for §f/trails§7.", "§c§lNOTE: §fYou must §c/trails save§f after clicking to", "§fsave this data to the config!"}),
    remove = CheezTrails.ConstructItemStack(Material.BARRIER, 1, (short) 0, "§c§lRemove Icon", new String[] {"§7Select an Effect Icon and click this", "§7to remove it from the menu. Unused icons", "§7are shown in your inventory and can be", "§7restored by clicking on them."});

    private static Map<Player, MenuEditor> Editors = new WeakHashMap<Player, MenuEditor>();
    
    public static MenuEditor get(Player p) {
        return Editors.get(p);
    }
    
    public static MenuEditor remove(Player p) {
        return Editors.remove(p);
    }
    
    public MenuEditor(Player p) {
        this.p = p;
        saved = p.getInventory().getContents().clone();
        p.getInventory().clear();
        
        for (int i = 0; i < 9; i++) {
            p.getInventory().setItem(i, blank);
        }
        
        selected = -1;
        inv = Bukkit.createInventory(null, 54, "[ §1Effect Menu Editor §0]");
        upper = new HashMap<Integer, ParticleTrail>(ParticleTrail.getTrails());
        lower = new HashMap<Integer, ParticleTrail>();
        
        for (int i = 0; i < inv.getContents().length; i++) {
            inv.setItem(i, i > 44 ? blank : empty);
        }
        
        int slot = p.getInventory().firstEmpty();
        for (ParticleTrail trail : ParticleTrail.getAllTrails()) {
            if (trail.getSlot() >= 0)
                inv.setItem(trail.getSlot(), trail.getIcon());
            else {
                if (trail.getName().equals("deek"))
                    continue;
                    
                p.getInventory().setItem(slot, trail.getIcon());
                lower.put(slot, trail);
                slot++;
            }
        }
        
        inv.setItem(45, remove);
        inv.setItem(53, save);
        
        p.sendMessage(TrailHandler.PREFIX + "§fClick two slots to swap their positions. Click the Emerald to save!");
        p.openInventory(inv);
        
        Editors.put(p, this);
    }
    
    private ItemStack[] saved;
    private Map<Integer, ParticleTrail> upper, lower;
    private Player p;
    private Inventory inv;
    private int selected;
    
    public void handle(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            if (e.getClickedInventory() == e.getView().getTopInventory()) {
                int clicked = e.getRawSlot();
                if (clicked > 44) {
                    switch (e.getCurrentItem().getType()) {
                    case EMERALD:
                        p.sendMessage(TrailHandler.PREFIX + "Saved Effect Menu layout! Remember to use §e/trails save §fto write the data to the config file!");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.4F, 1.8F);
                        ParticleTrail.refresh(upper);
                        p.closeInventory();
                        break;
                    case BARRIER:
                        if (selected > -1 && upper.containsKey(selected)) {
                            p.playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 0.4F, 0.6F);
                            
                            ParticleTrail trail = upper.remove(selected);
                            int emptySlot = p.getInventory().firstEmpty();
                            
                            p.getInventory().setItem(emptySlot, trail.getIcon());
                            lower.put(emptySlot, trail);
                            inv.setItem(selected, empty);
                        } else {
                            p.sendMessage(TrailHandler.PREFIX + "Select an icon, then click this to remove it from the menu.");
                        }
                        
                        if (selected > -1)
                            inv.getItem(selected).removeEnchantment(CheezTrails.glow);
                        selected = -1;
                        break;
                        default:
                            break;
                    }
                } else {
                
                    if (selected > -1) {
                        if (clicked == selected) {
                            p.sendMessage(TrailHandler.PREFIX + "Congration! You swapped a slot with itself!");
                            p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 0.4F, 1.6F);
                        } else {
                            ItemStack i = e.getCurrentItem().clone(), i2 = inv.getItem(selected);
                            i2.removeEnchantment(CheezTrails.glow);
                            inv.setItem(clicked, i2);
                            inv.setItem(selected, i);
                            
                            ParticleTrail trail = upper.remove(selected);
                            ParticleTrail trail2 = upper.remove(clicked);
                            
                            if (trail != null)
                                upper.put(clicked, trail);
                            
                            if (trail2 != null)
                                upper.put(selected, trail2);
                            
                            p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 0.4F, 1.2F);
                        }

                        e.getCurrentItem().removeEnchantment(CheezTrails.glow);
                        selected = -1;
                    } else {
                        selected = clicked;
                        e.getCurrentItem().addEnchantment(CheezTrails.glow, 1);
                        p.playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 0.4F, 0.8F);
                    }
                    
                }
                
            } else {
                
                int clicked = e.getSlot();
                if (clicked > 8) {
                    if (selected > -1)
                        inv.getItem(selected).removeEnchantment(CheezTrails.glow);
                    selected = -1;
                    
                    ParticleTrail trail = lower.remove(clicked);
                    p.getInventory().setItem(clicked, null);
                    
                    for (int i = 0; i < 36; i++) {
                        if (!upper.containsKey(i)) {
                            upper.put(i, trail);
                            e.getView().getTopInventory().setItem(i, trail.getIcon());
                            p.playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 0.4F, 1.2F);
                            break;
                        }
                    }
                }
                
            }
        }
    }
    
    public void restore(Player p) {
        p.getInventory().setContents(saved);
    }

}
