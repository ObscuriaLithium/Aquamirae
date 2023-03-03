
package com.obscuria.aquamirae.common.events.features;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.common.blocks.OxygeliumBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OxygeliumFeature extends Feature<NoFeatureConfig> {
	private final List<Block> BLOCKS;

	public static OxygeliumFeature FEATURE = new OxygeliumFeature();
	public static ConfiguredFeature<?, ?> CONFIGURED_FEATURE = FEATURE.configured(IFeatureConfig.NONE).decorated(Placement.NOPE.configured(IPlacementConfig.NONE));

	public OxygeliumFeature() {
		super(NoFeatureConfig.CODEC);
		BLOCKS = Collections.singletonList(Blocks.GRAVEL);
	}

	public static Feature<?> feature() {
		return FEATURE;
	}

	@Override
	public boolean place(@Nonnull ISeedReader seedReader, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos origin, @Nonnull NoFeatureConfig config) {
		if (random.nextInt(3) != 1) return false;
		final int ox = origin.getX() - 6 + random.nextInt(12);
		final int oz = origin.getZ() - 6 + random.nextInt(12);
		final BlockPos mainPos = new BlockPos(ox, seedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, ox, oz) - 1, oz);
		if (!BLOCKS.contains(seedReader.getBlockState(mainPos).getBlock())) return false;
		boolean flag = random.nextDouble() > 0.7D;
		BlockState blockstate = Blocks.STONE.defaultBlockState();
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
						this.generateIcebergBlock(seedReader, random, mainPos, l, l1, j2, i2, k2, k1, flag1, j, d0, flag, blockstate);
					}
				}
			}
		}

		this.smooth(seedReader, mainPos, j1, l, flag1, i);

		for(int i3 = -k1; i3 < k1; ++i3) {
			for(int j3 = -k1; j3 < k1; ++j3) {
				for(int k3 = -1; k3 > -i1; --k3) {
					int l3 = flag1 ? MathHelper.ceil((float)k1 * (1.0F - (float)Math.pow(k3, 2.0D) / ((float)i1 * 8.0F))) : k1;
					int l2 = this.heightDependentRadiusSteep(random, -k3, i1, j1);
					if (i3 < l2) {
						this.generateIcebergBlock(seedReader, random, mainPos, i1, i3, k3, j3, l2, l3, flag1, j, d0, flag, blockstate);
					}
				}
			}
		}

		boolean flag2 = flag1 ? random.nextDouble() > 0.1D : random.nextDouble() > 0.7D;
		if (flag2) {
			this.generateCutOut(random, seedReader, j1, l, mainPos, flag1, i, d0, j);
		}

		final int count = 3 + random.nextInt(10);
		for (int index = 0; index <= count; index++) {
			int x = origin.getX() - 4 + random.nextInt(8);
			int z = origin.getZ() - 4 + random.nextInt(8);
			int y = seedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z) - 1;
			placeOxygelium(seedReader, x, y, z, random);
		}
		placeElodea(seedReader, mainPos.getX(), mainPos.getY(), mainPos.getZ(), random);
		return true;
	}

	private void placeOxygelium(ISeedReader world, int x, int y, int z, Random random) {
		final int max = 3 + random.nextInt(7);
		for (int i = 0; i <= max; i++) {
			final BlockState below = world.getBlockState(new BlockPos(x, y + i - 1, z));
			final BlockState state = world.getBlockState(new BlockPos(x, y + i, z));
			final BlockState above = world.getBlockState(new BlockPos(x, y + i + 1, z));
			if ((below.getMaterial().isSolid() || isStem(below)) && state.getMaterial().isLiquid()) {
				if (above.getMaterial().isLiquid()) {
					world.setBlock(new BlockPos(x, y + i, z), AquamiraeBlocks.OXYGELIUM.get().defaultBlockState()
							.setValue(OxygeliumBlock.TYPE, i < max ? OxygeliumBlock.Type.STEM : OxygeliumBlock.Type.EMPTY_BUD)
							.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 3);
				} else {
					world.setBlock(new BlockPos(x, y + i, z), AquamiraeBlocks.OXYGELIUM.get().defaultBlockState()
							.setValue(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD)
							.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 3);
				}
			}
		}
	}

	private boolean isStem(BlockState state) {
		return state.is(AquamiraeBlocks.OXYGELIUM.get()) && state.getValue(OxygeliumBlock.TYPE) == OxygeliumBlock.Type.STEM;
	}

	private void placeElodea(ISeedReader world, int x, int y, int z, Random random) {
		final int max = 4 + random.nextInt(20);
		for (int i = 0; i <= max; i++) {
			final BlockPos pos = new BlockPos(x - 7 + random.nextInt(14), y - 4, z - 7 + random.nextInt(14));
			int offset = 0;
			while (offset <= 10) {
				if ((world.getBlockState(pos.above(offset - 1)).getBlock() == Blocks.GRAVEL ||
						world.getBlockState(pos.above(offset - 1)).getBlock() == Blocks.STONE ||
						world.getBlockState(pos.above(offset - 1)).getBlock() == Blocks.COBBLESTONE) &&
						world.getBlockState(pos.above(offset)).getBlock() == Blocks.WATER) {
					world.setBlock(pos.above(offset), AquamiraeBlocks.ELODEA.get().defaultBlockState()
							.setValue(BlockStateProperties.WATERLOGGED, true), 3);
					break;
				}
				offset++;
			}
		}
	}

	private void generateCutOut(Random p_225100_, ISeedReader p_225101_, int p_225102_, int p_225103_, BlockPos p_225104_, boolean p_225105_, int p_225106_, double p_225107_, int p_225108_) {
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

	private void carve(int p_66036_, int p_66037_, BlockPos p_66038_, ISeedReader p_66039_, boolean p_66040_, double p_66041_, BlockPos p_66042_, int p_66043_, int p_66044_) {
		int i = p_66036_ + 1 + p_66043_ / 3;
		int j = Math.min(p_66036_ - 3, 3) + p_66044_ / 2 - 1;

		for(int k = -i; k < i; ++k) {
			for(int l = -i; l < i; ++l) {
				double d0 = this.signedDistanceEllipse(k, l, p_66042_, i, j, p_66041_);
				if (d0 < 0.0D) {
					BlockPos blockpos = p_66038_.offset(k, p_66037_, l);
					BlockState blockstate = p_66039_.getBlockState(blockpos);
					if (isIcebergState(blockstate) && p_66040_) this.setBlock(p_66039_, blockpos, Blocks.WATER.defaultBlockState());
				}
			}
		}
	}

	private void generateIcebergBlock(ISeedReader p_225110_, Random p_225111_, BlockPos pos, int p_225113_, int p_225114_, int p_225115_, int p_225116_, int p_225117_, int p_225118_, boolean p_225119_, int p_225120_, double p_225121_, boolean p_225122_, BlockState p_225123_) {
		double d0 = p_225119_ ? this.signedDistanceEllipse(p_225114_, p_225116_, BlockPos.ZERO, p_225118_, this.getEllipseC(p_225115_, p_225113_, p_225120_), p_225121_) : this.signedDistanceCircle(p_225114_, p_225116_, p_225117_, p_225111_);
		if (d0 < 0.0D) {
			BlockPos blockpos = pos.offset(p_225114_, p_225115_, p_225116_);
			double d1 = p_225119_ ? -0.5D : (double)(-6 - p_225111_.nextInt(3));
			if (d0 > d1 && p_225111_.nextDouble() > 0.9D) {
				return;
			}

			this.setIcebergBlock(blockpos, p_225110_, p_225111_, p_225113_ - p_225115_, p_225113_, p_225119_, p_225122_, p_225123_);
		}

	}

	private void setIcebergBlock(BlockPos pos, ISeedReader levelAccessor, Random p_225127_, int p_225128_, int p_225129_, boolean p_225130_, boolean p_225131_, BlockState p_225132_) {
		if (levelAccessor.getBlockState(pos.above()).getMaterial() == Material.AIR) return;
		BlockState blockstate = levelAccessor.getBlockState(pos);
		if (blockstate.getMaterial() == Material.AIR || blockstate.is(Blocks.SNOW_BLOCK) || blockstate.is(Blocks.ICE) || blockstate.is(Blocks.WATER)) {
			boolean flag = !p_225130_ || p_225127_.nextDouble() > 0.05D;
			int i = p_225130_ ? 3 : 2;
			if (p_225131_ && (double)p_225128_ <= (double)p_225127_.nextInt(Math.max(1, p_225129_ / i)) + (double)p_225129_ * 0.6D && flag) {
				this.setBlock(levelAccessor, pos, Blocks.COBBLESTONE.defaultBlockState());
			} else {
				this.setBlock(levelAccessor, pos, p_225132_);
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
		return p_159886_.is(Blocks.STONE) || p_159886_.is(Blocks.COBBLESTONE);
	}

	private boolean belowIsAir(ISeedReader p_66046_, BlockPos p_66047_) {
		return p_66046_.getBlockState(p_66047_.below()).getBlock() == Blocks.WATER;
	}

	private void smooth(ISeedReader p_66052_, BlockPos p_66053_, int p_66054_, int p_66055_, boolean p_66056_, int p_66057_) {
		int i = p_66056_ ? p_66057_ : p_66054_ / 2;

		for(int j = -i; j <= i; ++j) {
			for(int k = -i; k <= i; ++k) {
				for(int l = 0; l <= p_66055_; ++l) {
					BlockPos blockpos = p_66053_.offset(j, l, k);
					BlockState blockstate = p_66052_.getBlockState(blockpos);
					if (isIcebergState(blockstate)) {
						if (this.belowIsAir(p_66052_, blockpos)) {
							this.setBlock(p_66052_, blockpos, Blocks.WATER.defaultBlockState());
							this.setBlock(p_66052_, blockpos.above(), Blocks.WATER.defaultBlockState());
						} else if (isIcebergState(blockstate)) {
							BlockState[] ablockstate = new BlockState[]{p_66052_.getBlockState(blockpos.west()), p_66052_.getBlockState(blockpos.east()), p_66052_.getBlockState(blockpos.north()), p_66052_.getBlockState(blockpos.south())};
							int i1 = 0;

							for(BlockState blockstate1 : ablockstate) {
								if (!isIcebergState(blockstate1)) {
									++i1;
								}
							}

							if (i1 >= 3) {
								this.setBlock(p_66052_, blockpos, Blocks.WATER.defaultBlockState());
							}
						}
					}
				}
			}
		}
	}
}
