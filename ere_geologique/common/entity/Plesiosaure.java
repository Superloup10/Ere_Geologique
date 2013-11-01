package ere_geologique.common.entity;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ere_geologique.common.EreGeologique;
import ere_geologique.common.entity.Enums.EnumDinoType;
import ere_geologique.common.entity.Enums.EnumOrderType;
import ere_geologique.common.entity.IA.DinoAIAttackOnCollide;
import ere_geologique.common.entity.IA.DinoAIControlledByPlayer;
import ere_geologique.common.entity.IA.DinoAIEat;
import ere_geologique.common.entity.IA.DinoAIFishing;
import ere_geologique.common.entity.IA.DinoAIFollowOwner;
import ere_geologique.common.entity.IA.DinoAIWander;
import ere_geologique.common.entity.Interface.IWaterDino;

public class Plesiosaure extends Dinosaure implements IWaterDino
{
    private boolean looksWithInterest;
    //public final float HuntLimit = (float)(this.getHungerLimit() * 4 / 5);
    /*private float field_25048_b;
    private float field_25054_c;//For the interesting looking angle!
    private boolean isWolfShaking;
    private boolean field_25052_g;
    public int SubSpecies = 1;
    public boolean isBaby = true;*/
    //public int RushTick = 0;
     
    public float TargetY = 0.0F;
    private float randomMotionSpeed;
    private float randomMotionVecX = 0.0F;
    private float randomMotionVecY = 0.0F;
    private float randomMotionVecZ = 0.0F;
    //protected final int AGE_LIMIT = 18;

