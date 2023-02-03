package com.obscuria.aquamirae.client.animations;

import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.animations.AnimatedModel;
import com.obscuria.obscureapi.api.animations.Animation;
import com.obscuria.obscureapi.api.animations.Bone;
import com.obscuria.obscureapi.api.animations.KeyFrame;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class CaptainCorneliaAnimations extends AnimatedModel {
    public final Bone MAIN = Bone.create();
    public final Bone HEAD = Bone.create();
    public final Bone BODY_TOP = Bone.create();
    public final Bone BODY_TOP_2 = Bone.create();
    public final Bone BODY_BOTTOM = Bone.create();
    public final Bone LEFT_ARM = Bone.create();
    public final Bone LEFT_ARM_BOTTOM = Bone.create();
    public final Bone RIGHT_ARM = Bone.create();
    public final Bone RIGHT_ARM_BOTTOM = Bone.create();
    public final Bone WEAPON = Bone.create();
    public final Bone LEFT_LEG = Bone.create();
    public final Bone LEFT_LEG_BOTTOM = Bone.create();
    public final Bone RIGHT_LEG = Bone.create();
    public final Bone RIGHT_LEG_BOTTOM = Bone.create();
    public final Bone TENTACLE_1 = Bone.create();
    public final Bone TENTACLE_1_1 = Bone.create();
    public final Bone TENTACLE_1_2 = Bone.create();
    public final Bone TENTACLE_1_3 = Bone.create();
    public final Bone TENTACLE_1_4 = Bone.create();
    public final Bone TENTACLE_2 = Bone.create();
    public final Bone TENTACLE_2_1 = Bone.create();
    public final Bone TENTACLE_2_2 = Bone.create();
    public final Bone TENTACLE_2_3 = Bone.create();
    public final Bone TENTACLE_2_4 = Bone.create();
    public final Bone TENTACLE_3 = Bone.create();
    public final Bone TENTACLE_3_1 = Bone.create();
    public final Bone TENTACLE_3_2 = Bone.create();
    public final Bone TENTACLE_3_3 = Bone.create();
    public final Bone TENTACLE_3_4 = Bone.create();

    private static final float IDLE_SPEED = 0.1F;
    public final Animation.Idle IDLE = Animation.Idle.create().pose(0.2f,
            KeyFrame.create(MAIN).rotation(0.6F, 1.2F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0F),
            KeyFrame.create(BODY_BOTTOM).rotation(15F, 20F, 0F, 0F, 0F, 0F, IDLE_SPEED, -0.9F),
            KeyFrame.create(BODY_TOP_2).rotation(6F, -6F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.9F),
            KeyFrame.create(HEAD).rotation(4F, -6F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.8F),
            KeyFrame.create(RIGHT_ARM).rotation(12F, -9F, 9F, -27F, 6F, 20F, IDLE_SPEED, -0.95F),
            KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(-16F, 20F, 0F, 0F, 0F, 0F, IDLE_SPEED, -0.9F),
            KeyFrame.create(WEAPON).rotation(-5F, -35F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.85F),
            KeyFrame.create(LEFT_ARM).rotation(12F, -9F, -9F, 27F, -9F, -27F, IDLE_SPEED, -0.95F),
            KeyFrame.create(LEFT_ARM_BOTTOM).rotation(-24F, 30F, 0F, 0F, 0F, 0F, IDLE_SPEED, -0.9F),
            KeyFrame.create(RIGHT_LEG).rotation(12F, 24F, 0.6F, -3F, 0.6F, -3F, IDLE_SPEED, -0.8F),
            KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-12F, -48F, 0F, 0F, 0F, 0F, IDLE_SPEED, -0.6F),
            KeyFrame.create(LEFT_LEG).rotation(6F, -12F, 0.6F, 3F, 0.6F, 3F, IDLE_SPEED, -0.8F),
            KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-24F, -27F, 0F, 0F, 0F, 0F, IDLE_SPEED, -0.7F),
            KeyFrame.create(TENTACLE_1).rotation(16F, -20F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.35F),
            KeyFrame.create(TENTACLE_1_1).rotation(16F, -12F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.3F),
            KeyFrame.create(TENTACLE_1_2).rotation(16F, -6F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.25F),
            KeyFrame.create(TENTACLE_1_3).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.2F),
            KeyFrame.create(TENTACLE_1_4).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.15F),
            KeyFrame.create(TENTACLE_2).rotation(16F, -20F, 0F, -29F, 0F, 0F, IDLE_SPEED, 0.25F),
            KeyFrame.create(TENTACLE_2_1).rotation(16F, -12F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.2F),
            KeyFrame.create(TENTACLE_2_2).rotation(16F, -6F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.15F),
            KeyFrame.create(TENTACLE_2_3).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.1F),
            KeyFrame.create(TENTACLE_2_4).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.05F),
            KeyFrame.create(TENTACLE_3).rotation(16F, -20F, 0F, 29F, 0F, 0F, IDLE_SPEED, 0.45F),
            KeyFrame.create(TENTACLE_3_1).rotation(16F, -12F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.4F),
            KeyFrame.create(TENTACLE_3_2).rotation(16F, -6F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.35F),
            KeyFrame.create(TENTACLE_3_3).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.3F),
            KeyFrame.create(TENTACLE_3_4).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.25F));

    private static final float MOVE_SPEED = 0.4F;
    public final Animation.Move MOVE = Animation.Move.create().pose(0.2f,
            KeyFrame.create(MAIN).rotation(-1.4F, 1.8F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0F),
            KeyFrame.create(BODY_BOTTOM).rotation(6F, -36F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0F),
            KeyFrame.create(BODY_TOP).rotation(6F, -24F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0F),
            KeyFrame.create(BODY_TOP_2).rotation(6F, 12F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0F),
            KeyFrame.create(RIGHT_ARM).rotation(12F, -9F, 9F, -27F, -9F, 27F, MOVE_SPEED, 0.05F),
            KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(24F, 30F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0F),
            KeyFrame.create(LEFT_ARM).rotation(12F, -9F, -9F, 27F, 9F, -27F, MOVE_SPEED, 0.05F),
            KeyFrame.create(LEFT_ARM_BOTTOM).rotation(24F, 30F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0F),
            KeyFrame.create(RIGHT_LEG).rotation(24F, 12F, 0.6F, -6F, 0.6F, -6F, MOVE_SPEED, 0.5F),
            KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(24F, -74F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.7F),
            KeyFrame.create(LEFT_LEG).rotation(24F, 0F, -0.6F, 6F, -0.6F, 6F, MOVE_SPEED, 0.5F),
            KeyFrame.create(LEFT_LEG_BOTTOM).rotation(24F, -30F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.8F),
            KeyFrame.create(TENTACLE_1).rotation(-14F, -29F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.25F),
            KeyFrame.create(TENTACLE_1_1).rotation(-14F, -18F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.2F),
            KeyFrame.create(TENTACLE_1_2).rotation(-14F, -12F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.15F),
            KeyFrame.create(TENTACLE_1_3).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.1F),
            KeyFrame.create(TENTACLE_1_4).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.05F),
            KeyFrame.create(TENTACLE_2).rotation(-14F, -29F, 0F, -29F, 0F, 0F, MOVE_SPEED, 0.25F),
            KeyFrame.create(TENTACLE_2_1).rotation(-14F, -18F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.2F),
            KeyFrame.create(TENTACLE_2_2).rotation(-14F, -12F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.15F),
            KeyFrame.create(TENTACLE_2_3).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.1F),
            KeyFrame.create(TENTACLE_2_4).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.05F),
            KeyFrame.create(TENTACLE_3).rotation(-14F, -29F, 0F, 29F, 0F, 0F, MOVE_SPEED, 0.25F),
            KeyFrame.create(TENTACLE_3_1).rotation(-14F, -18F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.2F),
            KeyFrame.create(TENTACLE_3_2).rotation(-14F, -12F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.15F),
            KeyFrame.create(TENTACLE_3_3).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.1F),
            KeyFrame.create(TENTACLE_3_4).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.05F));

    public final Animation.Death DEATH = Animation.Death.create()
            .pose(40, 28, 0.3F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(2.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(-14F, 42F, -2F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, 25F, 0F),
                    KeyFrame.create(HEAD).rotation(3F, -56F, -8F),
                    KeyFrame.create(LEFT_ARM).rotation(-21F, 12F, -18.5F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(70F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(57F, 25F, 74F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(75F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(35F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(25F, 0F, 0F),
                    KeyFrame.create(RIGHT_LEG).rotation(57F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-90F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F))
            .pose(28, 0, 0.3F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(1.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(-10F, -4F, 8F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, -20F, 0F),
                    KeyFrame.create(HEAD).rotation(2F, 24F, -4.5F),
                    KeyFrame.create(LEFT_ARM).rotation(-50F, 30F, -35F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(100F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(35F, 12F, 47F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(10F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-82F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(-32F, 2F, -4F),
                    KeyFrame.create(RIGHT_LEG).rotation(57F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-90F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F));

    public final Animation ATTACK = Animation.create("attack")
            .sound(40, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_1, SoundSource.HOSTILE, 2f, 1f)
            .sound(20, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_2, SoundSource.HOSTILE, 2f, 1f)
            .pose(60, 50, 0.25f, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(2F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(0F, -17.5F, 0F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, -32.5F, 0F),
                    KeyFrame.create(HEAD).rotation(-10F, 46F, -3.5F),
                    KeyFrame.create(LEFT_ARM).rotation(30F, 35F, -32F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(25F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(5.5F, 28F, 19.5F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(25F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-50F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(27.5F, 0F, 0F),
                    KeyFrame.create(RIGHT_LEG).rotation(25F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-60F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F))
            .pose(50, 40, 0.5F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(1.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(0F, -7.5F, 0F),
                    KeyFrame.create(BODY_TOP_2).rotation(10F, -15F, 0F),
                    KeyFrame.create(HEAD).rotation(-15F, 27F, 0F),
                    KeyFrame.create(LEFT_ARM).rotation(-19F, 12.5F, -31F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(105F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(-10F, 1F, 20F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(52F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-50F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(-22.5F, 0F, 0F),
                    KeyFrame.create(RIGHT_LEG).rotation(25F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-70F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F))
            .pose(40, 28, 0.2F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(2.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(-14F, 42F, -2F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, 25F, 0F),
                    KeyFrame.create(HEAD).rotation(3F, -56F, -8F),
                    KeyFrame.create(LEFT_ARM).rotation(-21F, 12F, -18.5F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(70F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(57F, 25F, 74F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(75F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-35F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(25F, 0F, 0F),
                    KeyFrame.create(RIGHT_LEG).rotation(57F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-90F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F))
            .pose(28, 20, 0.75F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(1.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(-10F, -4F, 8F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, -20F, 0F),
                    KeyFrame.create(HEAD).rotation(2F, 24F, -4.5F),
                    KeyFrame.create(LEFT_ARM).rotation(-50F, 30F, -35F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(100F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(35F, 12F, 47F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(10F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-82F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(-32F, 2F, -4F),
                    KeyFrame.create(RIGHT_LEG).rotation(57F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-90F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F))
            .pose(20, 8, 0.2F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(2.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(0F, -17.5F, 0F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, -27.5F, 0F),
                    KeyFrame.create(HEAD).rotation(-22F, 42F, -9F),
                    KeyFrame.create(LEFT_ARM).rotation(33.5F, 12F, -18.5F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(67F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(-48F, -10.5F, 57F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(75F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-45F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(25F, 0F, 0F),
                    KeyFrame.create(RIGHT_LEG).rotation(32.4F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-90F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(0F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-38F, 0F, 0F))
            .pose(8, 0, 0.75F, Animation.Mode.CUBIC,
                    KeyFrame.create(MAIN).rotation(1.5F, 0F, 0F),
                    KeyFrame.create(BODY_TOP).rotation(-12.5F, 35F, 0F),
                    KeyFrame.create(BODY_TOP_2).rotation(0F, 7.5F, 0F),
                    KeyFrame.create(HEAD).rotation(1F, -38F, 0F),
                    KeyFrame.create(LEFT_ARM).rotation(-21.5F, 12F, -18.5F),
                    KeyFrame.create(LEFT_ARM_BOTTOM).rotation(70F, 0F, 0F),
                    KeyFrame.create(RIGHT_ARM).rotation(24F, -10.5F, 57F),
                    KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(12.5F, 0F, 0F),
                    KeyFrame.create(WEAPON).rotation(-60F, 0F, 0F),
                    KeyFrame.create(BODY_BOTTOM).rotation(-28F, 10F, -2F),
                    KeyFrame.create(RIGHT_LEG).rotation(57F, 2.6F, -4.2F),
                    KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(-90F, 0F, 0F),
                    KeyFrame.create(LEFT_LEG).rotation(-2.5F, 0F, 2.5F),
                    KeyFrame.create(LEFT_LEG_BOTTOM).rotation(-17.5F, 0F, 0F));

    @Nullable
    @Override
    protected Animation.Idle getIdleAnimation(Entity entity) {
        return this.IDLE;
    }

    @Nullable
    @Override
    protected Animation.Move getMoveAnimation(Entity entity) {
        return this.MOVE;
    }

    @Nullable
    @Override
    protected Animation.Death getDeathAnimation(Entity entity) {
        return this.DEATH;
    }

    public CaptainCorneliaAnimations(Entity entity) {
        super(entity);
    }
}
