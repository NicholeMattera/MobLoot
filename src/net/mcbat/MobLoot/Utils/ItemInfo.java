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
		if (item >= 0 && item <= 28)			return true; 
		else if (item == 30) 					return true;
		else if (item == 35)					return true;
		else if (item >= 37 && item <= 95)		return true;
		else if (item >= 256 && item <= 357)	return true;
		
		return false;
	}
	
	private boolean hasData(int item, int data) {
		if (data == 0)
			return true;
		
		if (item == 6 || item == 17 || item == 263) {
			if (data >= 0 && data <= 2)		return true;
			else							return false;
		}
		else if (item == 35 || item == 351) {
			if (data >= 0 && data <= 15)	return true;
			else							return false;
		}
		else if (item == 43 || item == 44) {
			if (data >= 0 && data <= 3)		return true;
			else							return false;
		}
		
		return false;
	}
}
