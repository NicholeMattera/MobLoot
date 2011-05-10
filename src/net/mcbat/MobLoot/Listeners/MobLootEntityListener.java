package net.mcbat.MobLoot.Listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.mcbat.MobLoot.MobLoot;
import net.mcbat.MobLoot.Utils.CreatureID;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;
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
			ArrayList<Integer> creatureDrop = _plugin.getConfigManager().getDrop(mob.getWorld().getName(), creature);
			
			if (creatureDrop != null) {
				List<ItemStack> drops = event.getDrops();
				
				if (drops != null) {
					drops.clear();
				
					Iterator<Integer> creatureDropIterator = creatureDrop.iterator();
					while (creatureDropIterator.hasNext()) {
						int itemNum = creatureDropIterator.next().intValue();
					
						if (itemNum == 0) {
							drops.clear();
							break;
						}
					
						ItemStack item = new ItemStack(itemNum);
						drops.add(item);
					}
				}
			}
		}
	}
}
