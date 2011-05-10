package net.mcbat.MobLoot.Commands;

import java.util.Iterator;
import java.util.List;

import net.mcbat.MobLoot.MobLoot;
import net.mcbat.MobLoot.Utils.Colors;
import net.mcbat.MobLoot.Utils.CreatureID;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ml implements CommandExecutor {
	private final MobLoot _plugin;
	
	public ml(MobLoot plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)sender, "MobLoot.Admin.ml")) || (_plugin.Permissions == null && sender.isOp())) {
			this.mlCommand(sender, label, args);
		}
		else {
			sender.sendMessage(Colors.Red+"You do no have access to that command.");
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
					
					if (items.length > 0) {
						try {
							int[] itemsInt = this.convertStringArraytoIntArray(items);
							_plugin.getConfigManager().setDrop(world.getName(), mob, itemsInt);
							sender.sendMessage(Colors.White+args[1]+Colors.DarkGreen+" now drops "+Colors.White+args[2]+Colors.DarkGreen+" in world "+Colors.White+args[0]+Colors.DarkGreen+".");
						} 
						catch (Exception e) {
							this.commandUsage(sender, label);
						}
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
		else
			this.commandUsage(sender, label);
	}
	
	private void commandUsage(CommandSender sender, String label) {
		List<World> worlds = _plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();
		
		String worldsStr = Colors.Gray+"Worlds: ";
		
		while (worldIterator.hasNext()) {
			World world = worldIterator.next();
			
			worldsStr += world.getName();
			worldsStr += " ";
		}
		
		sender.sendMessage(Colors.Red+"Usage: /"+label+" [world] [mob] <item,item,...>");
		sender.sendMessage(worldsStr);
		sender.sendMessage(Colors.Gray+"Mobs: Zombie PigZombie Skeleton Slime Chicken Pig Spider Creeper ElectrifiedCreeper Ghast Squid Giant Cow Sheep Wolf TamedWolf");
	}
	
	public int[] convertStringArraytoIntArray(String[] sarray) throws Exception {
		if (sarray != null) {
			int intarray[] = new int[sarray.length];
			
			for (int i = 0; i < sarray.length; i++) {
				intarray[i] = Integer.parseInt(sarray[i]);
			}
			
			return intarray;
		}

		return null;
	}

}
