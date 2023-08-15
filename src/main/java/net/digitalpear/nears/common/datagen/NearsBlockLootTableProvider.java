package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.common.blocks.SoulBerryBushBlock;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class NearsBlockLootTableProvider extends FabricBlockLootTableProvider {


    public NearsBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        LootCondition.Builder cropAgeConditionBuilder = BlockStatePropertyLootCondition.builder(NBlocks.CINDER_WHEAT)
                .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(CropBlock.AGE, 7));

        addDrop(NBlocks.NEAR_HANG, cropDrops(NBlocks.NEAR_HANG, NItems.NEAR, NItems.NEAR_SPORES, cropAgeConditionBuilder));

        addDrop(NBlocks.FAAR_BUNDLE, faarBundle(NBlocks.FAAR_BUNDLE));
        addDrop(NBlocks.FAAR_GROWTH, makeBushDrops(NBlocks.FAAR_GROWTH, NItems.FAAR_SEEDS));

        addDrop(NBlocks.SOUL_BERRY_BUSH, makeBushDrops(NBlocks.SOUL_BERRY_BUSH, NItems.SOUL_BERRIES));

        addDrop(NBlocks.CINDER_WHEAT, cropDrops(NBlocks.CINDER_WHEAT, NItems.CINDER_GRAIN, NItems.CINDER_SEEDS, cropAgeConditionBuilder));
        addDrop(NBlocks.CINDER_GRASS, cinderGrassDrops(NBlocks.CINDER_GRASS));


        addDrop(NBlocks.CINDER_BALE);

        addDrop(NBlocks.NEAR_HANG_STEM, makeNearStemDrops(NBlocks.NEAR_HANG_STEM, NItems.NEAR, NItems.NEAR_TWIG));
        addDrop(NBlocks.NEAR_HANG, dropsNothing());
    }


    public net.minecraft.loot.LootTable.Builder cinderGrassDrops(Block dropWithShears) {
        return dropsWithShears(dropWithShears, (LootPoolEntry.Builder)this.applyExplosionDecay(dropWithShears,
                ((LeafEntry.Builder)ItemEntry.builder(NItems.CINDER_SEEDS)
                        .conditionally(RandomChanceLootCondition.builder(0.125F)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 2))));
    }

    public net.minecraft.loot.LootTable.Builder faarBundle(Block drop) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop,
                ItemEntry.builder(NItems.FAAR)
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0F, 7.0F))).apply(
                        ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
                        .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.createMax(9)))));
    }

    public LootTable.Builder makeNearStemDrops(Block block, Item fruit, Item twig){
        return this.applyExplosionDecay(block, LootTable.builder()
                        .pool(LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.7f)).with(ItemEntry.builder(twig)).build())


                /*
                    Nears
                 */
                .pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create()
                                .exactMatch(SoulBerryBushBlock.AGE, 3))).with(ItemEntry.builder(fruit))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE)))
                .pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create()
                                .exactMatch(SoulBerryBushBlock.AGE, 2))).with(ItemEntry.builder(fruit))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE)))
                .pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(block).properties(net.minecraft.predicate.StatePredicate.Builder.create()
                        .exactMatch(SoulBerryBushBlock.AGE, 1))).with(ItemEntry.builder(fruit))
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))));
    }
    public LootTable.Builder makeBushDrops(Block block, Item fruit){
        return this.applyExplosionDecay(block, LootTable.builder().pool(LootPool.builder()
                .conditionally(BlockStatePropertyLootCondition.builder(NBlocks.SOUL_BERRY_BUSH).properties(net.minecraft.predicate.StatePredicate.Builder.create()
                        .exactMatch(SoulBerryBushBlock.AGE, 3))).with(ItemEntry.builder(fruit))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F)))
                .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE)))
                .pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(NBlocks.SOUL_BERRY_BUSH).properties(net.minecraft.predicate.StatePredicate.Builder.create()
                        .exactMatch(SoulBerryBushBlock.AGE, 2))).with(ItemEntry.builder(fruit))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))));
    }


}
