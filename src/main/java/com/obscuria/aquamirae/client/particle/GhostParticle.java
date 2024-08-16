
package com.obscuria.aquamirae.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostParticle extends TextureSheetParticle {
	private final SpriteSet sprites;

	protected GhostParticle(ClientLevel world,
							double x, double y, double z,
							double vx, double vy, double vz,
							SpriteSet sprites) {
		super(world, x, y, z);
		this.sprites = sprites;
		this.quadSize *= 1.3f;
		this.lifetime = 40;
		this.gravity = -0.3f;
		this.hasPhysics = false;
		this.xd = vx * 0.1;
		this.yd = vy * 0.1;
		this.zd = vz * 0.1;
		this.setSize(0.2f, 0.2f);
		this.setSpriteFromAge(sprites);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	@Override
	public int getLightColor(float delta) {
		return 15728880;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.removed) return;
		this.setSprite(this.sprites.get((this.age / 3) % 12 + 1, 12));
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public GhostParticle createParticle(SimpleParticleType type, ClientLevel level,
									   double x, double y, double z,
									   double vx, double vy, double vz) {
			return new GhostParticle(level, x, y, z, vx, vy, vz, this.sprites);
		}
	}
}
