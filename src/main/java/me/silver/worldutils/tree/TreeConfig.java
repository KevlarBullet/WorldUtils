package me.silver.worldutils.tree;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeConfig {

    private String treeType;
    private String treeBase;
    private double rotMin;
    private double rotMax;
    private int baseWidth;

    private Map<Character, List<String>> rules = new HashMap<>();

    public TreeConfig(String fileName, String filePath) {

    }

    public void loadConfig(String fileName, String filePath) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(filePath, fileName));

        treeType = config.getKeys(false).iterator().next();
//        treeType = config.getName();
        treeBase = config.getString(treeType + ".base");
        rotMin = config.getDouble(treeType + ".rotation.min");
        rotMax = config.getDouble(treeType + ".rotation.max");
        List<String> rules = config.getStringList(treeType + ".rules");
    }
}
