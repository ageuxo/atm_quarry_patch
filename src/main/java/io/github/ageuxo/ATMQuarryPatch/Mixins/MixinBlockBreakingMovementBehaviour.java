package io.github.ageuxo.ATMQuarryPatch.Mixins;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.thevortex.allthemodium.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;
import java.util.function.Consumer;

@Mixin(BlockBreakingMovementBehaviour.class)
public abstract class MixinBlockBreakingMovementBehaviour {

    @Redirect(remap = false,
            method = "destroyBlock",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BlockHelper;destroyBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLjava/util/function/Consumer;)V"))
    private void destroyBlock(Level world, BlockPos pos, float effectChance, Consumer<ItemStack> droppedItemCallback) {
        FakePlayer atmQuarryPatchFakePlayer = new FakePlayer((ServerLevel) world, new GameProfile(UUID.randomUUID(), "ATMQuarryPatch FakePlayer"));
        ItemStack usedTool = new ItemStack(ModRegistry.ALLTHEMODIUM_PICKAXE.get());
        BlockHelper.destroyBlockAs(world, pos, atmQuarryPatchFakePlayer, usedTool, effectChance, droppedItemCallback);
    }
}
