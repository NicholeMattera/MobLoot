package net.mcbat.MobLoot.Commands;

import net.mcbat.MobLoot.Utils.CreatureID;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobLoot {
	private final net.mcbat.MobLoot.MobLoot _plugin;

	public MobLoot(net.mcbat.MobLoot.MobLoot plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("set")) {
				if (((Player) sender).hasPermission("MobLoot.Commands.set")) {
					this.set(sender, label, args);
				}
				else {
					sender.sendMessage(ChatColor.RED+"You do no have access to that command.");
				}
			}
			else if (args[0].equalsIgnoreCase("load")) {
				if (((Player) sender).hasPermission("MobLoot.Commands.load")) {
					this.load(sender, label, args);
				}
				else {
					sender.sendMessage(ChatColor.RED+"You do no have access to that command.");
				}
			}
			else if (args[0].equalsIgnoreCase("save")) {
				if (((Player) sender).hasPermission("MobLoot.Commands.save")) {
					this.save(sender, label, args);
				}
				else {
					sender.sendMessage(ChatColor.RED+"You do no have access to that command.");
				}
			}
			else {
				this.commandUsage(sender, label, "");
			}
		}
		else {
			this.commandUsage(sender, label, "");
		}
		
		return true;
	}
	
	private void set(CommandSender sender, String label, String[] args) {
		if (args.length == 4) {
			World world = _plugin.getServer().getWorld(args[1]);

			if (world != null) {
				CreatureID mob = CreatureID.fromName(args[2]);

				if (mob != null) {
					String[] items = args[3].split(",");

					_plugin.getConfigManager().setDrop(world.getName(), mob, items);
					sender.sendMessage(ChatColor.GREEN+"Mob "+ChatColor.WHITE+args[2].toLowerCase()+ChatColor.GREEN+" now drops "+ChatColor.WHITE+args[3].toLowerCase()+ChatColor.GREEN+" in world "+ChatColor.WHITE+args[1]+ChatColor.GREEN+".");
				}
				else
					this.commandUsage(sender, label, "set");
			}
			else 
				this.commandUsage(sender, label, "set");
		}
		else
			this.commandUsage(sender, label, "set");
	}
	
	private void load(CommandSender sender, String label, String[] args) {
		if (args.length == 1) {
			_plugin.getConfigManager().loadConfig();
			sender.sendMessage(ChatColor.GREEN+"Configuration loaded.");
		}
		else 
			this.commandUsage(sender, label, "load");
	}

	private void save(CommandSender sender, String label, String[] args) {
		if (args.length == 1) {
			_plugin.getConfigManager().saveConfig();
			sender.sendMessage(ChatColor.GREEN+"Configuration saved.");
		}
		else this.commandUsage(sender, label, "save");
	}

	private void commandUsage(CommandSender sender, String label, String stage) {
		if (stage.equalsIgnoreCase("set")) {
			sender.sendMessage(ChatColor.RED+"[==== "+ChatColor.GREEN+"/"+label+" set"+ChatColor.RED+" ====]");
			sender.sendMessage(ChatColor.GREEN+"/"+label+" set "+ChatColor.LIGHT_PURPLE+"<world> <mob> <item,item,...>"+ChatColor.GREEN+" - "+ChatColor.WHITE+"Edit mob loot.");
		}
		else if (stage.equalsIgnoreCase("load")) {
			sender.sendMessage(ChatColor.RED+"[==== "+ChatColor.GREEN+"/"+label+" load"+ChatColor.RED+" ====]");
			sender.sendMessage(ChatColor.GREEN+"/"+label+" load - "+ChatColor.WHITE+"Reload the configuration from disk.");
		}
		else if (stage.equalsIgnoreCase("save")) {
			sender.sendMessage(ChatColor.RED+"[==== "+ChatColor.GREEN+"/"+label+" save"+ChatColor.RED+" ====]");
			sender.sendMessage(ChatColor.GREEN+"/"+label+" save - "+ChatColor.WHITE+"Save the configuration to disk.");
		}
		else {
			sender.sendMessage(ChatColor.RED+"[==== "+ChatColor.GREEN+"/"+label+ChatColor.RED+" ====]");
			sender.sendMessage(ChatColor.GREEN+"/"+label+" set "+ChatColor.LIGHT_PURPLE+"<world> <mob> <item,item,...>"+ChatColor.GREEN+" - "+ChatColor.WHITE+"Edit mob loot.");
			sender.sendMessage(ChatColor.GREEN+"/"+label+" load - "+ChatColor.WHITE+"Reload the configuration from disk.");
			sender.sendMessage(ChatColor.GREEN+"/"+label+" save - "+ChatColor.WHITE+"Save the configuration to disk.");			
		}
	}
}
