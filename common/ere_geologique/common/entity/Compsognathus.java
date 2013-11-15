package ere_geologique.common.entity;

import java.util.Vector;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ere_geologique.client.LocalizationStrings;
import ere_geologique.common.entity.Enums.EnumDinoType;
import ere_geologique.common.entity.Enums.EnumSituation;
import ere_geologique.common.entity.IA.DinoAIAttackOnCollide;
import ere_geologique.common.entity.IA.DinoAIEat;
import ere_geologique.common.entity.IA.DinoAIFollowOwner;
import ere_geologique.common.entity.IA.DinoAITargetNonTamedExceptSelfClass;
import ere_geologique.common.entity.IA.DinoAIWander;
import ere_geologique.common.gui.GuiPedia;

public class Compsognathus extends Dinosaure
{
    private boolean looksWithInterest;
    private float field_70926_e;
    private float field_70924_f;
    
    public int LearningChestTick = 900;
    public boolean PreyChecked = false;
    public boolean SupportChecked = false;
    public Vector MemberList = new Vector();

    public Compsognathus(World var1)
    {
        super(var1,EnumDinoType.Compsognathus);
        this.looksWithInterest = false;
        this.updateSize();
        
        
        /*
         * EDIT VARIABLES PER DINOSAUR TYPE
         */
        
        this.adultAge = EnumDinoType.Compsognathus.AdultAge;
        
        // Set initial size for hitbox. (length/width, height)
        this.setSize(1.0F, 1.0F);
        
        // Size of dinosaur at day 0.
        this.minSize = 0.25F;
        
        // Size of dinosaur at age Adult.
        this.maxSize = 1.0F;        
        

        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, TRex.class, 8.0F, 0.3F, 0.35F));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, Brachiosaurus.class, 8.0F, 0.3F, 0.35F));
        this.tasks.addTask(3, new DinoAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0F, 10.0F, 2.0F));
        this.tasks.addTask(6, new DinoAIEat(this, 24));
        this.tasks.addTask(7, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new DinoAITargetNonTamedExceptSelfClass(this, EntityLiving.class, 16.0F, 50, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.31D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(3.0D);
    }
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
    {
        super.setAttackTarget(par1EntityLivingBase);

        if (par1EntityLivingBase == null)
        {
            this.setAngry(false);
        }
        else if (!this.isTamed())
        {
            this.setAngry(true);
        }
    }
    
    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
        if (this.isModelized())
            return super.getTexture();
            switch (this.getSubSpecies())
            {
                case 1:
                    return "ere_geologique:textures/entity/Compsognathus_Purple.png";

                case 2:
                    return "ere_geologique:textures/entity/Compsognathus_Green.png";

                default:
                	return "ere_geologique:textures/entity/Compsognathus_Green.png";
            }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setInteger("LearningChestTick", this.LearningChestTick);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.LearningChestTick=var1.getInteger("LearningChestTick");
    }


    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        if(this.LearningChestTick>0 && this.isNearbyChest() && this.isAdult())
        {
        	this.LearningChestTick--;
        	if(this.LearningChestTick==0)
        		this.SendStatusMessage(EnumSituation.LearningChest);//, this.SelfType);
        }
    }
    
    public boolean isLearnedChest()
    {
        return this.LearningChestTick == 0;
    }
    
    private boolean isNearbyChest()
    {
        TileEntity var5 = null;
        for (int var6 = -10; var6 <= 10; ++var6)
        {
            for (int var7 = 0; var7 <= 3; ++var7)
            {
                for (int var8 = -10; var8 <= 10; ++var8)
                {
                    var5 = this.worldObj.getBlockTileEntity((int)(this.posX + (double)var6), (int)(this.posY + (double)var7), (int)(this.posZ + (double)var8));
                    if (var5 instanceof TileEntityChest)
                        return true;
                }
            }
        }
        return false;
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Disables a mob's ability to move on its own while true.
     */
    protected boolean isMovementCeased()
    {
        return this.isSitting();// || this.field_25052_g;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float par1)
    {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15F * (float)Math.PI;
    }
    

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            Entity entity = par1DamageSource.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                par2 = (par2 + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int i = this.isTamed() ? 4 : 2;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity var1, float var2)
    {
        if (var1.isDead)
        {
            this.setAttackTarget((EntityLiving)null);
        }

        if (var2 > 2.0F && var2 < 5.0F && this.rand.nextInt(10) == 0)
        {
            if (this.onGround)
            {
                double var3 = var1.posX - this.posX;
                double var5 = var1.posZ - this.posZ;
                float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
                this.motionX = var3 / (double)var7 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                this.motionZ = var5 / (double)var7 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
                this.worldObj.playSoundAtEntity(this, "Raptor_attack", this.getSoundVolume() * 2.0F, 1.0F);
                this.jump();
            }
        }
        else if ((double)var2 < 1.899999976158142D && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            var1.attackEntityFrom(DamageSource.causeMobDamage(this), 2 + this.getDinoAge());
        }
    }

    public void setTamed(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
        }
        else
        {
            this.ItemInMouth = null;
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
    }


    @SideOnly(Side.CLIENT)
    public void ShowPedia(GuiPedia p0)
    {
    	super.ShowPedia(p0);
    	if(this.LearningChestTick==0)
    		p0.AddStringLR(StatCollector.translateToLocal(LocalizationStrings.PEDIA_TEXT_CHEST), true);
    }

    public EntityAnimal spawnBabyAnimal(EntityAnimal var1)
    {
        return new Compsognathus(this.worldObj);
    }
}
