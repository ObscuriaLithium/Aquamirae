package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.bundle.ItemBundle;
import com.obscuria.core.common.item.DedicatedContent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

@DedicatedContent(owner = "PIPO")
public class AxeOfFrostfire extends SwordItem implements IAbilitable {
    public static final String DURATION = "DURATION";
    public static final Ability ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
            .setRelatedItems(ItemBundle.direct(AquamiraeItems.AXE_OF_FROSTFIRE))
            .addTier(AbilityTier.create(Component.translatable("ability.aquamirae.axe_of_frostfire"))
                    .with(DURATION, Variable.create(10).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
                    .with("2", Variable.create(10).inverted().withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS)))
            .addTier(AbilityTier.create(Component.translatable("ability.aquamirae.axe_of_frostfire"))
                    .with(DURATION, Variable.create(10).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
                    .with("2", Variable.create(10).inverted().withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
                    .requiringDust(4))
            .build();

    public AxeOfFrostfire() {
        super(AquamiraeTiers.AXE_OF_FROSTFIRE, 3, -1f, new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public Optional<Ability> getAbility(ItemStack stack) {
        return Optional.of(ABILITY);
    }
}
