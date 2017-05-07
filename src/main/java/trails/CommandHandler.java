package trails;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import trails.listener.TrailHandler;
import trails.listener.TrailListener;
import trails.menu.MenuEditor;
import trails.menu.MenuHandler;

public class CommandHandler implements CommandExecutor {

	@Override
    public boolean onCommand(CommandSender sender, Command lbl, String cmd, String[] args) {
		if (cmd.equalsIgnoreCase("trail") || cmd.equalsIgnoreCase("trails")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				if (args.length > 0) {
					
					if (p.isOp()) {
						if (args[0].equalsIgnoreCase("set")) {
							try {
								Player o = Bukkit.getPlayer(args[1]);
								TrailHandler.setActiveTrail(o, ParticleTrail.get(args[2]), true);
								p.sendMessage(TrailHandler.PREFIX + "Forcefully set §e" + o.getName() + "'s §fparticle effect to §e" + args[2] + "§f.");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.4F, 1.2F);
							} catch (Exception e) {
								p.sendMessage(TrailHandler.PREFIX + "§cInvalid syntax or Player does not exist. §f/trail set <player> <trail>");
							}
							return true;
						}
						if (args[0].equalsIgnoreCase("edit")) {
							try {
								MenuEditor me = MenuEditor.get(p);
								if (me == null)
									new MenuEditor(p);
								else
									MenuEditor.remove(p).restore(p);
							} catch (Exception e) {
								p.sendMessage(TrailHandler.PREFIX + "§cSomething went wrong with §e/trail edit§c! Please report this error!");
								e.printStackTrace();
							}
							return true;
						}
						if (args[0].equalsIgnoreCase("save")) {
							try {
								ParticleTrail.save(CheezTrails.config());
								p.sendMessage(TrailHandler.PREFIX + "Saved menu icon positions to configuration file.");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.4F, 1.2F);
							} catch (Exception e) {
								p.sendMessage(TrailHandler.PREFIX + "§cSomething went wrong with §e/trail save§c! Please report this error!");
								e.printStackTrace();
							}
							return true;
						}
						if (args[0].equalsIgnoreCase("reload")) {
							try {
								CheezTrails.reload();
								ParticleTrail.load(CheezTrails.getInstance().getConfig());
								p.sendMessage(TrailHandler.PREFIX + "Reloaded menu icon positions from configuration file.");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.4F, 1.2F);
							} catch (Exception e) {
								p.sendMessage(TrailHandler.PREFIX + "§cSomething went wrong with §e/trail reload§c! Please report this error!");
								e.printStackTrace();
							}
							return true;
						}
						if (args[0].equalsIgnoreCase("restart")) {
							try {
								CheezTrails.reload();
								
								int i = CheezTrails.config().getInt("stand-trail-tick-speed");
								TrailListener.a(i);
								p.sendMessage(TrailHandler.PREFIX + "Restarted particle maker with tick delay of: §e" + i + "§f.");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.4F, 1.2F);
							} catch (Exception e) {
								p.sendMessage(TrailHandler.PREFIX + "§cSomething went wrong with §e/trail restart§c! Please report this error!");
								e.printStackTrace();
							}
							return true;
						}
					}
					
					if (args[0].equalsIgnoreCase("help")) {
						for (String s : help)
							sender.sendMessage(s);
						
						if (sender.isOp()) {
							for (String s : morehelp)
								sender.sendMessage(s);
						}
						return true;
					}
					
					if (args[0].equalsIgnoreCase("list")) {
						sender.sendMessage("§6§lList of CheezTrails:");
						if (sender.isOp()) {
							for (ParticleTrail trail : ParticleTrail.getAllTrails()) {
								if (!trail.getName().equals("deek"))
									sender.sendMessage("§7• " + trail.getName());
							}
						} else {
							for (ParticleTrail trail : ParticleTrail.getTrails().values()) {
								sender.sendMessage("§7• " + trail.getName());
							}
						}
						return true;
					}
						
					if (args[0].equalsIgnoreCase("off")) {
						if (args.length > 1 && sender.isOp()) {

							try {
								Player o = Bukkit.getPlayer(args[1]);
								TrailHandler.removeTrail(o);
								p.sendMessage(TrailHandler.PREFIX + "Forcefully removed §e" + o.getName() + "'s §fparticle effect.");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 0.4F, 1.2F);
							} catch (Exception e) {
								p.sendMessage(TrailHandler.PREFIX + "Did you mean: §f/trail off <player>");
							}
							return true;
							
						}
						
						try {
							TrailHandler.removeTrail(p);
						} catch (Exception e) {
							p.sendMessage(TrailHandler.PREFIX + "Did you mean: §f/trail off§7?");
						}
						return true;
						
					}
					
					try {
						TrailHandler.setActiveTrail(p, ParticleTrail.get(args[0]), false);
						return true;
					} catch (Exception e) {
					}
				}
				MenuHandler.openMenu(p);
			}
			return true;
		}
		return false;
	}
	
	private static final String[] help = {
		"§e§l==== << §6CheezTrails V1.0 §e§l>> ====",
		"§cCommands:",
		"§f• /trail §7Show effect menu",
		"§f• /trail off §7Deactivate current effect",
		"§f• /trail list §7List all effects by name",
		"§f• /trail [name] §7Activate specified effect",
	}, morehelp = {
		"§f• /trail set [name] [trail] §7Force an effect on a player even if they normally don not have the permission",
		"§f• /trail off [name] §7Force-deactivate a player's effect",
		"§f• /trail edit §7Open a GUI to edit the §o/trail§7 menu",
		"§f• /trail save §7Save changes from GUI-editor menu",
		"§f• /trail reload §7Reload values from config",
	};

}
