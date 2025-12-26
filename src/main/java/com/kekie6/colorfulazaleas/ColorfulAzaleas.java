package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.decorators.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.common.*;
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
        AzaleaItemGroups.register(modBus);

        // Boats
        registerAllAzaleaBoats();
        
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

    public static void registerAllAzaleaBoats() {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();

            woodSet.setBoatItem(TerraformBoatItemHelper.registerBoatItem(woodSet.getAzaleaBoatsId(), false));
            woodSet.setChestBoatItem(TerraformBoatItemHelper.registerBoatItem(woodSet.getAzaleaBoatsId(), true));
        }
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}