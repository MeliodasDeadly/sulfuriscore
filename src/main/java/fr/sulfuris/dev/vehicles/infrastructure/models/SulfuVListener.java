package fr.sulfuris.dev.vehicles.infrastructure.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import javax.annotation.Nullable;

public abstract class SulfuVListener implements Listener {

    protected Event event;
    protected @Nullable Player player;

    private SulfuVEvent api;

    protected SulfuVListener() {
        this.api = null;
    }

    protected SulfuVListener(SulfuVEvent api) {
        this.api = api;
    }

    protected boolean isCancelled() {
        if (event == null) throw new NullPointerException("Cannot check if event is cancelled if event is null.");

        return getAPI().isCancelled();
    }

    protected SulfuVEvent getAPI() {
        if (api == null) throw new NullPointerException("Event API not specified for this listener.");

        return api;
    }

    protected void setAPI(SulfuVEvent event) {
        this.api = event;
    }

    protected void callAPI() {
        if (api == null) throw new NullPointerException("Event API not specified for this listener.");

        api.setPlayer(player);
        api.call();
    }

    protected void callAPI(@Nullable Player player) throws NullPointerException {
        if (api == null) throw new NullPointerException("Event API not specified for this listener.");

        api.setPlayer(player);
        api.call();
    }
}
