package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


@SuppressWarnings("unused")
public class NearsAdvancementProvider extends FabricAdvancementProvider {
    public NearsAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        HolderLookup.RegistryLookup<Item> itemRegistry = registryLookup.lookupOrThrow(Registries.ITEM);

        AdvancementHolder dummy = AdvancementSubProvider.createPlaceholder("nether/root");
        AdvancementHolder symbiotic = Advancement.Builder
            .advancement()
            .parent(dummy)
            .display(
                NItems.NEAR,
                Component.translatable("advancements.nether.symbiotic.title"),
                Component.translatable("advancements.nether.symbiotic.description"),
                null, // children to parent advancements don't need a background set
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .rewards(AdvancementRewards.Builder.experience(2))
            .addCriterion("got_nether_fruit", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemRegistry, NItemTags.NETHER_FRUITS).build()))
            .save(consumer, Nears.MOD_ID + ":nether/symbiotic");

        AdvancementHolder ohHowFaarWeGo = Advancement.Builder
            .advancement()
            .parent(symbiotic)
            .display(
                NItems.FAAR,
                Component.translatable("advancements.nether.oh_how_faar_we_go.title"),
                Component.translatable("advancements.nether.oh_how_faar_we_go.description"),
                null, // children to parent advancements don't need a background set
                AdvancementType.CHALLENGE,
                true,
                true,
                false
            )
            .rewards(AdvancementRewards.Builder.experience(69))
            .addCriterion("get_near", InventoryChangeTrigger.TriggerInstance.hasItems(NItems.NEAR))
            .addCriterion("get_faar", InventoryChangeTrigger.TriggerInstance.hasItems(NItems.FAAR))
            .addCriterion("get_soul_berries", InventoryChangeTrigger.TriggerInstance.hasItems(NItems.SOUL_BERRIES))
            .save(consumer, Nears.MOD_ID + ":nether/oh_how_faar_we_go");
        
        AdvancementHolder aPieForTheSoul = makeItemBasedAdvancement(
            consumer,
            "a_pie_for_the_soul",
            NItems.SOULLESS_PASTRY,
            AdvancementType.TASK,
            InventoryChangeTrigger.TriggerInstance.hasItems(NItems.SOULLESS_PASTRY),
            "get_soulless_pastry",
            2, symbiotic, false
        );
        
        AdvancementHolder volcanicBotany = makeAdvancement(
            consumer,
            "volcanic_botany",
            NItems.CINDER_SEEDS,
            AdvancementType.TASK,
            ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(NBlocks.CINDER_GRAIN),
            "cinder_seeds",
            2, dummy
        );
    }

    public AdvancementHolder makeItemBasedAdvancement(Consumer<AdvancementHolder> consumer, String name, Item icon, AdvancementType frame, Criterion<InventoryChangeTrigger.TriggerInstance> conditions, String criterionNames, int reward, AdvancementHolder parent, boolean hidden){
        return Advancement.Builder
            .advancement()
            .parent(parent)
            .display(
                icon,
                Component.translatable("advancements.nether." + name + ".title"),
                Component.translatable("advancements.nether." + name + ".description"),
                null,
                frame,
                true,
                true,
                hidden
            )
            .rewards(AdvancementRewards.Builder.experience(reward))
            .addCriterion(criterionNames, conditions)
            .save(consumer, Nears.MOD_ID + ":nether/" + name);
    }


    public AdvancementHolder makeAdvancement(Consumer<AdvancementHolder> consumer, String name, Item icon, AdvancementType frame, Criterion<ItemUsedOnLocationTrigger.TriggerInstance> conditions, String criterionNames, int reward, AdvancementHolder parent, boolean hidden){
        return Advancement.Builder
            .advancement()
            .parent(parent)
            .display(
                icon,
                Component.translatable("advancements.nether." + name + ".title"),
                Component.translatable("advancements.nether." + name + ".description"),
                null,
                frame,
                true,
                true,
                hidden
            )
            .rewards(AdvancementRewards.Builder.experience(reward))
            .addCriterion(criterionNames, conditions)
            .save(consumer, Nears.MOD_ID + ":nether/" + name);
    }
    
    public AdvancementHolder makeAdvancement(Consumer<AdvancementHolder> consumer, String name, Item icon, AdvancementType frame, Criterion<ItemUsedOnLocationTrigger.TriggerInstance> conditions, String criterionNames, int reward, AdvancementHolder parent){
        return makeAdvancement(consumer, name, icon, frame, conditions, criterionNames, reward, parent, false);
    }
}