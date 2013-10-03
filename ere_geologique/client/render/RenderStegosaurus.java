package ere_geologique.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ere_geologique.common.entity.Stegosaurus;

public class RenderStegosaurus extends RenderLiving
{
    private static final ResourceLocation loc = new ResourceLocation("ere_geologique:textures/entity/Stegosaurus_Adult.png");
    
    public RenderStegosaurus(ModelBase var1, float var2)
    {
        super(var1, var2);
        this.setRenderPassModel(var1);
    }
    
    public RenderStegosaurus(ModelBase var1, ModelBase var2, float var3)
    {
        super(var1, var3);
        this.setRenderPassModel(var2);
    }
    
    /**
     * Applies the scale to the transform matrix
     * 
     * Use this to grow the dinonsaur with age.
     */
    protected void preRenderScale(Stegosaurus stegosaurus, float par2)
    {
        GL11.glScalef(stegosaurus.getDinoWidth(), stegosaurus.getDinoHeight(), stegosaurus.getDinoLength());
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderScale((Stegosaurus)par1EntityLivingBase, par2);
    }
    
    protected ResourceLocation func_110919_a(Stegosaurus par1Entity)
    {
        return loc;
    }
    
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110919_a((Stegosaurus)par1Entity);
    }
}