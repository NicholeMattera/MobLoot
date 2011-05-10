package net.mcbat.MobLoot;

import java.util.logging.Logger;

import net.mcbat.MobLoot.Commands.ml;
import net.mcbat.MobLoot.Commands.mll;
import net.mcbat.MobLoot.Commands.mls;
import net.mcbat.MobLoot.Config.ConfigManager;
import net.mcbat.MobLoot.Listeners.MobLootEntityListener;
import net.mcbat.MobLoot.Listeners.MobLootServerListener;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class MobLoot extends JavaPlugin {
	private final Logger _logger;
	private ConfigManager _configManager;

	public PermissionHandler Permissions = null;
	
	public MobLoot() {
		_logger = Logger.getLogger("Minecraft");
	}
	
	@Override
	public void onEnable() {
		_logger.info("[MobLoot] v"+this.getDescription().getVersion()+" (Helium) loaded.");
		_logger.info("[MobLoot] Developed by: [Mattera, Steven (IchigoKyger)].");
		
		_configManager = new ConfigManager(this);
		_configManager.loadConfig();
		
		getCommand("ml").setExecutor(new ml(this));
		getCommand("mll").setExecutor(new mll(this));
		getCommand("mls").setExecutor(new mls(this));
		
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
		_logger.info("[MobBounty] Plugin disabled.");
	}
	
	public ConfigManager getConfigManager() {
		return _configManager;
	}
	
	public Logger getMinecraftLogger() {
		return _logger;
	}
}
