package ere_geologique.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import ere_geologique.common.creativetabs.EGCreativeTab;

public class EGItem extends Item
{
	String TextureFileName;
	
	public EGItem(String TextureFileName0)
	{
		super();
		this.TextureFileName=TextureFileName0;
		this.setCreativeTab(EGCreativeTab.EGCreativeTabItem);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon("ere_geologique:" + TextureFileName);
    }
}