package fr.sulfuris.dev;

import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.util.Vector;

import java.util.Random;

public class Utils {
    public static String chat(final String s) {
        return s.replace("&", "§");
    }

    public static Boolean chanceOf(final int percentage) {
        final int rndom = (int)(Math.random() * 100.0);
        if (rndom < percentage) {
            return true;
        }
        return false;
    }

    public static float randFloat(float min, float max) {
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;

    }

    public static String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return String.valueOf(l.getWorld().getName()) + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    public static Location getLocationString(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, (double)x, (double)y, (double)z);
        }
        return null;
    }

    public static void drawLine(Particle particle, Location point1, Location point2, double space, Color color) {
        World world = point1.getWorld();
        Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1f);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            world.spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(), 1, 0f, 0f, 0f, 0, dustOptions);
            length += space;
        }
    }
}
