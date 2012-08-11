package net.minecraft.src;

public class mod_TorchLevers extends BaseMod
{
	public static int blockid = 160;
	public static int itemid = 389;
	public static Block TLblock = new TLBlock(blockid, 80).setBlockName("TorchLeverBlock");
	public static Item TLitem = new TLItem(itemid).setItemName("TorchLeverItem");
	public void load()
	{
		ModLoader.addName(TLblock,  "TorchLever Block (you should not have this)");
		ModLoader.registerBlock(TLblock);
		ModLoader.addName(TLitem, "TorchLever");
		ModLoader.addRecipe(new ItemStack(TLitem, 1), new Object [] {"#", Character.valueOf('#'), Block.dirt});
		ModLoader.addRecipe(new ItemStack(TLblock, 1), new Object [] {"#",Character.valueOf('#'), TLitem});
		TLitem.iconIndex = ModLoader.addOverride("/gui/items.png", "/ajax/torchlevers/tlitem.png");
		ModLoader.addOverride(null, null);
	}
	@Override
	public String getVersion()
	{
		return "1.3.1";
	}
}