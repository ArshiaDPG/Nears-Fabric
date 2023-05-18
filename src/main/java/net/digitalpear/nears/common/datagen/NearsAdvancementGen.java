package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.function.Consumer;


@SuppressWarnings("unused")
public class NearsAdvancementGen extends FabricAdvancementProvider {

    public NearsAdvancementGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {


        Advancement dummy = Advancement.Builder.create().display(Blocks.RED_NETHER_BRICKS, Text.translatable("advancements.nether.root.title"), Text.translatable("advancements.nether.root.description"), new Identifier("textures/gui/advancements/backgrounds/nether.png"), AdvancementFrame.TASK, false, false, false).criterion("entered_nether", ChangedDimensionCriterion.Conditions.to(World.NETHER)).build(consumer, "nether/root");
        Advancement symbiotic = Advancement.Builder.create().parent(dummy)
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

        Advancement ohHowFaarWeGo = Advancement.Builder.create().parent(symbiotic)
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

        Advancement aPieForTheSoul = Advancement.Builder.create().parent(symbiotic)
                .display(
                        NItems.SOULLESS_PASTRY,
                        Text.translatable("advancements.nether.a_pie_for_the_soul.title"),
                        Text.translatable("advancements.nether.a_pie_for_the_soul.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(2))
                .criterion("get_soulless_pastry", InventoryChangedCriterion.Conditions.items(NItems.SOULLESS_PASTRY))
                .build(consumer,  Nears.MOD_ID + ":nether/a_pie_for_the_soul");

    }
}
