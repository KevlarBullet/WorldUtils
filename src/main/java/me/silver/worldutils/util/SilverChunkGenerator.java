package me.silver.worldutils.util;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

public class SilverChunkGenerator extends ChunkGenerator {

    private double scale = 0.004;
    private double frequency = 1.8;
    private double amplitude = 0.4;
    private double exponent = 2.25;

    private static final int SEA_LEVEL = 82;
    private static final int MAX_HILL_HEIGHT = 40;
    private static final int GROUND_OFFSET = 62;

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {

        ChunkData chunkData = this.createChunkData(world);
        // Octaves determine how many passes the generator makes on the noise
        // Higher values add more layers of detail
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        // The size of the noise map
        // Lower values create wider/taller hills
        generator.setScale(this.scale);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // More magic values to be deciphered later
                // - Frequency: Changes the horizontal size of each subsequent octave
                //      Higher values create narrower/more defined details
                // - Amplitude: Determines the height of each wave
                //      Lower values create taller hills
                // - Normalize:
                //
                // - MAX_HILL_HEIGHT: The max height difference for hills
                // - GROUND_OFFSET: Bringing the land up to a more normal height
                double noise = generator.noise(chunkX * 16 + x, chunkZ * 16 + z, this.frequency, this.amplitude, true) + 1;

//                int y = (int) ((Math.atan(5 * noise) / 180 + 0.5) * MAX_HILL_HEIGHT + GROUND_OFFSET);

                double noiseY = Math.pow(noise, noise) * 40 + 62;

                int y = (int)noiseY;
//                Material material;
//
//                if (noiseY > SEA_LEVEL + 7) {
//                    if (noiseY % 7 > 4) {
//                        y = (int) Math.round(noiseY + (7 - noiseY % 7) + 0.3);
//                        material = Material.GRASS_PATH;
//                    } else {
//                        y = (int) Math.round(noiseY + ((noiseY % 7) * 1.85) + 0.3);
//                        material = Material.MYCEL;
//                    }
//                } else {
//                    y = (int) noiseY;
//                    material = Material.GRASS;
//                }


                if (y >= SEA_LEVEL) {
                    chunkData.setBlock(x, y, z, Material.GRASS);
                } else {
                    chunkData.setBlock(x, y, z, Material.DIRT);
                }

                for (int stone = y - 1; stone > 0; stone--) {
                    chunkData.setBlock(x, stone, z, Material.STONE);
                }

                for (int water = y + 1; water < SEA_LEVEL + 1; water++) {
                    chunkData.setBlock(x, water, z, Material.WATER);
                }
            }
        }

        return chunkData;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public void setExponent(double exponent) {
        this.exponent = exponent;
    }

}
