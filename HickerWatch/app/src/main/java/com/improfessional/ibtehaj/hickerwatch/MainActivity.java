package com.improfessional.ibtehaj.hickerwatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    Location location_1;
    private final String key = "debug";
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION;
    TextView latitudeText, longitudeText, speedText,
            bearingText, accuracyText, altitudeText;

    TextView locationText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        askingForPermissions();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), true);
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
        location_1 = locationManager.getLastKnownLocation(provider);

        latitudeText = (TextView) findViewById(R.id.latitudeText);
        longitudeText = (TextView) findViewById(R.id.longitudeText);
        altitudeText = (TextView) findViewById(R.id.altitudeText);
        bearingText = (TextView) findViewById(R.id.bearingText);
        accuracyText = (TextView) findViewById(R.id.accuracyText);
        locationText  = (TextView) findViewById(R.id.locationText);
        speedText = (TextView) findViewById(R.id.speedText);


        if(location_1 != null){

            Double latitude = location_1.getLatitude(),
                    longitude=location_1.getLongitude();
            Double altitude =location_1.getAltitude();
            Float bearing = location_1.getBearing();
            Float accuracy= location_1.getAccuracy();
            Float speed = location_1.getSpeed();


            latitudeText.setText(String.format("LATITUDE: %s", latitude));
            longitudeText.setText(String.format("LONGITUDE: %s", longitude));
            altitudeText.setText(String.format("ALTITUDE: %s", altitude));
            bearingText.setText(String.format("BEARING: %s", bearing));
            accuracyText.setText(String.format("ACCURACY: %s", accuracy));

//            String o ="SPEED: "+speed;
            speedText.setText(String.format("SPEED: %s", speed));





        }else{
            Toast.makeText(getApplicationContext(),"Couldn't Fetch the Location. ",Toast.LENGTH_LONG).show();

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
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
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        if(location_1!= null){
            Log.i(key,"location = "+String.valueOf(location_1));
        }else {
            Log.i(key,"Some error");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude(),
                longitude = location.getLongitude();
        Double altitude = location.getAltitude();
        Float bearing = location.getBearing();
        Float accuracy = location.getAccuracy();
        Float speed = location.getSpeed();


        Log.i(key, "LATITUDE = " + latitude.toString());
        Log.i(key, "LONGITUDE = " + longitude.toString());
        Log.i(key, "speed = " + String.valueOf(speed));
        Log.i(key, "bearing = " + String.valueOf(bearing));
        Log.i(key, "accuracy = " + String.valueOf(accuracy));
        Log.i(key, "altitude = " + String.valueOf(altitude));


//          {


        latitudeText.setText(String.format("LATITUDE: %s", latitude));
        longitudeText.setText(String.format("LONGITUDE: %s", longitude));
        altitudeText.setText(String.format("ALTITUDE: %s", altitude));
        bearingText.setText(String.format("BEARING: %s", bearing));
        accuracyText.setText(String.format("ACCURACY: %s", accuracy));
        speedText.setText(String.format("SPEED: %s", speed));




//          }
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Log.i(key, "ADDRESS = " + String.valueOf(addressList.get(0)));
                Log.i(key, "ADDRESS = " + String.valueOf(addressList.get(0).getCountryName()));
                locationText.setText(String.format("LOCATION: %s", addressList.get(0).getCountryName()));
            } else {
                Log.i(key, "ERROR! Fetching ADDRESS Failed.");
                locationText.setText("ERROR! Fetching ADDRESS Failed.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void askingForPermissions(){
        /*                                ASKING FOR LOCATION PERMISSION START                               */
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

/*                                ASKING FOR LOCATION PERMISSION END                               */
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
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
                    location_1 = locationManager.getLastKnownLocation(provider);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), ("Please provide access to Location"), Toast.LENGTH_LONG).show();
                }
                break;
        }

        //for other permission
    }



}
