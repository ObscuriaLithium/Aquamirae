
package com.obscuria.aquamirae.common.items;

import com.obscuria.obscureapi.common.items.ObscureRarity;
import com.obscuria.obscureapi.util.ItemUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class RuneOfTheStormItem extends Item {
	public RuneOfTheStormItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(ObscureRarity.MYTHIC));
	}

	@SubscribeEvent
	public static void onLivingDamage(@NotNull LivingDamageEvent event) {
		final LivingEntity source = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
		if (source == null) return;
		final ItemStack weapon = source.getMainHandItem();
		if (ItemUtils.hasPerk(weapon, new ResourceLocation("aquamirae", "rune_of_the_storm"))
				&& source.level().getBiome(source.blockPosition()).value().getBaseTemperature() * 100f <= 0) {
			event.setAmount(event.getAmount() * 1.33F);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@NotNull ItemStack itemstack) {
		return true;
	}
}
