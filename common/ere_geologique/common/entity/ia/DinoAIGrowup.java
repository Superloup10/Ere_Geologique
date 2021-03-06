package ere_geologique.common.entity.ia;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import ere_geologique.common.command.CommandDino;
import ere_geologique.common.entity.Dinosaure;
import ere_geologique.common.entity.enums.EnumDinoType;
import ere_geologique.common.entity.enums.EnumSituation;

public class DinoAIGrowup extends EntityAIBase
{
    protected Dinosaure AITarget;

    public DinoAIGrowup(Dinosaure var1)
    {
        this.AITarget = var1;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {  
        if (/*EGOptions.DinoGrows && */this.AITarget.getDinoAge() < this.AITarget.SelfType.MaxAge)
        {
            this.AITarget.increaseDinoAgeTick();
            return this.AITarget.getDinoAgeTick() >= this.AITarget.SelfType.AgingTicks;
        }
        return false;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if(!this.AITarget.worldObj.isRemote)
        {
	        //this.AITarget.setPosition(this.AITarget.posX, this.AITarget.posY, this.AITarget.posZ);
	
	        if ((this.AITarget.CheckSpace() && this.AITarget.SelfType!=EnumDinoType.Mosasaurus) || (this.AITarget.isInWater() && this.AITarget.SelfType==EnumDinoType.Mosasaurus))
	        {
	        	this.AITarget.setDinoAgeTick(0);
	            this.AITarget.increaseDinoAge();
	            this.AITarget.worldObj.setEntityState(this.AITarget, this.AITarget.AGING_MESSAGE);
	            //this.AITarget.CheckSkin();
	            this.AITarget.updateSize();
	            if (this.AITarget.getMaxHealth() < this.AITarget.getHealth())
	            {//the dino heals itself 15% when growing up
	            	if(CommandDino.heal_Dinos)
	            		this.AITarget.heal(MathHelper.ceiling_double_int(this.AITarget.getHealth()*0.15f));
	            }
	            /*this.AITarget.setDinoAge(this.AITarget.getDinoAge()-1);
	            this.AITarget.worldObj.setEntityState(this.AITarget, this.AITarget.AGING_MESSAGE);
	            //this.AITarget.CheckSkin();
	
	            if (this.AITarget.getHealth() > MathHelper.ceiling_double_int(this.AITarget.getMaxHealth()*0.05f))
	            {
	            	if(Fossil.FossilOptions.Heal_Dinos)
	            		this.AITarget.attackEntityFrom(DamageSource.generic, MathHelper.ceiling_double_int(this.AITarget.getMaxHealth()*0.05f));
	            }
	
	            this.AITarget.updateSize();
	            //this.AITarget.setPosition(this.AITarget.posX, this.AITarget.posY, this.AITarget.posZ);
	
	            if (this.AITarget.isTamed())
	            {
	                this.AITarget.SendStatusMessage(EnumSituation.NoSpace);//, this.AITarget.SelfType);
	            }*/
	        }
	        else
	        	this.AITarget.SendStatusMessage(EnumSituation.NoSpace);//, this.AITarget.SelfType);
        }
    }
}
