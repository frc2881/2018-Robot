package org.usfirst.frc2881.karlk.telemetry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Sends a live stream of telemetry data using the <a href="https://en.wikipedia.org/wiki/Server-sent_events">Server-Sent-Events</a>
 * protocol so a browser may consume the events using the <a href="https://developer.mozilla.org/en-US/docs/Web/API/EventSource">EventSource</a>
 * JavaScript object that's built-in to modern browsers.
 */
class HttpSseListener implements TelemetryListener {
    private final Telemetry telemetry;
    private final OutputStream out;
    private String[] headers;

    HttpSseListener(Telemetry telemetry, OutputStream out) {
        this.telemetry = telemetry;
        this.out = out;
    }

    @Override
    public boolean onMode(Telemetry.Mode mode, boolean enabled) {
        StringBuilder buf = new StringBuilder();
        buf.append("event: mode\n");
        buf.append("data: {");
        Json.appendField(buf, "mode", mode);
        buf.append(',');
        Json.appendField(buf, "enabled", enabled);
        buf.append("}\n\n");
        return send(buf.toString());
    }

    @Override
    public boolean onData(String[] headers, double[] data) {
        StringBuilder buf = new StringBuilder();
        if (this.headers == null || !Arrays.equals(headers, this.headers)) {
            this.headers = headers;
            buf.append("event: headers\n");
            buf.append("data: ");
            Json.append(buf, headers);
            buf.append("\n\n");
        }
        buf.append("event: data\n");
        buf.append("data: ");
        Json.append(buf, data);
        buf.append("\n\n");
        return send(buf.toString());
    }

    @Override
    public boolean ping() {
        return send(": .\n\n");
    }

    private boolean send(String message) {
        try {
            out.write(message.getBytes(UTF_8));
            return true;
        } catch (IOException e) {
            System.out.printf("Telemetry listener disconnected (%d remaining): %s%n",
                    telemetry.getNumListeners() - 1, e.getMessage());
            safeClose(out);
            return false;  // no more messages please!
        }
    }

    private void safeClose(OutputStream out) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            // Ignore
        }
    }
}
