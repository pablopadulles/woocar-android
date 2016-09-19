package com.padulles.pablo.woocar_android;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView messageTextView1;
    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextView1 = (TextView) findViewById(R.id.textview1);
    }

    public void clickButtonRecorrido(View v) {
        comenzarLocalizacion();
    }

    private void comenzarLocalizacion() {
        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 20000, 0, locListener);

        //Obtenemos la última posición conocida
        //Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //actualiza la posicion
        locListener = new LocationListener() {
            private PollSQLiteHelper con;

            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
                con = new PollSQLiteHelper(MainActivity.this, "PosicionGPS", null, 1);
                SQLiteDatabase db = con.getWritableDatabase();
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("latitude", String.valueOf(location.getLongitude()));
                nuevoRegistro.put("longitude", String.valueOf(location.getAccuracy()));
                nuevoRegistro.put("speed", String.valueOf(location.getSpeed()));
                db.insert("posiciones", null, nuevoRegistro);
            }

            public void onProviderDisabled(String provider) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };


    }

    private void mostrarPosicion(Location loc) {

        messageTextView1.setText("GPS: \n" + String.valueOf(loc.getLatitude()) + "\n" +
                String.valueOf(loc.getLongitude()) + "\n" +
                String.valueOf(loc.getAccuracy()) + "\n" +
                String.valueOf(loc.getSpeed()));

    }
}