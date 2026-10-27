package com.simibubi.create.content.logistics.chute;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

/**
 * Item handler for block-entity-less inventories (the vanilla composter is the only one in vanilla).
 * The container is re-resolved on every call because a composter swaps its container
 * (input / output / empty) whenever its fill level changes.
 */
public class WorldlyContainerHolderHandler implements IItemHandler {

	private final Level level;
	private final BlockPos pos;
	private final Direction face;

	public WorldlyContainerHolderHandler(Level level, BlockPos pos, Direction face) {
		this.level = level;
		this.pos = pos;
		this.face = face;
	}

	private IItemHandler resolve() {
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof WorldlyContainerHolder holder)
			return new SidedInvWrapper(holder.getContainer(state, level, pos), face);
		return null;
	}

	@Override
	public int getSlots() {
		IItemHandler h = resolve();
		return h == null ? 0 : h.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		IItemHandler h = resolve();
		return h == null ? ItemStack.EMPTY : h.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		IItemHandler h = resolve();
		return h == null ? stack : h.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		IItemHandler h = resolve();
		return h == null ? ItemStack.EMPTY : h.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		IItemHandler h = resolve();
		return h == null ? 0 : h.getSlotLimit(slot);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		IItemHandler h = resolve();
		return h != null && h.isItemValid(slot, stack);
	}
}
