package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.object.boat.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.*;

@Mod(value = ColorfulAzaleas.MOD_ID, dist = Dist.CLIENT)
public class ColorfulAzaleasClient {
	
	public ColorfulAzaleasClient(IEventBus modBus) {
		ColorfulAzaleas.LOGGER.info("Generating Cutouts for " + ColorfulAzaleas.MOD_ID);
		for (ColorfulTree tree : AzaleaBlocks.trees) {
			WoodSet woodSet = tree.getWoodSet();
			
			// TODO: render layers
			/*BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT,
					tree.getSapling(),
					tree.getPottedSapling(),
					tree.getFloweringLeaves(),
					tree.getBloomingLeaves(),
					tree.getAzaleaLeaves(),
					tree.getDroopingLeaves()
			);
			
			BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT,
					woodSet.getDoor(),
					woodSet.getTrapdoor()
			);*/
		}
		
		//BlockRenderLayerMap.putBlock(AzaleaBlocks.DROOPING_AZALEA_LEAVES, ChunkSectionLayer.CUTOUT);
	}
	
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		for (ColorfulTree tree : AzaleaBlocks.trees) {
			WoodSet woodSet = tree.getWoodSet();
			
			ModelLayerLocation boatLayer = new ModelLayerLocation(
					ColorfulAzaleas.id(woodSet.getWoodSet() + "_azalea_boat"),
					"main"
			);
			
			ModelLayerLocation chestBoatLayer = new ModelLayerLocation(
					ColorfulAzaleas.id(woodSet.getWoodSet() + "_azalea_chest_boat"),
					"main"
			);
			
			event.registerEntityRenderer(woodSet.getBoatEntityType().get(), context -> new BoatRenderer(context, boatLayer));
			event.registerEntityRenderer(woodSet.getChestBoatEntityType().get(), context -> new BoatRenderer(context, chestBoatLayer));
		}
	}
	
	@SubscribeEvent // on the mod event bus only on the physical client
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		for (ColorfulTree tree : AzaleaBlocks.trees) {
			WoodSet woodSet = tree.getWoodSet();
			
			ModelLayerLocation boatLayer = new ModelLayerLocation(
					ColorfulAzaleas.id(woodSet.getWoodSet() + "_azalea_boat"),
					"main"
			);
			event.registerLayerDefinition(boatLayer, BoatModel::createBoatModel);
			
			ModelLayerLocation chestBoatLayer = new ModelLayerLocation(
					ColorfulAzaleas.id(woodSet.getWoodSet() + "_azalea_chest_boat"),
					"main"
			);
			event.registerLayerDefinition(chestBoatLayer, BoatModel::createChestBoatModel);
		}
	}
	
	@SubscribeEvent // on the mod event bus only on the physical client
	public static void registerLayerDefinitions(BlockEntityTypeAddBlocksEvent event) {
		Block[] shelves = new Block[AzaleaBlocks.trees.length];
		Block[] signs = new Block[AzaleaBlocks.trees.length];
		Block[] hangingSigns = new Block[AzaleaBlocks.trees.length];
		int i = 0;
		for (ColorfulTree tree : AzaleaBlocks.trees) {
			WoodSet woodSet = tree.getWoodSet();
			shelves[i] = woodSet.getShelf().get();
			signs[i] = woodSet.getSign().get();
			hangingSigns[i] = woodSet.getHangingSign().get();
			i++;
		}
		
		event.modify(BlockEntityType.SHELF, shelves);
		event.modify(BlockEntityType.SIGN, signs);
		event.modify(BlockEntityType.HANGING_SIGN, hangingSigns);
	}
	
}