package pe.app.com.demo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContenidoBuscarServicios extends Fragment{

    Button busqueda;
    EditText fechaInicio;
    EditText fechaFin;

    Context mCtx;

    Calendar miCalendario = Calendar.getInstance();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar_servicios, container, false);

        mCtx = rootView.getContext();

        busqueda = (Button)rootView.findViewById(R.id.btnBuscarServicios);
        fechaInicio = (EditText) rootView.findViewById(R.id.edtFechaInicio);
        fechaFin = (EditText) rootView.findViewById(R.id.edtFechaFin);

        fechaInicio.setFocusableInTouchMode(false);
        fechaFin.setFocusableInTouchMode(false);

        final DatePickerDialog.OnDateSetListener dialogFechaFin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                anio = datePicker.getYear();
                mes = datePicker.getMonth();
                dia = datePicker.getDayOfMonth();

                fechaFin.setText(dia + "/" + mes + "/" + anio);

            }
        };

        final DatePickerDialog.OnDateSetListener dialogFechaInicio = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                anio = datePicker.getYear();
                mes = datePicker.getMonth();
                dia = datePicker.getDayOfMonth();

                fechaInicio.setText(dia + "/" + mes + "/" + anio);

            }
        };

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(mCtx, dialogFechaInicio, miCalendario
                        .get(Calendar.YEAR), miCalendario.get(Calendar.MONTH),
                        miCalendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(mCtx, dialogFechaFin, miCalendario
                        .get(Calendar.YEAR), miCalendario.get(Calendar.MONTH),
                        miCalendario.get(Calendar.DAY_OF_MONTH)).show();
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

    private void actualizarFecha(EditText textoEditar) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        textoEditar.setText(sdf.format(miCalendario.getTime()));
    }
}
