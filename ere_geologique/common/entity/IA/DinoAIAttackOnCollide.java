package ere_geologique.common.entity.IA;

import ere_geologique.common.entity.Dinosaure;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class DinoAIAttackOnCollide extends EntityAIBase
{
    World worldObj;
    private final Dinosaure attacker;

    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    int attackTick;
    double field_75440_e;
    boolean field_75437_f;

    /** The PathEntity of our entity. */
    PathEntity entityPathEntity;
    Class classTarget;
    private int field_75445_i;

    public DinoAIAttackOnCollide(Dinosaure par1Dinosaure, Class par2Class, double par3, boolean par5)
    {
        this(par1Dinosaure, par3, par5);
        this.classTarget = par2Class;
    }

    public DinoAIAttackOnCollide(Dinosaure par1dinosaure, double par2, boolean par4)
    {
        this.attacker = par1dinosaure;
        this.worldObj = par1dinosaure.worldObj;
        this.field_75440_e = par2;
        this.field_75437_f = par4;
        this.setMutexBits(3);
    }
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass()))
        {
            return false;
        }
        else
        {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
            return this.entityPathEntity != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.field_75437_f ? !this.attacker.getNavigator().noPath() : this.attacker.func_110176_b(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.field_75440_e);
        this.field_75445_i = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.attacker.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);

        if ((this.field_75437_f || this.attacker.getEntitySenses().canSee(entitylivingbase)) && --this.field_75445_i <= 0)
        {
            this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
            this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.field_75440_e);
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        double d0 = (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + entitylivingbase.width);

        if (this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ) <= d0)
        {
            if (this.attackTick <= 0)
            {
                this.attackTick = 20;

                if (this.attacker.getHeldItem() != null)
                {
                    this.attacker.swingItem();
                }

                this.attacker.attackEntityAsMob(entitylivingbase);
            }
        }
    }
}