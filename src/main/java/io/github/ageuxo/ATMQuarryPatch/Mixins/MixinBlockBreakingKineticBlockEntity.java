package io.github.ageuxo.ATMQuarryPatch.Mixins;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.thevortex.allthemodium.registry.ModRegistry;
import io.github.ageuxo.ATMQuarryPatch.ATMQuarryPatch;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(BlockBreakingKineticBlockEntity.class)
public abstract class MixinBlockBreakingKineticBlockEntity {

    @Redirect(remap = false,
            method = "onBlockBroken",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BlockHelper;destroyBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLjava/util/function/Consumer;)V"))
    private void destroyBlock(Level world, BlockPos pos, float effectChance, Consumer<ItemStack> droppedItemCallback) {
        ItemStack usedTool = new ItemStack(ModRegistry.ALLTHEMODIUM_PICKAXE.get());
        BlockHelper.destroyBlockAs(world, pos, ATMQuarryPatch.getFakePlayer(world), usedTool, effectChance, droppedItemCallback);    }
}
