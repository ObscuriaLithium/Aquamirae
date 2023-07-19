
package com.obscuria.aquamirae.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class GhostShineParticle extends AnimatedParticle {
	protected GhostShineParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, SpriteProvider spriteProvider) {
		super(world, x, y, z, spriteProvider, 0.8f);
		this.velocityX = vx * 0.1f;
		this.velocityY = vy * 0.1f;
		this.velocityZ = vz * 0.1f;
		this.scale *= 0.5f;
		this.maxAge = 30;
		this.gravityStrength = -0.1f;
		this.collidesWithWorld = true;
		setSpriteForAge(spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_LIT;
	}

	@Override
	public void tick() {
		prevAngle = angle;
		angle += age * 0.01f;
		super.tick();
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType type, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new GhostShineParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}

