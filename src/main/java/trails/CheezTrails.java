package trails;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import trails.listener.TrailHandler;
import trails.listener.TrailListener;
import utilities.Utils;

public class CheezTrails extends JavaPlugin {
    
    private static CheezTrails plugin;
    private static FileConfiguration config, lastused;
    public static Enchantment glow;
    
    private TrailListener listener;
    
    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();
        config = getConfig();
        
        ParticleTrail.load(config);
        // load after particle trails are loaded
        File lastusedfile = new File(this.getDataFolder() + File.separator + "lastused.yml");
        if (!lastusedfile.exists()) {
            try {
                lastusedfile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        lastused = YamlConfiguration.loadConfiguration(lastusedfile);
        TrailHandler.loadLastUsed(lastused);
        
        listener = new TrailListener(config.getInt("stand-trail-tick-speed"));
        getServer().getPluginManager().registerEvents(listener, this);
        
        boolean cheezutils = getServer().getPluginManager().getPlugin("CheezUtils") == null;
        glow = cheezutils ? new EnchantGlow() : Utils.glow;
        
        if (!cheezutils)
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                try {
                    Enchantment.registerEnchantment(glow);
                } catch (IllegalArgumentException e) {
    
                }
            } catch (Exception e) {
            }
        
        CommandExecutor exe = new CommandHandler();
        getCommand("trail").setExecutor(exe);
        getCommand("trails").setExecutor(exe);
    }
    
    public static void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
    
    public static void save() {
        plugin.saveConfig();
    }
    
    public static CheezTrails getInstance() {
        return plugin;
    }
    
    public static FileConfiguration config() {
        return config;
    }
    
    public TrailListener getListener() {
        return listener;
    }
    
    public static int random(int range) {
        return (int) (Math.random() * (range + 1));
    }
    
    public static ItemStack ConstructItemStack(Material mat, int amount, short durability, String displayName, List<String> lore) {
        ItemStack i = new ItemStack(mat, amount, durability);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(displayName.replace("&", "§"));
        List<String> newlore = new LinkedList<String>();
        for (String s : lore)
            newlore.add(s.replace("&", "§"));
        meta.setLore(newlore);
        i.setItemMeta(meta);
        return i;
    }
    
    public static ItemStack ConstructItemStack(Material mat, int amount, short durability, String displayName, String[] lore) {
        List<String> loreList = new LinkedList<String>();
        for (String s : lore)
            loreList.add(s);
        
        return ConstructItemStack(mat, amount, durability, displayName, loreList);
    }

}
