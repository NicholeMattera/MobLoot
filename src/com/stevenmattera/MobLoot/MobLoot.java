package com.stevenmattera.MobLoot;

import java.util.logging.Logger;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MobLoot extends JavaPlugin {
	private final Logger _logger = Logger.getLogger("Minecraft");

	private ConfigManager _configManager;
	
	@SuppressWarnings("unused")
	private ListenerManager _listenerManager;
	
	private com.stevenmattera.MobLoot.Commands.MobLoot _mobLoot;
	
	@Override
	public void onEnable() {
		_logger.info("[MobLoot] v4.0 (Oxygen) enabled.");
		_logger.info("[MobLoot] Developed by: [Mattera, Steven]");
		
		_configManager = new ConfigManager(this);
		_listenerManager = new ListenerManager(this);
		_mobLoot = new com.stevenmattera.MobLoot.Commands.MobLoot(this);
	}

	@Override
	public void onDisable() {
		_configManager.saveConfig();
		
		_configManager = null;
		_listenerManager = null;
		_mobLoot = null;
		
		_logger.info("[MobLoot] v4.0 (Oxygen) disabled.");
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
