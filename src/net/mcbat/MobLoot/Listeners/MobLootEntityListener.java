package net.mcbat.MobLoot.Listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.mcbat.MobLoot.MobLoot;
import net.mcbat.MobLoot.Utils.CreatureID;
import net.mcbat.MobLoot.Utils.ItemInfo;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;

public class MobLootEntityListener extends EntityListener {
    private final MobLoot _plugin;
    
    public MobLootEntityListener(MobLoot plugin) {
    	_plugin = plugin;
    }

    public void registerEvents() {
        PluginManager pm = _plugin.getServer().getPluginManager();

        pm.registerEvent(Event.Type.ENTITY_DEATH, this, Priority.Normal, _plugin);
    }
    
	public void onEntityDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
				
		LivingEntity mob = (LivingEntity) event.getEntity();
		CreatureID creature = CreatureID.fromEntity(mob);

		if (creature != null) {
			ArrayList<ItemInfo> creatureDrop = _plugin.getConfigManager().getDrop(mob.getWorld().getName(), creature);
			
			if (creatureDrop != null) {
				List<ItemStack> drops = event.getDrops();
				
				if (drops != null) {
					drops.clear();
				
					Iterator<ItemInfo> creatureDropIterator = creatureDrop.iterator();
					while (creatureDropIterator.hasNext()) {
						ItemInfo dropInfo = creatureDropIterator.next();

						if (dropInfo != null && dropInfo.isValid()) {
							if (dropInfo.itemID == 0) {
								drops.clear();
								break;
							}
					
							if (dropInfo.chance == 100) {
								if (dropInfo.dataID == 0) {
									drops.add(new ItemStack(dropInfo.itemID, dropInfo.quantity));
								}
								else {
									MaterialData matData = new MaterialData(dropInfo.itemID, dropInfo.dataID);
									drops.add(matData.toItemStack(dropInfo.quantity));
								}
							}
							else {
								Random rand = new Random();
								
								if (rand.nextInt(100) <= dropInfo.chance) {
									if (dropInfo.dataID == 0) {
										drops.add(new ItemStack(dropInfo.itemID, dropInfo.quantity));
									}
									else {
										MaterialData matData = new MaterialData(dropInfo.itemID, dropInfo.dataID);
										drops.add(matData.toItemStack(dropInfo.quantity));
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
