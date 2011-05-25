package net.mcbat.MobLoot.Commands;

import java.util.Iterator;
import java.util.List;

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
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)sender, "MobLoot.Admin.ml")) || (_plugin.Permissions == null && sender.isOp())) {
			this.mlCommand(sender, label, args);
		}
		else {
			sender.sendMessage(ChatColor.RED+"You do no have access to that command.");
		}
		return true;
	}
	
	private void mlCommand(CommandSender sender, String label, String[] args) {
		if (args.length == 3) {
			World world = _plugin.getServer().getWorld(args[0]);

			if (world != null) {
				CreatureID mob = CreatureID.fromName(args[1]);

				if (mob != null) {
					String[] items = args[2].split(",");

					_plugin.getConfigManager().setDrop(world.getName(), mob, items);
					sender.sendMessage(ChatColor.WHITE+args[1]+ChatColor.DARK_GREEN+" now drops "+ChatColor.WHITE+args[2]+ChatColor.DARK_GREEN+" in world "+ChatColor.WHITE+args[0]+ChatColor.DARK_GREEN+".");
				}
				else
					this.commandUsage(sender, label);
			}
			else 
				this.commandUsage(sender, label);
		}
		else
			this.commandUsage(sender, label);
	}

	private void commandUsage(CommandSender sender, String label) {
		List<World> worlds = _plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();

		String worldsStr = ChatColor.GRAY+"Worlds: ";

		while (worldIterator.hasNext()) {
			World world = worldIterator.next();

			worldsStr += world.getName();
			worldsStr += " ";
		}

		sender.sendMessage(ChatColor.RED+"Usage: /"+label+" [world] [mob] <item,item,...>");
		sender.sendMessage(worldsStr);
		sender.sendMessage(ChatColor.GRAY+"Mobs: Zombie PigZombie Skeleton Slime Chicken Pig Spider Creeper ElectrifiedCreeper Ghast Squid Giant Cow Sheep Wolf TamedWolf");
	}
}
