package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.datagen.*;
import com.kekie6.colorfulazaleas.decorators.*;
import com.kekie6.colorfulazaleas.entity.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.loot.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.data.event.*;
import net.neoforged.neoforge.event.*;
import org.slf4j.*;

import java.util.*;

@Mod(ColorfulAzaleas.MOD_ID)
public class ColorfulAzaleas {
	
	public static final String MOD_ID = "colorfulazaleas";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	public static final TreeDecoratorType<ColorfulTreeDecorator> COLORFUL_TREE_DECORATOR = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, id("colorful_tree_decorator"), new TreeDecoratorType<>(ColorfulTreeDecorator.CODEC));
	
	public ColorfulAzaleas(IEventBus modBus) {
		AzaleaItems.register(modBus);
		AzaleaBlocks.init(modBus);
		AzaleaEntities.init(modBus);
		AzaleaItemGroups.register(modBus);
		
		NeoForge.EVENT_BUS.addListener(ColorfulAzaleas::lootTableLoad);
	}
	
	@SubscribeEvent // on the mod event bus
	public static void lootTableLoad(LootTableLoadEvent event) {
		ResourceKey<LootTable> key = event.getKey();
		
		if (key.equals(Blocks.AZALEA_LEAVES.getLootTable().get())) {
			LootPool.Builder poolBuilder = LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.when(LootItemRandomChanceCondition.randomChance(0.01f))
					.add(TagEntry.tagContents(ModTags.Items.AZALEA_SAPLINGS));
			event.getTable().addPool(poolBuilder.build());
		}
	}
	
	@SubscribeEvent // on the mod event bus
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
	
	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}