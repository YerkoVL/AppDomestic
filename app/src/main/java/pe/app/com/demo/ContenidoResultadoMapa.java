package pe.app.com.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

public class ContenidoResultadoMapa extends Fragment {
    Context mCtx;
    private MapboxMap map;
    private FloatingActionButton floatingActionButton;
    private MapView mapView;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_resultado_mapa, container, false);
        mCtx = rootView.getContext();

        rootView.setScrollContainer(false);
        rootView.setScrollbarFadingEnabled(false);

        Mapbox.getInstance(mCtx, getString(R.string.accesToken));

        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // CREACION DE FRAGMENTO
            final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            LatLng lima = new LatLng(-12.0453, -77.0311);

            // CONSTRUCCION DE MAPBOX
            MapboxMapOptions options = new MapboxMapOptions();
            options.styleUrl(Style.MAPBOX_STREETS);
            options.logoEnabled(false);
            options.compassEnabled(true);
            options.doubleTapGesturesEnabled(true);
            options.camera(new CameraPosition.Builder()
                    .target(lima)
                    .zoom(10)
                    .build());

            // CREACION FRAGMENTO DE MAPA
            mapFragment = SupportMapFragment.newInstance(options);

            // Add map fragment to parent container
            transaction.add(R.id.resultadosMapa, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map != null) {
                }
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                map = mapboxMap;
            }
        });

        return rootView;
    }
}
