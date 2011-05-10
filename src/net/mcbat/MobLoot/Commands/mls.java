package net.mcbat.MobLoot.Commands;

import net.mcbat.MobLoot.MobLoot;
import net.mcbat.MobLoot.Utils.Colors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mls implements CommandExecutor {
	private final MobLoot _plugin;
	
	public mls(MobLoot plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)sender, "MobLoot.Admin.mls")) || (_plugin.Permissions == null && sender.isOp())) {
			_plugin.getConfigManager().saveConfig();
			sender.sendMessage(Colors.DarkGreen+"MobLoot config has been saved.");
		}
		else {
			sender.sendMessage(Colors.Red+"You do no have access to that command.");
		}
		
		return true;
	}
}
