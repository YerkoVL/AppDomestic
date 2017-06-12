package pe.app.com.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ContenidoBuscarServicios extends Fragment{

    Button busqueda;
    Button fechaInicio;
    Button fechaFin;

    private PersianDatePickerDialog picker;

    Context mCtx;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar_servicios, container, false);

        mCtx = rootView.getContext();

        busqueda = (Button)rootView.findViewById(R.id.btnBuscarServicios);
        fechaInicio = (Button) rootView.findViewById(R.id.btnFechaInicio);
        fechaFin = (Button) rootView.findViewById(R.id.btnFechaFin);

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar(view);
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar(view);
            }
        });

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buscarServicios();
                actualizarSP();
                startActivity(new Intent(mCtx,MenuPrincipalActivity.class));
            }
        });

        return rootView;
    }

    public void actualizarSP(){
        SharedPreferences prefs = mCtx.getSharedPreferences("busquedaServicios",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("valor", "SI");
        editor.commit();
    }

    public void showCalendar(View v) {

        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(2017, 3, 13);

        Typeface face= Typeface.createFromAsset(mCtx.getAssets(),"iamplayer.ttf");

        picker = new PersianDatePickerDialog(mCtx)
                .setPositiveButtonString("Aceptar")
                .setNegativeButton("Cancelar")
                .setTodayButton("Hoy")
                .setTodayButtonVisible(true)
                .setInitDate(initDate)
                .setTypeFace(face)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        Toast.makeText(mCtx, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDisimised() {

                    }
                });

        picker.show();
    }
}
