
package com.obscuria.aquamirae.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShineParticle extends TextureSheetParticle {
	private final SpriteSet sprites;
	private float angularVelocity;
	private final float angularAcceleration;

	protected ShineParticle(ClientLevel level,
							double x, double y, double z,
							double vx, double vy, double vz,
							SpriteSet sprites) {
		super(level, x, y, z);
		this.sprites = sprites;
		this.quadSize *= 0.8f;
		this.lifetime = level.random.nextInt(20, 30);
		this.gravity = 0.1f;
		this.hasPhysics = true;
		this.xd = vx * 0.1;
		this.yd = vy * 0.1;
		this.zd = vz * 0.1;
		this.angularVelocity = 0.1f;
		this.angularAcceleration = 0.01f;
		this.setSize(0.2f, 0.2f);
		this.setSpriteFromAge(sprites);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	@Override
	public int getLightColor(float partialTick) {
		return 15728880;
	}

	@Override
	public void tick() {
		super.tick();
		this.oRoll = this.roll;
		this.roll += this.angularVelocity;
		this.angularVelocity += this.angularAcceleration;
		if (this.removed) return;
		this.setSprite(this.sprites.get((this.age / 3) % 10 + 1, 10));
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public ShineParticle createParticle(SimpleParticleType type, ClientLevel level,
											double x, double y, double z,
											double vx, double vy, double vz) {
			return new ShineParticle(level, x, y, z, vx, vy, vz, this.sprites);
		}
	}
}
