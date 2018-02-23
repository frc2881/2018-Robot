package org.usfirst.frc2881.karlk.telemetry;

class Json {

    static void appendField(StringBuilder buf, String key, Object value) {
        append(buf, key);
        buf.append(':');
        append(buf, value);
    }

    static void append(StringBuilder buf, Object obj) {
        if (obj == null) {
            buf.append("null");
        } else if (obj instanceof Double || obj instanceof Float) {
            buf.append(((Number) obj).doubleValue());
        } else if (obj instanceof Number || obj instanceof Boolean) {
            buf.append(obj);
        } else {
            // Note: this doesn't check for Map and List etc because nothing uses them yet
            append(buf, obj.toString());
        }
    }

    static void append(StringBuilder buf, String[] strings) {
        buf.append('[');
        for (int i = 0; i < strings.length; i++) {
            if (i != 0) {
                buf.append(',');
            }
            append(buf, strings[i]);
        }
        buf.append(']');
    }

    static void append(StringBuilder buf, double[] values) {
        buf.append('[');
        for (int i = 0; i < values.length; i++) {
            if (i != 0) {
                buf.append(',');
            }
            append(buf, values[i]);
        }
        buf.append(']');
    }

    static void append(StringBuilder buf, double n) {
        if (Double.isNaN(n) || Double.isInfinite(n)) {
            append(buf, Double.toString(n));
        } else {
            buf.append(n);
        }
    }

    static void append(StringBuilder buf, String string) {
        buf.append('"');
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (ch == '"' || ch == '\\') {
                buf.append('\\').append(ch);
            } else if (ch < ' ') {
                buf.append(escape(ch));
            } else {
                buf.append(ch);
            }
        }
        buf.append('"');
    }

    private static String escape(char ch) {
        switch (ch) {
            case '\b':
                return "\\b";
            case '\f':
                return "\\f";
            case '\n':
                return "\\n";
            case '\r':
                return "\\r";
            case '\t':
                return "\\t";
            default:
                return String.format("\\u%04X", ch & 0xff);
        }
    }
}
