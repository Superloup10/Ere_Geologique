package ere_geologique.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
 
public class RendermobCroMagnon extends RenderLiving {
 
    private ModelBase ModelmobCroMagnon = new ModelmobCroMagnon();
 
    public RendermobCroMagnon(ModelmobCroMagnon ModelmobCroMagnon, float par2)
    {
        super(new ModelmobCroMagnon(), 0.5F);
    }
}