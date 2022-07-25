package fr.sulfuris.dev.vehicles.infrastructure.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SulfuVEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    protected boolean cancelled = false;
    private @Nullable Player player;

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
