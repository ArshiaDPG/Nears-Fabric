package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


@SuppressWarnings("unused")
public class NearsAdvancementProvider extends FabricAdvancementProvider {


    public NearsAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry dummy = Advancement.Builder.create().display(Blocks.RED_NETHER_BRICKS, Text.translatable("advancements.nether.root.title"), Text.translatable("advancements.nether.root.description"), Identifier.of("textures/gui/advancements/backgrounds/nether.png"), AdvancementFrame.TASK, false, false, false).criterion("entered_nether", ChangedDimensionCriterion.Conditions.to(World.NETHER)).build(consumer, "nether/root");


        AdvancementEntry symbiotic = Advancement.Builder.create().parent(dummy)
                .display(
                        NItems.NEAR,
                        Text.translatable("advancements.nether.symbiotic.title"),
                        Text.translatable("advancements.nether.symbiotic.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(2))
                .criterion("got_nether_fruit", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(NItemTags.NETHER_FRUITS).build()))
                .build(consumer, Nears.MOD_ID + ":nether/symbiotic");

        AdvancementEntry ohHowFaarWeGo = Advancement.Builder.create().parent(symbiotic)
                .display(
                        NItems.FAAR,
                        Text.translatable("advancements.nether.oh_how_faar_we_go.title"),
                        Text.translatable("advancements.nether.oh_how_faar_we_go.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(69))
                .criterion("get_near", InventoryChangedCriterion.Conditions.items(NItems.NEAR))
                .criterion("get_faar", InventoryChangedCriterion.Conditions.items(NItems.FAAR))
                .criterion("get_soul_berries", InventoryChangedCriterion.Conditions.items(NItems.SOUL_BERRIES))
                .build(consumer,  Nears.MOD_ID + ":nether/oh_how_faar_we_go");

        AdvancementEntry aPieForTheSoul = makeItemBasedAdvancement(consumer,
                "a_pie_for_the_soul",
                NItems.SOULLESS_PASTRY,
                AdvancementFrame.TASK,
                InventoryChangedCriterion.Conditions.items(NItems.SOULLESS_PASTRY),
                "get_soulless_pastry",
                2, symbiotic, false);

        AdvancementEntry volcanicBotany = makeAdvancement(consumer,
                "volcanic_botany",
                NItems.CINDER_SEEDS,
                AdvancementFrame.TASK,
                ItemCriterion.Conditions.createPlacedBlock(NBlocks.CINDER_GRAIN),
                "cinder_seeds",
                2, dummy);
    }

    public AdvancementEntry makeItemBasedAdvancement(Consumer<AdvancementEntry> consumer, String name, Item icon, AdvancementFrame frame, AdvancementCriterion<InventoryChangedCriterion.Conditions> conditions, String criterionNames, int reward, AdvancementEntry parent, boolean hidden){
        return Advancement.Builder.create().parent(parent)
                .display(
                        icon,
                        Text.translatable("advancements.nether." + name + ".title"),
                        Text.translatable("advancements.nether." + name + ".description"),
                        null,
                        frame,
                        true,
                        true,
                        hidden
                )
                .rewards(AdvancementRewards.Builder.experience(reward))
                .criterion(criterionNames, conditions)
                .build(consumer, Nears.MOD_ID + ":nether/" + name);
    }


    public AdvancementEntry makeAdvancement(Consumer<AdvancementEntry> consumer, String name, Item icon, AdvancementFrame frame, AdvancementCriterion<ItemCriterion.Conditions> conditions, String criterionNames, int reward, AdvancementEntry parent, boolean hidden){
        return Advancement.Builder.create().parent(parent)
                .display(
                        icon,
                        Text.translatable("advancements.nether." + name + ".title"),
                        Text.translatable("advancements.nether." + name + ".description"),
                        null,
                        frame,
                        true,
                        true,
                        hidden
                )
                .rewards(AdvancementRewards.Builder.experience(reward))
                .criterion(criterionNames, conditions)
                .build(consumer, Nears.MOD_ID + ":nether/" + name);
    }
    public AdvancementEntry makeAdvancement(Consumer<AdvancementEntry> consumer, String name, Item icon, AdvancementFrame frame, AdvancementCriterion<ItemCriterion.Conditions> conditions, String criterionNames, int reward, AdvancementEntry parent){
        return makeAdvancement(consumer, name, icon, frame, conditions, criterionNames, reward, parent, false);
    }


}