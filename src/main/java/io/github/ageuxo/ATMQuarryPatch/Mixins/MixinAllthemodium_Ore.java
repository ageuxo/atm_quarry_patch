package io.github.ageuxo.ATMQuarryPatch.Mixins;

import com.thevortex.allthemodium.blocks.Allthemodium_Ore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Allthemodium_Ore.class)
public abstract class MixinAllthemodium_Ore extends RedStoneOreBlock {

    public MixinAllthemodium_Ore(Properties p_55453_) {
        super(p_55453_);
    }


    /**
     * @author ageuxo
     * @reason Completely remove restriction on breaking the block for FakePlayers
     */
    @Overwrite(remap = false)
    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity player){
        return super.canEntityDestroy(state, world, pos, player);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        if (player instanceof FakePlayer){
            return false;
        } else {
            return super.canHarvestBlock(state, level, pos, player);
        }
    }

    @ModifyArg( method = "<init>()V",
            at = @At( value = "INVOKE",
                target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;" ),
            index = 0)
    private static float allTheModium_break_patch$getStrength(float p_60914_){
        return 19;
    }

}
