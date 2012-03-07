/*
 * MobBounty - Version 5.0 (Neon)
 * CreatureID.java - Creature Enumeration used for Identifying Creatures.
 * 
 * Programmed by: Steven Mattera
 * Special Thanks to: Bukkit Team for Identification Code from CraftEntity.java:36-102
 * 
 */

package com.stevenmattera.MobLoot.Misc;

import org.bukkit.entity.*;

public enum CreatureID {
	ANIMALS("Animals"),
	BLAZE("Blaze"),
	CAVE_SPIDER("CaveSpider"),
	CHICKEN("Chicken"),
	COMPLEX_MOB("ComplexMob"),
	COW("Cow"),
	CREATURE("Creature"),
	CREEPER("Creeper"),
	ELECTRIFIED_CREEPER("ElectrifiedCreeper"),
	ENDERDRAGON("Enderdragon"),
	ENDERMAN("Enderman"),
	FLYING("Flying"),
	GHAST("Ghast"),
	GIANT("Giant"),
	LIVING_MOB("LivingMob"),
	MAGMA_CUBE_TINY("MagmaCubeTiny"),
	MAGMA_CUBE_SMALL("MagmaCubeSmall"),
	MAGMA_CUBE_BIG("MagmaCubeBig"),
	MONSTER("Monster"),
	MUSHROOM_COW("MushroomCow"),
	PIG("Pig"),
	PIG_ZOMBIE("PigZombie"),
	SHEEP("Sheep"),
	SILVERFISH("Silverfish"),
	SKELETON("Skeleton"),
	SLIME_TINY("SlimeTiny"),
	SLIME_SMALL("SlimeSmall"),
	SLIME_BIG("SlimeBig"),
	SNOWMAN("Snowman"),
	SPIDER("Spider"),
	SQUID("Squid"),
	VILLAGER("Villager"),
	WATER_MOB("WaterMob"),
	WOLF("Wolf"),
	WOLF_TAMED("TamedWolf"),
	WOLF_TAMED_BY_SELF("SelfTamedWolf"),
	ZOMBIE("Zombie");
	
	private final String _name;

	private CreatureID(final String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public static CreatureID fromName(String name) {
		for (CreatureID id : CreatureID.values()) {
			if (id.name().equalsIgnoreCase(name)) { return id; }
		}
		
		return null;
	}
	
	public static CreatureID valueOf(Entity entity, Player player) {
		// Logic from CraftEntity.java:35-102
		// Order is important!
		if (entity instanceof Entity) {
			if (entity instanceof Creature) {
				// Animals
	            if (entity instanceof Animals) {
	            	if (entity instanceof Chicken) { return CreatureID.CHICKEN; }
	            	else if (entity instanceof Cow) {
	            		if (entity instanceof MushroomCow) { return CreatureID.MUSHROOM_COW; }
	            		else { return CreatureID.COW; }
	            	}
	            	else if (entity instanceof Pig) { return CreatureID.PIG; }
	            	else if (entity instanceof Wolf) { 
	            		Wolf wolfEntity = (Wolf) entity;
	    				
	    				if (wolfEntity.isTamed() && wolfEntity.getOwner() instanceof Player) {
	    					Player owner = (Player) wolfEntity.getOwner();
	    					
	    					if (owner.getName().equalsIgnoreCase(player.getName())) { return CreatureID.WOLF_TAMED_BY_SELF; }
	    					else { return CreatureID.WOLF_TAMED; }
	    				}
	    				else { return CreatureID.WOLF; }
	            	}
	            	else if (entity instanceof Sheep) { return CreatureID.SHEEP; }
	            	else { return CreatureID.ANIMALS; }
	            }
	            // Monsters
	            else if (entity instanceof Monster) {
	            	if (entity instanceof Zombie) {
	            		if (entity instanceof PigZombie) { return CreatureID.PIG_ZOMBIE; }
	            		else { return CreatureID.ZOMBIE; }
	            	}
	            	else if (entity instanceof Creeper) { 
	            		if (((Creeper) entity).isPowered())	{ return CreatureID.ELECTRIFIED_CREEPER; }
	                	else								{ return CreatureID.CREEPER; }
	            	}
	            	else if (entity instanceof Enderman) { return CreatureID.ENDERMAN; }
	            	else if (entity instanceof Silverfish) { return CreatureID.SILVERFISH; }
	            	else if (entity instanceof Giant) { return CreatureID.GIANT; }
	            	else if (entity instanceof Skeleton) { return CreatureID.SKELETON; }
	            	else if (entity instanceof Blaze) { return CreatureID.BLAZE; }
	            	else if (entity instanceof Spider) {
	            		if (entity instanceof CaveSpider) { return CreatureID.CAVE_SPIDER; }
	            		else { return CreatureID.SPIDER; }
	            	}
	            	else  { return CreatureID.MONSTER; }
	            }
	            // Water Animals
	            else if (entity instanceof WaterMob) {
	            	if (entity instanceof Squid) { return CreatureID.SQUID; }
	            	else { return CreatureID.WATER_MOB; }
	            }
	            else if (entity instanceof Snowman) { return CreatureID.SNOWMAN; }
	            else if (entity instanceof Villager) { return CreatureID.VILLAGER; }
	            else { return CreatureID.CREATURE; }
	        }
			// Slimes
			else if (entity instanceof Slime) {
				if (entity instanceof MagmaCube) {
					MagmaCube magmaCubeEntity = (MagmaCube) entity;
					
					if (magmaCubeEntity.getSize() == 1) { return CreatureID.MAGMA_CUBE_TINY; }
					else if (magmaCubeEntity.getSize() == 2) { return CreatureID.MAGMA_CUBE_SMALL; }
					else if (magmaCubeEntity.getSize() > 2) { return CreatureID.MAGMA_CUBE_BIG; }
				}
				else {
					Slime slimeCubeEntity = (Slime) entity;
					
					if (slimeCubeEntity.getSize() == 1) { return CreatureID.SLIME_TINY; }
					else if (slimeCubeEntity.getSize() == 2) { return CreatureID.SLIME_SMALL; }
					else if (slimeCubeEntity.getSize() > 2) { return CreatureID.SLIME_BIG; }
				}
			}
			// Flying
			else if (entity instanceof Flying) {
				if (entity instanceof Ghast) { return CreatureID.GHAST; }
				else { return CreatureID.FLYING; }
			}
			// Dragon
			else if (entity instanceof ComplexLivingEntity) {
				if (entity instanceof EnderDragon) { return CreatureID.ENDERDRAGON; }
				else { return CreatureID.COMPLEX_MOB; }
	        }
	        else { return CreatureID.LIVING_MOB; }
		}

		return null;
	}
}
