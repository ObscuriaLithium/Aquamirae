package com.obscuria.aquamirae.client.animations;

import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.animations.hekate.Animation;
import com.obscuria.obscureapi.api.animations.hekate.Animations;
import com.obscuria.obscureapi.api.animations.hekate.Functions;
import com.obscuria.obscureapi.api.animations.hekate.KeyFrame;
import net.minecraft.sounds.SoundSource;

public class CaptainCorneliaAnimations extends Animations<CaptainCornelia> {
    public final String MAIN = "main", HEAD = "head", BODY_TOP = "bodyTop", BODY_TOP_2 = "bodyTop2", BODY_BOTTOM = "bodyBottom", LEFT_ARM = "leftArm",
            LEFT_ARM_BOTTOM = "leftArmBottom", RIGHT_ARM = "rightArm", RIGHT_ARM_BOTTOM = "rightArmBottom", WEAPON = "weapon", LEFT_LEG = "leftLeg",
            LEFT_LEG_BOTTOM = "leftLegBottom", RIGHT_LEG = "rightLeg", RIGHT_LEG_BOTTOM = "rightLegBottom", TENTACLE_1 = "ten1", TENTACLE_1_1 = "ten1_1",
            TENTACLE_1_2 = "ten1_2", TENTACLE_1_3 = "ten1_3", TENTACLE_1_4 = "ten1_4", TENTACLE_2 = "ten2", TENTACLE_2_1 = "ten2_1", TENTACLE_2_2 = "ten2_2",
            TENTACLE_2_3 = "ten2_3", TENTACLE_2_4 = "ten2_4", TENTACLE_3 = "ten3", TENTACLE_3_1 = "ten3_1", TENTACLE_3_2 = "ten3_2", TENTACLE_3_3 = "ten3_3",
            TENTACLE_3_4 = "ten3_4";
    private static final float IDLE_SPEED = 0.1F;
    private static final float MOVE_SPEED = 0.4F;
    public final Animation.Idle IDLE;
    public final Animation.Move MOVE;
    public final Animation.Death DEATH;
    public final Animation ATTACK;

    public CaptainCorneliaAnimations(CaptainCornelia cornelia) {
        super(cornelia);
        this.defineBones(MAIN, HEAD, BODY_TOP, BODY_TOP_2, BODY_BOTTOM, LEFT_ARM, LEFT_ARM_BOTTOM, RIGHT_ARM, RIGHT_ARM_BOTTOM, WEAPON, LEFT_LEG, LEFT_LEG_BOTTOM,
                RIGHT_LEG, RIGHT_LEG_BOTTOM, TENTACLE_1, TENTACLE_1_1, TENTACLE_1_2, TENTACLE_1_3, TENTACLE_1_4, TENTACLE_2, TENTACLE_2_1, TENTACLE_2_2, TENTACLE_2_3,
                TENTACLE_2_4, TENTACLE_3, TENTACLE_3_1, TENTACLE_3_2, TENTACLE_3_3, TENTACLE_3_4);
        this.IDLE = Animation.Idle.create("idle")
                .pose(0, 100, Functions.LINEAR, 1f,
                        KeyFrame.create(MAIN).rotation(0.6F, 1.2F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0F),
                        KeyFrame.create(BODY_BOTTOM).rotation(15F, 20F, 0F, 0F, 0F, 0F, IDLE_SPEED, -0.9F),
                        KeyFrame.create(BODY_TOP).rotation(0f, 0f, 0f),
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
                        KeyFrame.create(TENTACLE_3_4).rotation(16F, 0F, 0F, 0F, 0F, 0F, IDLE_SPEED, 0.25F))
                .initialPose(this)
                .getAsIdle();
        this.MOVE = Animation.Move.create("move")
                .pose(0, 100, Functions.LINEAR,1f,
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
                        KeyFrame.create(TENTACLE_3_4).rotation(-14F, -6F, 0F, 0F, 0F, 0F, MOVE_SPEED, 0.05F))
                .getAsMove();
        this.DEATH = Animation.Death.create("death", 0.2f)
                .pose(0, 12, Functions.REVERSED_CUBIC, 1f,
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
                .pose(12, 40, Functions.REVERSED_QUINT, 2f,
                        KeyFrame.create(MAIN).rotation(-5F, 0F, 0F),
                        KeyFrame.create(BODY_TOP).rotation(-82.5F, 0F, 0F),
                        KeyFrame.create(BODY_TOP_2).rotation(12.5F, 0F, 0F),
                        KeyFrame.create(HEAD).rotation(-27F, 34F, -16F),
                        KeyFrame.create(LEFT_ARM).rotation(47F, 55F, 5F),
                        KeyFrame.create(LEFT_ARM_BOTTOM).rotation(82F, 0F, 0F),
                        KeyFrame.create(RIGHT_ARM).rotation(37F, -56F, 22F),
                        KeyFrame.create(RIGHT_ARM_BOTTOM).rotation(62F, 0F, 0F),
                        KeyFrame.create(WEAPON).rotation(-40F, 0F, 0F),
                        KeyFrame.create(BODY_BOTTOM).rotation(-85F, -5F, 0F),
                        KeyFrame.create(RIGHT_LEG).rotation(0F, 0F, -5F),
                        KeyFrame.create(RIGHT_LEG_BOTTOM).rotation(0F, 0F, 0F),
                        KeyFrame.create(LEFT_LEG).rotation(0F, 0F, 2.5F),
                        KeyFrame.create(LEFT_LEG_BOTTOM).rotation(0F, 0F, 0F))
                .getAsDeath();
        this.ATTACK = Animation.create("attack", 0.1f)
                .sound(20, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_1, SoundSource.HOSTILE, 2f, 1f)
                .sound(40, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_2, SoundSource.HOSTILE, 2f, 1f)
                .pose(0, 10, Functions.CUBIC, 1f,
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
                .pose(10, 20, Functions.CUBIC, 2f,
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
                .pose(20, 32, Functions.CUBIC, 1f,
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
                .pose(32, 40, Functions.CUBIC, 2f,
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
                .pose(40, 52, Functions.CUBIC, 1f,
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
                .pose(52, 60, Functions.CUBIC, 3f,
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
    }

    @Override
    protected Animation.Idle getIdleAnimation(CaptainCornelia cornelia) {
        return this.IDLE;
    }

    @Override
    protected Animation.Move getMoveAnimation(CaptainCornelia cornelia) {
        return this.MOVE;
    }

    @Override
    protected Animation.Death getDeathAnimation(CaptainCornelia cornelia) {
        return this.DEATH;
    }
}
