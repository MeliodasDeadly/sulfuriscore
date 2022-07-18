package fr.sulfuris.dev.data;

import fr.sulfuris.dev.main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JobDataList {
    public static String getJob(final String s) {
        return s.replace(s, main.getPlugin().getConfig().getString(s));
    }
}
