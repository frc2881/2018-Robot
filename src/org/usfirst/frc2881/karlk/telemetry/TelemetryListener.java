package org.usfirst.frc2881.karlk.telemetry;

interface TelemetryListener {
    boolean onMode(Telemetry.Mode mode, boolean enabled);

    boolean onData(String[] headers, double[] data);

    boolean ping();
}
