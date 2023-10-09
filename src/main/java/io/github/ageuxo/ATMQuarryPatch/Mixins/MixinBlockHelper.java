package io.github.ageuxo.ATMQuarryPatch.Mixins;

import com.simibubi.create.foundation.utility.BlockHelper;
import io.github.ageuxo.ATMQuarryPatch.ATMQuarryPatch;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(BlockHelper.class)
public abstract class MixinBlockHelper {

    @Redirect(method = "destroyBlockAs",
                at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/world/level/block/Block;getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;"))
    private static List<ItemStack> getDrops(BlockState pState, ServerLevel pLevel, BlockPos pPos, BlockEntity pBlockEntity, Entity pEntity, ItemStack pTool){
        if (pState.is(ATMQuarryPatch.BREAKABLE_TAGKEY) && pEntity instanceof FakePlayer){
            return List.of();
        } else {
            return Block.getDrops(pState, pLevel, pPos, pBlockEntity, pEntity, pTool);
        }
    }


}
