package net.mcbat.MobLoot.Listeners;

import net.mcbat.MobLoot.MobLoot;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.PluginManager;

import com.nijikokun.bukkit.Permissions.Permissions;

public class MobLootServerListener extends ServerListener {
	private final MobLoot _plugin;
	
	public MobLootServerListener(MobLoot plugin) {
		_plugin = plugin;
	}
	
    public void registerEvents() {
        PluginManager pm = _plugin.getServer().getPluginManager();

        pm.registerEvent(Event.Type.PLUGIN_ENABLE, this, Priority.Monitor, _plugin);
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, this, Priority.Monitor, _plugin);
    }
	
	public void onPluginEnable(PluginEnableEvent event) {
		if (_plugin.Permissions == null && event.getPlugin().getDescription().getName().equals("Permissions")) {
			_plugin.Permissions = ((Permissions)event.getPlugin()).getHandler();
			_plugin.getMinecraftLogger().info("[MobLoot] hooked into Permissions/GroupManager.");
		}
	}
	
	public void onPluginDisable(PluginDisableEvent event) {
		if (_plugin.Permissions != null && event.getPlugin().getDescription().getName().equals("Permissions")) {
			_plugin.Permissions = null;
			_plugin.getMinecraftLogger().info("[MobLoot] un-hooked from Permissions/GroupManager");
		}
	}

}
