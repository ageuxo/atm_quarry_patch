package io.github.ageuxo.ATMQuarryPatch;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod("atm_quarry_patch")
public class ATMQuarryPatch {
    public static final String MODID = "atm_quarry_patch";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final TagKey<Block> BREAKABLE_TAGKEY = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ATMQuarryPatch.MODID, "breakable"));


    @Mod.EventBusSubscriber
    public static class Events{
        @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOWEST)
        public static void onBlockBreak(BlockEvent.BreakEvent event){
            BlockState state = event.getState();
            LOGGER.debug("{} {} {}", event.isCanceled(), event.getPlayer(), state);
            if (state.is(BREAKABLE_TAGKEY)) {
                event.setCanceled(false);
                LOGGER.debug("{}", event.isCanceled());
            }
        }
    }
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents{
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event){
            DataGenerator generator = event.getGenerator();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider(true, new ModBlockTagProvider(generator, MODID, existingFileHelper));
        }
    }
}
