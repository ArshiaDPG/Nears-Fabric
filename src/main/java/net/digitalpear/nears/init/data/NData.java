
package net.digitalpear.nears.init.data;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.dispenser.DispenserFaarBundleBehavior;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class NData {

    public static void registerCompostables(){
        CompostableRegistry compostingChanceRegistry = CompostableRegistry.INSTANCE;

        compostingChanceRegistry.add(NItems.FAAR_SEEDS, 0.3f);
        compostingChanceRegistry.add(NItems.SOUL_BERRY_PIPS, 0.3f);
        compostingChanceRegistry.add(NItems.NEAR_SPORES, 0.3f);
        compostingChanceRegistry.add(NItems.CINDER_SEEDS, 0.3f);

        compostingChanceRegistry.add(NBlocks.FAAR_BUNDLE, 1.0f);
        compostingChanceRegistry.add(NItems.SOULLESS_PASTRY, 1.0f);

        compostingChanceRegistry.add(NItems.SOUL_BERRIES, 0.5f);
        compostingChanceRegistry.add(NItems.FAAR, 0.2f);
        compostingChanceRegistry.add(NItems.NEAR, 0.4f);
        compostingChanceRegistry.add(NItems.CINDER_GRAIN, 0.5f);

        compostingChanceRegistry.add(NBlocks.CINDER_GRASS, 0.4f);
        compostingChanceRegistry.add(NItems.CINDER_GRAIN, 0.4f);
        compostingChanceRegistry.add(NItems.CINDER_SANGAK, 0.6f);
        compostingChanceRegistry.add(NBlocks.CINDER_BALE, 1.0f);
    }


    public static void registerLootTableModifications(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, wrapperLookup) -> {
            if (BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(key) && source.isBuiltin()) {
                tableBuilder.modifyPools(context -> context.add(LootItem.lootTableItem(NItems.NEAR).setWeight(6).setQuality(Rarity.COMMON.ordinal() + 1))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))));
            }
            else if (BuiltInLootTables.BASTION_OTHER.equals(key) && source.isBuiltin()) {
                tableBuilder.modifyPools(context -> context.add(LootItem.lootTableItem(NItems.NEAR).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))));

            }
            else if (BuiltInLootTables.NETHER_BRIDGE.equals(key) && source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(NItems.SOUL_BERRIES).setWeight(1).setQuality(Rarity.COMMON.ordinal() + 1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 6.0F))));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }

    public static void registerDispenserBehavior(){
        DispenserBlock.registerBehavior(NBlocks.FAAR_BUNDLE, new DispenserFaarBundleBehavior());
    }

    public static void init(){
        registerCompostables();
        registerLootTableModifications();
        registerDispenserBehavior();
    }
}
