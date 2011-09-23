package net.mcbat.MobLoot.Utils;

import net.minecraft.server.EntityWolf;

import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.entity.Animals;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public enum CreatureID {
	CAVE_SPIDER("CaveSpider"),
	CHICKEN("Chicken"),
	COW("Cow"),
	CREEPER("Creeper"),
	ELECTRIFIED_CREEPER("ElectrifiedCreeper"),
	ENDERMAN("Enderman"),
	GHAST("Ghast"),
	GIANT("Giant"),
	MONSTER("Monster"),
	PIG("Pig"),
	PIG_ZOMBIE("PigZombie"),
	SHEEP("Sheep"),
	SILVERFISH("Silverfish"),
	SKELETON("Skeleton"),
	SLIME("Slime"),
	SPIDER("Spider"),
	SQUID("Squid"),
	WOLF("Wolf"),
	WOLF_TAMED("TamedWolf"),
	WOLF_TAMED_BY_SELF("SelfTamedWolf"),
	ZOMBIE("Zombie");
	
	private String _name;

	private CreatureID(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public static CreatureID fromName(String name) {
		for (CreatureID id : CreatureID.values()) {
			if (id.name().equalsIgnoreCase(name))
				return id;
		}
		
		return null;
	}
	
	public static CreatureID fromEntity(Entity entity, String playerName) {
		// Order borrowed from CraftBukkit->CraftEntity.java (Thank You! =D)
		if (entity instanceof Animals) {
			if (entity instanceof Chicken) return CreatureID.CHICKEN;
            else if (entity instanceof Cow) return CreatureID.COW;
            else if (entity instanceof Pig) return CreatureID.PIG;
            else if (entity instanceof Wolf) { 
				EntityWolf wolfEntity = ((CraftWolf) entity).getHandle();
				
				if (wolfEntity.getOwnerName().equalsIgnoreCase(""))
					return CreatureID.WOLF;
				else if (wolfEntity.getOwnerName().equalsIgnoreCase(playerName))
					return CreatureID.WOLF_TAMED_BY_SELF;
				else
					return CreatureID.WOLF_TAMED;
			}
            else if (entity instanceof Sheep) return CreatureID.SHEEP;
		}
        else if (entity instanceof Monster) {
            if (entity instanceof Zombie) {
                if (entity instanceof PigZombie) 		return CreatureID.PIG_ZOMBIE;
                else 									return CreatureID.ZOMBIE;
            }
            else if (entity instanceof Creeper) {
            	if (((Creeper) entity).isPowered())		return CreatureID.ELECTRIFIED_CREEPER;
            	else									return CreatureID.CREEPER;
            }
            else if (entity instanceof Enderman) 	return CreatureID.ENDERMAN;
            else if (entity instanceof Silverfish)	return CreatureID.SILVERFISH;
            else if (entity instanceof Giant)		return CreatureID.GIANT;
            else if (entity instanceof Skeleton)	return CreatureID.SKELETON;
            else if (entity instanceof Spider) {
                if (entity instanceof CaveSpider)		return CreatureID.CAVE_SPIDER;
                else 									return CreatureID.SPIDER;
            }
            else  									return CreatureID.MONSTER;
        }
        else if (entity instanceof Squid)		return CreatureID.SQUID;
        else if (entity instanceof Slime)		return CreatureID.SLIME;
        else if (entity instanceof Ghast)		return CreatureID.GHAST;

		return null;
	}	
}

