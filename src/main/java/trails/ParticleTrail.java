package trails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import trails.ConfigMessages.ConfigMessage;
import trails.trails.*;
import trails.trails.Wings.WingShape;
import utilities.particles.ParticleEffects;
import utilities.particles.ParticleEffects.OrdinaryColor;

public abstract class ParticleTrail {
    
    private static Map<Integer, ParticleTrail> TrailSlots = new HashMap<Integer, ParticleTrail>();
    private static Map<String, ParticleTrail> TrailIDs = new HashMap<String, ParticleTrail>();
    private static Set<String> addedPerms = new HashSet<String>();
    
    public static Map<Integer, ParticleTrail> getTrails() {
        TrailSlots.remove(-1);
        return TrailSlots;
    }
    
    public static Collection<ParticleTrail> getAllTrails() {
        return TrailIDs.values();
    }
    
    public static void refresh(Map<Integer, ParticleTrail> trails) {
        TrailSlots.clear();
        
        for (Iterator<Integer> it = trails.keySet().iterator(); it.hasNext();) {
            int i = it.next();
            ParticleTrail eff = trails.get(i);
            
            eff.slot = i;
            TrailSlots.put(i, eff);
        }
        
        for (ParticleTrail trail : TrailIDs.values()) {
            if (!TrailSlots.containsValue(trail)) {
                trail.slot = -1;
            }
        }
    }
    
    public static void load(FileConfiguration config) {
        TrailSlots.clear();
        TrailIDs.clear();
        
        for (ConfigMessage c : ConfigMessage.values())
            c.loadMessage();
        
        for (String s : addedPerms) {
            Bukkit.getServer().getPluginManager().removePermission(s);
        }
        addedPerms.clear();
        
        load(new Blizzard(new ParticleInfo(config.getConfigurationSection("trails.blizzard"))));
        load(new Blood(new ParticleInfo(config.getConfigurationSection("trails.blood"))));
        load(new Cookie(new ParticleInfo(config.getConfigurationSection("trails.dessert"))));
        load(new Corruption(new ParticleInfo(config.getConfigurationSection("trails.corruption"))));
        load(new DoubleRings(new ParticleInfo(config.getConfigurationSection("trails.golden")), new OrdinaryColor(255, 117, 1), new OrdinaryColor(255, 255, 1)));
        load(new DoubleRings(new ParticleInfo(config.getConfigurationSection("trails.crystalline")), new OrdinaryColor(241, 161, 255), new OrdinaryColor(217, 1, 255), new OrdinaryColor(157, 1, 255)));
        load(new DoubleRings(new ParticleInfo(config.getConfigurationSection("trails.terra")), new OrdinaryColor(1, 191, 13), new OrdinaryColor(72, 255, 5)));
        load(new DoubleRings(new ParticleInfo(config.getConfigurationSection("trails.oceanic")), new OrdinaryColor(1, 125, 255), new OrdinaryColor(1, 210, 255)));
        load(new DoubleRings(new ParticleInfo(config.getConfigurationSection("trails.shadow")), new OrdinaryColor(1, 1, 1), new OrdinaryColor(70, 70, 70), new OrdinaryColor(100, 100, 100)));
        load(new Emerald(new ParticleInfo(config.getConfigurationSection("trails.emerald"))));
        load(new Firestorm(new ParticleInfo(config.getConfigurationSection("trails.firestorm"))));
        load(new Flame(new ParticleInfo(config.getConfigurationSection("trails.flame"))));
        load(new Heart(new ParticleInfo(config.getConfigurationSection("trails.heart"))));
        load(new Knowledge(new ParticleInfo(config.getConfigurationSection("trails.knowledge"))));
        load(new Magic(new ParticleInfo(config.getConfigurationSection("trails.magic"))));
        load(new Melon(new ParticleInfo(config.getConfigurationSection("trails.melon"))));
        load(new Music(new ParticleInfo(config.getConfigurationSection("trails.music"))));
        load(new Nyan(new ParticleInfo(config.getConfigurationSection("trails.nyan"))));
        load(new Purity(new ParticleInfo(config.getConfigurationSection("trails.purity"))));
        load(new Shine(new ParticleInfo(config.getConfigurationSection("trails.shine"))));
        load(new Smoke(new ParticleInfo(config.getConfigurationSection("trails.smoke"))));
        load(new Storm(new ParticleInfo(config.getConfigurationSection("trails.storm"))));
        load(new Sugarcane(new ParticleInfo(config.getConfigurationSection("trails.sugarcane"))));
        load(new Wings(new ParticleInfo(config.getConfigurationSection("trails.angelic")), WingShape.PIETY, ParticleEffects.RED_DUST, ParticleEffects.FIREWORKS_SPARK, new OrdinaryColor(255, 255, 255), null, new OrdinaryColor(255, 255, 1)));
        load(new Wings(new ParticleInfo(config.getConfigurationSection("trails.draconic")), WingShape.DRACONIC, ParticleEffects.RED_DUST, ParticleEffects.RED_DUST, new OrdinaryColor(133, 38, 38), null, null));
        load(new Wings(new ParticleInfo(config.getConfigurationSection("trails.fairy")), WingShape.ANGEL, ParticleEffects.RED_DUST, ParticleEffects.RED_DUST, new OrdinaryColor(212, 255, 255), new OrdinaryColor(255, 212, 255), null));
        load(new InLove(new ParticleInfo(config.getConfigurationSection("trails.love"))));
        load(new ForeverAlone(new ParticleInfo(config.getConfigurationSection("trails.alone"))));
        load(new Vortex(new ParticleInfo(config.getConfigurationSection("trails.vortex"))));
        load(new Sparkle(new ParticleInfo(config.getConfigurationSection("trails.sparkle"))));
        load(new BloodyRain(new ParticleInfo(config.getConfigurationSection("trails.bloodyrain"))));
        load(new Bloop(new ParticleInfo(config.getConfigurationSection("trails.bloop"))));
        load(new Thunderstorm(new ParticleInfo(config.getConfigurationSection("trails.thunderstorm"))));
        load(new Secret(new ParticleInfo(-1, "MILK_BUCKET", "deek", "§d§lSECRET", "trails.deek", "§7LOLOLLOLKOLOLOL")));
        load(new Gamebreaker(new ParticleInfo(config.getConfigurationSection("trails.gamebreaker"))));
    }
    
