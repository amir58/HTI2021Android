package com.amirmohammed.hti2021androidone.maps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.amirmohammed.hti2021androidone.R;
import com.amirmohammed.hti2021androidone.databinding.ActivitySimpleMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivitySimpleMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySimpleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        // Add a marker in Sydney and move the camera
        // 30.0460481,31.2464471,15.29z
        LatLng sydney = new LatLng(30.0460481, 31.2464471);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        drawSomeMarkersOnMap();
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        System.out.println(latLng.toString());
//        drawMarker(latLng);
        getAddress(latLng);
    }

    private void drawMarker(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }

    private void getAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, new Locale("ar", "eg"));
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String title = addressList.get(0).getAddressLine(0);
            System.out.println(title);

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void drawSomeMarkersOnMap() {
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(30.0460481, 31.2464471));
        latLngList.add(new LatLng(30.0570481, 31.2565471));
        latLngList.add(new LatLng(30.0680481, 31.2666471));
        latLngList.add(new LatLng(30.0790481, 31.2767471));

        for (LatLng latLng : latLngList) {
            drawMarker(latLng);
        }
    }

}