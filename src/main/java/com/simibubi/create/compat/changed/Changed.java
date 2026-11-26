package com.simibubi.create.compat.changed;

import net.ltxprogrammer.changed.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;

public class Changed {

	private static Boolean loaded;

	public static boolean isLoaded() {
		if (loaded == null)
			loaded = ModList.get().isLoaded("changed");
		return loaded;
	}

	/** Visible Changed body for a transfurred player, otherwise the entity itself. */
	public static LivingEntity resolveBody(LivingEntity entity) {
		if (!isLoaded())
			return entity;
		return Impl.resolve(entity);
	}

	private static class Impl {
		static LivingEntity resolve(LivingEntity entity) {
			return EntityUtil.maybeGetOverlaying(entity);
		}
	}
}
