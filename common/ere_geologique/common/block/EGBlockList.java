package ere_geologique.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ere_geologique.client.LocalizationStrings;
import ere_geologique.common.EreGeologique;
import ere_geologique.common.config.EGProperties;
import ere_geologique.common.creativetabs.EGCreativeTab;

public class EGBlockList
{
	public static Block Leaves;
	public static Block Wood;
	public static Block Sapling;
	public static Block Plank;
	public static Block DoubleSlab;
	public static Block Slab;
	public static Block Stair;
	public static GlaciaPortal GlaciaPortal;
	public static Block Feeder;
	public static Block AnalyzerIdle;
	public static Block AnalyserActive;
	public static Block CultivatorIdle;
	public static Block CultivatorActive;
	public static Block Fossil;
	public static Block ReinforcedStone;
	public static Block ReinforcedGlass;
	public static Block SteelBlock;
	public static Block Skull;
	public static BlueFire BlueFire;
	
    public static Block nanoLeavesBlock, nanoSaplingsBlock;
	
	public static void loadBlock()
	{
		try
		{
		   Leaves = new Leaves(EGProperties.LeavesID).setStepSound(Block.soundGrassFootstep).setHardness(0.2F).setLightOpacity(1).setUnlocalizedName("Leaves").setTextureName("ere_geologique:Leaves");
	       Wood = new Wood(EGProperties.WoodID).setStepSound(Block.soundWoodFootstep).setHardness(2.0F).setUnlocalizedName("Wood").setTextureName("ere_geologique:Wood");
	       Sapling = new Sapling(EGProperties.SaplingID).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setUnlocalizedName("Sapling").setTextureName("ere_geologique:Sapling");
	       Plank = new Plank(EGProperties.PlankID).setStepSound(Block.soundWoodFootstep).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("Plank").setTextureName("ere_geologique:Plank");
	       Slab = new Slab(EGProperties.SlabID, false).setStepSound(Block.soundWoodFootstep).setHardness(3F).setResistance(1.0F).setUnlocalizedName("Slab").setCreativeTab(EGCreativeTab.EGCreativeTabBlock);
	       DoubleSlab = new Slab(EGProperties.DoubleSlabID, true).setStepSound(Block.soundWoodFootstep).setHardness(3F).setResistance(1.0F).setUnlocalizedName("Slab");
	       Stair = new Stair(EGProperties.StairID, Plank,1).setStepSound(Block.soundWoodFootstep).setHardness(3F).setResistance(1.0F).setUnlocalizedName("Stair");
	       GlaciaPortal = (GlaciaPortal) new GlaciaPortal(EGProperties.GlaciaPortalID).setHardness(-1.0F).setStepSound(Block.soundGlassFootstep).setLightValue(0.75F).setUnlocalizedName("GlaciaPortal").setTextureName("ere_geologique:GlaciaPortal");
	       Feeder = new Feeder(EGProperties.FeederID).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName(LocalizationStrings.FEEDER_NAME).setCreativeTab(EGCreativeTab.EGCreativeTabBlock);
	       AnalyzerIdle = new Analyzer(EGProperties.AnalyzerIdleID, false).setHardness(3.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName(LocalizationStrings.ANALYZER_IDLE_NAME).setCreativeTab(EGCreativeTab.EGCreativeTabBlock);
	       AnalyserActive = new Analyzer(EGProperties.AnalyzerActiveID, true).setLightValue(0.9375F).setHardness(3.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName(LocalizationStrings.ANALYZER_ACTIVE_NAME);
	       CultivatorIdle = new Cultivator(EGProperties.CultivatorIdleID, false).setLightValue(0.9375F).setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName(LocalizationStrings.CULTIVATE_IDLE_NAME).setCreativeTab(EGCreativeTab.EGCreativeTabBlock);
	       CultivatorActive = new Cultivator(EGProperties.CultivatorActiveID, true).setLightValue(0.9375F).setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName(LocalizationStrings.CULTIVATE_ACTIVE_NAME);
	       Fossil = new Fossil(EGProperties.FossilID).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("Fossil").setTextureName("ere_geologique:Fossil");
	       ReinforcedStone = new ReinforcedStone(EGProperties.ReinforcedStoneID).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("ReinforcedStone").setTextureName("ere_geologique:reinforced_stone");
	       ReinforcedGlass = new ReinforcedGlass(EGProperties.ReinforcedGlassID, Material.glass, false).setHardness(0.3F).setResistance(10.0F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("ReinforcedGlass").setTextureName("ere_geologique:reinforced_glass");
	       SteelBlock = new ReinforcedStone(EGProperties.SteelBlockID).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("SteelBlock").setTextureName("ere_geologique:steel_block");
	       Skull = new Skull(EGProperties.FossilSkullID).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("FossilSkull");
	       BlueFire = (BlueFire) new BlueFire(EGProperties.BlueFireID).setHardness(0.0F).setLightValue(1.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("BlueFire");
	       
	       GameRegistry.registerBlock(Leaves, ItemBlockLeaves.class, "Leaves");
	       GameRegistry.registerBlock(Wood, ItemBlockWood.class, "Wood");
	       GameRegistry.registerBlock(Sapling, ItemBlockSapling.class, "Sapling");
	       GameRegistry.registerBlock(Plank, ItemBlockPlank.class, "Plank");
	       GameRegistry.registerBlock(Slab, ItemBlockSlab.class, "Slab");
	       GameRegistry.registerBlock(DoubleSlab, ItemBlockSlab.class, "DoubleSlab");
	       GameRegistry.registerBlock(Stair, "Stair");
	       GameRegistry.registerBlock(GlaciaPortal, "GlaciaPortal");
	       GameRegistry.registerBlock(Feeder, "Feeder");
	       GameRegistry.registerBlock(AnalyzerIdle, "AnalyzerIdle");
	       GameRegistry.registerBlock(AnalyserActive, "AnalyserActive");
	       GameRegistry.registerBlock(CultivatorIdle, "CultivatorIdle");
	       GameRegistry.registerBlock(CultivatorActive, "CultivatorActive");
	       GameRegistry.registerBlock(Fossil, "Fossil");
	       GameRegistry.registerBlock(ReinforcedStone, "ReinforcedStone");
	       GameRegistry.registerBlock(ReinforcedGlass, "ReinforcedGlass");
	       GameRegistry.registerBlock(SteelBlock, "Steelblock");
	       GameRegistry.registerBlock(Skull, "Skull");
	       GameRegistry.registerBlock(BlueFire, "BlueFire");

	       MinecraftForge.setBlockHarvestLevel(Fossil, 0, "pickaxe", 2);

		}
		catch(Exception ex)
		{
			EreGeologique.EGLog.severe("Erreur lors de l'initialisation des blocs!");
		}
		EreGeologique.EGLog.info("Initialisation des blocs terminés!");
		
    	if(Loader.isModLoaded("Nanotech_mod"))
    	{
    		try
    		{
    			nanoLeavesBlock = (Block)Class.forName("fr.mcnanotech.kevin_68.nanotech_mod.main.blocks.nanoLeaves").getField("nanoLeaves").get(null);
    			nanoSaplingsBlock = (Block)Class.forName("fr.mcnanotech.kevin_68.nanotech_mod.main.blocks.nanoSaplings").getField("nanoSaplings").get(null);
    		}
    		catch(Exception ex)
    		{
    			EreGeologique.EGLog.severe("Erreur lors de l'initialisation du Nanotech_mod");
    		}
    		EreGeologique.EGLog.info("Initialisation du Nanotech_mod terminé");
    	}
	}
}