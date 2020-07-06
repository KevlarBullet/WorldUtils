package me.silver.worldutils.tree;

import me.silver.worldutils.util.Utils;
import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Stack;

// This really doesn't have to have instances - it's more of a utility class
public class TreeGenerator {

    public static HashMap<Character, String> rules = new HashMap<>();

    // A String representing how the tree should be built (Ex. F[+F[-F]F]F[[+F][-F]]
    // Current grammar:
    // F: Draw a line from the current top location, update current top location in stack to this
    // [: Push a new location to the top of the stack
    // ]: Pop top location from the stack
    // +: Yaw right
    // -: Yaw left
    // ^: Pitch up
    // &: Pitch down
    // !: Decrease width // Still need to implement this
    // L: Create leaf
    // Anything else will be counted as a placeholder to be replaced by a rule
    private String treeString;
    private final int iterations;

    private final Stack<Location> treeNodes = new Stack<>();

    public TreeGenerator(String treeString, int iterations) {
        this.treeString = treeString;
        this.iterations = iterations;
    }

    public void buildTree(Location origin) {
        if (iterations > 1) {
            updateString();
        }

        treeNodes.push(origin);
        origin.setPitch(-90f);
        origin.setYaw(0f);

        for (char c : treeString.toLowerCase().toCharArray()) {
            Location currentLocation = treeNodes.peek();

            switch (c) {
                case 'f':
                    Vec3D o = new Vec3D(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ());
                    // Draw a line 4 blocks long starting at the origin block
                    Vec3D e = o.e(Utils.fromPitchYaw(currentLocation.getPitch(), currentLocation.getYaw()).a(4d));
                    Location endLocation = new Location(currentLocation.getWorld(), e.x, e.y, e.z, currentLocation.getYaw(), currentLocation.getPitch());

                    Utils.createLine(currentLocation, endLocation);

                    currentLocation.setX(e.x);
                    currentLocation.setY(e.y);
                    currentLocation.setZ(e.z);
                    break;
                case '[':
                    treeNodes.push(treeNodes.peek().clone());
                    break;
                case ']':
                    treeNodes.pop();
                    break;
                case '+':
                    currentLocation.setYaw(currentLocation.getYaw() + 45);
                    break;
                case '-':
                    currentLocation.setYaw(currentLocation.getYaw() - 45);
                    break;
                case '^':
                    currentLocation.setPitch(currentLocation.getPitch() + 45);
                    break;
                case '&':
                    currentLocation.setPitch(currentLocation.getPitch() - 45);
                    break;
                case '!':
                    // TODO: Implement width
                    break;
                case 'L':
                    o = new Vec3D(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ());
                    e = o.e(Utils.fromPitchYaw(currentLocation.getPitch(), currentLocation.getYaw()).a(4d));
                    endLocation = new Location(currentLocation.getWorld(), e.x, e.y, e.z, currentLocation.getYaw(), currentLocation.getPitch());

                    endLocation.getBlock().setType(Material.LEAVES);
            }
        }

//        Vec3D o = new Vec3D(origin.getX(), origin.getY(), origin.getZ());
//        // Draw a line 4 blocks long starting at the origin block
//        Vec3D e = o.e(Utils.fromPitchYaw(-45f, -90f).a(8d));
//
//        Location destination = new Location(origin.getWorld(), e.x, e.y, e.z);
//
//        Utils.createLine(origin, destination);
    }

    public void updateString() {
        StringBuilder builder = new StringBuilder();
        String ts = this.treeString;

        for (int i = 0; i < this.iterations; i++) {
            for (char c : ts.toLowerCase().toCharArray()) {
                String rule = rules.get(c);

                if (rule != null) {
                    builder.append(rules.get(c));
                } else {
                    builder.append(c);
                }
            }

            ts = builder.toString();
        }

        this.treeString = builder.toString();
    }
}
