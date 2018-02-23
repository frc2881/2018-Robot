package org.usfirst.frc2881.karlk.telemetry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class Telemetry {
    public enum Mode {AUTO, TELEOP, TEST}

    private final Map<String, DoubleSupplier> metrics = new LinkedHashMap<>();
    private final AsyncListenerList listeners = new AsyncListenerList();
    private String[] headers;
    private long lastModeChange = System.nanoTime();
    private double[] lastData;
    private int consecutiveUnchanged;

    public Telemetry() {
        addMetric("time", () -> (System.nanoTime() - lastModeChange) / 1_000_000_000.0);
    }

    /**
     * Registers a new metric for telemetry.  The robot should do this when the program starts.
     */
    public void addMetric(String name, DoubleSupplier valueFn) {
        metrics.put(name, valueFn);
        headers = null;
        lastData = null;
    }

    /**
     * Registers a new listener to receive telemetry updates.
     */
    public void addListener(TelemetryListener listener) {
        listeners.add(listener);
    }

    public int getNumListeners() {
        return listeners.size();
    }

    /**
     * Sends a "mode" event to browsers.
     */
    public void onMode(Mode mode, boolean enabled) {
        listeners.onMode(mode, enabled);

        lastModeChange = System.nanoTime();
        lastData = null;
    }

    /**
     * Captures a snapshot of the current telemetry values and sends the data to telemetry listeners.
     */
    public void periodic() {
        if (listeners.size() == 0) {
            return;  // skip a bunch of work if nobody is paying attention...
        }

        if (headers == null) {
            headers = metrics.keySet().toArray(new String[0]);
        }

        double[] data = metrics.values().stream().mapToDouble(DoubleSupplier::getAsDouble).toArray();

        // Suppress events if nothing has changed in a while. Note: skip the "time" column when checking for changes
        if (lastData == null || !rangeEquals(data, lastData, 1, data.length)) {
            lastData = data;
            consecutiveUnchanged = 0;
        } else if (++consecutiveUnchanged >= 50) {
            return;  // bot is idle, pause updates for now
        }

        listeners.onData(headers, data);
    }

    @SuppressWarnings("SameParameterValue")
    private boolean rangeEquals(double[] a, double[] b, int offset, int end) {
        for (int i = offset; i < end; i++) {
            if (a[i] != b[i] && !(Double.isNaN(a[i]) && Double.isNaN(b[i]))) {
                return false;
            }
        }
        return true;
    }
}
