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
import net.minecraft.world.level.storage.loot.parameters.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.data.event.*;

import java.util.*;

@Mod(value = ColorfulAzaleas.MOD_ID, dist = Dist.CLIENT)
public class ColorfulAzaleasClient {
	
	public ColorfulAzaleasClient(IEventBus modBus) {
		modBus.addListener(ColorfulAzaleasClient::gatherData);
		modBus.addListener(ColorfulAzaleasClient::registerEntityRenderers);
		modBus.addListener(ColorfulAzaleasClient::registerLayerDefinitions);
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
	
	private static final Map<ColorfulTree, ModelLayerLocation> BOAT_LAYERS = new HashMap<>();
	private static final Map<ColorfulTree, ModelLayerLocation> CHEST_BOAT_LAYERS = new HashMap<>();
	
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			
			ModelLayerLocation boatLayerLocation = new ModelLayerLocation(ColorfulAzaleas.id("boat/" + woodSet.getWoodSet() + "_azalea"), "main");
			BOAT_LAYERS.put(tree, boatLayerLocation);
			event.registerLayerDefinition(boatLayerLocation, BoatModel::createBoatModel);
			
			ModelLayerLocation chestBoatLayerLocation = new ModelLayerLocation(ColorfulAzaleas.id("chest_boat/" + woodSet.getWoodSet() + "_azalea"), "main");
			CHEST_BOAT_LAYERS.put(tree, chestBoatLayerLocation);
			event.registerLayerDefinition(chestBoatLayerLocation, BoatModel::createChestBoatModel);
		}
	}
	
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			
			event.registerEntityRenderer(woodSet.getBoatEntityType().get(), context -> new BoatRenderer(context, BOAT_LAYERS.get(tree)));
			event.registerEntityRenderer(woodSet.getChestBoatEntityType().get(), context -> new BoatRenderer(context, CHEST_BOAT_LAYERS.get(tree)));
		}
	}
	
}