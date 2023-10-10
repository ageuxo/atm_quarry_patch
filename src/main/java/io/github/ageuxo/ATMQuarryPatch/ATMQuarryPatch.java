package io.github.ageuxo.ATMQuarryPatch;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod("atm_quarry_patch")
public class ATMQuarryPatch {
    public static final String MODID = "atm_quarry_patch";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final TagKey<Block> BREAKABLE_TAGKEY = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ATMQuarryPatch.MODID, "breakable"));

    public static final UUID FAKE_PLAYER_ID = UUID.randomUUID();
    public static final GameProfile FAKE_PROFILE = new GameProfile(FAKE_PLAYER_ID, "ATMQuarryPatch FakePlayer");

    private static final Map<ServerLevel, FakePlayer> PLAYER_MAP = new HashMap<>();

    public static Player getFakePlayer(Level level){
        if (level instanceof ServerLevel serverLevel){
            FakePlayer fakePlayer;
            if (!PLAYER_MAP.containsKey(level)) {
                fakePlayer = new FakePlayer(serverLevel, FAKE_PROFILE);
                PLAYER_MAP.put(serverLevel, fakePlayer);
            } else {
                fakePlayer = PLAYER_MAP.get(level);
            }
            return fakePlayer;
        } else {
            return Minecraft.getInstance().player;
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
