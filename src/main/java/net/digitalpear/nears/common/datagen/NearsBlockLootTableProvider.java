package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.common.blocks.NearHangBlock;
import net.digitalpear.nears.common.blocks.NearHangStemBlock;
import net.digitalpear.nears.common.blocks.SoulBerryBushBlock;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class NearsBlockLootTableProvider extends FabricBlockLootSubProvider {
    private final HolderLookup.Provider registryLookup;
    
    public NearsBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
        this.registryLookup = registryLookup.join();
    }

    @Override
    public void generate() {
        LootItemCondition.Builder cropAgeConditionBuilder = LootItemBlockStatePropertyCondition
            .hasBlockStateProperties(NBlocks.CINDER_GRAIN)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));

        add(NBlocks.NEAR_HANG, createCropDrops(NBlocks.NEAR_HANG, NItems.NEAR, NItems.NEAR_SPORES, cropAgeConditionBuilder));
        
        add(NBlocks.FAAR_BUNDLE, faarBundle(NBlocks.FAAR_BUNDLE));
        add(NBlocks.FAAR_GROWTH, makeBushDrops(NBlocks.FAAR_GROWTH, NItems.FAAR_SEEDS));
        
        add(NBlocks.SOUL_BERRY_BUSH, makeBushDrops(NBlocks.SOUL_BERRY_BUSH, NItems.SOUL_BERRIES));

        add(NBlocks.CINDER_GRAIN, createCropDrops(NBlocks.CINDER_GRAIN, NItems.CINDER_GRAIN, NItems.CINDER_SEEDS, cropAgeConditionBuilder));
        add(NBlocks.CINDER_GRASS, cinderGrassDrops(NBlocks.CINDER_GRASS));
        
        
        dropSelf(NBlocks.CINDER_BALE);
        
        dropSelf(NBlocks.NEAR_TWIG_BLOCK);

        add(NBlocks.NEAR_HANG_STEM, makeNearStemDrops(NBlocks.NEAR_HANG_STEM, NItems.NEAR, NItems.NEAR_TWIG));
        add(NBlocks.NEAR_HANG, LootTable.lootTable()
                .pool(
                    LootPool
                        .lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(
                            LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(NBlocks.NEAR_HANG)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(NearHangBlock.AGE, NearHangBlock.MAX_AGE))
                        )
                        .add(this.applyExplosionDecay(
                            NItems.NEAR_SPORES,
                            LootItem
                                .lootTableItem(NItems.NEAR_SPORES)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 3F)))
                        ))
                        .build()
                )
                .pool(
                    LootPool
                        .lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(
                            LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(NBlocks.NEAR_HANG)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(NearHangBlock.AGE, NearHangBlock.MAX_AGE)).invert()
                        )
                        .add(this.applyExplosionDecay(NItems.NEAR_SPORES, LootItem.lootTableItem(NItems.NEAR_SPORES)))
                        .build()
                )
        );
    }


    public LootTable.Builder cinderGrassDrops(Block dropWithShears) {
        HolderLookup.RegistryLookup<Enchantment> impl = this.registryLookup.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createShearsDispatchTable(
            dropWithShears,
            this.applyExplosionDecay(
                dropWithShears,
                LootItem
                    .lootTableItem(NItems.CINDER_SEEDS)
                    .when(LootItemRandomChanceCondition.randomChance(0.125F))
                    .apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE), 2))
            )
        );
    }

    public LootTable.Builder faarBundle(Block drop) {
        HolderLookup.RegistryLookup<Enchantment> impl = this.registryLookup.lookupOrThrow(Registries.ENCHANTMENT);
        
        return this.createSilkTouchDispatchTable(
            drop,
            this.applyExplosionDecay(
                drop,
                LootItem
                    .lootTableItem(NItems.FAAR)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                    .apply(LimitCount.limitCount(IntRange.upperBound(9)))
            )
        );
    }

    public LootTable.Builder makeNearStemDrops(Block block, Item fruit, Item twig){
        HolderLookup.RegistryLookup<Enchantment> impl = this.registryLookup.lookupOrThrow(Registries.ENCHANTMENT);
        
        return this.applyExplosionDecay(block, LootTable.lootTable()
            //Near Twig
            .pool(
                LootPool
                    .lootPool()
                    .when(LootItemRandomChanceCondition.randomChance(0.7f))
                    .add(LootItem.lootTableItem(twig))
                    .build()
            )
            
            //Nears
            .pool(
                LootPool
                    .lootPool()
                    .when(
                        LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(block)
                            .setProperties(
                                StatePropertiesPredicate.Builder
                                    .properties()
                                    .hasProperty(SoulBerryBushBlock.AGE, 3)
                                    .hasProperty(NearHangStemBlock.SUPPORTED, true)
                            )
                    )
                    .add(LootItem.lootTableItem(fruit))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                    .build()
            )
            .pool(
                LootPool
                    .lootPool()
                    .when(
                        LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(block)
                            .setProperties(
                                StatePropertiesPredicate.Builder
                                    .properties()
                                    .hasProperty(SoulBerryBushBlock.AGE, 2)
                                    .hasProperty(NearHangStemBlock.SUPPORTED, true)
                            )
                    )
                    .add(LootItem.lootTableItem(fruit))
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                    .build()
            )
        );
    }

    public LootTable.Builder makeBushDrops(Block block, Item fruit){
	    HolderLookup.RegistryLookup<Enchantment> impl = this.registryLookup.lookupOrThrow(Registries.ENCHANTMENT);
        return this.applyExplosionDecay(
            block,
            LootTable
                .lootTable()
                .pool(
	                LootPool
                        .lootPool()
	                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3)))
	                    .add(LootItem.lootTableItem(fruit))
	                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
	                    .apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                        .build()
                )
                .pool(
                    LootPool
                        .lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(NBlocks.SOUL_BERRY_BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 2)))
                        .add(LootItem.lootTableItem(fruit))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                        .build()
                )
        );
    }
}
