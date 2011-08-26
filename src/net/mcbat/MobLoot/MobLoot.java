package net.mcbat.MobLoot;

import java.util.logging.Logger;

import net.mcbat.MobLoot.Config.ConfigManager;
import net.mcbat.MobLoot.Listeners.MobLootEntityListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MobLoot extends JavaPlugin {
	private final Logger _logger = Logger.getLogger("Minecraft");
	
	private ConfigManager _configManager;
	private net.mcbat.MobLoot.Commands.MobLoot _mobLoot;
	
	@Override
	public void onEnable() {
		_logger.info("[MobLoot] v"+this.getDescription().getVersion()+" (Carbon) enabled.");
		_logger.info("[MobLoot] Developed by: [Mattera, Steven (IchigoKyger)].");
		
		_configManager = new ConfigManager(this);
		_mobLoot = new net.mcbat.MobLoot.Commands.MobLoot(this);
				
		(new MobLootEntityListener(this)).registerEvents();
	}

	@Override
	public void onDisable() {
		_configManager.saveConfig();
		_logger.info("[MobLoot] v"+this.getDescription().getVersion()+" (Carbon) disabled.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("Commands are designed for in-game only.");
			return true;
		}
		
		return _mobLoot.onCommand(sender, command, label, args);
	}
	
	public ConfigManager getConfigManager() {
		return _configManager;
	}
	
	public Logger getMinecraftLogger() {
		return _logger;
	}
}
