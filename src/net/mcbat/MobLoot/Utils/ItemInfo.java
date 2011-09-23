package net.mcbat.MobLoot.Utils;

public class ItemInfo {
	public int itemID;
	public byte dataID;
	public int quantity;
	public int chance;
	
	public ItemInfo(String itemInfo) {
		String[] itemData = itemInfo.split(":");
		
		try {
			if (itemData.length == 4) {
				itemID = Integer.parseInt(itemData[0]);
				dataID = Byte.parseByte(itemData[1]);
				quantity = Integer.parseInt(itemData[2]);
				chance = Integer.parseInt(itemData[3]);
			}
			else if (itemData.length == 3) {
				itemID = Integer.parseInt(itemData[0]);
				dataID = 0;
				quantity = Integer.parseInt(itemData[1]);
				chance = Integer.parseInt(itemData[2]);
			}
			else if (itemData.length == 2) {
				itemID = Integer.parseInt(itemData[0]);
				dataID = 0;
				quantity = Integer.parseInt(itemData[1]);
				chance = 100;
			}
			else if (itemData.length == 1) {
				itemID = Integer.parseInt(itemData[0]);
				dataID = 0;
				quantity = 1;
				chance = 100;
			}
			else
				invalidate();
		}
		catch (Exception E) {
			invalidate();
		}
		
		if (itemID != -1 && !isItem(itemID))
			invalidate();
		if (itemID != -1 && dataID != -1 && !hasData(itemID, dataID))
			invalidate();
		if (quantity < 1)
			quantity = 1;
	}
	
	public ItemInfo(int item, int quant, int dropChance) {
		itemID = item;
		dataID = 0;
		quantity = quant;
		chance = dropChance;
	}
	
	public ItemInfo(int item, byte data, int quant, int dropChance) {
		itemID = item;
		dataID = data;
		quantity = quant;
		chance = dropChance;		
	}
	
	public boolean isValid() {
		return (itemID != -1 && dataID != -1 && quantity != -1 && chance != -1);
	}
	
	public String toString() {
		return itemID+":"+dataID+":"+quantity+":"+chance;
	}
	
	private void invalidate() {
		itemID = -1;
		dataID = -1;
		quantity = -1;
		chance = -1;
	}
	
	private boolean isItem(int item) {
		if (item >= 0 && item <= 115)			return true; 
		else if (item >= 256 && item <= 372)	return true;
		else if (item >= 2256 && item <= 2257)	return true;
		
		return false;
	}
	
	private boolean hasData(int item, int data) {
		if (data == 0)
			return true;
		
		if (item == 70 || item == 72 || item == 263) {
			if (data == 0 || data == 1)		return true;
			else							return false;
		}
		if (item == 17 || item == 18 || item == 31 || item == 84 || item == 97) {
			if (data >= 0 && data <= 2)		return true;
			else							return false;
		}
		else if (item == 43 || item == 44 || item == 53 || item == 65 || item == 67 || item == 86 || item == 91 || item == 98) {
			if (data >= 0 && data <= 3)		return true;
			else							return false;
		}
		else if (item == 50 || item == 75 || item == 76 || item == 92) {
			if (data >= 0 && data <= 5)		return true;
			else							return false;
		}
		else if (item == 59 || item == 96 || item == 104 || item == 105 || item == 107) {
			if (data >= 0 && data <= 7)		return true;
			else							return false;
		}
		else if (item == 60) {
			if (data >= 0 && data <= 8)		return true;
			else							return false;
		}
		else if (item == 27 || item == 28 || item == 66) {
			if (data >= 0 && data <= 9)		return true;
			else							return false;
		}
		else if (item == 99 || item == 100) {
			if (data >= 0 && data <= 10) 	return true;
			else							return false;
		}
		else if (item == 6 || item == 8 || item == 9 || item == 11 || item == 35 || item == 51 || item == 55 || item == 63 || item == 64 || item == 71 || item == 81 || item == 83 || item == 93 || item == 94 || item == 106 || item == 351) {
			if (data >= 0 && data <= 15)	return true;
			else							return false;
		}
		else if (item == 23 || item == 54 || item == 61 || item == 62 || item == 68) {
			if (data >= 2 && data <= 5)		return true;
			else							return false;
		}
		else if (item == 69) {
			if (data >= 6 && data <= 14)	return true;
			else							return false;
		}
		else if (item == 26) {
			if (data >= 0 && data <= 3)			return true;
			else if (data >= 8 && data <= 11)	return true;
			else								return false;
		}
		else if (item == 29 || item == 33 || item == 34) {
			if (data >= 0 && data <= 5)			return true;
			else if (data >= 8 && data <= 13)	return true;
			else								return false;
		}
		else if ((item >= 256 && item <= 259) || (item >= 267 && item <= 279) || (item >= 283 && item <= 286) || (item >= 290 && item <= 294) || (item >= 298 && item <= 317) || item == 359) {
			return true;
		}
		
		return false;
	}
}
