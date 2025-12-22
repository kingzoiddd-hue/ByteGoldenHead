package dev.zoid.bytegoldenhead;

import dev.zoid.bytegoldenhead.cmds.GoldenHeadCmd;
import dev.zoid.bytegoldenhead.item.GoldenHead;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class ByteGoldenHead extends JavaPlugin {

    @Override
    public void onEnable() {
        GoldenHead.init();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, GoldenHeadCmd::register);
        logStartupArt();
    }

    private void logStartupArt() {
        String[] art = {
                "    ___      __     ",
                "   / _ )__ __/ /____ ",
                "  / _  / // / __/ -_)    GoldenHead",
                "  /____/\\_, /\\__/\\__/    discord.gg/DRf6n5tR",
                "       /___/        "
        };

        for (String line : art) {
            this.getComponentLogger().info(line);
        }
    }
}