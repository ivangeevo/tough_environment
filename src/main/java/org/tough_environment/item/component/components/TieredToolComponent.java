package org.tough_environment.item.component.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;

public record TieredToolComponent(float tierMultiplier, List<TieredToolComponent.Rule> rules, float defaultMiningSpeed, int damagePerBlock) {
	public static final Codec<TieredToolComponent> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.FLOAT.fieldOf("tier_multiplier").forGetter(TieredToolComponent::tierMultiplier),
					TieredToolComponent.Rule.CODEC.listOf().fieldOf("rules").forGetter(TieredToolComponent::rules),
					Codec.FLOAT.optionalFieldOf("default_mining_speed", 1.0F).forGetter(TieredToolComponent::defaultMiningSpeed),
					Codecs.NONNEGATIVE_INT.optionalFieldOf("damage_per_block", 1).forGetter(TieredToolComponent::damagePerBlock)
					)
					.apply(instance, TieredToolComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, TieredToolComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.FLOAT, TieredToolComponent::tierMultiplier,
			TieredToolComponent.Rule.PACKET_CODEC.collect(PacketCodecs.toList()), TieredToolComponent::rules,
			PacketCodecs.FLOAT, TieredToolComponent::defaultMiningSpeed,
			PacketCodecs.VAR_INT, TieredToolComponent::damagePerBlock,
			TieredToolComponent::new
	);

	public float getSpeed(BlockState state) {
		for (TieredToolComponent.Rule rule : this.rules) {
			if (rule.speed.isPresent() && state.isIn(rule.blocks)) {
				return rule.speed.get();
			}
		}

		return this.defaultMiningSpeed;
	}

	public boolean isCorrectForDrops(BlockState state) {
		for (TieredToolComponent.Rule rule : this.rules) {
			if (rule.correctForDrops.isPresent() && state.isIn(rule.blocks)) {
				return rule.correctForDrops.get();
			}
		}

		return false;
	}

	public record Rule(RegistryEntryList<Block> blocks, Optional<Float> speed, Optional<Boolean> correctForDrops) {
		public static final Codec<TieredToolComponent.Rule> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("blocks").forGetter(TieredToolComponent.Rule::blocks),
								Codecs.POSITIVE_FLOAT.optionalFieldOf("speed").forGetter(TieredToolComponent.Rule::speed),
								Codec.BOOL.optionalFieldOf("correct_for_drops").forGetter(TieredToolComponent.Rule::correctForDrops)
						)
						.apply(instance, TieredToolComponent.Rule::new)
		);
		public static final PacketCodec<RegistryByteBuf, TieredToolComponent.Rule> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.registryEntryList(RegistryKeys.BLOCK),
				TieredToolComponent.Rule::blocks,
				PacketCodecs.FLOAT.collect(PacketCodecs::optional),
				TieredToolComponent.Rule::speed,
				PacketCodecs.BOOL.collect(PacketCodecs::optional),
				TieredToolComponent.Rule::correctForDrops,
				TieredToolComponent.Rule::new
		);

		public static TieredToolComponent.Rule ofAlwaysDropping(List<Block> blocks, float speed) {
			return of(blocks, Optional.of(speed), Optional.of(true));
		}

		public static TieredToolComponent.Rule ofAlwaysDropping(TagKey<Block> blocks, float speed) {
			return of(blocks, Optional.of(speed), Optional.of(true));
		}

		public static TieredToolComponent.Rule ofNeverDropping(TagKey<Block> tag) {
			return of(tag, Optional.empty(), Optional.of(false));
		}

		public static TieredToolComponent.Rule of(TagKey<Block> tag, float speed) {
			return of(tag, Optional.of(speed), Optional.empty());
		}

		public static TieredToolComponent.Rule of(List<Block> blocks, float speed) {
			return of(blocks, Optional.of(speed), Optional.empty());
		}

		private static TieredToolComponent.Rule of(TagKey<Block> tag, Optional<Float> speed, Optional<Boolean> correctForDrops) {
			return new TieredToolComponent.Rule(Registries.BLOCK.getOrCreateEntryList(tag), speed, correctForDrops);
		}

		private static TieredToolComponent.Rule of(List<Block> blocks, Optional<Float> speed, Optional<Boolean> correctForDrops) {
			return new TieredToolComponent.Rule(
					RegistryEntryList.of(blocks.stream().map(Block::getRegistryEntry).collect(Collectors.toList())),
					speed,
					correctForDrops
			);
		}
	}

	public static class Builder {

		float tierMultiplier;
		List<TieredToolComponent.Rule> rules;
		float defaultMiningSpeed;
		int damagePerBlock;

		public TieredToolComponent.Builder tierMultiplier(float value) {
			this.tierMultiplier = value;
			return this;
		}

		public TieredToolComponent.Builder withRules(List<TieredToolComponent.Rule> rules) {
			this.rules = rules;
			return this;
		}

		public TieredToolComponent.Builder defaultSpeed(float speed) {
			this.defaultMiningSpeed = speed;
			return this;
		}

		public TieredToolComponent.Builder damagePerBlock(int damagePerBlock) {
			this.damagePerBlock = damagePerBlock;
			return this;
		}


		public TieredToolComponent build() {
			return new TieredToolComponent(this.tierMultiplier, this.rules, this.defaultMiningSpeed, this.damagePerBlock);
		}
	}

	public record StatusEffectEntry(StatusEffectInstance effect, float probability) {
		public static final Codec<TieredToolComponent.StatusEffectEntry> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
						StatusEffectInstance.CODEC.fieldOf("effect").forGetter(TieredToolComponent.StatusEffectEntry::effect),
						Codec.floatRange(0.0F, 1.0F).optionalFieldOf("probability", 1.0F).forGetter(TieredToolComponent.StatusEffectEntry::probability)
					)
					.apply(instance, TieredToolComponent.StatusEffectEntry::new)
		);
		public static final PacketCodec<RegistryByteBuf, TieredToolComponent.StatusEffectEntry> PACKET_CODEC = PacketCodec.tuple(
			StatusEffectInstance.PACKET_CODEC,
			TieredToolComponent.StatusEffectEntry::effect,
			PacketCodecs.FLOAT,
			TieredToolComponent.StatusEffectEntry::probability,
			TieredToolComponent.StatusEffectEntry::new
		);

		public StatusEffectInstance effect() {
			return new StatusEffectInstance(this.effect);
		}
	}
}