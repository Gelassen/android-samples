package com.example.dkazakov.weather;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public class Params {
    private static final String NAMESPACE = Params.class.getName();
    public static class Intent {
        private Intent() {
            // hidden
        }
        public final static String EXTRA_COMMAND = NAMESPACE.concat(".EXTRA_COMMAND");
        public final static String EXTRA_RECEIVER = NAMESPACE.concat(".EXTRA_RECEIVER");
    }
}
