package net.mcbat.MobLoot.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.util.config.Configuration;

import net.mcbat.MobLoot.MobLoot;
import net.mcbat.MobLoot.Utils.CreatureID;
import net.mcbat.MobLoot.Utils.ItemInfo;

public class ConfigManager {
	private final MobLoot _plugin;
	
	private final File _configFile;
	private Configuration _config;

	private final HashMap<String, HashMap<CreatureID, ArrayList<ItemInfo>>> _worldDropTable;
	
	public ConfigManager(MobLoot plugin) {
		_plugin = plugin;
		_configFile = new File(_plugin.getDataFolder() + File.separator + "General.yml");
		_worldDropTable = new HashMap<String, HashMap<CreatureID, ArrayList<ItemInfo>>>();
		
		loadConfig();
	}
	
	public void loadConfig() {
		if (_configFile.exists()) {	
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();

			_config = new Configuration(_configFile);
			_config.load();
			
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();
				loadWorld(world.getName());
			}
		}
		else
			this.createConfig();
	}
	
	public void saveConfig() {
		_config.save();

		_plugin.getMinecraftLogger().info("[MobLoot] Config saved.");
	}
	
	public ArrayList<ItemInfo> getDrop(String worldName, CreatureID creature) {
		HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = _worldDropTable.get(worldName);
		
		if (dropTable == null)
			dropTable = loadWorld(worldName);
		
		ArrayList<ItemInfo> drop = dropTable.get(creature);
		
		if (drop == null || drop.size() == 0)
			return null;
		else
			return drop;
	}
	
	public void setDrop(String worldName, CreatureID creature, String[] items) {
		HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = new HashMap<CreatureID, ArrayList<ItemInfo>>();
		ArrayList<String> itemDropsData = new ArrayList<String>();
		ArrayList<ItemInfo> itemDrops = new ArrayList<ItemInfo>();

		for (int i = 0; i < items.length; i++) {
			itemDrops.add(new ItemInfo(items[i]));
			itemDropsData.add(items[i]);
		}
		
		dropTable.put(creature, itemDrops);
		
		_config.setProperty(worldName+"."+creature.getName(), itemDropsData);
		_worldDropTable.put(worldName, dropTable);
	}
	
	private void createConfig() {
		List<World> worlds = _plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();

		_config = new Configuration(_configFile);

		while (worldIterator.hasNext()) {
			World world = worldIterator.next();

			HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = new HashMap<CreatureID, ArrayList<ItemInfo>>();
			
			for (CreatureID creature : CreatureID.values()) {
				ArrayList<ItemInfo> creatureDrop = new ArrayList<ItemInfo>();
				dropTable.put(creature, creatureDrop);

				_config.setProperty(world.getName()+"."+creature.getName(), creatureDrop);
			}
			
			_worldDropTable.put(world.getName(), dropTable);
		}

		
		this.saveConfig();
	}
	
	private HashMap<CreatureID, ArrayList<ItemInfo>> loadWorld(String worldName) {
		HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = new HashMap<CreatureID, ArrayList<ItemInfo>>();
		
		for (CreatureID creature : CreatureID.values()) {
			ArrayList<String> creatureDropData = (ArrayList<String>) _config.getStringList(worldName+"."+creature.getName(), new ArrayList<String>());
			ArrayList<ItemInfo> creatureDrop = new ArrayList<ItemInfo>();
			Iterator<String> creatureDropDataIterator = creatureDropData.iterator();
			
			while (creatureDropDataIterator.hasNext()) {
				creatureDrop.add(new ItemInfo(creatureDropDataIterator.next()));
			}
			
			dropTable.put(creature, creatureDrop);
		}
		
		_worldDropTable.put(worldName, dropTable);

		return dropTable;
	}
}