    public Plesiosaure(World var1)
    {
        super(var1,EnumDinoType.Plesiosaure);
        this.looksWithInterest = false;
        this.setSubSpecies((new Random()).nextInt(3) + 1);

        this.updateSize(); 
        
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(2, this.ridingHandler = new DinoAIControlledByPlayer(this));
        this.tasks.addTask(3, new DinoAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, 5.0F, 2.0F, 1.0F));
        this.tasks.addTask(7, new DinoAIEat(this, 24));
        this.tasks.addTask(8, new DinoAIFishing(this, /*this.HuntLimit,*/ 1));
        this.tasks.addTask(9, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(11, new EntityAILookIdle(this));
    }

	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(21.0D);

    }
    
    /**
     * Returns the texture's file path as a String.
     */
    @Override
    public String getTexture()
    {
        if (this.isModelized())
            return super.getTexture();
            switch (this.getSubSpecies())
            {
                default:
                	return "ere_geologique:textures/entity/Plesiosaure_adult.png";
            }
    }
    
    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return !this.isModelized();
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean canBreatheUnderwater()
    {
        return true;
    }

    protected void updateEntityActionState()
    {
        if (!this.isModelized())
        {
            if (this.riddenByEntity == null)
            {
                super.updateEntityActionState();

                if (!this.isOnSurface() && (double)this.TargetY < this.posY)
                {
                    this.TargetY = (float)(this.posY++);
                }

                if (!this.isSitting() && !this.hasPath() && (new Random()).nextInt(1000) == 5)
                {
                    this.FindFish(6);
                }

                if (!this.worldObj.isRemote)
                {
                    this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
                }
            }
        }
    }

    public boolean isOnSurface()
    {
        return this.worldObj.isAirBlock((int)Math.floor(this.posX), (int)Math.floor(this.posY + (double)(this.getEyeHeight() / 2.0F)), (int)Math.floor(this.posZ));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        /*if (this.isInWater())
        {
            if (!this.hasPath() && this.riddenByEntity == null)
            {
                this.motionX *= 0.8D;
                this.motionZ *= 0.8D;
            }

            /*if (this.riddenByEntity != null)
            {
                EntityPlayerSP var1 = (EntityPlayerSP)this.riddenByEntity;

                if (var1.movementInput.moveForward + var1.movementInput.moveStrafe == 0.0F)
                {
                    this.motionX *= 0.8D;
                    this.motionZ *= 0.8D;
                }
            }*/

            /*if ((!this.isOnSurface() && (double)this.TargetY > this.posY || (double)this.TargetY < this.posY) && !this.isCollidedVertically)
            {
                if ((double)this.TargetY > this.posY)
                {
                    this.motionY += 0.02D;
                }
                else
                {
                    this.motionY -= 0.1D;
                }

                if (this.motionY > 0.4D)
                {
                    this.motionY = 0.4D;
                }

                if (this.motionY < -2.0D)
                {
                    this.motionY = -2.0D;
                }
            }

            if (Math.abs(this.posY - (double)this.TargetY) <= 0.125D)
            {
                this.motionY = 0.0D;
            }*/
        //}
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    /*public void moveEntityWithHeading(float var1, float var2)
    {
        double var3;

        if (this.isInWater())
        {
            if (this.isOnSurface() && this.motionY > 0.0D)
            {
                this.motionY = 0.0D;
            }

            var3 = this.posY;
            this.moveFlying(var1, var2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var3, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        else if (this.handleLavaMovement())
        {
            var3 = this.posY;
            this.moveFlying(var1, var2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
            this.motionY -= 0.02D;

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var3, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        else
        {
            float var8 = 0.91F;

            if (this.onGround)
            {
                var8 = 0.5460001F;
                int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (var4 > 0)
                {
                    var8 = Block.blocksList[var4].slipperiness * 0.91F;
                }
            }

            float var9 = 0.1627714F / (var8 * var8 * var8);
            float var5 = this.onGround ? this.landMovementFactor * var9 : this.jumpMovementFactor;
            this.moveFlying(var1, var2, var5);
            var8 = 0.91F;

            if (this.onGround)
            {
                var8 = 0.5460001F;
                int var6 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (var6 > 0)
                {
                    var8 = Block.blocksList[var6].slipperiness * 0.91F;
                }
            }

            if (this.isOnLadder())
            {
                float var11 = 0.15F;

                if (this.motionX < (double)(-var11))
                {
                    this.motionX = (double)(-var11);
                }

                if (this.motionX > (double)var11)
                {
                    this.motionX = (double)var11;
                }

                if (this.motionZ < (double)(-var11))
                {
                    this.motionZ = (double)(-var11);
                }

                if (this.motionZ > (double)var11)
                {
                    this.motionZ = (double)var11;
                }

                this.fallDistance = 0.0F;

                if (this.motionY < -0.15D)
                {
                    this.motionY = -0.15D;
                }

                if (this.isSneaking() && this.motionY < 0.0D)
                {
                    this.motionY = 0.0D;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.isCollidedHorizontally && this.isOnLadder())
            {
                this.motionY = 0.2D;
            }

            if (!this.isInWater())
            {
                this.motionY -= 0.08D;
            }

            this.motionY *= 0.9800000190734863D;
            this.motionX *= (double)var8;
            this.motionZ *= (double)var8;
        }

        this.prevLegYaw = this.legYaw;
        var3 = this.posX - this.prevPosX;
        double var10 = this.posZ - this.prevPosZ;
        float var7 = MathHelper.sqrt_double(var3 * var3 + var10 * var10) * 4.0F;

        if (var7 > 1.0F)
        {
            var7 = 1.0F;
        }

        this.legYaw += (var7 - this.legYaw) * 0.4F;
        this.legSwing += this.legYaw;
    }*/

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float getBlockPathWeight(int var1, int var2, int var3)
    {
        return 0.5F - this.worldObj.getLightBrightness(var1, var2, var3);
    }

    /*public float getInterestedAngle(float var1)
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

    protected void getPathOrWalkableBlock(Entity var1, float var2)
    {
        PathEntity var3 = this.worldObj.getPathEntityToEntity(this, var1, 16.0F, true, false, true, false);
        this.setPathToEntity(var3);
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
        if (this.modelizedDrop())
        {
            return true;
        }
        else
        {
            Entity var3 = var1.getEntity();

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
                    if (this.isTamed() && var3 instanceof EntityPlayer && ((EntityPlayer)var3).username.equalsIgnoreCase(this.getOwnerName()))
                    {
                        return true;
                    }

                    this.entityToAttack = var3;
                }

                return true;
            }
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

    public boolean isSelfAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /*public boolean isSelfSitting()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }*/

    public void setSelfAngry(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
            //this.moveSpeed = 2.0F;
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
            //this.moveSpeed = 0.5F;
        }
    }

    /*public void setSelfSitting(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }*/

    /*public void setTamed(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
    }*/

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /*private void InitSize()
    {
        this.updateSize();
        this.setPosition(this.posX, this.posY, this.posZ);
        this.moveSpeed = this.getSpeed();//0.5F + (float)(this.getDinoAge() * 3);
    }*/

    public boolean CheckSpace()
    {
        if (!this.isInWater())
        {
            return !this.isEntityInsideOpaqueBlock();
        }
        else
        {
            for (int var1 = 0; var1 < 8; ++var1)
            {
                float var2 = ((float)((var1 >> 0) % 2) - 0.5F) * this.width * 0.9F;
                float var3 = ((float)((var1 >> 1) % 2) - 0.5F) * 0.1F;
                float var4 = ((float)((var1 >> 2) % 2) - 0.5F) * this.width * 0.9F;
                int var5 = MathHelper.floor_double(this.posX + (double)var2);
                int var6 = MathHelper.floor_double(this.posY + (double)this.getEyeHeight() + (double)var3);
                int var7 = MathHelper.floor_double(this.posZ + (double)var4);
                Block var8 = Block.blocksList[this.worldObj.getBlockId(var1, var5, var6)];

                if (var8 != null && var8 != Block.waterStill && var8 != Block.waterMoving)
                {
                    return false;
                }
            }

            return true;
        }
    }

    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
            this.riddenByEntity.setPosition(this.posX, this.posY + (double)this.getDinoHeight() * 0.75D + 0.07D * (double)(18 - this.getDinoAge()), this.posZ);
        }
    }

    /*public boolean HandleEating(int var1)
    {
        if (this.getHunger() >= this.getHungerLimit())
        {
            return false;
        }
        else
        {
            this.increaseHunger(var1);
            this.showHeartsOrSmokeFX(false);

            if (this.getHunger() >= this.getHungerLimit())
            {
                this.setHunger(this.getHungerLimit());
            }

            return true;
        }
    }*/

    private boolean FindFish(int var1)
    {
        if (this.isSitting())
        {
            return false;
        }
        else
        {
            List var2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0D, 0.0D, 1.0D));

            if (var2 != null)
            {
                for (int var3 = 0; var3 < var2.size(); ++var3)
                {
                    if (var2.get(var3) instanceof EntityItem)
                    {
                        EntityItem var4 = (EntityItem)var2.get(var3);

                        if (var4.getEntityItem().itemID == Item.fishRaw.itemID || var4.getEntityItem().itemID == Item.fishCooked.itemID)
                        {
                            this.increaseHunger(10);
                            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((new Random()).nextFloat() - (new Random()).nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            var4.setDead();
                            return true;
                        }
                    }
                }
            }

            return false;
        }
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
     * Causes this entity to do an upwards motion (jumping).
     */
    /*protected void jump()
    {
        if (this.isInWater() && this.isCollidedHorizontally && this.riddenByEntity == null)
        {
            super.jump();
        }
    }*/

    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }

    public float HandleRiding(float Speed, float SpeedBoosted)
    {
        /*if (this.RushTick > 0)
        {
            --this.RushTick;
        }

        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayerSP)
        {
            if (this.onGround || this.isInWater())
            {
                if (((EntityPlayerSP)this.riddenByEntity).movementInput.jump)
                {
                    this.jump();
                    ((EntityPlayerSP)this.riddenByEntity).movementInput.jump = false;
                }

                for (this.rotationYaw -= ((EntityPlayerSP)this.riddenByEntity).movementInput.moveStrafe * 5.0F; this.rotationYaw < -180.0F; this.rotationYaw += 360.0F)
                {
                    ;
                }

                while (this.rotationYaw >= 180.0F)
                {
                    this.rotationYaw -= 360.0F;
                }

                this.moveForward = ((EntityPlayerSP)this.riddenByEntity).movementInput.moveForward * this.moveSpeed;
            }

            if (((EntityPlayerSP)this.riddenByEntity).movementInput.sneak)
            {
                this.TargetY = (float)this.posY - 0.5F;
            }
            else if (this.isOnSurface())
            {
                this.TargetY = (float)this.posY;
            }
            else if (((EntityPlayerSP)this.riddenByEntity).movementInput.moveForward == 0.0F)
            {
                this.TargetY = (float)this.posY + 0.2F;
            }
            else
            {
                this.TargetY = (float)this.posY;
            }
        }*/
    	if(this.RiderForward>0)
    		Speed += (this.getAIMoveSpeed()*(this.isInWater()?3.2F:1.3F) - Speed) * 0.1F*this.RiderForward;
    	else
    		if(Speed>0)
    		{
    			Speed += (this.getAIMoveSpeed()*(this.isInWater()?3.2F:1.3F) - Speed) * 0.4F*this.RiderForward;//Break faster
    			if(Speed<0)Speed=0;
    		}
    		else
    			Speed += (this.getAIMoveSpeed()*(this.isInWater()?3.2F:1.3F) - Speed) * 0.06F*this.RiderForward;
    	this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw - (this.isInWater()?this.RiderStrafe*2.0F:this.RiderStrafe*5.0F));
    	if(this.isInWater())
    	{
    		if(this.RiderJump)
            	this.motionY+=(0.4F-this.motionY)*0.1F;
    		else if(this.RiderSneak)
            	this.motionY+=(-0.4F-this.motionY)*0.1F;
    		else this.motionY+= this.motionY<0? 0.01F : -0.01F;//Make the upwards motion slower
    		if((this.isOnSurface() && this.motionY>0F) || (this.motionY>-0.01F && this.motionY<0.01F))
    			this.motionY=0.0F;
    		if(this.RiderJump && this.isOnSurface())
    			this.jump();
    	}
    	else
    	{
    		if(this.RiderJump)
            {
            	this.getJumpHelper().setJumping();
            	this.RiderJump=false;
            }
    	}
    	this.moveEntityWithHeading(0.0F, Speed+Speed*(0.3F+0.85F*MathHelper.sin(SpeedBoosted*(float)Math.PI)));
    	//this.posY+=this.motionY;
        return Speed;
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
            if (!this.isInWater())
            {
                super.applyEntityCollision(var1);
            }
        }
    }

    public int BlockInteractive()
    {
    	int destroyed=0;
        for (int var1 = (int)Math.round(this.boundingBox.minX) - 1; var1 <= (int)Math.round(this.boundingBox.maxX) + 1; ++var1)
        {
            for (int var2 = (int)Math.round(this.boundingBox.minY); var2 <= (int)Math.round(this.boundingBox.maxY); ++var2)
            {
                for (int var3 = (int)Math.round(this.boundingBox.minZ) - 1; var3 <= (int)Math.round(this.boundingBox.maxZ) + 1; ++var3)
                {
                    if (!this.worldObj.isAirBlock(var1, var2, var3))
                    {
                        int var4 = this.worldObj.getBlockId(var1, var2, var3);

                        if (!this.inWater)
                        {
                            /*if (this.isTamed() && this.riddenByEntity == null)
                            {
                                if (var4 == Block.wood.blockID || var4 == Block.leaves.blockID)
                                {
                                    this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
                                    this.RushTick = 10;
                                }
                            }
                            else*/ if ((double)Block.blocksList[var4].getBlockHardness(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ) <= 1.5D || var4 == Block.wood.blockID || var4 == Block.planks.blockID || var4 == Block.woodDoubleSlab.blockID || var4 == Block.woodSingleSlab.blockID || (double)Block.blocksList[var4].getBlockHardness(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ) >= 0.0D)
                            {
                                if ((new Random()).nextInt(10) == 5)
                                {
                                    Block.blocksList[var4].dropBlockAsItem(this.worldObj, var1, var2, var3, 1, 0);
                                }

                                this.worldObj.setBlock(var1, var2, var3, 0);
                                destroyed++;
                                //this.RushTick = 10;
                            }
                        }
                    }
                }
            }
        }
        return destroyed;
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving var1)
    {
        super.onKillEntity(var1);

        if (var1 instanceof Nautilus)
        {
            this.increaseHunger(100);
        }
        if(EreGeologique.EGOptions.Heal_Dinos)
        	this.heal(5);
    }

    public void SetOrder(EnumOrderType var1)
    {
        this.setPathToEntity((PathEntity)null);
        this.setTarget((Entity)null);
        this.OrderStatus = var1;
    }

    public Plesiosaure spawnBabyAnimal(EntityAgeable var1)
    {
        return new Plesiosaure(this.worldObj);
    }

    /*public void updateSize()
    {
        this.setSize((float)(1.0D + 0.3D * (double)((float)this.getDinoAge())), (float)(1.0D + 0.3D * (double)((float)this.getDinoAge())));
    }

    public float getGLX()
    {
        return (float)(1.0D + 0.3D * (double)((float)this.getDinoAge()));
    }

    public float getGLY()
    {
        return (float)(1.0D + 0.3D * (double)((float)this.getDinoAge()));
    }*/

    /*public EntityAgeable func_90011_a(EntityAgeable var1)
    {
        return this.spawnBabyAnimal(var1);
    }*/

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return this.spawnBabyAnimal(var1);
	}
	/**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        double var9;

        if (this.isInWater())
        {
            var9 = this.posY;
            this.moveFlying(par1, par2, this.isAIEnabled() ? 0.04F : 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
 //           if(this.riddenByEntity==null)
            	this.motionY += 0.02D;// + -> - gravity is on it if not ridden, then handling the riding...going straight forward

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var9, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        else if (this.handleLavaMovement())
        {
            var9 = this.posY;
            this.moveFlying(par1, par2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
            this.motionY -= 0.02D;

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var9, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        else
        {
            float var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (var4 > 0)
                {
                    var3 = Block.blocksList[var4].slipperiness * 0.91F;
                }
            }

            float var8 = 0.16277136F / (var3 * var3 * var3);
            float var5;

            if (this.onGround)
            {
                if (this.isAIEnabled())
                {
                    var5 = this.getAIMoveSpeed();
                }
                else
                {
                    var5 = 1.0F;//this.landMovementFactor;
                }

                var5 *= var8;
            }
            else
            {
                var5 = this.jumpMovementFactor;
            }

            this.moveFlying(par1, par2, var5);
            var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var6 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (var6 > 0)
                {
                    var3 = Block.blocksList[var6].slipperiness * 0.91F;
                }
            }
//            System.out.println(String.valueOf(this.motionY));
            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded))
            {
                if (this.posY > 0.0D)
                {
                    this.motionY = -0.1D;
                }
                else
                {
                    this.motionY = 0.0D;
                }
            }
            else
            {
            	if(!this.isInWater())
            	{
            		this.motionY -= 0.08D;
            	}
            }

            this.motionY *= 0.9800000190734863D;
            this.motionX *= (double)var3;
            this.motionZ *= (double)var3;
        }

        //this.prevLegYaw = this.legYaw;
        var9 = this.posX - this.prevPosX;
        double var12 = this.posZ - this.prevPosZ;
        float var11 = MathHelper.sqrt_double(var9 * var9 + var12 * var12) * 4.0F;

        if (var11 > 1.0F)
        {
            var11 = 1.0F;
        }

        //this.legYaw += (var11 - this.legYaw) * 0.4F;
        //this.legSwing += this.legYaw;
    }
}