package net.mcbat.MobLoot;

import java.util.logging.Logger;

import net.mcbat.MobLoot.Config.ConfigManager;
import net.mcbat.MobLoot.Listeners.MobLootEntityListener;
import net.mcbat.MobLoot.Listeners.MobLootServerListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class MobLoot extends JavaPlugin {
	private final Logger _logger = Logger.getLogger("Minecraft");
	
	private ConfigManager _configManager;
	private MobLootCommands _commandManager;
	
	public PermissionHandler Permissions = null;
	
	@Override
	public void onEnable() {
		_logger.info("[MobLoot] v"+this.getDescription().getVersion()+" (Beryllium) enabled.");
		_logger.info("[MobLoot] Developed by: [Mattera, Steven (IchigoKyger)].");
		
		_configManager = new ConfigManager(this);
		_commandManager = new MobLootCommands(this);
		
		if (Permissions == null) {
			Plugin PermissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
			
			if (PermissionsPlugin != null) {
				if (PermissionsPlugin.isEnabled()) {
					Permissions = ((Permissions) PermissionsPlugin).getHandler();
					_logger.info("[MobLoot] hooked into Permissions/GroupManager.");
				}
			}
		}
		
		(new MobLootEntityListener(this)).registerEvents();		
		(new MobLootServerListener(this)).registerEvents();	
	}

	@Override
	public void onDisable() {
		_configManager.saveConfig();
		_logger.info("[MobLoot] v"+this.getDescription().getVersion()+" (Beryllium) disabled.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return _commandManager.onCommand(sender, command, label, args);
	}
	
	public ConfigManager getConfigManager() {
		return _configManager;
	}
	
	public Logger getMinecraftLogger() {
		return _logger;
	}
}
