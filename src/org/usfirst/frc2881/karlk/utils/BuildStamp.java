package org.usfirst.frc2881.karlk.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Reads the file "build-stamp.txt" that should have been created during deploy by the
 * "deploy-to-robot" ant task in build.xml.
 */
public class BuildStamp {
    public static String read() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                BuildStamp.class.getClassLoader().getResourceAsStream("build-stamp.txt"), UTF_8))) {
            return in.readLine();
        } catch (Exception e) {
            return "<unknown>";
        }
    }
}
