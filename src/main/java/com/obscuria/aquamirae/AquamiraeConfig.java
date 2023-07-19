package com.obscuria.aquamirae;

@SuppressWarnings("all")
public class AquamiraeConfig {

	public static final double DEFAULT_CORNELIA_MAX_HEALTH = 200.0;
	public static final double DEFAULT_CORNELIA_ARMOR = 16.0;
	public static final double DEFAULT_CORNELIA_ATTACK_DAMAGE = 1.0;
	public static final double DEFAULT_CORNELIA_ATTACK_KNOCKBACK = 2.0;
	public static final double DEFAULT_CORNELIA_FOLLOW_RANGE = 128.0;
	public static final double DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE = 0.5;
	public static final double DEFAULT_CORNELIA_MOVEMENT_SPEED = 0.2;
	public static final int DEFAULT_CORNELIA_SKILL_USES = 2;

	public static final double DEFAULT_ANGLERFISH_MAX_HEALTH = 40.0;
	public static final double DEFAULT_ANGLERFISH_ARMOR = 2.0;
	public static final double DEFAULT_ANGLERFISH_ATTACK_DAMAGE = 6.0;
	public static final double DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK = 1.0;
	public static final double DEFAULT_ANGLERFISH_FOLLOW_RANGE = 48.0;
	public static final double DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE = 0.0;
	public static final double DEFAULT_ANGLERFISH_SWIM_SPEED = 3.0;

	public static final double DEFAULT_MAW_MAX_HEALTH = 20.0;
	public static final double DEFAULT_MAW_ARMOR = 0.0;
	public static final double DEFAULT_MAW_ATTACK_DAMAGE = 4.0;
	public static final double DEFAULT_MAW_ATTACK_KNOCKBACK = 0.3;
	public static final double DEFAULT_MAW_FOLLOW_RANGE = 24.0;
	public static final double DEFAULT_MAW_KNOCKBACK_RESISTANCE = 0.0;
	public static final double DEFAULT_MAW_SWIM_SPEED = 5.0;
	public static final double DEFAULT_MAW_MOVEMENT_SPEED = 0.2;

	public static final double DEFAULT_SOUL_MAX_HEALTH = 30.0;
	public static final double DEFAULT_SOUL_ARMOR = 4.0;
	public static final double DEFAULT_SOUL_ATTACK_DAMAGE = 7.0;
	public static final double DEFAULT_SOUL_ATTACK_KNOCKBACK = 0.7;
	public static final double DEFAULT_SOUL_FOLLOW_RANGE = 24.0;
	public static final double DEFAULT_SOUL_KNOCKBACK_RESISTANCE = 0.0;
	public static final double DEFAULT_SOUL_SWIM_SPEED = 3.0;
	public static final double DEFAULT_SOUL_MOVEMENT_SPEED = 0.2;

	public static final double DEFAULT_MOTHER_MAX_HEALTH = 100.0;
	public static final double DEFAULT_MOTHER_ARMOR = 6.0;
	public static final double DEFAULT_MOTHER_ATTACK_DAMAGE = 5.0;
	public static final double DEFAULT_MOTHER_ATTACK_KNOCKBACK = 0.5;
	public static final double DEFAULT_MOTHER_FOLLOW_RANGE = 128.0;
	public static final double DEFAULT_MOTHER_KNOCKBACK_RESISTANCE = 0.2;
	public static final double DEFAULT_MOTHER_SWIM_SPEED = 3.0;

	public static final double DEFAULT_EEL_MAX_HEALTH = 180.0;
	public static final double DEFAULT_EEL_ARMOR = 20.0;
	public static final double DEFAULT_EEL_ATTACK_DAMAGE = 8.0;
	public static final double DEFAULT_EEL_ATTACK_KNOCKBACK = 2.0;
	public static final double DEFAULT_EEL_FOLLOW_RANGE = 32.0;

