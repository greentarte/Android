package com.example.student.p680;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView txtv;
    private MarkerOptions mOpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtv = findViewById(R.id.txtv);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request Check Permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);
            Toast.makeText(this, "FINE_location", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            }, 1);
            Toast.makeText(this, "COARSE_location", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                requestMyLocation();
                break;
            case R.id.btn_busan:
                setLocation("부산", 35.1644465, 129.1551213);
                break;
            case R.id.btn_guwangju:
                setLocation("광주", 35.1600896, 126.8495911);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng m1 = new LatLng(37.5012319, 127.0396087);
        LatLng m2 = new LatLng(37.500555, 127.034908);
        LatLng m3 = new LatLng(37.501155, 127.031258);
        mMap.addMarker(new MarkerOptions().position(m1).title("Marker in MultiCampus").icon(BitmapDescriptorFactory.fromResource(R.mipmap.chicken_icon)));
        mMap.addMarker(new MarkerOptions().position(m2).title("Marker in M2").icon(BitmapDescriptorFactory.fromResource(R.mipmap.chicken_icon)));
        mMap.addMarker(new MarkerOptions().position(m3).title("Marker in M3").icon(BitmapDescriptorFactory.fromResource(R.mipmap.chicken_icon)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(m1));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
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
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                showCurrentLocation(lastLocation);
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
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
                    }
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void setLocation(String name, double latitude, double longitude) {
        Location location = new Location(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        showCurrentLocation(location);
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        if (mOpt == null) {
            mOpt = new MarkerOptions();
        }

        txtv.setText(location.getLatitude() + " " + location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
    }
}
