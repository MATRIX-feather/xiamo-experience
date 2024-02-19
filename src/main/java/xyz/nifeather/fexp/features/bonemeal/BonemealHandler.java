package xyz.nifeather.fexp.features.bonemeal;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Lidded;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBlock;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import xiamomc.pluginbase.Annotations.Initializer;
import xiamomc.pluginbase.Bindables.Bindable;
import xyz.nifeather.fexp.FPluginObject;
import xyz.nifeather.fexp.FeatherExperience;
import xyz.nifeather.fexp.MaterialTypes;
import xyz.nifeather.fexp.config.ConfigOption;
import xyz.nifeather.fexp.config.FConfigManager;
import xyz.nifeather.fexp.features.bonemeal.handlers.CoralHandler;
import xyz.nifeather.fexp.features.bonemeal.handlers.FlowerHandler;
import xyz.nifeather.fexp.features.bonemeal.handlers.SugarcaneHandler;

import java.util.Arrays;
import java.util.List;

public class BonemealHandler extends FPluginObject
{
    public BonemealHandler()
    {
        registerRange(new FlowerHandler(), new SugarcaneHandler(), new CoralHandler());
    }

    private final List<IBonemealHandler> bonemealHandlers = new ObjectArrayList<>();

    /**
     * Register a sub handler to this handler
     * @param handler The target handler to register
     * @return Whether this operation performs successfully
     */
    public boolean register(IBonemealHandler handler)
    {
        if (bonemealHandlers.stream().anyMatch(h -> h.getIdentifier().equalsIgnoreCase(handler.getIdentifier())))
            return false;

        bonemealHandlers.add(handler);
        return true;
    }

    public boolean registerRange(List<IBonemealHandler> handlers)
    {
        var allSuccess = true;

        for (var handler : handlers)
        {
            allSuccess = register(handler) && allSuccess;
        }

        return allSuccess;
    }

    public boolean registerRange(IBonemealHandler... handlers)
    {
        return registerRange(Arrays.stream(handlers).toList());
    }

    public void unRegister(String id)
    {
        var handler = bonemealHandlers.stream()
                .filter(h -> h.getIdentifier().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (handler == null) return;

        unRegister(handler);
    }

    public void unRegister(IBonemealHandler handler)
    {
        if (!bonemealHandlers.contains(handler)) return;

        bonemealHandlers.remove(handler);
    }

    /**
     * @return 执行是否成功
     */
    public boolean onBonemeal(ItemStack stack, Block targetBlock)
    {
        if (stack.getType() != Material.BONE_MEAL) return false;

        boolean executeSuccess = false;

        for (var handler : bonemealHandlers)
        {
            try
            {
                if (handler.onBonemeal(targetBlock))
                {
                    executeSuccess = true;
                    break;
                }
            }
            catch (Throwable t)
            {
                logger.error("Handler '%s' thrown an error while handling bone meal: %s".formatted(handler.getIdentifier(), t.getMessage()));
                t.printStackTrace();
            }
        }

        return executeSuccess;
    }
}
