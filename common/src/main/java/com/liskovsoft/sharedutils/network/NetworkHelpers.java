package com.liskovsoft.sharedutils.network;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Minimal stub to provide {@link #getHttpsURLConnection} used by patched
 * ExoPlayer. It just delegates to standard {@link URL#openConnection()}.
 * In the original library this probably injected custom TLS settings, but
 * the default connection is sufficient for normal playback.
 */
public final class NetworkHelpers {
    private NetworkHelpers() {}

    public static HttpURLConnection getHttpsURLConnection(URL url) {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
