package com.obscuria.aquamirae.common.entity.npc;

import com.obscuria.aquamirae.common.entity.Maw;
import com.obscuria.aquamirae.common.entity.TorturedSoul;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ShadowMerchant extends AbstractIllager implements Npc, Merchant {
    protected @Nullable MerchantOffers offers;
    private @Nullable Player tradingPlayer;

    public ShadowMerchant(EntityType<? extends AbstractIllager> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new ShadowMerchant.HideIfAttackedGoal(this));
        this.goalSelector.addGoal(0, new ShadowMerchant.EatBreadGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Maw.class, 10.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, TorturedSoul.class, 10.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.5D));
        this.goalSelector.addGoal(1, new ShadowMerchant.LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(2, new ShadowMerchant.TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public boolean canTrade() {
        return this.isAlive() && !this.isInWater() && this.onGround() && !this.hurtMarked;
    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    public void stopTrading() {
        this.setTradingPlayer(null);
    }

    protected SoundEvent getTradeUpdatedSound(boolean success) {
        return success ? SoundEvents.VINDICATOR_CELEBRATE : SoundEvents.VINDICATOR_AMBIENT;
    }

    @Override
    @SuppressWarnings("all")
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.canTrade() && !this.isTrading()) {
            if (hand == InteractionHand.MAIN_HAND)
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            if (!this.getOffers().isEmpty() && !this.isClientSide()) {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        final var offers = this.getOffers();
        if (!offers.isEmpty())
            tag.put("Offers", offers.createTag());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Offers", 10))
            this.offers = new MerchantOffers(tag.getCompound("Offers"));
    }

    @Override
    public @Nullable Entity changeDimension(ServerLevel level, ITeleporter teleporter) {
        this.stopTrading();
        return super.changeDimension(level, teleporter);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        final var result = super.hurt(source, amount);
        if (result) this.hurtMarked = true;
        return result;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        this.stopTrading();
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            ShadowTrades.DEFAULT.apply(this, offers);
        }

        return this.offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        if (tradingPlayer == null) return;
        this.level().playSound(null, tradingPlayer,
                SoundEvents.EXPERIENCE_ORB_PICKUP,
                SoundSource.PLAYERS, 1f,
                (float) this.random.triangle(1, 0.1));
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
        if (!this.isClientSide() && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.playSound(this.getTradeUpdatedSound(!stack.isEmpty()), this.getSoundVolume(),
                    (float) this.random.triangle(0.9, 0.1));
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VINDICATOR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_34103_) {
        return SoundEvents.VINDICATOR_HURT;
    }

    @Override
    public void applyRaidBuffs(int size, boolean flag) {
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Override
    public @Nullable Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int amount) {
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }

    public static class HideIfAttackedGoal extends Goal {
        private final ShadowMerchant merchant;
        private final ItemStack potion;
        private final ItemStack milk;
        private int cooldown;

        public HideIfAttackedGoal(ShadowMerchant merchant) {
            this.merchant = merchant;
            this.potion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY);
            this.milk = Items.MILK_BUCKET.getDefaultInstance();
        }

        @Override
        public boolean canUse() {
            if (--this.cooldown > 0) return false;
            return this.shouldDisappear() || this.shouldAppear();
        }

        @Override
        public boolean canContinueToUse() {
            return this.merchant.isUsingItem();
        }

        @Override
        public void start() {
            if (this.shouldDisappear())
                this.merchant.setItemSlot(EquipmentSlot.MAINHAND, this.potion.copy());
            if (this.shouldAppear())
                this.merchant.setItemSlot(EquipmentSlot.MAINHAND, this.milk.copy());
            this.merchant.startUsingItem(InteractionHand.MAIN_HAND);
        }

        @Override
        public void stop() {
            final var isAppears = this.isAppears();
            this.cooldown = isAppears ? 20 : 200;
            this.merchant.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.merchant.playSound(isAppears
                            ? SoundEvents.VINDICATOR_CELEBRATE
                            : SoundEvents.VINDICATOR_AMBIENT,
                    1.0F, this.merchant.getRandom().nextFloat() * 0.1F + 0.8F);
        }


        private boolean shouldDisappear() {
            return this.merchant.getLastHurtByMob() != null && !this.merchant.isInvisible();
        }

        private boolean shouldAppear() {
            return this.merchant.getLastHurtByMob() == null && this.merchant.isInvisible();
        }

        private boolean isAppears() {
            return this.merchant.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.MILK_BUCKET);
        }
    }

    public static class EatBreadGoal extends UseItemGoal<ShadowMerchant> {
        private final ShadowMerchant merchant;
        private int cooldown;

        public EatBreadGoal(ShadowMerchant merchant) {
            super(merchant, Items.BREAD.getDefaultInstance(), SoundEvents.PLAYER_BURP,
                    m -> m.level().isNight() && !m.isTrading());
            this.merchant = merchant;
        }

        @Override
        public boolean canUse() {
            if (--this.cooldown > 0) return false;
            return super.canUse();
        }

        @Override
        public void stop() {
            this.cooldown = this.merchant.random.nextInt(20, 400);
            super.stop();
        }
    }

    public static class TradeWithPlayerGoal extends Goal {
        private final ShadowMerchant merchant;

        public TradeWithPlayerGoal(ShadowMerchant merchant) {
            this.merchant = merchant;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        @SuppressWarnings("all")
        public boolean canUse() {
            final var player = this.merchant.getTradingPlayer();
            if (player == null) return false;
            if (!this.merchant.canTrade()) return stopAndFalse();
            if (this.merchant.distanceToSqr(player) > 32.0D) return stopAndFalse();
            if (player.containerMenu == null) return stopAndFalse();
            return true;
        }

        @Override
        public void start() {
            this.merchant.getNavigation().stop();
        }

        @Override
        public void stop() {
            this.merchant.stopTrading();
        }

        private boolean stopAndFalse() {
            this.merchant.stopTrading();
            return false;
        }
    }

    public static class LookAtTradingPlayerGoal extends LookAtPlayerGoal {
        private final ShadowMerchant merchant;

        public LookAtTradingPlayerGoal(ShadowMerchant merchant) {
            super(merchant, Player.class, 8.0F);
            this.merchant = merchant;
        }

        @Override
        public boolean canUse() {
            if (this.merchant.isTrading()) {
                this.lookAt = this.merchant.getTradingPlayer();
                return true;
            } else {
                return false;
            }
        }
    }
}
