package org.tough_environment.util;

public class WorldGenerationState {

    private static boolean worldGeneration = true;  // Default state: world generation is ongoing.

    // Method to set the world generation state
    public static void setWorldGeneration(boolean isGenerating) {
        worldGeneration = isGenerating;
    }

    // Method to check if we are in world generation
    public static boolean isWorldGeneration() {
        return worldGeneration;
    }

    // Hook method: Call this method during chunk generation
    public static void onWorldGenerationStart() {
        setWorldGeneration(true);  // Set the flag to true when world generation starts
    }

    // Hook method: Call this method when world generation ends
    public static void onWorldGenerationEnd() {
        setWorldGeneration(false);  // Set the flag to false when world generation ends
    }
}
