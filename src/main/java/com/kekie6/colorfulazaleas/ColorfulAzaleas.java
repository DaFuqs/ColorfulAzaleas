package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.datagen.*;
import com.kekie6.colorfulazaleas.decorators.*;
import com.kekie6.colorfulazaleas.entity.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.data.loot.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
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
	
	public ColorfulAzaleas(IEventBus modBus) {
		AzaleaItems.register(modBus);
		AzaleaBlocks.register(modBus);
		AzaleaEntities.register(modBus);
		AzaleaItemGroups.register(modBus);
		AzaleaTreeDecorators.register(modBus);
		
		NeoForge.EVENT_BUS.addListener(ColorfulAzaleas::lootTableLoad);
		modBus.addListener(ColorfulAzaleas::blockEntityTypeAddBlocksEvent);
	}
	
	public static void lootTableLoad(LootTableLoadEvent event) {
		ResourceKey<LootTable> key = event.getKey();
		
		if (key.equals(Blocks.AZALEA_LEAVES.getLootTable().get())) {
			LootPool.Builder poolBuilder = LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.when(LootItemRandomChanceCondition.randomChance(0.01f))
					.add(TagEntry.expandTag(ModTags.Items.AZALEA_SAPLINGS));
			event.getTable().addPool(poolBuilder.build());
		}
	}
	
	public static void blockEntityTypeAddBlocksEvent(BlockEntityTypeAddBlocksEvent event) {
		Block[] shelves = new Block[AzaleaBlocks.TREES.length];
		Block[] signs = new Block[AzaleaBlocks.TREES.length];
		Block[] wallSigns = new Block[AzaleaBlocks.TREES.length];
		Block[] hangingSigns = new Block[AzaleaBlocks.TREES.length];
		Block[] wallHangingSigns = new Block[AzaleaBlocks.TREES.length];
		int i = 0;
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			shelves[i] = woodSet.getShelf().get();
			signs[i] = woodSet.getSign().get();
			wallSigns[i] = woodSet.getWallSign().get();
			hangingSigns[i] = woodSet.getHangingSign().get();
			wallHangingSigns[i] = woodSet.getWallHangingSign().get();
			i++;
		}
		
		event.modify(BlockEntityTypes.SHELF, shelves);
		event.modify(BlockEntityTypes.SIGN, signs);
		event.modify(BlockEntityTypes.HANGING_SIGN, hangingSigns);
		event.modify(BlockEntityTypes.SIGN, wallSigns);
		event.modify(BlockEntityTypes.HANGING_SIGN, wallHangingSigns);
	}
	
	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}