	/*
	public static class Common {
		public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
		public static final ForgeConfigSpec COMMON_SPEC;
		public static final ForgeConfigSpec.BooleanValue notifications;

		public static final ForgeConfigSpec.DoubleValue corneliaMovementSpeed;
		public static final ForgeConfigSpec.DoubleValue corneliaMaxHealth;
		public static final ForgeConfigSpec.DoubleValue corneliaArmor;
		public static final ForgeConfigSpec.DoubleValue corneliaAttackDamage;
		public static final ForgeConfigSpec.DoubleValue corneliaKnockbackResistance;
		public static final ForgeConfigSpec.DoubleValue corneliaAttackKnockback;
		public static final ForgeConfigSpec.DoubleValue corneliaFollowRange;
		public static final ForgeConfigSpec.BooleanValue corneliaSpinAbility;
		public static final ForgeConfigSpec.IntValue corneliaRegenerationAbility;

		public static final ForgeConfigSpec.DoubleValue eelMaxHealth;
		public static final ForgeConfigSpec.DoubleValue eelArmor;
		public static final ForgeConfigSpec.DoubleValue eelAttackDamage;
		public static final ForgeConfigSpec.DoubleValue eelAttackKnockback;
		public static final ForgeConfigSpec.DoubleValue eelFollowRange;

		public static final ForgeConfigSpec.DoubleValue anglerfishMaxHealth;
		public static final ForgeConfigSpec.DoubleValue anglerfishArmor;
		public static final ForgeConfigSpec.DoubleValue anglerfishAttackDamage;
		public static final ForgeConfigSpec.DoubleValue anglerfishAttackKnockback;
		public static final ForgeConfigSpec.DoubleValue anglerfishFollowRange;
		public static final ForgeConfigSpec.DoubleValue anglerfishKnockbackResistance;
		public static final ForgeConfigSpec.DoubleValue anglerfishSwimSpeed;

		public static final ForgeConfigSpec.DoubleValue mawMaxHealth;
		public static final ForgeConfigSpec.DoubleValue mawArmor;
		public static final ForgeConfigSpec.DoubleValue mawAttackDamage;
		public static final ForgeConfigSpec.DoubleValue mawAttackKnockback;
		public static final ForgeConfigSpec.DoubleValue mawFollowRange;
		public static final ForgeConfigSpec.DoubleValue mawKnockbackResistance;
		public static final ForgeConfigSpec.DoubleValue mawSwimSpeed;
		public static final ForgeConfigSpec.DoubleValue mawSpeed;

		public static final ForgeConfigSpec.DoubleValue soulMaxHealth;
		public static final ForgeConfigSpec.DoubleValue soulArmor;
		public static final ForgeConfigSpec.DoubleValue soulAttackDamage;
		public static final ForgeConfigSpec.DoubleValue soulAttackKnockback;
		public static final ForgeConfigSpec.DoubleValue soulFollowRange;
		public static final ForgeConfigSpec.DoubleValue soulKnockbackResistance;
		public static final ForgeConfigSpec.DoubleValue soulSwimSpeed;
		public static final ForgeConfigSpec.DoubleValue soulSpeed;

		public static final ForgeConfigSpec.DoubleValue motherMaxHealth;
		public static final ForgeConfigSpec.DoubleValue motherArmor;
		public static final ForgeConfigSpec.DoubleValue motherAttackDamage;
		public static final ForgeConfigSpec.DoubleValue motherAttackKnockback;
		public static final ForgeConfigSpec.DoubleValue motherFollowRange;
		public static final ForgeConfigSpec.DoubleValue motherKnockbackResistance;
		public static final ForgeConfigSpec.DoubleValue motherSwimSpeed;

		static {
			BUILDER.push("General");
			notifications = BUILDER.worldRestart().define("chatNotifications", true);
			BUILDER.pop();

			BUILDER.push("Mobs");

			BUILDER.push("GhostOfCaptainCornelia");
			corneliaMovementSpeed = BUILDER.worldRestart().defineInRange("movementSpeed", DEFAULT_CORNELIA_MOVEMENT_SPEED, 0.0, 10.0);
			corneliaMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", DEFAULT_CORNELIA_MAX_HEALTH, 1.0, 100000.0);
			corneliaArmor = BUILDER.worldRestart().defineInRange("armor", DEFAULT_CORNELIA_ARMOR, 0.0, 1000.0);
			corneliaAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", DEFAULT_CORNELIA_ATTACK_DAMAGE, 1.0, 1000.0);
			corneliaKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE, 0.0, 10.0);
			corneliaAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", DEFAULT_CORNELIA_ATTACK_KNOCKBACK, 0.0, 10.0);
			corneliaFollowRange = BUILDER.worldRestart().defineInRange("followRange", DEFAULT_CORNELIA_FOLLOW_RANGE, 1.0, 256.0);
			corneliaSpinAbility = BUILDER.worldRestart().define("pullAndSpinTargets", true);
			corneliaRegenerationAbility = BUILDER.worldRestart().defineInRange("regenerationSkillUses", DEFAULT_CORNELIA_SKILL_USES, 0, 1000);
			BUILDER.pop();

			BUILDER.push("AnglerfishEntity");
			anglerfishSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", DEFAULT_ANGLERFISH_SWIM_SPEED, 0.0, 100.0);
			anglerfishMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", DEFAULT_ANGLERFISH_MAX_HEALTH, 1.0, 100000.0);
			anglerfishArmor = BUILDER.worldRestart().defineInRange("armor", DEFAULT_ANGLERFISH_ARMOR, 0.0, 1000.0);
			anglerfishKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE, 0.0, 10.0);
			anglerfishAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", DEFAULT_ANGLERFISH_ATTACK_DAMAGE, 1.0, 1000.0);
			anglerfishAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK, 0.0, 10.0);
			anglerfishFollowRange = BUILDER.worldRestart().defineInRange("followRange", DEFAULT_ANGLERFISH_FOLLOW_RANGE, 1.0, 256.0);
			BUILDER.pop();

			BUILDER.push("MawEntity");
			mawSpeed = BUILDER.worldRestart().defineInRange("movementSpeed", DEFAULT_MAW_MOVEMENT_SPEED, 0.0, 10.0);
			mawSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", DEFAULT_MAW_SWIM_SPEED, 0.0, 100.0);
			mawMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", DEFAULT_MAW_MAX_HEALTH, 1.0, 100000.0);
			mawArmor = BUILDER.worldRestart().defineInRange("armor", DEFAULT_MAW_ARMOR, 0.0, 1000.0);
			mawKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", DEFAULT_MAW_KNOCKBACK_RESISTANCE, 0.0, 10.0);
			mawAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", DEFAULT_MAW_ATTACK_DAMAGE, 1.0, 1000.0);
			mawAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", DEFAULT_MAW_ATTACK_KNOCKBACK, 0.0, 10.0);
			mawFollowRange = BUILDER.worldRestart().defineInRange("followRange", DEFAULT_MAW_FOLLOW_RANGE, 1.0, 256.0);
			BUILDER.pop();

			BUILDER.push("TorturedSoulEntity");
			soulSpeed = BUILDER.worldRestart().defineInRange("movementSpeed", DEFAULT_SOUL_MOVEMENT_SPEED, 0.0, 10.0);
			soulSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", DEFAULT_SOUL_SWIM_SPEED, 0.0, 100.0);
			soulMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", DEFAULT_SOUL_MAX_HEALTH, 1.0, 100000.0);
			soulArmor = BUILDER.worldRestart().defineInRange("armor", DEFAULT_SOUL_ARMOR, 0.0, 1000.0);
			soulKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", DEFAULT_SOUL_KNOCKBACK_RESISTANCE, 0.0, 10.0);
			soulAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", DEFAULT_SOUL_ATTACK_DAMAGE, 1.0, 1000.0);
			soulAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", DEFAULT_SOUL_ATTACK_KNOCKBACK, 0.0, 10.0);
			soulFollowRange = BUILDER.worldRestart().defineInRange("followRange", DEFAULT_SOUL_FOLLOW_RANGE, 1.0, 256.0);
			BUILDER.pop();

			BUILDER.push("MotherOfTheMaze");
			motherSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", DEFAULT_MOTHER_SWIM_SPEED, 0.0, 100.0);
			motherMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", DEFAULT_MOTHER_MAX_HEALTH, 1.0, 100000.0);
			motherArmor = BUILDER.worldRestart().defineInRange("armor", DEFAULT_MOTHER_ARMOR, 0.0, 1000.0);
			motherKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", DEFAULT_MOTHER_KNOCKBACK_RESISTANCE, 0.0, 10.0);
			motherAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", DEFAULT_MOTHER_ATTACK_DAMAGE, 1.0, 1000.0);
			motherAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", DEFAULT_MOTHER_ATTACK_KNOCKBACK, 0.0, 10.0);
			motherFollowRange = BUILDER.worldRestart().defineInRange("followRange", DEFAULT_MOTHER_FOLLOW_RANGE, 1.0, 256.0);
			BUILDER.pop();

			BUILDER.push("EelEntity");
			eelMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", DEFAULT_EEL_MAX_HEALTH, 1.0, 100000.0);
			eelArmor = BUILDER.worldRestart().defineInRange("armor", DEFAULT_EEL_ARMOR, 0.0, 1000.0);
			eelAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", DEFAULT_EEL_ATTACK_DAMAGE, 1.0, 1000.0);
			eelAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", DEFAULT_EEL_ATTACK_KNOCKBACK, 0.0, 10.0);
			eelFollowRange = BUILDER.worldRestart().defineInRange("followRange", DEFAULT_EEL_FOLLOW_RANGE, 1.0, 256.0);
			BUILDER.pop();
			BUILDER.pop();
			COMMON_SPEC = BUILDER.build();
		}
	}

	public static class Client {
		public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
		public static final ForgeConfigSpec CLIENT_SPEC;
		public static final ForgeConfigSpec.BooleanValue stylizedBossbar;
		public static final ForgeConfigSpec.BooleanValue particles;
		public static final ForgeConfigSpec.BooleanValue ambientSounds;
		public static final ForgeConfigSpec.BooleanValue biomeMusic;
		public static final ForgeConfigSpec.BooleanValue bossMusic;
		public static final ForgeConfigSpec.BooleanValue overlay;
		static {
			BUILDER.push("General");
			overlay = BUILDER.worldRestart().define("renderThreeBoltHelmetOverlay", true);
			stylizedBossbar = BUILDER.worldRestart().define("stylizedBossbar", true);
			BUILDER.pop();
			BUILDER.push("IceMazeAmbient");
			particles = BUILDER.worldRestart().define("spawnParticles", true);
			ambientSounds = BUILDER.worldRestart().define("playAmbientSounds", true);
			biomeMusic = BUILDER.worldRestart().define("playBiomeMusic", true);
			bossMusic = BUILDER.worldRestart().define("playCorneliaMusic", true);
			BUILDER.pop();
			CLIENT_SPEC = BUILDER.build();
		}
	}

	public static void register() {
		Path configPath = FMLPaths.CONFIGDIR.get();
		Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "Obscuria");
		try { Files.createDirectory(modConfigPath); }
		catch (FileAlreadyExistsException ignored) {}
		catch (IOException e) { Aquamirae.LOGGER.warn("Failed to create Obscuria config directory", e); }
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Common.COMMON_SPEC, "Obscuria/aquamirae-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Client.CLIENT_SPEC, "Obscuria/aquamirae-client.toml");
	}

	 */
}
