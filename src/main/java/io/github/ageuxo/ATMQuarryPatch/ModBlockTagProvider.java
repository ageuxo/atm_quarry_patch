package io.github.ageuxo.ATMQuarryPatch;

import com.thevortex.allthemodium.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ATMQuarryPatch.BREAKABLE_TAGKEY).add(ModRegistry.ALLTHEMODIUM_ORE.get(), ModRegistry.ALLTHEMODIUM_SLATE_ORE.get(), ModRegistry.VIBRANIUM_ORE.get(), ModRegistry.UNOBTAINIUM_ORE.get());
    }
}
