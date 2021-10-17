package com.devh.common.util;

public class OSUtils {
    private final String mOs;

    /* Singleton */
    private static OSUtils instance;
    public static OSUtils getInstance() {
        if(instance == null)
            instance = new OSUtils();
        return instance;
    }
    /* Singleton */

    /* Constructor */
    public OSUtils() {
        this.mOs = System.getProperty("os.name").toLowerCase();
    }
    /* Constructor */

    public boolean isWindows() {
        return this.mOs.contains("win");
    }
    public boolean isMac() {
        return this.mOs.contains("mac");
    }
    public boolean isUnix() {
        return this.mOs.contains("nix") ||
                this.mOs.contains("nux") ||
                this.mOs.contains("aix") ||
                this.mOs.contains("hp-ux");
    }
    public boolean isSolaris() {
        return this.mOs.contains("sunos");
    }
}
