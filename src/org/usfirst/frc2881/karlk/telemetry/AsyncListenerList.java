package org.usfirst.frc2881.karlk.telemetry;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Holds a list of {@code TelemetryListener} objects and forwards messages the them off the main thread
 * to avoid blocking the main robot loop.
 */
class AsyncListenerList {
    private final List<TelemetryListener> listeners = new LinkedList<>();
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>(250);
    private volatile int numListeners;

    AsyncListenerList() {
        // Start a thread that delivers the messages off the main thread
        Thread thread = new Thread(this::deliverLoop, "Telemetry");
        thread.setDaemon(true);
        thread.start();
    }

    void add(TelemetryListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            numListeners = listeners.size();
        }
    }

    int size() {
        return numListeners;
    }

    void onMode(Telemetry.Mode mode, boolean enabled) {
        send(new ModeMessage(mode, enabled));
    }

    void onData(String[] headers, double[] data) {
        send(new DataMessage(headers, data));
    }

    private void send(Message message) {
        while (!queue.offer(message)) {
            // If the consumer isn't keeping up then discard stale events
            queue.poll();
        }
    }

    private void deliverLoop() {
        try {
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                Message message = queue.poll(2, TimeUnit.SECONDS);
                synchronized (listeners) {
                    listeners.removeIf(listener -> !deliver(listener, message));
                    numListeners = listeners.size();
                }
            }
        } catch (InterruptedException e) {
            // exit the infinite loop
        }
    }

    /** Forwards a message to a listener.  Returns true if the listener is still alive and wants more messages. */
    private boolean deliver(TelemetryListener listener, Message message) {
        try {
            if (message instanceof ModeMessage) {
                ModeMessage m = (ModeMessage) message;
                return listener.onMode(m.mode, m.enabled);
            } else if (message instanceof DataMessage) {
                DataMessage m = (DataMessage) message;
                return listener.onData(m.headers, m.data);
            } else {
                return listener.ping();
            }
        } catch (Throwable t) {
            synchronized (System.err) {
                System.err.print("Unexpected exception delivering message to telemetry listener: ");
                t.printStackTrace(System.err);
            }
            return false;  // don't send any more messages to this listener
        }
    }

    private interface Message {
    }

    private static class ModeMessage implements Message {
        final Telemetry.Mode mode;
        final boolean enabled;

        ModeMessage(Telemetry.Mode mode, boolean enabled) {
            this.mode = mode;
            this.enabled = enabled;
        }
    }

    private static class DataMessage implements Message {
        final String[] headers;
        final double[] data;

        DataMessage(String[] headers, double[] data) {
            this.headers = headers;
            this.data = data;
        }
    }
}
