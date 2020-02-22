package com.example.nearby;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.nearby.model.Adrasses;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Context context;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double latitude;
    private double longitude;
    ArrayList<Adrasses> adrasses;




    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        Toast.makeText(context, ""+sessionId, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        getAddresses();


        MapsInitializer.initialize(this);
        mMap.setMyLocationEnabled(true);
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent=new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private void getAddresses() {
        Bundle bundle = getIntent().getExtras();
       // adrasses =bundle.getParcelableArrayList("LatLong");
        setRestaurantMarker();
    }
    private void setRestaurantMarker() {
        for (int i = 0;i<adrasses.size();i++){
            Adrasses restaurant = adrasses.get(i);

            char firstLatter = restaurant.getName().charAt(0);
            LatLng latLng = new LatLng(restaurant.getLatitude(),restaurant.getLongitude());

            setMerker(latLng,String.valueOf(i));
        }
    }

    private void setMerker(LatLng latLng, String position) {
        mMap.addMarker(new MarkerOptions().position(latLng).snippet(position).icon(BitmapDescriptorFactory.defaultMarker()));
    }

}

