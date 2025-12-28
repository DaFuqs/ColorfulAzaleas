package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.datagen.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.object.boat.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.data.event.*;
import net.neoforged.neoforge.event.*;

import java.util.*;

@Mod(value = ColorfulAzaleas.MOD_ID, dist = Dist.CLIENT)
public class ColorfulAzaleasClient {
	
	public ColorfulAzaleasClient(IEventBus modBus) {

		modBus.addListener(ColorfulAzaleasClient::clientSetup);
		modBus.addListener(ColorfulAzaleasClient::gatherData);
		modBus.addListener(ColorfulAzaleasClient::registerEntityRenderers);
		modBus.addListener(ColorfulAzaleasClient::registerLayerDefinitions);
	}
	
	public static void clientSetup(FMLClientSetupEvent event) {
		ColorfulAzaleas.LOGGER.info("Generating Cutouts for " + ColorfulAzaleas.MOD_ID);
		
		ItemBlockRenderTypes.setRenderLayer(AzaleaBlocks.DROOPING_AZALEA_LEAVES.get(), ChunkSectionLayer.CUTOUT);
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			
			ItemBlockRenderTypes.setRenderLayer(tree.getSapling().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getPottedSapling().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getFloweringLeaves().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getBloomingLeaves().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getAzaleaLeaves().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getDroopingLeaves().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getSapling().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(tree.getSapling().get(), ChunkSectionLayer.CUTOUT);
			
			ItemBlockRenderTypes.setRenderLayer(woodSet.getDoor().get(), ChunkSectionLayer.CUTOUT);
			ItemBlockRenderTypes.setRenderLayer(woodSet.getTrapdoor().get(), ChunkSectionLayer.CUTOUT);
		}
	}
	
	public static void gatherData(GatherDataEvent.Client event) {
		event.createProvider(ModBlockTagProvider::new);
		event.createProvider(ModEntityTagProvider::new);
		event.createProvider(ModItemTagProvider::new);
		event.createProvider(ModLanguageProvider::new);
		event.createProvider((output, lookupProvider) -> new LootTableProvider(
				output, Set.of(),
				List.of(new LootTableProvider.SubProviderEntry(ModLootTableProvider::new, LootContextParamSets.EMPTY)),
				lookupProvider)
		);
		event.createProvider(ModModelProvider::new);
		event.createProvider(ModRecipeProvider.Runner::new);
	}
	
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			
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
	
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			
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
	
}