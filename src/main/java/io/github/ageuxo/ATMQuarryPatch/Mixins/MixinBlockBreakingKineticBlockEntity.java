package io.github.ageuxo.ATMQuarryPatch.Mixins;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;
import java.util.function.Consumer;

@Mixin(BlockBreakingKineticBlockEntity.class)
public abstract class MixinBlockBreakingKineticBlockEntity {

    @Redirect(remap = false,
            method = "onBlockBroken",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BlockHelper;destroyBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLjava/util/function/Consumer;)V"))
    private void destroyBlock(Level world, BlockPos pos, float effectChance, Consumer<ItemStack> droppedItemCallback) {
        BlockHelper.destroyBlockAs(world, pos, new FakePlayer((ServerLevel) world, new GameProfile(UUID.randomUUID(), "ATMQuarryPatch FakePlayer")), new ItemStack(Items.NETHERITE_PICKAXE), effectChance, droppedItemCallback);
    }
}
