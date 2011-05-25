package net.mcbat.MobLoot.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobLootSave {
	private final net.mcbat.MobLoot.MobLoot _plugin;

	public MobLootSave(net.mcbat.MobLoot.MobLoot plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)sender, "MobLoot.Admin.mll")) || (_plugin.Permissions == null && sender.isOp())) {
			_plugin.getConfigManager().loadConfig();
			sender.sendMessage(ChatColor.DARK_GREEN+"MobLoot config has been loaded.");
		}
		else {
			sender.sendMessage(ChatColor.RED+"You do no have access to that command.");
		}

		return true;
	}
}
