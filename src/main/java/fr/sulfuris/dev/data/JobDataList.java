package fr.sulfuris.dev.data;

import fr.sulfuris.dev.main;

import java.util.List;

public class JobDataList {
    public static String getJob(final String s) {
        return s.replace(s, main.getPlugin().getConfig().getString(s));
    }
    public static List<String> getlist(){
        return main.getPlugin().getConfig().getStringList(Math.random() + "");
    }
}
