package ere_geologique.common.item;

import net.minecraft.item.Item;
import ere_geologique.common.creativetabs.EGCreativeTab;

public class TranquilizerDart extends Item
{
	public TranquilizerDart()
	{
		super();
		this.setCreativeTab(EGCreativeTab.EGCreativeTabItem);
		this.setMaxStackSize(64);
	}
}