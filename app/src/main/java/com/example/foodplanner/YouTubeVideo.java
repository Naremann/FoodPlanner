package com.example.foodplanner;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public abstract class YouTubeVideo {
    private static String extractVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("https://www.youtube.com/")) {
            String[] splitUrl = youtubeUrl.split("v=");
            if (splitUrl.length > 1) {
                videoId = splitUrl[1];
            }
        }
        return videoId;
    }

    public static void loadVideoUrlInWebView(WebView webView, String videoUrl, ProgressBar progressBar){
        String videoId = extractVideoId(videoUrl);

        // Create the embed URL
        String embedUrl = "https://www.youtube.com/embed/" + videoId;

        // Enable JavaScript in the WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable hardware acceleration for better performance
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        // Load the YouTube video using the embed URL
        webView.loadUrl(embedUrl);

    }

}
