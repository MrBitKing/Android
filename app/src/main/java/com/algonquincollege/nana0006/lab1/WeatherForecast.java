package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Weather Forecast";
    private ProgressBar progress;
    private ImageView weatherImage;
    private TextView currentTempText;
    private TextView minTempText;
    private TextView maxTempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        weatherImage = (ImageView) findViewById(R.id.weatherImage);
        currentTempText = (TextView) findViewById(R.id.currentWeather);
        minTempText = (TextView) findViewById(R.id.minTemp);
        maxTempText = (TextView) findViewById(R.id.maxTemp);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        progress.setMax(100);
        new ForecastQuery().execute();
    }


    //inner class
    class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemp;
        private String maxTemp;
        private String currTemp;
        private String currWeather;
        private Bitmap bitmap;

        @Override
        protected String doInBackground(String... args) {
            InputStream stream;

            //checking network connectivity
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.i(ACTIVITY_NAME, "Device is connected to network");
            } else {
                Log.e(ACTIVITY_NAME, "No network connection is available");
            }

            // connecting to url and reading data input stream
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

                //  URL url = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); //in milliseconds
                conn.setConnectTimeout(15000); //in millisenconds
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                //test
                Log.d(ACTIVITY_NAME, "connecting with url..");
                conn.connect();
                //test
                Log.d(ACTIVITY_NAME, "reading stream");
                stream = conn.getInputStream();
                //test
                Log.d(ACTIVITY_NAME, "stream is: " + stream);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(stream, null);
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType != XmlPullParser.START_TAG) {
                        eventType = parser.next();
                        continue;
                    } else {
                        if (parser.getName().equals("temperature")) {
                            currTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        } else if (parser.getName().equals("weather")) {
                            currWeather = parser.getAttributeValue(null, "icon");
                        }
                        eventType = parser.next();
                    }
                }
                conn.disconnect();

                //connecting or searching through file to get weather image
                if (fileExist(currWeather + ".png")) {
                    Log.i(ACTIVITY_NAME, "Weather image exists, read from file");
                    File file = getBaseContext().getFileStreamPath(currWeather + ".png");
                    FileInputStream fis = new FileInputStream(file);
                    bitmap = BitmapFactory.decodeStream(fis);
                } else {
                    Log.i(ACTIVITY_NAME, "Weather image does not exist, download from URL");

                    URL imageUrl = new URL("http://openweathermap.org/img/w/" + currWeather + ".png");
                    conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    stream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(stream);

                    FileOutputStream fos = openFileOutput(currWeather + ".png", Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    conn.disconnect();
                }
                publishProgress(100);
            } catch (FileNotFoundException fne) {
                Log.e(ACTIVITY_NAME, fne.getMessage());
            } catch (XmlPullParserException parserException) {
                Log.e(ACTIVITY_NAME, parserException.getMessage());
            } catch (IOException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {

            super.onProgressUpdate(value);
            progress.setVisibility(View.VISIBLE);
            //   progress.getProgress();
            progress.setProgress(value[0]);
            if (value[0] == 100) {

            }
        }

        @Override
        protected void onPostExecute(String args) {
            progress.setVisibility(View.INVISIBLE);
            currentTempText.setText("Current: " + currTemp + "C");
            minTempText.setText("Min: " + minTemp + "C");
            maxTempText.setText("Max: " + maxTemp + "C");
            weatherImage.setImageBitmap(bitmap);
        }

        public boolean fileExist(String name) {
            File file = getBaseContext().getFileStreamPath(name);
            return file.exists();
        }
    }
}