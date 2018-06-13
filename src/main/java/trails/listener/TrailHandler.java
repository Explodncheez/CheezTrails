package trails.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import trails.ConfigMessages.ConfigMessage;
import trails.ParticleTrail;

public class TrailHandler {
    
    public static Map<Player, ParticleTrail> ScrubPlayers = new HashMap<Player, ParticleTrail>();
    public static Map<ParticleTrail, Integer> UsedTrails = new HashMap<ParticleTrail, Integer>();
    private static Map<UUID, ParticleTrail> lastUsed = new HashMap<>();
    public static final String PREFIX = "§9CheezTrails §1§l> §7";
    
    public static int getTrailCount(ParticleTrail trail) {
        return UsedTrails.get(trail) == null ? 0 : UsedTrails.get(trail);
    }
    
    public static void setLastUsed(UUID id, @Nullable ParticleTrail trail) {
        if (trail == null) {
            lastUsed.remove(id);
            return;
        }
        lastUsed.put(id, trail);
    }
    
    public static void activateLastUsed(UUID id) {
        ParticleTrail pt = lastUsed.get(id);
        if (pt == null) {
            return;
        }
        setActiveTrail(Bukkit.getPlayer(id), pt, false);
    }
    
    public static void loadLastUsed(FileConfiguration config) {
        ConfigurationSection sec = config.getConfigurationSection("last-used");
        if (sec == null) {
            sec = config.createSection("last-used");
        }
        for (String key : sec.getKeys(false)) {
            ParticleTrail trail = ParticleTrail.get(sec.getString(key));
            if (trail == null) {
                continue;
            }
            lastUsed.put(UUID.fromString(key), trail);
        }
    }
    
    public static FileConfiguration saveLastUsed() {
        FileConfiguration config = new YamlConfiguration();
        ConfigurationSection sec = config.createSection("last-used");
        for (Map.Entry<UUID, ParticleTrail> entry : lastUsed.entrySet()) {
            sec.set(entry.getKey().toString(), entry.getValue().getName());
        }
        return config;
    }
    
    public static boolean setActiveTrail(Player p, ParticleTrail trail, boolean ignorePerm) {
        if (trail.hasPermission(p) || ignorePerm) {
            ParticleTrail ptrail = ScrubPlayers.get(p);
            if (ptrail != null) {
                a(ptrail);
                ptrail.removeUser(p);
            }
            
            ScrubPlayers.put(p, trail);
            p.sendMessage(PREFIX + ConfigMessage.ACTIVATE_EFFECT_SUCCESS.getMessage(trail.getIcon().getItemMeta().getDisplayName()));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.8F, 1.35F);
            UsedTrails.put(trail, getTrailCount(trail) + 1);
            trail.addUser(p);
            return true;
        }
        p.sendMessage(PREFIX + ConfigMessage.ACTIVATE_EFFECT_FAIL.getMessage(trail.getIcon().getItemMeta().getDisplayName()));
        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.8F, 1.25F);
        return false;
    }
    
    public static boolean removeTrail(Player p) {
        ParticleTrail trail = ScrubPlayers.remove(p);
        if (trail != null) {
            p.sendMessage(PREFIX + ConfigMessage.DEACTIVATE_EFFECT_SUCCESS.getMessage(trail.getIcon().getItemMeta().getDisplayName()));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.8F, 0.75F);
            
            a(trail);
            trail.removeUser(p);
            return true;
        }
        p.sendMessage(PREFIX + ConfigMessage.DEACTIVATE_EFFECT_FAIL.getMessage("%TRAIL%"));
        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.8F, 1.25F);
        return false;
    }
    
    private static void a(ParticleTrail trail) {
        int i = getTrailCount(trail);
        if (i == 1) {
            UsedTrails.remove(trail);
        } else {
            UsedTrails.put(trail, i - 1);
        }
    }
    
    public static void r(Player p) {
        ParticleTrail trail = ScrubPlayers.remove(p);
        
        if (trail != null) {
            a(trail);
            trail.removeUser(p);
        }
        
    }
    
    public static ParticleTrail getActiveTrail(Player p) {
        return ScrubPlayers.get(p);
    }

}
