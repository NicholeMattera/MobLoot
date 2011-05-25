package net.mcbat.MobLoot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MobLootCommands {
	private final net.mcbat.MobLoot.Commands.MobLoot _mobLoot;
	private final net.mcbat.MobLoot.Commands.MobLootLoad _mobLootLoad;
	private final net.mcbat.MobLoot.Commands.MobLootSave _mobLootSave;
	
	public MobLootCommands(MobLoot plugin) {
		_mobLoot = new net.mcbat.MobLoot.Commands.MobLoot(plugin);
		_mobLootLoad = new net.mcbat.MobLoot.Commands.MobLootLoad(plugin);
		_mobLootSave = new net.mcbat.MobLoot.Commands.MobLootSave(plugin);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("mobloot")) {
			return _mobLoot.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("moblootload")) {
			return _mobLootLoad.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("moblootsave")) {
			return _mobLootSave.onCommand(sender, command, label, args);
		}
		
		return false;
	}
	
}
