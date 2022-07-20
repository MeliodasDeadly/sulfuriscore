package fr.sulfuris.dev.data;

import fr.sulfuris.dev.Main;

import java.util.List;

public class JobDataList {
    public static String getJob(final String s) {
        return s.replace(s, Main.getPlugin().getConfig().getString(s));
    }
    public static List<String> getlist(){
        return Main.getPlugin().getConfig().getStringList(Math.random() + "");
    }
}
