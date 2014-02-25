package ere_geologique.common.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ere_geologique.common.EreGeologique;
import ere_geologique.common.entity.Dinosaure;
import ere_geologique.common.entity.Plesiosaure;
import ere_geologique.common.entity.enums.EnumOrderType;

public class MagicConch extends Item
{
    public MagicConch()
    {
        super();
        //this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.maxStackSize = 1;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    //public int getIconFromDamage(int var1)
    //{
    //    return this.iconIndex;
    //}

    //public String getItemNameIS(ItemStack var1)
    //{
    //    String var2 = "MagicConch";
    //    String var3 = EnumOrderType.values()[var1.getItemDamage()].toString();
    //    return "MagicConch" + var3;
    //}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
       // String var4 = "Drum.";
      //  String var5 = "Msg.";
       // String var6 = "Head";
      //  String var7 = "Middle";
      //  String var8 = "Tail";
        String var9 = StatCollector.translateToLocal("Dino.Plesiosaure");//EntityDinosaur.GetNameByEnum(EnumDinoType.Plesiosaur, true);
        String var10 = StatCollector.translateToLocal("mGCName.head");
        //String var11 = StatCollector.translateToLocal("Drum.Msg.Middle");
        //String var12 = StatCollector.translateToLocal("Drum.Msg.Tail");
        String var13 = "";
        List var14 = var2.getEntitiesWithinAABB(Plesiosaure.class, AxisAlignedBB.getAABBPool().getAABB(var3.posX, var3.posY, var3.posZ, var3.posX + 1.0D, var3.posY + 1.0D, var3.posZ + 1.0D).expand(30.0D, 4.0D, 30.0D));
        Iterator var15 = var14.iterator();

        while (var15.hasNext())
        {
            Entity var16 = (Entity)var15.next();
            Dinosaure var17 = (Dinosaure)var16;

            if (var17.isTamed())
            {
                var17.SetOrder(EnumOrderType.values()[var1.getItemDamage()]);
                var2.spawnParticle("note", var16.posX, var16.posY + 1.2D, var16.posZ, 0.0D, 0.0D, 0.0D);
            }
        }

        var13 = StatCollector.translateToLocal("order." + EnumOrderType.values()[var1.getItemDamage()].toString());
       // Fossil.ShowMessage(var10 + var9 + var11 + " " + var13 + var12, var3);
        if (!var3.worldObj.isRemote)
        {
        EreGeologique.ShowMessage(var10 + var9 + " " + var13, var3);
        }
        return var1;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("ere_geologique:Magic_Conch");
    }
}