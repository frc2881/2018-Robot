package org.usfirst.frc2881.karlk.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Reads the file "build-stamp.txt" that should have been created during deploy by the
 * "deploy-to-robot" ant task in build.xml.
 */
public class BuildStamp {
    public static final String VERSION;
    public static final String DESCRIPTION;

    static {
        String version = null, description = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                BuildStamp.class.getClassLoader().getResourceAsStream("build-stamp.txt"), UTF_8))) {
            version = in.readLine();
            description = in.readLine();
        } catch (Exception e) {
            // ignore
        }
        VERSION = Objects.toString(version, "<unknown>");
        DESCRIPTION = Objects.toString(description, "");
    }
}
