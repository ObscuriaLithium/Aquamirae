package com.obscuria.aquamirae.client;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;

@Deprecated
public class HekateLegacy {

	public static float idle(float degreeSwing, float degreeBase, float speed, float offset, float ageInTicks, float mod) {
		degreeSwing = degreeSwing * (float) Math.PI / 180F;
		degreeBase = degreeBase * (float) Math.PI / 180F;
		float result = (degreeSwing * Mth.cos(ageInTicks * speed + offset * 6.283F) + degreeBase) * mod;
		return result;
	}

	public static float move(boolean invert, float degreeSwing, float degreeBase, float speed, float offset, float limbSwing, float mod) {
		degreeSwing = degreeSwing * (float) Math.PI / 180F;
		degreeBase = degreeBase * (float) Math.PI / 180F;
		float invertValue = (invert ? -1F : 1F);
		float result = (invertValue * mod * degreeSwing * Mth.cos(limbSwing * speed + offset * 6.283F) + degreeBase) * mod;
		return result;
	}

	public static float fixed(float degree, float mod) {
		degree = degree * (float) Math.PI / 180F;
		return degree * mod;
	}

	public static float getHeadYaw(float yaw, float mod) {
		return (yaw / (180F / (float) Math.PI)) * mod;
	}

	public static float getModifier(float ageInTicks, float speed, float offset) {
		return (0.5F + Mth.cos(ageInTicks * speed + offset * 6.283F) * 0.5F);
	}

	public static float getFrame(Entity e, String anim, double speed, double durationTick, double endTick) {
		double frame = 0;
		if (e.getPersistentData().getBoolean(anim + "Anim")) {
			double delta = e.getPersistentData().getDouble("hekateEntity");
			double currentTick = e.getPersistentData().getDouble(anim + "Tick") + 1.0 * delta;
			frame = e.getPersistentData().getDouble(anim + "Frame");
			if (currentTick < durationTick) {
				frame = frame + (1.0 - frame) * (speed * delta);
			} else if (currentTick > endTick) {
				e.getPersistentData().putBoolean(anim + "Anim", false);
				frame = 0;
				currentTick = 0;
			} else {
				frame = frame + (0.0 - frame) * (speed * delta);
			}
			e.getPersistentData().putDouble(anim + "Tick", currentTick);
			e.getPersistentData().putDouble(anim + "Frame", frame);
		}
		return (float) frame;
	}

	public static void updateRenderer(Entity e) {
		e.getPersistentData().putDouble("hekateRenderer", e.getPersistentData().getDouble("hekateRenderer") + 1);
	}

	public static void updateEntity(Entity e) {
		e.getPersistentData().putDouble("hekateEntity", (60.0 / (e.getPersistentData().getDouble("hekateRenderer") * 20.0)));
		e.getPersistentData().putDouble("hekateRenderer", 0);
	}

	public static float setIdleModifier(float limbSwingAmount, float mod) {
		return (Math.max((1F - limbSwingAmount * mod), 0F));
	}

	public static float setMoveModifier(float limbSwingAmount, float mod) {
		return (Math.min((limbSwingAmount * mod), 1F));
	}

	public static boolean isPrepared(Entity e) {
		return (((e instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)).getCount() > 0);
	}

	public static void playPrepared(Entity e) {
		e.getPersistentData().putBoolean(
				((((e instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)).getDisplayName().getString()) + "Anim"), (true));
	}

	public static void play(String anim, Entity e) {
		e.getPersistentData().putBoolean(anim + "Anim", (true));
	}
}
