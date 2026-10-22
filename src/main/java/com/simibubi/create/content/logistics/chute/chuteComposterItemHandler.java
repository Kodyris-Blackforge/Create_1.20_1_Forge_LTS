package com.simibubi.create.content.logistics.chute;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

/**
 * Exposes a vanilla composter (no block entity) as an IItemHandler.
 * The composter's container changes class with its fill level, so it is
 * re-fetched on every call instead of being cached.
 */
public class ComposterItemHandler implements IItemHandler {

	private final Level level;
	private final BlockPos pos;
	private final Direction face;

	public ComposterItemHandler(Level level, BlockPos pos, Direction face) {
		this.level = level;
		this.pos = pos;
		this.face = face;
	}

	private IItemHandler inner() {
		BlockState state = level.getBlockState(pos);
		if (!(state.getBlock() instanceof ComposterBlock composter))
			return null;
		WorldlyContainer container = composter.getContainer(state, level, pos);
		return new SidedInvWrapper(container, face);
	}

	@Override
	public int getSlots() {
		IItemHandler h = inner();
		return h == null ? 0 : h.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		IItemHandler h = inner();
		return h == null ? ItemStack.EMPTY : h.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		IItemHandler h = inner();
		return h == null ? stack : h.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		IItemHandler h = inner();
		return h == null ? ItemStack.EMPTY : h.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		IItemHandler h = inner();
		return h == null ? 0 : h.getSlotLimit(slot);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		IItemHandler h = inner();
		return h != null && h.isItemValid(slot, stack);
	}
}
