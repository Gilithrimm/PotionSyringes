package com.safenar.potion_syringes.items;

import com.safenar.potion_syringes.PotionSyringes;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Syringe extends PotionItem {

	public Syringe(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity
				? (PlayerEntity) user
				: null;
		if (playerEntity instanceof ServerPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
		}

		if (!world.isClient) {
			List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);

			for (StatusEffectInstance sei : list) {
				if (sei.getEffectType().isInstant()) {
					sei.getEffectType().applyInstantEffect(playerEntity,
							playerEntity,
							user,
							sei.getAmplifier(),
							1.0);
				} else {
					int d = sei.mapDuration(duration -> (int) (duration * .75));
					user.addStatusEffect(new StatusEffectInstance(sei.getEffectType(),
							d,
							sei.getAmplifier(),
							sei.isAmbient(),
							sei.shouldShowParticles(),
							sei.shouldShowIcon()));
				}
			}
		}

		if (playerEntity != null) {
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
			if (!playerEntity.getAbilities().creativeMode) {
				stack.decrement(1);
			}
		}

		if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
			if (stack.isEmpty()) {
				return new ItemStack(PotionSyringes.EMPTY_SYRINGE);
			}

			if (playerEntity != null) {
				playerEntity.getInventory().insertStack(new ItemStack(PotionSyringes.EMPTY_SYRINGE));
			}
		}

		return stack;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 1;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return ActionResult.PASS;
	}
}
