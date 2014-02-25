package ere_geologique.common.entity.ia;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ere_geologique.common.entity.Dinosaure;
//import ere_geologique.common.entity.enums.EnumOrderType;

public class DinoAIFollowOwner extends EntityAIBase
{
    private Dinosaure DinoEntity;
    private EntityLivingBase theOwner;
    World theWorld;
    private double field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean field_75344_i;

    public DinoAIFollowOwner(Dinosaure par1EntityTameable, double par2, float par4, float par5)
    {
        this.DinoEntity = par1EntityTameable;
        this.theWorld = par1EntityTameable.worldObj;
        this.field_75336_f = par2;
        this.petPathfinder = par1EntityTameable.getNavigator();
        this.minDist = par4;
        this.maxDist = par5;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.DinoEntity.isTamed())
        {
            return false;
        }
        return true;
        /*else
        {
            EntityLivingBase entitylivingbase = this.DinoEntity.func_130012_q();

            if (entitylivingbase == null)
            {
                return false;
            }
            else if (this.DinoEntity.getOrderType() != null && this.DinoEntity.getOrderType() != EnumOrderType.Follow)
            {
                return false;
            }
            else if (this.DinoEntity.isSitting())
            {
                return false;
            }
            else if (this.DinoEntity.getDistanceSqToEntity(entitylivingbase) < (double)(this.minDist * this.minDist))
            {
                return false;
            }
            else
            {
                this.theOwner = entitylivingbase;
                return true;
            }
        }*/
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.petPathfinder.noPath() && this.DinoEntity.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.DinoEntity.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_75343_h = 0;
        this.field_75344_i = this.DinoEntity.getNavigator().getAvoidsWater();
        this.DinoEntity.getNavigator().setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        this.DinoEntity.getNavigator().setAvoidsWater(this.field_75344_i);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.DinoEntity.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.DinoEntity.getVerticalFaceSpeed());

        if (!this.DinoEntity.isSitting())
        {
            if (--this.field_75343_h <= 0)
            {
                this.field_75343_h = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f))
                {
                    if (!this.DinoEntity.getLeashed())
                    {
                        if (this.DinoEntity.getDistanceSqToEntity(this.theOwner) >= 144.0D)
                        {
                            int i = MathHelper.floor_double(this.theOwner.posX) - 2;
                            int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
                            int k = MathHelper.floor_double(this.theOwner.boundingBox.minY);

                            for (int l = 0; l <= 4; ++l)
                            {
                                for (int i1 = 0; i1 <= 4; ++i1)
                                {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.theWorld.doesBlockHaveSolidTopSurface(theWorld, i + l, k - 1, j + i1) && !this.theWorld.isBlockNormalCubeDefault(i + l, k, j + i1, field_75344_i) && !this.theWorld.isBlockNormalCubeDefault(i + l, k + 1, j + i1, field_75344_i))
                                    {
                                        this.DinoEntity.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.DinoEntity.rotationYaw, this.DinoEntity.rotationPitch);
                                        this.petPathfinder.clearPathEntity();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}