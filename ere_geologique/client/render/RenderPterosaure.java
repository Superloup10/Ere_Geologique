package ere_geologique.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ere_geologique.common.entity.Pterosaure;

@SideOnly(Side.CLIENT)
public class RenderPterosaure extends RenderLiving
{
    private static final ResourceLocation loc = new ResourceLocation("ere_geologique:textures/entity/Pterosaure.png");

    public RenderPterosaure(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    protected void preRenderScale(Pterosaure entitydinosaur, float par2)
    {
        GL11.glScalef(entitydinosaur.getDinoWidth(), entitydinosaur.getDinoHeight(), entitydinosaur.getDinoLength());
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderScale((Pterosaure)par1EntityLivingBase, par2);
    }
    
    protected ResourceLocation func_110919_a(Pterosaure par1Entity)
    {
        return new ResourceLocation(par1Entity.getTexture());
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110919_a((Pterosaure)par1Entity);
    }
    
}