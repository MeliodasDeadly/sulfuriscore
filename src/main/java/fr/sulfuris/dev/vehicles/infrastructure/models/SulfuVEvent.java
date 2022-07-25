package fr.sulfuris.dev.vehicles.infrastructure.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract class for the plugin's API events
 */
public abstract class SulfuVEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    /**
     * Whether the event is cancelled
     *
     * @see #isCancelled()
     */
    protected boolean cancelled = false;
    private @Nullable Player player;

    /**
     * Method required in every event
     */
    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Call this event (to other plugins)
     */
    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }

    /**
     * Check whether the event is cancelled.
     * If the event isn't cancellable, always returns false
     *
     * @see fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Get event-player
     *
     * @return Event-player or "null" if player is not specified
     */
    public @Nullable Player getPlayer() {
        return player;
    }

    /**
     * Set event-player
     *
     * @param player Set event-player (use "null" if player is not specified)
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
