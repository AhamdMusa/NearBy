package com.example.nearby.freegments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearby.MainActivity;
import com.example.nearby.MapsActivity;
import com.example.nearby.MyLocationService;
import com.example.nearby.R;
import com.example.nearby.model.Adrasses;
import com.example.nearby.model.MyLocationListener;
import com.example.nearby.pogos.NearbyLocation;
import com.example.nearby.pogos.Result;
import com.example.nearby.retrofit.IGoogleAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;


public class AllFragment extends Fragment {

    static AllFragment instance;



    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng myLocation;
    protected Context context;
    public double latitude, longitude;
    private Location location;
    private Marker marker;
    IGoogleAPI iGoogleAPI;
    private CardView haspital, school,atm;
    int hosVal;
    TextView hosValu;
    String hospitalURL;
    private List<Adrasses> addressList;

    public static AllFragment getInstance() {
        return instance;
    }

    public static void setInstance(AllFragment instance) {
        AllFragment.instance = instance;
    }

    public AllFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View allFragmentView = inflater.inflate(R.layout.fragment_all, container, false);
        haspital=allFragmentView.findViewById(R.id.hospitalCard);
        hosValu=allFragmentView.findViewById(R.id.hosValue);




        instance =this;

        Dexter.withActivity((Activity) getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        updateLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getContext(), "You must give Permission", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


        return allFragmentView;
    }

    private void onClicksAll() {

        haspital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
               // bundle.putParcelableArrayList("LatLong", (ArrayList<? extends Parcelable>) addressList);
                Intent i = new Intent(getActivity(), MapsActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    private void updateLocation() {
        locationResuest();
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,getPendingIntent());
    }

    private PendingIntent getPendingIntent() {

        Intent intent =new Intent(getContext(), MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDET);
        return PendingIntent.getBroadcast(getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void locationResuest() {
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(30000).setSmallestDisplacement(20f);
    }

    public void location(LatLng latLng)    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myLocation=latLng;
                latitude=myLocation.latitude;
                longitude=myLocation.longitude;
               Toast.makeText(getContext(), ""+latitude+""+longitude, Toast.LENGTH_SHORT).show();
                //-----mathads-----------//
                nearByPlaces("atm");

            }
        });
    }

    private void nearByPlaces(String placeType) {
        String url=getUrl(latitude,longitude,placeType);
        iGoogleAPI.getNearByPlaces(url).enqueue(new Callback<NearbyLocation>() {
            @Override
            public void onResponse(Call<NearbyLocation> call, Response<NearbyLocation> response) {
                if (response.isSuccessful()){
                    for (int i=0;i<response.body().getResults().size();i++)   //------------------Gondogol at size--------------//
                    {
                        Result googlePlaces = response.body().getResults().get(i);
                        double lat = Double.parseDouble(String.valueOf((googlePlaces.getGeometry().getLocation().getLat())));//------------------Gondogol at String.valueOf--------------//
                        double lng = Double.parseDouble(String.valueOf((googlePlaces.getGeometry().getLocation().getLng())));
                        String placeName = googlePlaces.getName();
                        String vicinity = googlePlaces.getVicinity();
                        LatLng latLng = new LatLng(lat, lng);
                        addressList.add(new Adrasses(placeName, vicinity, lat, lng));
                    }


                }
            }

            @Override
            public void onFailure(Call<NearbyLocation> call, Throwable t) {

            }
        });

        hosValu.setText(String.valueOf(5));


    }

    private String getUrl(double latitude, double longitude, String placeType) {
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+1000);
        googlePlaceUrl.append("&type="+placeType);
        googlePlaceUrl.append("&key=AIzaSyB9MxxJBmzLHdfsMEZSdV0vORR_MRwirPI");
        Log.d("getUrl",googlePlaceUrl.toString());
        hospitalURL=googlePlaceUrl.toString();
        onClicksAll();
        return googlePlaceUrl.toString();
    }


}