    public static void save(FileConfiguration config) {
        for (Iterator<String> it = TrailIDs.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            
            if (s.equals("deek"))
                continue;
            
            ConfigurationSection section = config.getConfigurationSection("trails." + s);
            ParticleTrail trail = TrailIDs.get(s);
            section.set("slot", trail.slot);
            section.set("icon", trail.icon.getType().toString() + (trail.icon.getDurability() > 0 ? ":" + trail.icon.getDurability() : ""));
            section.set("display-name", trail.display);
            section.set("permission", trail.perm);
            section.set("description", trail.lore);
        }
        CheezTrails.getInstance().saveConfig();
    }
    
    private static void load(ParticleTrail trail) {
        if (trail.getSlot() > -1)
            TrailSlots.put(trail.getSlot(), trail);
        TrailIDs.put(trail.getName(), trail);
    }
    
    public static ParticleTrail get(String id) {
        return TrailIDs.get(id.toLowerCase());
    }
    
    public static ParticleTrail get(int slot) {
        return TrailSlots.get(slot);
    }
    
    public ParticleTrail(ParticleInfo s) {
        this(s.id, s.matdat, s.name, s.display, s.permission, s.lore);
    }
    
    public ParticleTrail(int id, String matdat, String name, String display, String permission, List<String> lore) {
        slot = id;
        this.name = name;
        this.display = display;
        this.lore = lore;
        this.users = new HashSet<Player>();
        perm = permission.equals("") ? "trails." + name : permission;
        String[] str = matdat.split(":");
        
        Material m;
        short b;
        try {
            m = Material.valueOf(str[0].toUpperCase());
        } catch (Exception e) {
            Bukkit.getLogger().info("Invalid config value for 'material' of trail: " + name + "! (" + str[0] + ")");
            m = Material.GRASS;
        }
        
        try {
            b = str.length > 1 ? Short.parseShort(str[1]) : 0;
        } catch (Exception e ) {
            Bukkit.getLogger().info("Invalid config value for 'data' of trail: " + name + "!");
            b = 0;
        }
        
        icon = CheezTrails.ConstructItemStack(m, 1, b, display, lore);

        if (Bukkit.getServer().getPluginManager().getPermission(perm) == null) {
            Bukkit.getServer().getPluginManager().addPermission(new Permission(perm, PermissionDefault.OP));
            addedPerms.add(perm);
        }
    }

    private int slot;
    private String name, display, perm;
    private ItemStack icon;
    private List<String> lore;
    
    protected Set<Player> users;
    
    public void addUser(Player p) {
        users.add(p);
    }
    
    public void removeUser(Player p) {
        users.remove(p);
    }
    
    public ItemStack getIcon() {
        return icon;
    }
    
    public int getSlot() {
        return slot;
    }
    
    public String getName() {
        return name;
    }
    
    public ItemStack getIcon(Player p) {
        ItemStack copy = getIcon().clone();
        
        if (!hasPermission(p)) {
            copy.setType(Material.GRAY_DYE);
        }
        
        return copy;
    }
    
    public boolean hasPermission(Player p) {
        return p.hasPermission(perm);
    }
    
    public abstract void doMoveEffect(Player p);
    
    public abstract void doStandEffect();
    
    public static class ParticleInfo {
        
        public ParticleInfo(ConfigurationSection s) {
            id = s.getInt("slot");
            matdat = s.getString("icon");
            name = s.getCurrentPath().replace("trails.", "");
            display = s.getString("display-name");
            permission = s.getString("permission");
            lore = s.getStringList("description");
        }
        
        public ParticleInfo(int id, String matdat, String name, String display, String permission, String... lore) {
            this.id = id;
            this.matdat = matdat;
            this.name = name;
            this.display = display;
            this.permission = permission;
            this.lore = new ArrayList<String>();
            for (String s : lore) {
                this.lore.add(s);
            }
        }
        
        private int id;
        private String matdat, name, display, permission;
        private List<String> lore;
    }

}
