
package com.obscuria.aquamirae.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.obscuria.aquamirae.common.blocks.OxygeliumBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

@SuppressWarnings("deprecation")
public class OxygeliumFeature extends Feature<DefaultFeatureConfig> {
	private final List<Block> BLOCKS;

	public OxygeliumFeature(Codec<DefaultFeatureConfig> codec) {
		super(codec);
		BLOCKS = List.of(Blocks.GRAVEL);
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		final int xo = context.getOrigin().getX() + context.getRandom().nextBetween(-6, 6);
		final int zo = context.getOrigin().getZ() + context.getRandom().nextBetween(-6, 6);
		final BlockPos mainPos = new BlockPos(xo, context.getWorld().getTopY(Heightmap.Type.OCEAN_FLOOR_WG, xo, zo) - 1, zo);
		if (!BLOCKS.contains(context.getWorld().getBlockState(mainPos).getBlock())) return false;
		StructureWorldAccess worldAccess = context.getWorld();
		Random random = context.getRandom();
		boolean flag = random.nextDouble() > 0.7D;
		BlockState blockstate = Blocks.STONE.getDefaultState();
		double d0 = random.nextDouble() * 2.0D * Math.PI;
		int i = 11 - random.nextInt(5);
		int j = 3 + random.nextInt(3);
		boolean flag1 = random.nextDouble() > 0.7D;
		int l = flag1 ? random.nextInt(6) + 6 : random.nextInt(15) + 3;
		if (!flag1 && random.nextDouble() > 0.9D) {
			l += random.nextInt(19) + 7;
		}

		int i1 = Math.min(l + random.nextInt(11), 18);
		int j1 = Math.min(l + random.nextInt(7) - random.nextInt(5), 11);
		int k1 = flag1 ? i : 11;

		for(int l1 = -k1; l1 < k1; ++l1) {
			for(int i2 = -k1; i2 < k1; ++i2) {
				for(int j2 = 0; j2 < l; ++j2) {
					int k2 = flag1 ? this.heightDependentRadiusEllipse(j2, l, j1) : this.heightDependentRadiusRound(random, j2, l, j1);
					if (flag1 || l1 < k2) {
						this.generateIcebergBlock(worldAccess, random, mainPos, l, l1, j2, i2, k2, k1, flag1, j, d0, flag, blockstate);
					}
				}
			}
		}

		this.smooth(worldAccess, mainPos, j1, l, flag1, i);

		for(int i3 = -k1; i3 < k1; ++i3) {
			for(int j3 = -k1; j3 < k1; ++j3) {
				for(int k3 = -1; k3 > -i1; --k3) {
					int l3 = flag1 ? MathHelper.ceil((float)k1 * (1.0F - (float)Math.pow(k3, 2.0D) / ((float)i1 * 8.0F))) : k1;
					int l2 = this.heightDependentRadiusSteep(random, -k3, i1, j1);
					if (i3 < l2) {
						this.generateIcebergBlock(worldAccess, random, mainPos, i1, i3, k3, j3, l2, l3, flag1, j, d0, flag, blockstate);
					}
				}
			}
		}

		boolean flag2 = flag1 ? random.nextDouble() > 0.1D : random.nextDouble() > 0.7D;
		if (flag2) {
			this.generateCutOut(random, worldAccess, j1, l, mainPos, flag1, i, d0, j);
		}

		final int count = context.getRandom().nextBetween(3, 13);
		for (int index = 0; index <= count; index++) {
			int x = (int) (context.getOrigin().getX() + context.getRandom().nextTriangular(0, 4));
			int z = (int) (context.getOrigin().getZ() + context.getRandom().nextTriangular(0, 4));
			int y = context.getWorld().getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z) - 1;
			placeOxygelium(context.getWorld(), x, y, z, context.getRandom());
		}
		placeElodea(worldAccess, mainPos.getX(), mainPos.getY(), mainPos.getZ(), random);
		return true;
	}

	private void placeOxygelium(StructureWorldAccess world, int x, int y, int z, Random random) {
		final int max = random.nextBetween(3, 10);
		for (int i = 0; i <= max; i++) {
			final BlockState down = world.getBlockState(new BlockPos(x, y + i - 1, z));
			final BlockState state = world.getBlockState(new BlockPos(x, y + i, z));
			final BlockState up = world.getBlockState(new BlockPos(x, y + i + 1, z));
			if ((down.isSolid() || isStem(down)) && state.isLiquid()) {
				if (up.isLiquid()) {
					world.setBlockState(new BlockPos(x, y + i, z), AquamiraeBlocks.OXYGELIUM.getDefaultState()
							.with(OxygeliumBlock.TYPE, i < max ? OxygeliumBlock.Type.STEM : OxygeliumBlock.Type.EMPTY_BUD)
							.with(Properties.WATERLOGGED, Boolean.TRUE), 3);
				} else {
					world.setBlockState(new BlockPos(x, y + i, z), AquamiraeBlocks.OXYGELIUM.getDefaultState()
							.with(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD)
							.with(Properties.WATERLOGGED, Boolean.TRUE), 3);
				}
			}
		}
	}

	private boolean isStem(BlockState state) {
		return state.isOf(AquamiraeBlocks.OXYGELIUM) && state.get(OxygeliumBlock.TYPE) == OxygeliumBlock.Type.STEM;
	}

	private void placeElodea(StructureWorldAccess world, int x, int y, int z, Random random) {
		final int max = random.nextBetween(4, 24);
		for (int i = 0; i <= max; i++) {
			final BlockPos pos = new BlockPos(
					x + (int) Math.round(random.nextTriangular(0, 7)),
					y - 4,
					z + (int) Math.round(random.nextTriangular(0, 7)));
			int offset = 0;
			while (offset <= 10) {
				if ((world.getBlockState(pos.up(offset - 1)).getBlock() == Blocks.GRAVEL ||
						world.getBlockState(pos.up(offset - 1)).getBlock() == Blocks.STONE ||
						world.getBlockState(pos.up(offset - 1)).getBlock() == Blocks.COBBLESTONE) &&
						world.getBlockState(pos.up(offset)).getBlock() == Blocks.WATER) {
					world.setBlockState(pos.up(offset), AquamiraeBlocks.ELODEA.getDefaultState(), 3);
					break;
				}
				offset++;
			}
		}
	}

	private void generateCutOut(Random p_225100_, StructureWorldAccess p_225101_, int p_225102_, int p_225103_, BlockPos p_225104_, boolean p_225105_, int p_225106_, double p_225107_, int p_225108_) {
		int i = p_225100_.nextBoolean() ? -1 : 1;
		int j = p_225100_.nextBoolean() ? -1 : 1;
		int k = p_225100_.nextInt(Math.max(p_225102_ / 2 - 2, 1));
		if (p_225100_.nextBoolean()) {
			k = p_225102_ / 2 + 1 - p_225100_.nextInt(Math.max(p_225102_ - p_225102_ / 2 - 1, 1));
		}

		int l = p_225100_.nextInt(Math.max(p_225102_ / 2 - 2, 1));
		if (p_225100_.nextBoolean()) {
			l = p_225102_ / 2 + 1 - p_225100_.nextInt(Math.max(p_225102_ - p_225102_ / 2 - 1, 1));
		}

		if (p_225105_) {
			k = l = p_225100_.nextInt(Math.max(p_225106_ - 5, 1));
		}

		BlockPos blockpos = new BlockPos(i * k, 0, j * l);
		double d0 = p_225105_ ? p_225107_ + (Math.PI / 2D) : p_225100_.nextDouble() * 2.0D * Math.PI;

		for(int i1 = 0; i1 < p_225103_ - 3; ++i1) {
			int j1 = this.heightDependentRadiusRound(p_225100_, i1, p_225103_, p_225102_);
			this.carve(j1, i1, p_225104_, p_225101_, false, d0, blockpos, p_225106_, p_225108_);
		}

		for(int k1 = -1; k1 > -p_225103_ + p_225100_.nextInt(5); --k1) {
			int l1 = this.heightDependentRadiusSteep(p_225100_, -k1, p_225103_, p_225102_);
			this.carve(l1, k1, p_225104_, p_225101_, true, d0, blockpos, p_225106_, p_225108_);
		}

	}

	private void carve(int p_66036_, int p_66037_, BlockPos p_66038_, StructureWorldAccess p_66039_, boolean p_66040_, double p_66041_, BlockPos p_66042_, int p_66043_, int p_66044_) {
		int i = p_66036_ + 1 + p_66043_ / 3;
		int j = Math.min(p_66036_ - 3, 3) + p_66044_ / 2 - 1;

		for(int k = -i; k < i; ++k) {
			for(int l = -i; l < i; ++l) {
				double d0 = this.signedDistanceEllipse(k, l, p_66042_, i, j, p_66041_);
				if (d0 < 0.0D) {
					BlockPos blockpos = p_66038_.add(k, p_66037_, l);
					BlockState blockstate = p_66039_.getBlockState(blockpos);
					if (isIcebergState(blockstate) && p_66040_) this.setBlockState(p_66039_, blockpos, Blocks.WATER.getDefaultState());
				}
			}
		}
	}

	private void generateIcebergBlock(StructureWorldAccess p_225110_, Random p_225111_, BlockPos pos, int p_225113_, int p_225114_, int p_225115_, int p_225116_, int p_225117_, int p_225118_, boolean p_225119_, int p_225120_, double p_225121_, boolean p_225122_, BlockState p_225123_) {
		double d0 = p_225119_ ? this.signedDistanceEllipse(p_225114_, p_225116_, BlockPos.ORIGIN, p_225118_, this.getEllipseC(p_225115_, p_225113_, p_225120_), p_225121_) : this.signedDistanceCircle(p_225114_, p_225116_, p_225117_, p_225111_);
		if (d0 < 0.0D) {
			BlockPos blockpos = pos.add(p_225114_, p_225115_, p_225116_);
			double d1 = p_225119_ ? -0.5D : (double)(-6 - p_225111_.nextInt(3));
			if (d0 > d1 && p_225111_.nextDouble() > 0.9D) {
				return;
			}

			this.setIcebergBlock(blockpos, p_225110_, p_225111_, p_225113_ - p_225115_, p_225113_, p_225119_, p_225122_, p_225123_);
		}

	}

	private void setIcebergBlock(BlockPos pos, StructureWorldAccess levelAccessor, Random p_225127_, int p_225128_, int p_225129_, boolean p_225130_, boolean p_225131_, BlockState p_225132_) {
		if (levelAccessor.getBlockState(pos.up()).isAir()) return;
		BlockState blockstate = levelAccessor.getBlockState(pos);
		if (blockstate.isAir() || blockstate.isOf(Blocks.SNOW_BLOCK) || blockstate.isOf(Blocks.ICE) || blockstate.isOf(Blocks.WATER)) {
			boolean flag = !p_225130_ || p_225127_.nextDouble() > 0.05D;
			int i = p_225130_ ? 3 : 2;
			if (p_225131_ && (double)p_225128_ <= (double)p_225127_.nextInt(Math.max(1, p_225129_ / i)) + (double)p_225129_ * 0.6D && flag) {
				this.setBlockState(levelAccessor, pos, Blocks.COBBLESTONE.getDefaultState());
			} else {
				this.setBlockState(levelAccessor, pos, p_225132_);
			}
		}

	}

	private int getEllipseC(int p_66019_, int p_66020_, int p_66021_) {
		int i = p_66021_;
		if (p_66019_ > 0 && p_66020_ - p_66019_ <= 3) {
			i = p_66021_ - (4 - (p_66020_ - p_66019_));
		}

		return i;
	}

	private double signedDistanceCircle(int p_225089_, int p_225090_, int p_225092_, Random p_225093_) {
		float f = 10.0F * MathHelper.clamp(p_225093_.nextFloat(), 0.2F, 0.8F) / (float)p_225092_;
		return (double)f + Math.pow(p_225089_ - BlockPos.ZERO.getX(), 2.0D) + Math.pow(p_225090_ - BlockPos.ZERO.getZ(), 2.0D) - Math.pow(p_225092_, 2.0D);
	}

	private double signedDistanceEllipse(int p_66023_, int p_66024_, BlockPos p_66025_, int p_66026_, int p_66027_, double p_66028_) {
		return Math.pow(((double)(p_66023_ - p_66025_.getX()) * Math.cos(p_66028_) - (double)(p_66024_ - p_66025_.getZ()) * Math.sin(p_66028_)) / (double)p_66026_, 2.0D) + Math.pow(((double)(p_66023_ - p_66025_.getX()) * Math.sin(p_66028_) + (double)(p_66024_ - p_66025_.getZ()) * Math.cos(p_66028_)) / (double)p_66027_, 2.0D) - 1.0D;
	}

	private int heightDependentRadiusRound(Random p_225095_, int p_225096_, int p_225097_, int p_225098_) {
		float f = 3.5F - p_225095_.nextFloat();
		float f1 = (1.0F - (float)Math.pow(p_225096_, 2.0D) / ((float)p_225097_ * f)) * (float)p_225098_;
		if (p_225097_ > 15 + p_225095_.nextInt(5)) {
			int i = p_225096_ < 3 + p_225095_.nextInt(6) ? p_225096_ / 2 : p_225096_;
			f1 = (1.0F - (float)i / ((float)p_225097_ * f * 0.4F)) * (float)p_225098_;
		}

		return MathHelper.ceil(f1 / 2.0F);
	}

	private int heightDependentRadiusEllipse(int p_66110_, int p_66111_, int p_66112_) {
		float f1 = (1.0F - (float)Math.pow(p_66110_, 2.0D) / ((float) p_66111_)) * (float)p_66112_;
		return MathHelper.ceil(f1 / 2.0F);
	}

	private int heightDependentRadiusSteep(Random p_225134_, int p_225135_, int p_225136_, int p_225137_) {
		float f = 1.0F + p_225134_.nextFloat() / 2.0F;
		float f1 = (1.0F - (float)p_225135_ / ((float)p_225136_ * f)) * (float)p_225137_;
		return MathHelper.ceil(f1 / 2.0F);
	}

	private static boolean isIcebergState(BlockState p_159886_) {
		return p_159886_.isOf(Blocks.STONE) || p_159886_.isOf(Blocks.COBBLESTONE);
	}

	private boolean belowIsAir(StructureWorldAccess p_66046_, BlockPos p_66047_) {
		return p_66046_.getBlockState(p_66047_.down()).getBlock() == Blocks.WATER;
	}

	private void smooth(StructureWorldAccess p_66052_, BlockPos p_66053_, int p_66054_, int p_66055_, boolean p_66056_, int p_66057_) {
		int i = p_66056_ ? p_66057_ : p_66054_ / 2;

		for(int j = -i; j <= i; ++j) {
			for(int k = -i; k <= i; ++k) {
				for(int l = 0; l <= p_66055_; ++l) {
					BlockPos blockpos = p_66053_.add(j, l, k);
					BlockState blockstate = p_66052_.getBlockState(blockpos);
					if (isIcebergState(blockstate)) {
						if (this.belowIsAir(p_66052_, blockpos)) {
							this.setBlockState(p_66052_, blockpos, Blocks.WATER.getDefaultState());
							this.setBlockState(p_66052_, blockpos.up(), Blocks.WATER.getDefaultState());
						} else if (isIcebergState(blockstate)) {
							BlockState[] ablockstate = new BlockState[]{p_66052_.getBlockState(blockpos.west()), p_66052_.getBlockState(blockpos.east()), p_66052_.getBlockState(blockpos.north()), p_66052_.getBlockState(blockpos.south())};
							int i1 = 0;

							for(BlockState blockstate1 : ablockstate) {
								if (!isIcebergState(blockstate1)) {
									++i1;
								}
							}

							if (i1 >= 3) {
								this.setBlockState(p_66052_, blockpos, Blocks.WATER.getDefaultState());
							}
						}
					}
				}
			}
		}
	}
}
