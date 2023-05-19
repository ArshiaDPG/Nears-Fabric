package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.SoulBerryBushBlock;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class NearsBlockLootTables extends FabricBlockLootTableProvider {


    public NearsBlockLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        LootCondition.Builder cropAgeConditionBuilder = BlockStatePropertyLootCondition.builder(NBlocks.CINDER_WHEAT)
                .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(CropBlock.AGE, 7));

        makeLoot(biConsumer, NBlocks.NEAR_BULB, cropDrops(NBlocks.NEAR_BULB, NItems.NEAR, NItems.NEAR_SEEDS, cropAgeConditionBuilder));

        makeLoot(biConsumer, NBlocks.FAAR_BUNDLE, faarBundle(NBlocks.FAAR_BUNDLE));
        makeLoot(biConsumer, NBlocks.FAAR_GROWTH, makeBushDrops(NBlocks.FAAR_GROWTH, NItems.FAAR_SEEDS));

        makeLoot(biConsumer, NBlocks.SOUL_BERRY_BUSH, makeBushDrops(NBlocks.SOUL_BERRY_BUSH, NItems.SOUL_BERRIES));

        makeLoot(biConsumer, NBlocks.CINDER_WHEAT, cropDrops(NBlocks.CINDER_WHEAT, NItems.CINDER_GRAIN, NItems.CINDER_SEEDS, cropAgeConditionBuilder));
        makeLoot(biConsumer, NBlocks.CINDER_GRASS, cinderGrassDrops(NBlocks.CINDER_GRASS));

    }


    public void makeLoot(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block, LootTable.Builder builder){
        biConsumer.accept(new Identifier(Nears.MOD_ID, Registries.BLOCK.getId(block).getPath()).withPrefixedPath("blocks/"), builder);
    }

    public net.minecraft.loot.LootTable.Builder cinderGrassDrops(Block dropWithShears) {
        return dropsWithShears(dropWithShears, (net.minecraft.loot.entry.LootPoolEntry.Builder)this.applyExplosionDecay(dropWithShears,
                ((net.minecraft.loot.entry.LeafEntry.Builder)ItemEntry.builder(NItems.CINDER_SEEDS)
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

    public LootTable.Builder makeBushDrops(Block block, Item fruit){
        return this.applyExplosionDecay(block, LootTable.builder().pool(LootPool.builder()
                .conditionally(BlockStatePropertyLootCondition.builder(NBlocks.SOUL_BERRY_BUSH).properties(net.minecraft.predicate.StatePredicate.Builder.create()
                        .exactMatch(SoulBerryBushBlock.AGE, 3))).with(ItemEntry.builder(fruit))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F)))
                .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))).pool(LootPool.builder()
                .conditionally(BlockStatePropertyLootCondition.builder(NBlocks.SOUL_BERRY_BUSH).properties(net.minecraft.predicate.StatePredicate.Builder.create()
                        .exactMatch(SoulBerryBushBlock.AGE, 2))).with(ItemEntry.builder(fruit))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))));
    }


}
