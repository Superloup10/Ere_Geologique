package ere_geologique.common.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ere_geologique.common.entity.DinoEgg;
import ere_geologique.common.entity.Nautilus;
import ere_geologique.common.entity.Enums.EnumDinoType;

public class Egg extends Item
{
    public static final int TypeCount = EnumDinoType.values().length;
    private int DinoType;

    public Egg(int var1,int DinoType0)
    {
        super(var1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.maxStackSize = 1;
        this.DinoType=DinoType0;
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("ere_geologique:"+EnumDinoType.values()[DinoType].name()+"_Egg");
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        float var4 = 1.0F;
        float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var4;
        float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var4;
        double var7 = var3.prevPosX + (var3.posX - var3.prevPosX) * (double)var4;
        double var9 = var3.prevPosY + (var3.posY - var3.prevPosY) * (double)var4 + 1.62D - (double)var3.yOffset;
        double var11 = var3.prevPosZ + (var3.posZ - var3.prevPosZ) * (double)var4;
        Vec3 var13 = var2.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var19 = var14 * var16;
        double var20 = 5.0D;
        Vec3 var22 = var13.addVector((double)var18 * var20, (double)var17 * var20, (double)var19 * var20);
        MovingObjectPosition var23 = var2.rayTraceBlocks_do_do(var13, var22, true, false);

        if (var23 == null)
        {
            return var1;
        }
        else
        {
            Vec3 var24 = var3.getLook(var4);
            boolean var25 = false;
            float var26 = 1.0F;
            List var27 = var2.getEntitiesWithinAABBExcludingEntity(var3, var3.boundingBox.addCoord(var24.xCoord * var20, var24.yCoord * var20, var24.zCoord * var20).expand((double)var26, (double)var26, (double)var26));
            Iterator var28 = var27.iterator();

            while (var28.hasNext())
            {
                Entity var29 = (Entity)var28.next();

                if (var29.canBeCollidedWith())
                {
                    float var30 = var29.getCollisionBorderSize();
                    AxisAlignedBB var31 = var29.boundingBox.expand((double)var30, (double)var30, (double)var30);

                    if (var31.isVecInside(var13))
                    {
                        var25 = true;
                    }
                }
            }

            if (var25)
            {
                return var1;
            }
            else
            {
                if (var23.typeOfHit == EnumMovingObjectType.TILE)
                {
                    int var34 = var23.blockX;
                    int var32 = var23.blockY;
                    int var33 = var23.blockZ;

                    if (!var2.isRemote)
                    {
                        if (var2.getBlockId(var34, var32, var33) == Blocks.snow.blockID)
                        {
                            --var32;
                        }
                        EnumDinoType i=this.GetTypeFromInt(var3.inventory.getCurrentItem().getItem());
                        if (!spawnCreature(var2, i, (double)((float)var34 + 0.5F), (double)((float)var32 + 1.0F), (double)((float)var33 + 0.5F)))
                        {
                            return var1;
                        }
                    }

                    if (!var3.capabilities.isCreativeMode)
                    {
                        --var1.stackSize;
                    }
                }

                return var1;
            }
        }
    }

    public static boolean spawnCreature(World var0, EnumDinoType var1, double var2, double var4, double var6)
    {
        Object var8;
        if (var1 == EnumDinoType.Nautilus)
        {
            var8 = new Nautilus(var0);
            ((Nautilus)var8).isOwned=true;
        }
        else
        {
            var8 = new DinoEgg(var0, var1);
        }

        if (var8 != null)
        {
            ((Entity)var8).setLocationAndAngles(var2, var4, var6, var0.rand.nextFloat() * 360.0F, 0.0F);
            var0.spawnEntityInWorld((Entity)var8);
        }

        return var8 != null;
    }

    private EnumDinoType GetTypeFromInt(Item var1)
    {
    	return EnumDinoType.values()[EnumDinoType.getIndex(var1)];
    }
}