
package com.obscuria.aquamirae.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ShineParticle {

	@OnlyIn(Dist.CLIENT)
	public static class Instance extends SpriteTexturedParticle {
		private final IAnimatedSprite spriteSet;
		private float angularVelocity;
		private final float angularAcceleration;

		protected Instance(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite spriteSet) {
			super(world, x, y, z);
			this.spriteSet = spriteSet;
			this.setSize((float) 0.2, (float) 0.2);
			this.quadSize *= (float) 0.8;
			this.lifetime = 30;
			this.gravity = (float) 0.1;
			this.hasPhysics = true;
			this.xd = vx * 0.1;
			this.yd = vy * 0.1;
			this.zd = vz * 0.1;
			this.angularVelocity = (float) 0.1;
			this.angularAcceleration = (float) 0.01;
			this.setSpriteFromAge(spriteSet);
		}

		@Override
		public int getLightColor(float partialTick) {
			return 15728880;
		}

		@Override
		@Nonnull
		public IParticleRenderType getRenderType() {
			return IParticleRenderType.PARTICLE_SHEET_LIT;
		}

		@Override
		public void tick() {
			super.tick();
			this.oRoll = this.roll;
			this.roll += this.angularVelocity;
			this.angularVelocity += this.angularAcceleration;
			if (this.isAlive()) this.setSprite(this.spriteSet.get((this.age / 3) % 10 + 1, 10));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(@Nonnull BasicParticleType typeIn, @Nonnull ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
									   double zSpeed) {
			return new Instance(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}
}
