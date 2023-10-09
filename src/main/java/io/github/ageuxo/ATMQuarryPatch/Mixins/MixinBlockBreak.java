package io.github.ageuxo.ATMQuarryPatch.Mixins;

import com.thevortex.allthemodium.events.BlockBreak;
import com.thevortex.allthemodium.registry.TagRegistry;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockBreak.class)
public class MixinBlockBreak {

    /**
     * @author ageuxo
     * @reason Strip out everything except The Other dim protection
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public static void on(BlockEvent.BreakEvent event){
        if (!event.getPlayer().isCreative()){
            if (event.getState().is(TagRegistry.OTHER_PROTECTION) && (event.getPlayer() instanceof FakePlayer || event.getPlayer() == null) && event.getLevel().getBiome(event.getPos()).is(TagRegistry.OTHER_BIOMES)) {
                event.setCanceled(true);
            }
        }
    }
}
