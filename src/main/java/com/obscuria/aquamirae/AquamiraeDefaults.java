package com.obscuria.aquamirae;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeDefaults {

    @ApiStatus.NonExtendable
    interface TerribleArmor {
        int POISON_DURATION_TIER_1 = 3;
        int POISON_DURATION_TIER_2 = 9;
        int BOOST_STRENGTH = 150;
        int BOOST_DURATION = 10;
    }

    @ApiStatus.NonExtendable
    interface AbyssalArmor {
        int HEAUME_DEBUFF_STRENGTH = 80;
        int HEAUME_DEBUFF_DURATION = 120;
        int HEAUME_COOLDOWN = 300;
    }

    @ApiStatus.NonExtendable
    interface TerribleSword {
        int BONUS_CHANCE_TIER_1 = 50;
        int BONUS_CHANCE_TIER_2 = 50;
        int BONUS_CHANCE_TIER_3 = 50;
        int DEBUFF_CHANCE_TIER_1 = 100;
        int DEBUFF_CHANCE_TIER_2 = 100;
        int DEBUFF_CHANCE_TIER_3 = 50;
        int DEBUFF_DAMAGE_TIER_1 = 3;
        int DEBUFF_DAMAGE_TIER_2 = 2;
        int DEBUFF_DAMAGE_TIER_3 = 2;
    }

    @ApiStatus.NonExtendable
    interface Divider {
        int DURATION_TIER_1 = 6;
        int DURATION_TIER_2 = 9;
        int DURATION_TIER_3 = 12;
        int STACK_TIER_1 = 6;
        int STACK_TIER_2 = 8;
        int STACK_TIER_3 = 10;
    }

    @ApiStatus.NonExtendable
    interface WhisperOfTheAbyss {
        int DURATION_TIER_1 = 6;
        int DURATION_TIER_2 = 9;
        int DURATION_TIER_3 = 12;
        int STACK_TIER_1 = 3;
        int STACK_TIER_2 = 4;
        int STACK_TIER_3 = 5;
    }

    @ApiStatus.NonExtendable
    interface RemnantsSaber {
        int BONUS_TIER_1 = 100;
        int BONUS_TIER_2 = 140;
    }

    @ApiStatus.NonExtendable
    interface PoisonedBlade {
        int DURATION_TIER_1 = 5;
        int DURATION_TIER_2 = 10;
        int COOLDOWN_TIER_1 = 10;
        int COOLDOWN_TIER_2 = 10;
    }

    @ApiStatus.NonExtendable
    interface DaggerOfGreed {
        int CHANCE_TIER_1 = 50;
        int CHANCE_TIER_2 = 75;
        int COUNT_TIER_1 = 3;
        int COUNT_TIER_2 = 3;
    }
}
