package com.obscuria.aquamirae.compat;

import com.obscuria.core.network.ModHook;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeCompats {
    ModHook CURIOS_API = ModHook.create("curios", "Curios API");
    ModHook BETTER_COMBAT = ModHook.create("better_combat", "Better Combat");
}
