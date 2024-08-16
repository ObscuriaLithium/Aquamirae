package com.obscuria.aquamirae.common.entity.npc;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.function.Supplier;

public interface ShadowTrade {
    MerchantOffer getOffer(ShadowMerchant merchant, RandomSource random);

    default ItemStack doubloon(RandomSource random, int min, int max) {
        final var doubloon = AquamiraeItems.DOUBLOON.get().getDefaultInstance();
        doubloon.setCount(random.nextInt(min, max + 1));
        return doubloon;
    }

    class ItemForDoubloon implements ShadowTrade {
        private final Supplier<ItemStack> result;
        private final int minCost;
        private final int maxCost;
        private final int storage;

        public static ItemForDoubloon Of(Supplier<Item> item, int count, int cost, int storage) {
            return new ItemForDoubloon(item, count, cost, cost, storage);
        }

        public static ItemForDoubloon Of(Supplier<Item> item, int count, int minCost, int maxCost, int storage) {
            return new ItemForDoubloon(item, count, minCost, maxCost, storage);
        }

        private ItemForDoubloon(Supplier<Item> item, int count, int minCost, int maxCost, int storage) {
            this.minCost = minCost;
            this.maxCost = maxCost;
            this.storage = storage;
            this.result = () -> {
                final var stack = item.get().getDefaultInstance();
                stack.setCount(count);
                return stack;
            };
        }

        @Override
        public MerchantOffer getOffer(ShadowMerchant merchant, RandomSource random) {
            return new MerchantOffer(
                    doubloon(random, minCost, maxCost), ItemStack.EMPTY, result.get(),
                    0, storage, 0, 0.2f, 0);
        }
    }

    class ServiceForDoubloon implements ShadowTrade {
        private final Supplier<ItemStack> material;
        private final Supplier<ItemStack> result;
        private final int minPrice;
        private final int maxPrice;
        private final int storage;

        public static ServiceForDoubloon Of(Supplier<Item> material, int materialCount,
                                            Supplier<Item> result, int resultCount,
                                            int minPrice, int maxPrice, int storage) {
            return new ServiceForDoubloon
                    (material, materialCount,
                            result, resultCount,
                            minPrice, maxPrice, storage);
        }

        private ServiceForDoubloon(Supplier<Item> material, int materialCount,
                                   Supplier<Item> result, int resultCount,
                                   int minPrice, int maxPrice, int storage) {
            this.material = () -> new ItemStack(material.get(), materialCount);
            this.result = () -> new ItemStack(result.get(), resultCount);
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.storage = storage;
        }

        @Override
        public MerchantOffer getOffer(ShadowMerchant merchant, RandomSource random) {
            return new MerchantOffer(
                    doubloon(random, minPrice, maxPrice), material.get(), result.get(),
                    0, storage, 0, 0.5f, 0);
        }
    }
}
