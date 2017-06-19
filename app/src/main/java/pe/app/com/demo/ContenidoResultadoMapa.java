package pe.app.com.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.provider.DocumentFile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import pe.app.com.demo.SQLiteHelper.MapasSQLHelper;
import pe.app.com.demo.entity.Mapa;

public class ContenidoResultadoMapa extends Fragment {
    Context mCtx;
    private MapboxMap map;
    private FloatingActionButton floatingActionButton;
    private MapView mapView;
    private List<Mapa> mapaArrayList = new ArrayList<>();
    View rootView;
    Double defaultLatitud = -12.132480885221323, defaultLongitud = -76.98358297348022;
    String idPersona, rubros = "Disponible", nombresCompletoPersona;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_resultado_mapa, container, false);
        mCtx = rootView.getContext();

        rootView.setScrollContainer(false);
        rootView.setScrollbarFadingEnabled(false);

        Mapbox.getInstance(mCtx, getString(R.string.accesToken));

        final SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // CREACION DE FRAGMENTO
            final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            try {
                idPersona = this.getArguments().getString("ID");
                rubros = this.getArguments().getString("RUBROS");
                nombresCompletoPersona = this.getArguments().getString("NOMBRES_COMPLETOS");
                defaultLongitud = this.getArguments().getDouble("LATITUD");
                defaultLatitud = getArguments().getDouble("LONGITUD");

                asignarValoresMapa(idPersona, nombresCompletoPersona,rubros,defaultLatitud,defaultLongitud);

            }catch (Exception e){
                e.printStackTrace();
                leerMapas();
            }

            LatLng lima = new LatLng(defaultLatitud,defaultLongitud);

            // CONSTRUCCION DE MAPBOX
            MapboxMapOptions options = new MapboxMapOptions();
            options.styleUrl(Style.MAPBOX_STREETS);
            options.logoEnabled(false);
            options.compassEnabled(true);
            options.doubleTapGesturesEnabled(true);
            options.camera(new CameraPosition.Builder()
                    .target(lima)
                    .zoom(12)
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

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                IconFactory iconFactory = IconFactory.getInstance(mCtx);
                Icon icon = iconFactory.fromResource(R.drawable.mapbox_marker_icon_default);

                for(int x=0;x<mapaArrayList.size();x++) {
                    String id = mapaArrayList.get(x).getIdSocio();
                    String rubros = mapaArrayList.get(x).getRubros();
                    String nombre = mapaArrayList.get(x).getNombreSocio();
                    String latitud = mapaArrayList.get(x).getLatitud();
                    String longitud = mapaArrayList.get(x).getLongitud();

                    mapboxMap.addMarker(new MarkerViewOptions()
                            .icon(icon)
                            //.rotation(90)
                            .anchor(0.5f, 0.5f)
                            .alpha(0.5f)
                            .infoWindowAnchor(0.5f, 0.5f)
                            .flat(true)
                            .position(new LatLng(Double.valueOf(latitud.trim()),Double.valueOf(longitud.trim())))
                            .title(nombre)
                            .snippet(rubros));
                }

                mapboxMap.setOnInfoWindowClickListener(new MapboxMap.OnInfoWindowClickListener() {
                    @Override
                    public boolean onInfoWindowClick(@NonNull Marker marker) {
                        double latitud = marker.getPosition().getLatitude();
                        double longitud = marker.getPosition().getLongitude();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(asignarPosicionCamara(latitud,longitud)),4000);
                        return true;
                    }
                });

                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        double latitud = marker.getPosition().getLatitude();
                        double longitud = marker.getPosition().getLongitude();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(asignarPosicionCamara(latitud,longitud)),4000);
                        return true;
                    }
                });

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(asignarPosicionCamaraNormal(defaultLatitud,defaultLongitud)),5000);
                    }
                });

            }
        });

        return rootView;
    }

    public CameraPosition asignarPosicionCamara(Double latitud, Double longitud){
        CameraPosition posicion = new CameraPosition.Builder()
                .target(new LatLng(latitud, longitud)) // Sets the new camera position
                .zoom(15) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build();
        return posicion;
    }

    public CameraPosition asignarPosicionCamaraNormal(Double latitud, Double longitud){
        CameraPosition posicion = new CameraPosition.Builder()
                .target(new LatLng(latitud, longitud))
                .zoom(10)
                .bearing(360)
                .build();
        return posicion;
    }

    public void asignarValoresMapa(String id,String nombres, String rubros, Double latitud, Double longitud){
        Mapa mapa = new Mapa(
                id,
                nombres,
                rubros,
                String.valueOf(latitud),
                String.valueOf(longitud)
        );
        mapaArrayList.add(mapa);
    }

    public void leerMapas(){
        MapasSQLHelper usdbh =
                new MapasSQLHelper(mCtx, "DBMapas", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery(" SELECT DISTINCT idPersona,nombrePersona, rubros, latitud, longitud FROM Mapas", null);

        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String nombre = c.getString(1);
                String rubros = c.getString(2);
                String longitud = c.getString(3);
                String latitud = c.getString(4);

                Mapa mapa= new Mapa(id, nombre, rubros, latitud, longitud);

                mapaArrayList.add(mapa);
            } while(c.moveToNext());
        }

    }
}
