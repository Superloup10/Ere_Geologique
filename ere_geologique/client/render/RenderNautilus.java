package ere_geologique.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ere_geologique.common.entity.Nautilus;

public class RenderNautilus extends RenderLiving
{
    private static final ResourceLocation loc = new ResourceLocation("ere_geologique:textures/entity/Nautilus.png");
    
    protected ResourceLocation func_110919_a(Nautilus par1Entity)
    {
        return loc;
    }
    
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110919_a((Nautilus)par1Entity);
    }
    
    public RenderNautilus(ModelBase var1, float var2)
    {
        super(var1, var2);
    }

    /**
     * Renders the Living Squid.
     */
    public void renderLivingSquid(Nautilus var1, double var2, double var4, double var6, float var8, float var9)
    {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    /**
     * Rotates the Squid's corpse.
     */
    protected void rotateSquidsCorpse(Nautilus var1, float var2, float var3, float var4)
    {
        float var5 = var1.field_70860_g + (var1.field_70859_f - var1.field_70860_g) * var4;
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glRotatef(180.0F - var3, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var5, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.2F, 0.0F);
    }

    protected float handleRotationFloat(Nautilus var1, float var2)
    {
        float var3 = var1.lastTentacleAngle + (var1.tentacleAngle - var1.lastTentacleAngle) * var2;
        return var3;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving var1, float var2)
    {
        return this.handleRotationFloat((Nautilus)var1, var2);
    }

    protected void rotateCorpse(EntityLiving var1, float var2, float var3, float var4)
    {
        this.rotateSquidsCorpse((Nautilus)var1, var2, var3, var4);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderLivingSquid((Nautilus)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderLivingSquid((Nautilus)var1, var2, var4, var6, var8, var9);
    }
}