package pe.app.com.demo;

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

public class BuscarServicios extends Fragment {

    Button busqueda;

    Context mCtx;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar_servicios, container, false);

        mCtx = rootView.getContext();

        busqueda = (Button)rootView.findViewById(R.id.btnBuscarServicios);

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
}