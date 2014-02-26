package ere_geologique.common.entity;

import io.netty.buffer.ByteBuf;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import ere_geologique.common.entity.enums.EnumDinoType;
import ere_geologique.common.entity.ia.DinoAIAttackOnCollide;
import ere_geologique.common.entity.ia.DinoAIEat;
import ere_geologique.common.entity.ia.DinoAIFollowOwner;
import ere_geologique.common.entity.ia.DinoAIWander;

public class Stegosaurus extends Dinosaure
{
    private boolean looksWithInterest;
    /*public final float HuntLimit = (float)this.getHungerLimit() * 0.9F;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isWolfShaking;
    private boolean field_25052_g;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    public int SubSpecies = 1;
    public boolean isBaby = true;*/
    public int RushTick = 0;
    //public int BreedTick = 3000;
    public boolean Running = false;

    public Stegosaurus(World var1)
    {
        super(var1,EnumDinoType.Stegosaurus);
        this.looksWithInterest = false;
        //this.SubSpecies = (new Random()).nextInt(3) + 1;
        //this.texture = "/mods/fossil/textures/Stegosaurus_Baby.png";
        //this.CheckSkin();
        //this.setSize(1.0F, 1.0F);
        //this.moveSpeed = 0.3F;
        //this.health = 8;
        //this.experienceValue=3;
        
        
        /*this.Width0=1.2F;
        this.WidthInc=0.5F;
        this.Length0=1.0F;
        this.LengthInc=0.7F;
        this.Height0=1.2F;
        this.HeightInc=0.36F;
        //this.BaseattackStrength=;
        //this.AttackStrengthIncrease=;
        //this.BreedingTime=;
        //this.BaseSpeed=;
        //this.SpeedIncrease=;
        this.MaxAge=13;
        this.BaseHealth=21;
        this.HealthIncrease=1;
        //this.AdultAge=;
        //this.AgingTicks=;
        this.MaxHunger=500;
        this.Hungrylevel=0.9F;*/
        this.updateSize();
        
        this.setSubSpecies((new Random()).nextInt(3) + 1);
        
        
        
        /*
         * EDIT VARIABLES PER DINOSAUR TYPE
         */
        
        this.adultAge = EnumDinoType.Stegosaurus.AdultAge;
        
        // Set initial size for hitbox. (length/width, height)
        this.setSize(1.0F, 0.8F);
        
        // Size of dinosaur at day 0.
        this.minSize = 1.0F;
        
        // Size of dinosaur at age Adult.
        this.maxSize = 8.0F;
        
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, new DinoAIAttackOnCollide(this,1.0D, true));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, 5.0F, 2.0F, 2.0F));
        this.tasks.addTask(7, new DinoAIEat(this, 24));
        this.tasks.addTask(7, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }
    
    public String getTexture()
    {
        if (this.isModelized())
            return super.getModelTexture();
        if(this.isAdult())
            return "ere_geologique:textures/entity/Stegosaurus_Adult.png";
		return "ere_geologique:textures/entity/Stegosaurus_Baby.png";
    }
    
    public String getDinosaurName()
    {
          return EnumDinoType.Stegosaurus.name();
    }
    
    @Override
    protected String getLivingSound()
    {
        return "ere_geologique:stegosaurus_living";
    }
    
    @Override
    protected String getHurtSound()
    {
        return "ere_geologique:stegosaurus_hurt";
    }
    
    @Override
    protected String getDeathSound()
    {
        return "ere_geologique:stegosaurus_death";
    }
    
    protected void updateEntityActionState()
    {
        if (this.riddenByEntity == null)
        {
            super.updateEntityActionState();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        /*this.field_25054_c = this.field_25048_b;

        if (this.looksWithInterest)
        {
            this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
        }
        else
        {
            this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
        }*/

        if (this.looksWithInterest)
        {
            this.numTicksToChaseTarget = 10;
        }
    }

    /*public boolean getSelfShaking()
    {
        return false;
    }

    public float getShadingWhileShaking(float var1)
    {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * var1) / 2.0F * 0.25F;
    }

    public float getShakeAngle(float var1, float var2)
    {
        float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * var1 + var2) / 1.8F;

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }
        else if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return MathHelper.sin(var3 * (float)Math.PI) * MathHelper.sin(var3 * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    public float getInterestedAngle(float var1)
    {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * var1) * 0.15F * (float)Math.PI;
    }*/

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

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        Entity var3 = var1.getEntity();
        this.setSitting(false);

        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
        {
            var2 = (var2 + 1) / 2;
        }

        if (!super.attackEntityFrom(var1, var2))
        {
            return false;
        }
        else
        {
            if (!this.isTamed() && !this.isSelfAngry())
            {
                if (var3 instanceof EntityPlayer)
                {
                    this.setSelfAngry(true);
                    this.entityToAttack = var3;
                }

                if (var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null)
                {
                    var3 = ((EntityArrow)var3).shootingEntity;
                }

                if (var3 instanceof EntityLiving)
                {
                    ;
                }
            }
            else if (var3 != this && var3 != null)
            {
                if (this.isTamed() && var3 instanceof EntityPlayer && ((EntityPlayer)var3).getCommandSenderName().equalsIgnoreCase(this.getOwnerName()))
                {
                    return true;
                }

                this.entityToAttack = var3;
            }

            return true;
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        return this.isSelfAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0D) : null;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
    	//Add special item interaction code here
        return super.interact(var1);
    }
    public void handleHealthUpdate(byte var1)
    {
        if (var1 == 7)
        {
            this.showHeartsOrSmokeFX(true, true);
        }
        else if (var1 == 6)
        {
            this.showHeartsOrSmokeFX(false, false);
        }
        else if (var1 == 8)
        {
            //this.field_25052_g = true;
            //this.timeWolfIsShaking = 0.0F;
            //this.prevTimeWolfIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(var1);
        }
    }

    public boolean isSelfAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setSelfAngry(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
  //          this.moveSpeed = 2.0F;
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
    //        this.moveSpeed = 0.5F;
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    public boolean CheckSpace()
    {
        return !this.isEntityInsideOpaqueBlock();
    }

    public void FaceToCoord(int var1, int var2, int var3)
    {
        double var4 = (double)var1;
        double var6 = (double)var3;
        float var8 = (float)(Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
        this.rotationYaw = this.updateRotation(this.rotationYaw, var8, 360.0F);
    }

    private float updateRotation(float var1, float var2, float var3)
    {
        float var4;

        for (var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        if (var4 > var3)
        {
            var4 = var3;
        }

        if (var4 < -var3)
        {
            var4 = -var3;
        }

        return var1 + var4;
    }

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void applyEntityCollision(Entity var1)
    {
        if (var1 instanceof EntityLiving && !(var1 instanceof EntityPlayer) && this.riddenByEntity != null && this.onGround)
        {
            this.onKillEntity((EntityLiving)var1);
            ((EntityLiving)var1).attackEntityFrom(DamageSource.causeMobDamage(this), 10);
        }
        else
        {
            super.applyEntityCollision(var1);
        }
    }

    public Stegosaurus spawnBabyAnimal(EntityAgeable var1)
    {
        return new Stegosaurus(this.worldObj);
    }

    /*public float getGLX()
    {
        return (float)(1.5D + 0.3D * (double)this.getDinoAge());
    }

    public float getGLY()
    {
        return (float)(1.5D + 0.3D * (double)this.getDinoAge());
    }*/

    /*public EntityAgeable func_90011_a(EntityAgeable var1)
    {
        return this.spawnBabyAnimal(var1);
    }*/

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return null;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {}

	@Override
	public void readSpawnData(ByteBuf additionalData) {}
}