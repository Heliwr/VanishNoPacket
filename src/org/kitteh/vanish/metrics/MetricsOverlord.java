package org.kitteh.vanish.metrics;

import org.bukkit.plugin.PluginManager;
import org.kitteh.vanish.VanishPlugin;

public class MetricsOverlord {
    public static Tracker command;
    public static Tracker vanish;
    public static Tracker unvanish;
    public static Tracker toggle;
    public static Tracker startup;
    public static Tracker fakejoin;
    public static Tracker fakequit;
    public static Tracker quitinvis;
    public static Tracker joininvis;
    public static Metrics metrics;

    private static final String[] permsplugins = { "DroxPerms", "GroupManager", "Permissions", "PermissionsBukkit", "PermissionsEx", "Privileges", "SimplyPerms", "Starburst", "bPermissions", "zPermissions" };

    public static void init(VanishPlugin plugin) {
        MetricsOverlord.command = new Tracker("Command");
        MetricsOverlord.vanish = new Tracker("Vanish");
        MetricsOverlord.unvanish = new Tracker("Unvanish");
        MetricsOverlord.toggle = new Tracker("Toggles");
        MetricsOverlord.startup = new Tracker("Startups");
        MetricsOverlord.startup.increment();
        MetricsOverlord.fakejoin = new Tracker("Fake Joins");
        MetricsOverlord.fakequit = new Tracker("Fake Quits");
        MetricsOverlord.quitinvis = new Tracker("Silent Quit");
        MetricsOverlord.joininvis = new Tracker("Silent Join");
        try {
            MetricsOverlord.metrics = new Metrics(plugin);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.command);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.vanish);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.unvanish);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.toggle);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.startup);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.fakejoin);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.fakequit);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.quitinvis);
            MetricsOverlord.metrics.addCustomData(MetricsOverlord.joininvis);
            MetricsOverlord.metrics.createGraph("Permissions Plugins").addPlotter(new Counter(MetricsOverlord.getPermsPlugin(plugin)));
            MetricsOverlord.metrics.createGraph("Online Mode").addPlotter(new Counter(plugin.getServer().getOnlineMode() ? "Online" : "Offline"));
            MetricsOverlord.metrics.createGraph("TagAPI").addPlotter(new Counter(plugin.getServer().getPluginManager().isPluginEnabled("TagAPI") ? "Yup" : "Nope"));
            MetricsOverlord.metrics.start();
        } catch (final Exception e) {
        }
    }

    private static String getPermsPlugin(VanishPlugin plugin) {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        for (final String permsplugin : MetricsOverlord.permsplugins) {
            if (pluginManager.isPluginEnabled(permsplugin)) {
                return permsplugin;
            }
        }
        return "Unknown or None";
    }
}
