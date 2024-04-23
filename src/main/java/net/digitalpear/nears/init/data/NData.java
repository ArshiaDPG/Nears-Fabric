package net.digitalpear.nears.init.data;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.dispenser.DispenserFaarBundleBehavior;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.DispenserBlock;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Rarity;

public class NData {

    public static void registerCompostables(){
        CompostingChanceRegistry compostingChanceRegistry = CompostingChanceRegistry.INSTANCE;

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

        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (LootTables.BASTION_HOGLIN_STABLE_CHEST.equals(key) && source.isBuiltin()) {
                tableBuilder.modifyPools(context -> context.with(ItemEntry.builder(NItems.NEAR).weight(6).quality(Rarity.COMMON.ordinal() + 1))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 4.0F))));
            }
            else if (LootTables.BASTION_OTHER_CHEST.equals(key) && source.isBuiltin()) {
                tableBuilder.modifyPools(context -> context.with(ItemEntry.builder(NItems.NEAR).weight(2).quality(Rarity.COMMON.ordinal() + 1))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))));

            }
            else if (LootTables.NETHER_BRIDGE_CHEST.equals(key) && source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.builder().with(ItemEntry.builder(NItems.SOUL_BERRIES).weight(1).quality(Rarity.COMMON.ordinal() + 1)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 6.0F))));
                tableBuilder.pool(poolBuilder);
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
