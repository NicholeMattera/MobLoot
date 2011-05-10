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

public class ConfigManager {
	private final MobLoot _plugin;
	
	private final File _configFile;
	private Configuration _config;

	private final HashMap<String, HashMap<CreatureID, ArrayList<Integer>>> _worldDropTable;
	
	public ConfigManager(MobLoot plugin) {
		_plugin = plugin;
		_configFile = new File(_plugin.getDataFolder() + File.separator + "General.yml");
		_worldDropTable = new HashMap<String, HashMap<CreatureID, ArrayList<Integer>>>();
	}
	
	public void loadConfig() {
		if (_configFile.exists()) {	
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();

			_config = new Configuration(_configFile);
			_config.load();
			
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();

				HashMap<CreatureID, ArrayList<Integer>> dropTable = new HashMap<CreatureID, ArrayList<Integer>>();
				
				for (CreatureID creature : CreatureID.values()) {
					ArrayList<Integer> creatureDrop = (ArrayList<Integer>) _config.getIntList(world.getName()+"."+creature.getName(), new ArrayList<Integer>());
					dropTable.put(creature, creatureDrop);
				}
				
				_worldDropTable.put(world.getName(), dropTable);
			}
		}
		else
			this.createConfig();
	}
	
	public void saveConfig() {
		_config.save();
	}
	
	public ArrayList<Integer> getDrop(String worldName, CreatureID creature) {
		HashMap<CreatureID, ArrayList<Integer>> dropTable = _worldDropTable.get(worldName);
		ArrayList<Integer> drop = dropTable.get(creature);
		
		if (drop.size() == 0)
			return null;
		else
			return dropTable.get(creature);
	}
	
	public void setDrop(String worldName, CreatureID creature, int[] items) {
		HashMap<CreatureID, ArrayList<Integer>> dropTable = new HashMap<CreatureID, ArrayList<Integer>>();
		ArrayList<Integer> itemDrops = new ArrayList<Integer>();

		for (int i = 0; i < items.length; i++) {
			itemDrops.add(new Integer(items[i]));
		}
		
		dropTable.put(creature, itemDrops);
		
		_config.setProperty(worldName+"."+creature.getName(), itemDrops);
		_worldDropTable.put(worldName, dropTable);
	}
	
	private void createConfig() {
		List<World> worlds = _plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();

		_config = new Configuration(_configFile);

		while (worldIterator.hasNext()) {
			World world = worldIterator.next();

			HashMap<CreatureID, ArrayList<Integer>> dropTable = new HashMap<CreatureID, ArrayList<Integer>>();
			
			for (CreatureID creature : CreatureID.values()) {
				ArrayList<Integer> creatureDrop = new ArrayList<Integer>();
				dropTable.put(creature, creatureDrop);

				_config.setProperty(world.getName()+"."+creature.getName(), creatureDrop);
			}
			
			_worldDropTable.put(world.getName(), dropTable);
		}

		
		this.saveConfig();
	}
}
