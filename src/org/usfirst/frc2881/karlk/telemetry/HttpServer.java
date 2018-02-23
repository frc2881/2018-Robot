package org.usfirst.frc2881.karlk.telemetry;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.logging.Level;

/**
 * Web server for delivering real-time telemetry data to web browers and other clients on the network.
 */
public class HttpServer {
    private final Telemetry telemetry;
    private final NanoHTTPD httpd;

    public HttpServer(Telemetry telemetry) {
        this(telemetry, 1190);
    }

    public HttpServer(Telemetry telemetry, int port) {
        this.telemetry = telemetry;
        this.httpd = new NanoHTTPD(port) {
            @Override
            public Response serve(IHTTPSession session) {
                try {
                    String uri = session.getUri();
                    System.out.printf("HTTP %s %s%n", session.getMethod(), uri);
                    if (session.getMethod() == Method.GET) {
                        switch (uri) {
                            case "/":
                                // Redirect to the main web page
                                Response response = newFixedLengthResponse(Status.TEMPORARY_REDIRECT, null, null);
                                response.addHeader("location", "index.htm");
                                return response;

                            case "/telemetry":
                                // Live stream of telemetry event data
                                return newListenerResponse(session);
                        }
                        // Serve static files
                        InputStream resource = getClass().getResourceAsStream("static" + uri);
                        if (resource != null) {
                            return newChunkedResponse(Status.OK, getMimeTypeForFile(uri), resource);
                        }
                    }
                    return super.serve(session);  // not found
                } catch (Exception e) {
                    String stackTrace = getStackTraceAsString(e);
                    System.err.println("Unexpected exception serving response: " + stackTrace);
                    return newFixedLengthResponse(Status.INTERNAL_ERROR, "text/plain", stackTrace);
                }
            }

            @Override
            protected boolean useGzipWhenAccepted(Response r) {
                return false;  // definitely not for SSE events, probably not necessary for plain files
            }
        };
    }

    public void start() {
        try {
            httpd.start();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        NanoHTTPD.LOG.setLevel(Level.WARNING);
    }

    private Response newListenerResponse(IHTTPSession session) throws Exception {
        PipedInputStream pipeIn = new PipedInputStream();
        PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
        telemetry.addListener(new HttpSseListener(telemetry, pipeOut));
        Response response = NanoHTTPD.newChunkedResponse(Status.OK, "text/event-stream", pipeIn);
        String origin = session.getHeaders().get("origin");
        if (origin != null) {
            response.addHeader("access-control-allow-origin", origin);
        }
        return response;
    }

    private static String getStackTraceAsString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
