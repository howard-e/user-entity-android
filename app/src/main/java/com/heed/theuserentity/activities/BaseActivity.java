package com.heed.theuserentity.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.net.HttpURLConnection;
import java.net.URL;

public class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getSimpleName();

    private boolean isConnectedToInternet = false;

    private InternetConnectionListener internetConnectionListener;
    private BroadcastReceiver mConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isConnectedToInternet = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isNetworkConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();

            if (isNetworkConnected) { // Does not mean connected to internet, just that connected to some access point
                new AsyncTask<Void, Void, Void>() { // This Async will send a request to a URL and a valid response is what will be used to confirm whether user is connected to internet or not

                    private HttpURLConnection urlConnection;

                    @Override
                    protected Void doInBackground(Void... voids) {
                        // Give this a timeout
                        try {
                            urlConnection = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                            urlConnection.setRequestProperty("User-Agent", "Android");
                            urlConnection.setRequestProperty("Connection", "close");
                            urlConnection.setConnectTimeout(1500);
                            urlConnection.connect();
                            isConnectedToInternet = (urlConnection.getResponseCode() == 204 && urlConnection.getContentLength() == 0);
                            Log.d(TAG, "isConnectedToInternet:" + isConnectedToInternet);
                        } catch (Exception e) {
                            Log.e(TAG, "Error checking internet connection:" + e.getMessage(), e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if (internetConnectionListener != null)
                            internetConnectionListener.onInternetConnectionStatusChanged(isConnectedToInternet);
                        if (urlConnection != null) urlConnection.disconnect();
                    }
                }.execute();
            } else {
                if (internetConnectionListener != null)
                    internetConnectionListener.onInternetConnectionStatusChanged(isConnectedToInternet);
                Log.d(TAG, "No network available!");
            }
        }
    };

    /**
     * To register the receiver to retrieve internet connection status when needed
     * <b>NB:</b> Do not use at the start an activity/or fragment that loads attached to activity and
     * expect an immediate response on connection status (due to AsyncTask delay).
     * <p>
     * In those instances, do the check manually like so to handle the onPostExecute yourself:
     * new AsyncTask<Void, Void, Void>() {
     * boolean isConnected = false;
     * <p>
     * protected Void doInBackground(Void... params) {
     * isConnected = ConnectionHelper.hasInternetAccess(SplashActivity.this);
     * return null;
     * }
     * <p>
     * protected void onPostExecute(Void aVoid) {
     * ConnectionHelper.disconnectConnection();
     * MyActivity.this.isConnected = isConnected;
     * doFollowUpMethod();
     * }
     * }.execute();
     */
    public void registerInternetCheckReceiver(InternetConnectionListener internetConnectionListener) {
        this.internetConnectionListener = internetConnectionListener;
        registerReceiver(mConnectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * To unregister the receiver retrieving the internet connection status when not needed
     */
    public void unregisterInternetCheckReceiver() {
        try {
            unregisterReceiver(mConnectionReceiver);
        } catch (IllegalArgumentException e) {
            //Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * @return A check to see if device is currently connected to the internet
     */
    public boolean isConnectedToInternet() {
        return isConnectedToInternet;
    }

    /**
     * An interface getting the updated connection status
     */
    public interface InternetConnectionListener {
        void onInternetConnectionStatusChanged(boolean isConnected);
    }

    /**
     * To display a simple Indeterminate dialog
     *
     * @param content    The text to be displayed with the dialog
     * @param horizontal true if horizontal bar, circular indeterminate otherwise
     * @return MaterialDialog to be manipulated such as hiding or showing later on
     */
    public MaterialDialog.Builder showIndeterminateProgressDialog(String content, boolean horizontal) {
        return new MaterialDialog.Builder(this)
                .content(content).progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(false).cancelable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterInternetCheckReceiver();
    }
}
