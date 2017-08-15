package com.commonsware.android.arXiv;

import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetworkUtils {
    static String getRedirectUrl(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        boolean redirect = false;
        int status = conn.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }
        String newUrl;
        if (redirect) {
            // get redirect url from "location" header field
            newUrl = conn.getHeaderField("Location");
        }
        else{
            // no need to redirect
            newUrl = url;
        }

        return newUrl;
    }

}
