package utilities.particles;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utilities.particles.ReflectionUtils.PackageType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


/**
 * <b>ParticleEffect Library</b>
 * <p>
 * This library was created by @DarkBlade12 and allows you to display all Minecraft particle effects on a Bukkit server
 * <p>
 * You are welcome to use it, modify it and redistribute it under the following conditions:
 * <ul>
 * <li>Don't claim this class as your own
 * <li>Don't remove this disclaimer
 * </ul>
 * <p>
 * Special thanks:
 * <ul>
 * <li>@microgeek (original idea, names and packet parameters)
 * <li>@ShadyPotato (1.8 names, ids and packet parameters)
 * <li>@RingOfStorms (particle behavior)
 * <li>@Cybermaxke (particle behavior)
 * </ul>
 * <p>
 * <i>It would be nice if you provide credit to me if you use this class in a published project</i>
 * 
 * @author DarkBlade12
 * @version 1.7
 */
public enum ParticleEffects {
    EXPLODE("explode", 0, -1, ParticleProperty.DIRECTIONAL),
    EXPLOSION_LARGE("largeexplode", 1, -1),
    HUGE_EXPLODE("hugeexplosion", 2, -1),
    FIREWORKS_SPARK("fireworksSpark", 3, -1, ParticleProperty.DIRECTIONAL),
    BUBBLE("bubble", 4, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),
    SPLASH("splash", 5, -1, ParticleProperty.DIRECTIONAL),
    WATER_WAKE("wake", 6, 7, ParticleProperty.DIRECTIONAL),
    SUSPENDED("suspended", 7, -1, ParticleProperty.REQUIRES_WATER),
    SUSPENDED_DEPTH("depthSuspend", 8, -1, ParticleProperty.DIRECTIONAL),
    CRIT("crit", 9, -1, ParticleProperty.DIRECTIONAL),
    MAGIC_CRIT("magicCrit", 10, -1, ParticleProperty.DIRECTIONAL),
    SMOKE("smoke", 11, -1, ParticleProperty.DIRECTIONAL),
    SMOKE_LARGE("largesmoke", 12, -1, ParticleProperty.DIRECTIONAL),
    SPELL("spell", 13, -1),
    INSTANT_SPELL("instantSpell", 14, -1),
    MOB_SPELL("mobSpell", 15, -1, ParticleProperty.COLORABLE),
    MOB_SPELL_AMBIENT("mobSpellAmbient", 16, -1, ParticleProperty.COLORABLE),
    WITCH_MAGIC("witchMagic", 17, -1),
    DRIP_WATER("dripWater", 18, -1),
    DRIP_LAVA("dripLava", 19, -1),
    VILLAGER_ANGRY("angryVillager", 20, -1),
    VILLAGER_HAPPY("happyVillager", 21, -1, ParticleProperty.DIRECTIONAL),
    TOWN_AURA("townaura", 22, -1, ParticleProperty.DIRECTIONAL),
    NOTE("note", 23, -1, ParticleProperty.COLORABLE),
    PORTAL("portal", 24, -1, ParticleProperty.DIRECTIONAL),
    ENCHANTMENT_TABLE("enchantmenttable", 25, -1, ParticleProperty.DIRECTIONAL),
    FLAME("flame", 26, -1, ParticleProperty.DIRECTIONAL),
    LAVA("lava", 27, -1),
    FOOTSTEP("footstep", 28, -1),
    CLOUD("cloud", 29, -1, ParticleProperty.DIRECTIONAL),
    RED_DUST("reddust", 30, -1, ParticleProperty.COLORABLE),
    SNOWBALL("snowballpoof", 31, -1),
    SNOW_SHOVEL("snowshovel", 32, -1, ParticleProperty.DIRECTIONAL),
    SLIME("slime", 33, -1),
    HEART("heart", 34, -1),
    BARRIER("barrier", 35, 8),
    ITEM_CRACK("iconcrack", 36, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    BLOCK_CRACK("blockcrack", 37, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    BLOCK_DUST("blockdust", 38, 7, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    WATER_DROP("droplet", 39, 8),
    ITEM_TAKE("take", 40, 8),
    MOB_APPEARANCE("mobappearance", 41, 8),
    DRAGON_BREATH("dragonbreath", 42, 8),
    END_ROD("endRod", 43, 8, ParticleProperty.DIRECTIONAL),
    DAMAGE_INDICATOR("damageIndicator", 44, 8, ParticleProperty.DIRECTIONAL),
    SWEEP_ATTACK("sweepAttack", 45, 8),
    FALLING_DUST("fallingdust", 46, 8, ParticleProperty.REQUIRES_DATA);

    private static final Map<String, ParticleEffects> NAME_MAP = new HashMap<String, ParticleEffects>();
    private static final Map<Integer, ParticleEffects> ID_MAP = new HashMap<Integer, ParticleEffects>();
    private final String name;
    private final int id;
    private final int requiredVersion;
    private final List<ParticleProperty> properties;

    // Initialize map for quick name and id lookup
    static {
        for (ParticleEffects effect : values()) {
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
        }
    }

    /**
     * Construct a new particle effect
     * 
     * @param name Name of this particle effect
     * @param id Id of this particle effect
     * @param requiredVersion Version which is required (1.x)
     * @param properties Properties of this particle effect
     */
    private ParticleEffects(String name, int id, int requiredVersion, ParticleProperty... properties) {
        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.properties = Arrays.asList(properties);
    }

    /**
     * Returns the name of this particle effect
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of this particle effect
     * 
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the required version for this particle effect (1.x)
     * 
     * @return The required version
     */
    public int getRequiredVersion() {
        return requiredVersion;
    }

    /**
     * Determine if this particle effect has a specific property
     * 
     * @return Whether it has the property or not
     */
    public boolean hasProperty(ParticleProperty property) {
        return properties.contains(property);
    }

    /**
     * Determine if this particle effect is supported by your current server version
     * 
     * @return Whether the particle effect is supported or not
     */
    public boolean isSupported() {
        return true;
        /*if (requiredVersion == -1) {
            return true;
        }
        return ParticlePacket.getVersion() >= requiredVersion;*/
    }

    /**
     * Returns the particle effect with the given name
     * 
     * @param name Name of the particle effect
     * @return The particle effect
     */
    public static ParticleEffects fromName(String name) {
        for (Entry<String, ParticleEffects> entry : NAME_MAP.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(name)) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }

    /**
     * Returns the particle effect with the given id
     * 
     * @param id Id of the particle effect
     * @return The particle effect
     */
    public static ParticleEffects fromId(int id) {
        for (Entry<Integer, ParticleEffects> entry : ID_MAP.entrySet()) {
            if (entry.getKey() != id) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }

    /**
     * Determine if water is at a certain location
     * 
     * @param location Location to check
     * @return Whether water is at this location or not
     */
    private static boolean isWater(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.WATER;
    }

    /**
     * Determine if the distance between @param location and one of the players exceeds 256
     * 
     * @param location Location to check
     * @return Whether the distance exceeds 256 or not
     */
    private static boolean isLongDistance(Location location, List<Player> players) {
        for (Player player : players) {
            if (player.getLocation().distanceSquared(location) < 65536) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * Determine if the data type for a particle effect is correct
     * 
     * @param effect Particle effect
     * @param data Particle data
     * @return Whether the data type is correct or not
     */
    private static boolean isDataCorrect(ParticleEffects effect, ParticleData data) {
        return ((effect == BLOCK_CRACK || effect == BLOCK_DUST || effect == FALLING_DUST) && data instanceof BlockData) || (effect == ITEM_CRACK && data instanceof ItemData);
    }

    /**
     * Determine if the color type for a particle effect is correct
     * 
     * @param effect Particle effect
     * @param color Particle color
     * @return Whether the color type is correct or not
     */
    private static boolean isColorCorrect(ParticleEffects effect, ParticleColor color) {
        return ((effect == MOB_SPELL || effect == MOB_SPELL_AMBIENT || effect == RED_DUST || effect == FALLING_DUST) && color instanceof OrdinaryColor) || (effect == NOTE && color instanceof NoteColor);
    }

    /**
     * Displays a particle effect which is only visible for all players within a certain range in the world of @param center
     * 
     * @param offsetX Maximum distance particles can fly away from the center on the x-axis
     * @param offsetY Maximum distance particles can fly away from the center on the y-axis
     * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
     * @param speed Display speed of the particles
     * @param amount Amount of particles
     * @param center Center location of the effect
     * @param range Range of the visibility
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect requires additional data
     * @throws IllegalArgumentException If the particle effect requires water and none is at the center location
     * @see ParticlePacket
     * @see ParticlePacket#sendTo(Location, double)
     */
    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range >= 256, null).sendTo(center, range);
    }

    /**
     * Displays a particle effect which is only visible for the specified players
     * 
     * @param offsetX Maximum distance particles can fly away from the center on the x-axis
     * @param offsetY Maximum distance particles can fly away from the center on the y-axis
     * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
     * @param speed Display speed of the particles
     * @param amount Amount of particles
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect requires additional data
     * @throws IllegalArgumentException If the particle effect requires water and none is at the center location
     * @see ParticlePacket
     * @see ParticlePacket#sendTo(Location, List)
     */
    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null).sendTo(center, players);
    }

    /**
     * Displays a particle effect which is only visible for the specified players
     * 
     * @param offsetX Maximum distance particles can fly away from the center on the x-axis
     * @param offsetY Maximum distance particles can fly away from the center on the y-axis
     * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
     * @param speed Display speed of the particles
     * @param amount Amount of particles
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect requires additional data
     * @throws IllegalArgumentException If the particle effect requires water and none is at the center location
     * @see #display(float, float, float, float, int, Location, List)
     */
    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    /**
     * Displays a single particle which flies into a determined direction and is only visible for all players within a certain range in the world of @param center
     * 
     * @param direction Direction of the particle
     * @param speed Display speed of the particle
     * @param center Center location of the effect
     * @param range Range of the visibility
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect requires additional data
     * @throws IllegalArgumentException If the particle effect is not directional or if it requires water and none is at the center location
     * @see ParticlePacket#ParticlePacket(ParticleEffects, Vector, float, boolean, ParticleData)
     * @see ParticlePacket#sendTo(Location, double)
     */
    public void display(Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, direction, speed, range >= 256, null).sendTo(center, range);
    }

    /**
     * Displays a single particle which flies into a determined direction and is only visible for the specified players
     * 
     * @param direction Direction of the particle
     * @param speed Display speed of the particle
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect requires additional data
     * @throws IllegalArgumentException If the particle effect is not directional or if it requires water and none is at the center location
     * @see ParticlePacket#ParticlePacket(ParticleEffects, Vector, float, boolean, ParticleData)
     * @see ParticlePacket#sendTo(Location, List)
     */
    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, direction, speed, isLongDistance(center, players), null).sendTo(center, players);
    }

    /**
     * Displays a single particle which flies into a determined direction and is only visible for the specified players
     * 
     * @param direction Direction of the particle
     * @param speed Display speed of the particle
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect requires additional data
     * @throws IllegalArgumentException If the particle effect is not directional or if it requires water and none is at the center location
     * @see #display(Vector, float, Location, List)
     */
    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        display(direction, speed, center, Arrays.asList(players));
    }

    /**
     * Displays a single particle which is colored and only visible for all players within a certain range in the world of @param center
     * 
     * @param color Color of the particle
     * @param center Center location of the effect
     * @param range Range of the visibility
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleColorException If the particle effect is not colorable or the color type is incorrect
     * @see ParticlePacket#ParticlePacket(ParticleEffects, ParticleColor, boolean)
     * @see ParticlePacket#sendTo(Location, double)
     */
    public void display(ParticleColor color, Location center, double range) throws ParticleVersionException, ParticleColorException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!isColorCorrect(this, color)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, color, range >= 256).sendTo(center, range);
    }

    /**
     * Displays a single particle which is colored and only visible for the specified players
     * 
     * @param color Color of the particle
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleColorException If the particle effect is not colorable or the color type is incorrect
     * @see ParticlePacket#ParticlePacket(ParticleEffects, ParticleColor, boolean)
     * @see ParticlePacket#sendTo(Location, List)
     */
    public void display(ParticleColor color, Location center, List<Player> players) throws ParticleVersionException, ParticleColorException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!isColorCorrect(this, color)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, color, isLongDistance(center, players)).sendTo(center, players);
    }

    /**
     * Displays a single particle which is colored and only visible for the specified players
     * 
     * @param color Color of the particle
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleColorException If the particle effect is not colorable or the color type is incorrect
     * @see #display(ParticleColor, Location, List)
     */
    public void display(ParticleColor color, Location center, Player... players) throws ParticleVersionException, ParticleColorException {
        display(color, center, Arrays.asList(players));
    }

    /**
     * Displays a particle effect which requires additional data and is only visible for all players within a certain range in the world of @param center
     * 
     * @param data Data of the effect
     * @param offsetX Maximum distance particles can fly away from the center on the x-axis
     * @param offsetY Maximum distance particles can fly away from the center on the y-axis
     * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
     * @param speed Display speed of the particles
     * @param amount Amount of particles
     * @param center Center location of the effect
     * @param range Range of the visibility
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
     * @see ParticlePacket
     * @see ParticlePacket#sendTo(Location, double)
     */
    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range >= 256, data).sendTo(center, range);
    }

    /**
     * Displays a particle effect which requires additional data and is only visible for the specified players
     * 
     * @param data Data of the effect
     * @param offsetX Maximum distance particles can fly away from the center on the x-axis
     * @param offsetY Maximum distance particles can fly away from the center on the y-axis
     * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
     * @param speed Display speed of the particles
     * @param amount Amount of particles
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
     * @see ParticlePacket
     * @see ParticlePacket#sendTo(Location, List)
     */
    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data).sendTo(center, players);
    }

    /**
     * Displays a particle effect which requires additional data and is only visible for the specified players
     * 
     * @param data Data of the effect
     * @param offsetX Maximum distance particles can fly away from the center on the x-axis
     * @param offsetY Maximum distance particles can fly away from the center on the y-axis
     * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
     * @param speed Display speed of the particles
     * @param amount Amount of particles
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
     * @see #display(ParticleData, float, float, float, float, int, Location, List)
     */
    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
        display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    /**
     * Displays a single particle which requires additional data that flies into a determined direction and is only visible for all players within a certain range in the world of @param center
     * 
     * @param data Data of the effect
     * @param direction Direction of the particle
     * @param speed Display speed of the particles
     * @param center Center location of the effect
     * @param range Range of the visibility
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
     * @see ParticlePacket
     * @see ParticlePacket#sendTo(Location, double)
     */
    public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, direction, speed, range >= 256, data).sendTo(center, range);
    }

    /**
     * Displays a single particle which requires additional data that flies into a determined direction and is only visible for the specified players
     * 
     * @param data Data of the effect
     * @param direction Direction of the particle
     * @param speed Display speed of the particles
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
     * @see ParticlePacket
     * @see ParticlePacket#sendTo(Location, List)
     */
    public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, direction, speed, isLongDistance(center, players), data).sendTo(center, players);
    }

    /**
     * Displays a single particle which requires additional data that flies into a determined direction and is only visible for the specified players
     * 
     * @param data Data of the effect
     * @param direction Direction of the particle
     * @param speed Display speed of the particles
     * @param center Center location of the effect
     * @param players Receivers of the effect
     * @throws ParticleVersionException If the particle effect is not supported by the server version
     * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
     * @see #display(ParticleData, Vector, float, Location, List)
     */
    public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
        display(data, direction, speed, center, Arrays.asList(players));
    }

    /**
     * Represents the property of a particle effect
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.7
     */
    public static enum ParticleProperty {
        /**
         * The particle effect requires water to be displayed
         */
        REQUIRES_WATER,
        /**
         * The particle effect requires block or item data to be displayed
         */
        REQUIRES_DATA,
        /**
         * The particle effect uses the offsets as direction values
         */
        DIRECTIONAL,
        /**
         * The particle effect uses the offsets as color values
         */
        COLORABLE;
    }

    /**
     * Represents the particle data for effects like {@link ParticleEffects#ITEM_CRACK}, {@link ParticleEffects#BLOCK_CRACK} and {@link ParticleEffects#BLOCK_DUST}
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.6
     */
    public static abstract class ParticleData {
        private final Material material;
        private final byte data;
        private final int[] packetData;

        /**
         * Construct a new particle data
         * 
         * @param material Material of the item/block
         * @param data Data value of the item/block
         */
        @SuppressWarnings("deprecation")
        public ParticleData(Material material, byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[] { material.getId(), data };
        }

        /**
         * Returns the material of this data
         * 
         * @return The material
         */
        public Material getMaterial() {
            return material;
        }

        /**
         * Returns the data value of this data
         * 
         * @return The data value
         */
        public byte getData() {
            return data;
        }

        /**
         * Returns the data as an int array for packet construction
         * 
         * @return The data for the packet
         */
        public int[] getPacketData() {
            return packetData;
        }

        /**
         * Returns the data as a string for pre 1.8 versions
         * 
         * @return The data string for the packet
         */
        public String getPacketDataString() {
            return "_" + packetData[0] + "_" + packetData[1];
        }
    }

    /**
     * Represents the item data for the {@link ParticleEffects#ITEM_CRACK} effect
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.6
     */
    public static final class ItemData extends ParticleData {
        /**
         * Construct a new item data
         * 
         * @param material Material of the item
         * @param data Data value of the item
         * @see ParticleData#ParticleData(Material, byte)
         */
        public ItemData(Material material, byte data) {
            super(material, data);
        }
    }

    /**
     * Represents the block data for the {@link ParticleEffects#BLOCK_CRACK} and {@link ParticleEffects#BLOCK_DUST} effects
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.6
     */
    public static final class BlockData extends ParticleData {
        /**
         * Construct a new block data
         * 
         * @param material Material of the block
         * @param data Data value of the block
         * @throws IllegalArgumentException If the material is not a block
         * @see ParticleData#ParticleData(Material, byte)
         */
        public BlockData(Material material, byte data) throws IllegalArgumentException {
            super(material, data);
            if (!material.isBlock()) {
                throw new IllegalArgumentException("The material is not a block");
            }
        }
    }

    /**
     * Represents the color for effects like {@link ParticleEffects#SPELL_MOB}, {@link ParticleEffects#SPELL_MOB_AMBIENT}, {@link ParticleEffects#REDSTONE} and {@link ParticleEffects#NOTE}
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.7
     */
    public static abstract class ParticleColor {
        /**
         * Returns the value for the offsetX field
         * 
         * @return The offsetX value
         */
        public abstract float getValueX();

        /**
         * Returns the value for the offsetY field
         * 
         * @return The offsetY value
         */
        public abstract float getValueY();

        /**
         * Returns the value for the offsetZ field
         * 
         * @return The offsetZ value
         */
        public abstract float getValueZ();
    }

    /**
     * Represents the color for effects like {@link ParticleEffects#SPELL_MOB}, {@link ParticleEffects#SPELL_MOB_AMBIENT} and {@link ParticleEffects#NOTE}
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.7
     */
    public static final class OrdinaryColor extends ParticleColor {
        private final int red;
        private final int green;
        private final int blue;

        /**
         * Construct a new ordinary color
         * 
         * @param red Red value of the RGB format
         * @param green Green value of the RGB format
         * @param blue Blue value of the RGB format
         * @throws IllegalArgumentException If one of the values is lower than 0 or higher than 255
         */
        public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
            if (red < 0) {
                throw new IllegalArgumentException("The red value is lower than 0");
            }
            if (red > 255) {
                throw new IllegalArgumentException("The red value is higher than 255");
            }
            this.red = red;
            if (green < 0) {
                throw new IllegalArgumentException("The green value is lower than 0");
            }
            if (green > 255) {
                throw new IllegalArgumentException("The green value is higher than 255");
            }
            this.green = green;
            if (blue < 0) {
                throw new IllegalArgumentException("The blue value is lower than 0");
            }
            if (blue > 255) {
                throw new IllegalArgumentException("The blue value is higher than 255");
            }
            this.blue = blue;
        }

        /**
         * Returns the red value of the RGB format
         * 
         * @return The red value
         */
        public int getRed() {
            return red;
        }

        /**
         * Returns the green value of the RGB format
         * 
         * @return The green value
         */
        public int getGreen() {
            return green;
        }

        /**
         * Returns the blue value of the RGB format
         * 
         * @return The blue value
         */
        public int getBlue() {
            return blue;
        }

        /**
         * Returns the red value divided by 255
         * 
         * @return The offsetX value
         */
        @Override
        public float getValueX() {
            return red / 255F;
        }

        /**
         * Returns the green value divided by 255
         * 
         * @return The offsetY value
         */
        @Override
        public float getValueY() {
            return green / 255F;
        }

        /**
         * Returns the blue value divided by 255
         * 
         * @return The offsetZ value
         */
        @Override
        public float getValueZ() {
            return blue / 255F;
        }
    }

    /**
     * Represents the color for the {@link ParticleEffects#NOTE} effect
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.7
     */
    public static final class NoteColor extends ParticleColor {
        private final int note;

        /**
         * Construct a new note color
         * 
         * @param note Note id which determines color
         * @throws IllegalArgumentException If the note value is lower than 0 or higher than 255
         */
        public NoteColor(int note) throws IllegalArgumentException {
            if (note < 0) {
                throw new IllegalArgumentException("The note value is lower than 0");
            }
            if (note > 24) {
                throw new IllegalArgumentException("The note value is higher than 24");
            }
            this.note = note;
        }

        /**
         * Returns the note value divided by 24
         * 
         * @return The offsetX value
         */
        @Override
        public float getValueX() {
            return note / 24F;
        }

        /**
         * Returns zero because the offsetY value is unused
         * 
         * @return zero
         */
        @Override
        public float getValueY() {
            return 0;
        }

        /**
         * Returns zero because the offsetZ value is unused
         * 
         * @return zero
         */
        @Override
        public float getValueZ() {
            return 0;
        }

    }

    /**
     * Represents a runtime exception that is thrown either if the displayed particle effect requires data and has none or vice-versa or if the data type is incorrect
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.6
     */
    private static final class ParticleDataException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        /**
         * Construct a new particle data exception
         * 
         * @param message Message that will be logged
         */
        public ParticleDataException(String message) {
            super(message);
        }
    }

    /**
     * Represents a runtime exception that is thrown either if the displayed particle effect is not colorable or if the particle color type is incorrect
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.7
     */
    private static final class ParticleColorException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        /**
         * Construct a new particle color exception
         * 
         * @param message Message that will be logged
         */
        public ParticleColorException(String message) {
            super(message);
        }
    }

    /**
     * Represents a runtime exception that is thrown if the displayed particle effect requires a newer version
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.6
     */
    private static final class ParticleVersionException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        /**
         * Construct a new particle version exception
         * 
         * @param message Message that will be logged
         */
        public ParticleVersionException(String message) {
            super(message);
        }
    }

    /**
     * Represents a particle effect packet with all attributes which is used for sending packets to the players
     * <p>
     * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
     * 
     * @author DarkBlade12
     * @since 1.5
     */
    public static final class ParticlePacket {
        private static int version;
        private static Class<?> enumParticle;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffects effect;
        private final float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleData data;
        private Object packet;

        /**
         * Construct a new particle packet
         * 
         * @param effect Particle effect
         * @param offsetX Maximum distance particles can fly away from the center on the x-axis
         * @param offsetY Maximum distance particles can fly away from the center on the y-axis
         * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
         * @param speed Display speed of the particles
         * @param amount Amount of particles
         * @param longDistance Indicates whether the maximum distance is increased from 256 to 65536
         * @param data Data of the effect
         * @throws IllegalArgumentException If the speed or amount is lower than 0
         * @see #initialize()
         */
        public ParticlePacket(ParticleEffects effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            initialize();
            if (speed < 0) {
                throw new IllegalArgumentException("The speed is lower than 0");
            }
            if (amount < 0) {
                throw new IllegalArgumentException("The amount is lower than 0");
            }
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
        }

        /**
         * Construct a new particle packet of a single particle flying into a determined direction
         * 
         * @param effect Particle effect
         * @param direction Direction of the particle
         * @param speed Display speed of the particle
         * @param longDistance Indicates whether the maximum distance is increased from 256 to 65536
         * @param data Data of the effect
         * @throws IllegalArgumentException If the speed is lower than 0
         * @see #ParticleEffect(ParticleEffect, float, float, float, float, int, boolean, ParticleData)
         */
        public ParticlePacket(ParticleEffects effect, Vector direction, float speed, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            this(effect, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, 0, longDistance, data);
        }

        /**
         * Construct a new particle packet of a single colored particle
         * 
         * @param effect Particle effect
         * @param color Color of the particle
         * @param longDistance Indicates whether the maximum distance is increased from 256 to 65536
         * @see #ParticleEffect(ParticleEffect, float, float, float, float, int, boolean, ParticleData)
         */
        public ParticlePacket(ParticleEffects effect, ParticleColor color, boolean longDistance) {
            this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1, 0, longDistance, null);
        }

        /**
         * Initializes {@link #packetConstructor}, {@link #getHandle}, {@link #playerConnection} and {@link #sendPacket} and sets {@link #initialized} to <code>true</code> if it succeeds
         * <p>
         * <b>Note:</b> These fields only have to be initialized once, so it will return if {@link #initialized} is already set to <code>true</code>
         * 
         * @throws VersionIncompatibleException if your bukkit version is not supported by this library
         */
        public static void initialize() throws VersionIncompatibleException {
            if (initialized) {
                return;
            }
            try {
                version = 10; //Integer.parseInt(Character.toString(PackageType.getServerVersion().charAt(3)));
                if (version > 7) {
                    enumParticle = PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                }
                Class<?> packetClass = PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
                packetConstructor = ReflectionUtils.getConstructor(packetClass);
                getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
                playerConnection = ReflectionUtils.getField("EntityPlayer", PackageType.MINECRAFT_SERVER, false, "playerConnection");
                sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", PackageType.MINECRAFT_SERVER.getClass("Packet"));
            } catch (Exception exception) {
                throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
            }
            initialized = true;
        }

        /**
         * Returns the version of your server (1.x)
         * 
         * @return The version number
         */
        public static int getVersion() {
            return version;
        }

        /**
         * Determine if {@link #packetConstructor}, {@link #getHandle}, {@link #playerConnection} and {@link #sendPacket} are initialized
         * 
         * @return Whether these fields are initialized or not
         * @see #initialize()
         */
        public static boolean isInitialized() {
            return initialized;
        }

        /**
         * Initializes {@link #packet} with all set values
         * 
         * @param center Center location of the effect
         * @throws PacketInstantiationException If instantion fails due to an unknown error
         */
        private void initializePacket(Location center) throws PacketInstantiationException {
            if (packet != null) {
                return;
            }
            try {
                packet = packetConstructor.newInstance();
                if (version < 8) {
                    String name = effect.getName();
                    if (data != null) {
                        name += data.getPacketDataString();
                    }
                    ReflectionUtils.setValue(packet, true, "a", name);
                } else {
                    ReflectionUtils.setValue(packet, true, "a", enumParticle.getEnumConstants()[effect.getId()]);
                    ReflectionUtils.setValue(packet, true, "j", longDistance);
                    if (data != null) {
                        ReflectionUtils.setValue(packet, true, "k", data.getPacketData());
                    }
                }
                ReflectionUtils.setValue(packet, true, "b", (float) center.getX());
                ReflectionUtils.setValue(packet, true, "c", (float) center.getY());
                ReflectionUtils.setValue(packet, true, "d", (float) center.getZ());
                ReflectionUtils.setValue(packet, true, "e", offsetX);
                ReflectionUtils.setValue(packet, true, "f", offsetY);
                ReflectionUtils.setValue(packet, true, "g", offsetZ);
                ReflectionUtils.setValue(packet, true, "h", speed);
                ReflectionUtils.setValue(packet, true, "i", amount);
            } catch (Exception exception) {
                throw new PacketInstantiationException("Packet instantiation failed", exception);
            }
        }

        /**
         * Sends the packet to a single player and caches it
         * 
         * @param center Center location of the effect
         * @param player Receiver of the packet
         * @throws PacketInstantiationException If instantion fails due to an unknown error
         * @throws PacketSendingException If sending fails due to an unknown error
         * @see #initializePacket(Location)
         */
        public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
            initializePacket(center);
            try {
                sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), packet);
            } catch (Exception exception) {
                throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", exception);
            }
        }

        /**
         * Sends the packet to all players in the list
         * 
         * @param center Center location of the effect
         * @param players Receivers of the packet
         * @throws IllegalArgumentException If the player list is empty
         * @see #sendTo(Location center, Player player)
         */
        public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
            if (players.isEmpty()) {
                throw new IllegalArgumentException("The player list is empty");
            }
            for (Player player : players) {
                sendTo(center, player);
            }
        }

        /**
         * Sends the packet to all players in a certain range
         * 
         * @param center Center location of the effect
         * @param range Range in which players will receive the packet (Maximum range for particles is usually 16, but it can differ for some types)
         * @throws IllegalArgumentException If the range is lower than 1
         * @see #sendTo(Location center, Player player)
         */
        public void sendTo(Location center, double range) throws IllegalArgumentException {
            if (range < 1) {
                throw new IllegalArgumentException("The range is lower than 1");
            }
            String worldName = center.getWorld().getName();
            double squared = range * range;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared) {
                    continue;
                }
                sendTo(center, player);
            }
        }

        /**
         * Represents a runtime exception that is thrown if a bukkit version is not compatible with this library
         * <p>
         * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
         * 
         * @author DarkBlade12
         * @since 1.5
         */
        private static final class VersionIncompatibleException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            /**
             * Construct a new version incompatible exception
             * 
             * @param message Message that will be logged
             * @param cause Cause of the exception
             */
            public VersionIncompatibleException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        /**
         * Represents a runtime exception that is thrown if packet instantiation fails
         * <p>
         * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
         * 
         * @author DarkBlade12
         * @since 1.4
         */
        private static final class PacketInstantiationException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            /**
             * Construct a new packet instantiation exception
             * 
             * @param message Message that will be logged
             * @param cause Cause of the exception
             */
            public PacketInstantiationException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        /**
         * Represents a runtime exception that is thrown if packet sending fails
         * <p>
         * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
         * 
         * @author DarkBlade12
         * @since 1.4
         */
        private static final class PacketSendingException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            /**
             * Construct a new packet sending exception
             * 
             * @param message Message that will be logged
             * @param cause Cause of the exception
             */
            public PacketSendingException(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }
}