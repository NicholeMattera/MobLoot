package com.stevenmattera.MobLoot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.stevenmattera.MobLoot.Misc.CreatureID;
import com.stevenmattera.MobLoot.Misc.ItemInfo;

public class ListenerManager implements Listener {
    private final MobLoot _plugin;
    
    public ListenerManager(MobLoot plugin) {
    	_plugin = plugin;
    	
    	Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
				
		LivingEntity mob = (LivingEntity) event.getEntity();
		Player player = null;
		
		EntityDamageEvent damageEvent = mob.getLastDamageCause();

		if (damageEvent instanceof EntityDamageByEntityEvent) {
			Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
			
			if (damager instanceof Player || damager instanceof Arrow) {
				if (damager instanceof Arrow) {
					if (((Arrow) damager).getShooter() instanceof Player)
						player = (Player) ((Arrow) damager).getShooter();
				}
				else
					player = (Player) damager;
			}
		}
		
		CreatureID creature = CreatureID.valueOf(mob, player);
		
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
