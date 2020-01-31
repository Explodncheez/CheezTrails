package utilities.particles;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
	EXPLODE(Particle.EXPLOSION_NORMAL, ParticleProperty.DIRECTIONAL),
	EXPLOSION_LARGE(Particle.EXPLOSION_LARGE),
	HUGE_EXPLODE(Particle.EXPLOSION_HUGE),
	FIREWORKS_SPARK(Particle.FIREWORKS_SPARK, ParticleProperty.DIRECTIONAL),
	BUBBLE(Particle.WATER_BUBBLE, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),
	SPLASH(Particle.WATER_SPLASH, ParticleProperty.DIRECTIONAL),
	WATER_WAKE(Particle.WATER_WAKE, ParticleProperty.DIRECTIONAL),
	SUSPENDED(Particle.SUSPENDED, ParticleProperty.REQUIRES_WATER),
	SUSPENDED_DEPTH(Particle.SUSPENDED_DEPTH, ParticleProperty.DIRECTIONAL),
	CRIT(Particle.CRIT, ParticleProperty.DIRECTIONAL),
	MAGIC_CRIT(Particle.CRIT_MAGIC, ParticleProperty.DIRECTIONAL),
	SMOKE(Particle.SMOKE_NORMAL, ParticleProperty.DIRECTIONAL),
	SMOKE_LARGE(Particle.SMOKE_LARGE, ParticleProperty.DIRECTIONAL),
	SPELL(Particle.SPELL),
	INSTANT_SPELL(Particle.SPELL_INSTANT),
	MOB_SPELL(Particle.SPELL_MOB, ParticleProperty.COLORABLE),
	MOB_SPELL_AMBIENT(Particle.SPELL_MOB_AMBIENT, ParticleProperty.COLORABLE),
	WITCH_MAGIC(Particle.SPELL_WITCH),
	DRIP_WATER(Particle.DRIP_WATER),
	DRIP_LAVA(Particle.DRIP_LAVA),
	VILLAGER_ANGRY(Particle.VILLAGER_ANGRY),
	VILLAGER_HAPPY(Particle.VILLAGER_HAPPY, ParticleProperty.DIRECTIONAL),
	TOWN_AURA(Particle.TOWN_AURA, ParticleProperty.DIRECTIONAL),
	NOTE(Particle.NOTE, ParticleProperty.COLORABLE),
	PORTAL(Particle.PORTAL, ParticleProperty.DIRECTIONAL),
	ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE, ParticleProperty.DIRECTIONAL),
	FLAME(Particle.FLAME, ParticleProperty.DIRECTIONAL),
	LAVA(Particle.LAVA),
	CLOUD(Particle.CLOUD, ParticleProperty.DIRECTIONAL),
	RED_DUST(Particle.REDSTONE, ParticleProperty.COLORABLE),
	SNOWBALL(Particle.SNOWBALL),
	SNOW_SHOVEL(Particle.FALLING_DUST, new BlockData(Material.SNOW_BLOCK, (byte) 0), ParticleProperty.REQUIRES_DATA, ParticleProperty.DIRECTIONAL),
	SLIME(Particle.SLIME),
	HEART(Particle.HEART),
	BARRIER(Particle.BARRIER),
	ITEM_CRACK(Particle.ITEM_CRACK, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
	BLOCK_CRACK(Particle.BLOCK_CRACK, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
	BLOCK_DUST(Particle.BLOCK_DUST, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
	WATER_DROP(Particle.WATER_DROP),
	//ITEM_TAKE(Particle.ITEM_TAKE),
	MOB_APPEARANCE(Particle.MOB_APPEARANCE),
    DRAGON_BREATH(Particle.DRAGON_BREATH),
    END_ROD(Particle.END_ROD, ParticleProperty.DIRECTIONAL),
    DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR, ParticleProperty.DIRECTIONAL),
    SWEEP_ATTACK(Particle.SWEEP_ATTACK),
    FALLING_DUST(Particle.FALLING_DUST, ParticleProperty.REQUIRES_DATA);

	private final Particle bukkitParticle;
	private final List<ParticleProperty> properties;
	private final ParticleData defaultdata;
	
	private ParticleEffects(Particle bukkitParticle, ParticleData data, ParticleProperty... properties) {
		this.bukkitParticle = bukkitParticle;
		this.properties = Arrays.asList(properties);
		this.defaultdata = data;
	}

	/**
	 * Construct a new particle effect
	 * 
	 * @param name Name of this particle effect
	 * @param id Id of this particle effect
	 * @param requiredVersion Version which is required (1.x)
	 * @param properties Properties of this particle effect
	 */
	private ParticleEffects(Particle bukkitParticle_, ParticleProperty... properties) {
		this.bukkitParticle = bukkitParticle_;
		this.properties = Arrays.asList(properties);
		this.defaultdata = null;
	}
	
	public boolean hasDefaults() {
		return this.defaultdata != null;
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
		return ((effect == MOB_SPELL || effect == MOB_SPELL_AMBIENT || effect == RED_DUST) && color instanceof OrdinaryColor) || (effect == NOTE && color instanceof NoteColor);
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
	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleDataException, IllegalArgumentException {
		/*if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect requires additional data");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}*/
		
		if (this.hasDefaults()) {
			this.display(this.defaultdata, offsetX, offsetY, offsetZ, speed, amount, center, range);
			return;
		}
		
		// temp. hack
		if(bukkitParticle == Particle.REDSTONE) {
			center.getWorld().spawnParticle(bukkitParticle, center, amount, offsetX, offsetY, offsetZ, speed, new Particle.DustOptions(Color.RED, 1), true);
			return;
		}
		// TODO range
		center.getWorld().spawnParticle(bukkitParticle, center, amount, offsetX, offsetY, offsetZ, speed, null, true);
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
	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleDataException, IllegalArgumentException {
		if (this.hasDefaults()) {
			this.display(this.defaultdata, offsetX, offsetY, offsetZ, speed, amount, center, players);
			return;
		}
		
		for(Player p : players) {
			// temp. hack
			if(bukkitParticle == Particle.REDSTONE) {
				center.getWorld().spawnParticle(bukkitParticle, center, amount, offsetX, offsetY, offsetZ, speed, new Particle.DustOptions(Color.RED, 1), true);
			} else {
				p.spawnParticle(bukkitParticle, center, amount, offsetX, offsetY, offsetZ, speed);
			}
		}
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
	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleDataException, IllegalArgumentException {
		if (this.hasDefaults()) {
			this.display(this.defaultdata, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
			return;
		}
		
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
	public void display(Vector direction, float speed, Location center, double range) throws  ParticleDataException, IllegalArgumentException {
		if (this.hasDefaults()) {
			this.display(this.defaultdata, direction, speed, center, range);
			return;
		}
		
		// temp hack
		if(bukkitParticle == Particle.REDSTONE) {
			center.getWorld().spawnParticle(bukkitParticle, center, 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, new Particle.DustOptions(Color.RED, 1), true);
			return;
		}
		
		// TODO range
		// amount = 0 -> use offset as direction
		center.getWorld().spawnParticle(bukkitParticle, center, 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, null, true);
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
	public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleDataException, IllegalArgumentException {
		if (this.hasDefaults()) {
			this.display(this.defaultdata, direction, speed, center, players);
			return;
		}
		
		for(Player p : players) {
			// temp hack
			if(bukkitParticle == Particle.REDSTONE) {
				p.spawnParticle(bukkitParticle, center, 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, new Particle.DustOptions(Color.RED, 1));
			} else {
				p.spawnParticle(bukkitParticle, center, 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed);
			}
		}
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
	public void display(Vector direction, float speed, Location center, Player... players) throws ParticleDataException, IllegalArgumentException {
		if (this.hasDefaults()) {
			this.display(this.defaultdata, direction, speed, center, Arrays.asList(players));
			return;
		}
		
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
	public void display(ParticleColor color, Location center, double range) throws ParticleColorException {
		if (!hasProperty(ParticleProperty.COLORABLE)) {
			throw new ParticleColorException("This particle effect is not colorable");
		}
		if (!isColorCorrect(this, color)) {
			throw new ParticleColorException("The particle color type is incorrect");
		}
		
		if(bukkitParticle == Particle.REDSTONE) {
			center.getWorld().spawnParticle(bukkitParticle, center, 1, 0D, 0D, 0D, 0D,
					new Particle.DustOptions(Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()), 1), true);
			return;
		}
		
		if (bukkitParticle == Particle.SPELL_MOB) {
			double red = color.getRed() / 255D;
			double green = color.getGreen() / 255D;
			double blue = color.getBlue() / 255D;
			center.getWorld().spawnParticle(Particle.SPELL_MOB, center, 0, red, green, blue, 1, null, true);
			return;
		}
		
		center.getWorld().spawnParticle(bukkitParticle, center, 0, color.getValueX(), color.getValueY(), color.getValueZ(), 0D, null, true);
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
	public void display(ParticleColor color, Location center, List<Player> players) throws ParticleColorException {
		for(Player p : players) {
			if(bukkitParticle == Particle.REDSTONE) {
				p.spawnParticle(bukkitParticle, center, 1, 0D, 0D, 0D, 
						new Particle.DustOptions(Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()), 1));
			} else {
				p.spawnParticle(bukkitParticle, center, 0, color.getValueX(), color.getValueY(), color.getValueZ());
			}
		}
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
	public void display(ParticleColor color, Location center, Player... players) throws ParticleColorException {
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
	public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleDataException {
		// TODO range
		center.getWorld().spawnParticle(bukkitParticle, center, amount, offsetX, offsetY, offsetZ, speed, data.getBukkitData(), true);
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
	public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleDataException {
		for(Player p : players) {
			p.spawnParticle(bukkitParticle, center, amount, offsetX, offsetY, offsetZ, speed, data.getBukkitData());
		}
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
	public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleDataException {
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
	public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleDataException {
		if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect does not require additional data");
		}
//		if (!isDataCorrect(this, data)) {
//			throw new ParticleDataException("The particle data type is incorrect");
//		}

		center.getWorld().spawnParticle(bukkitParticle, center, 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, data.getBukkitData(), true);
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
	public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleDataException {
		for(Player p : players) {
			p.spawnParticle(bukkitParticle, center, 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, data.getBukkitData());
		}
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
	public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleDataException {
		display(data, direction, speed, center, Arrays.asList(players));
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
		private final Object bukkitData;

		/**
		 * Construct a new particle data
		 * 
		 * @param material Material of the item/block
		 * @param data Data value of the item/block
		 */
		public ParticleData(Object bukkitData_) {
			this.bukkitData = bukkitData_;
		}

		/**
		 * Returns the data value of this data
		 * 
		 * @return The data value
		 */
		public Object getBukkitData() {
			return bukkitData;
		}
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
			super(new ItemStack(material));
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
			super(material.createBlockData());
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
		
		public abstract int getRed();
		public abstract int getGreen();
		public abstract int getBlue();
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
		@Override
		public int getRed() {
			return red;
		}

		/**
		 * Returns the green value of the RGB format
		 * 
		 * @return The green value
		 */
		@Override
		public int getGreen() {
			return green;
		}

		/**
		 * Returns the blue value of the RGB format
		 * 
		 * @return The blue value
		 */
		@Override
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

		@Override
		public int getRed() {
			return 0;
		}
		
		@Override
		public int getGreen() {
			return 0;
		}

		@Override
		public int getBlue() {
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
}