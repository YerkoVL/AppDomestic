package pe.app.com.demo.tools;

import android.content.Context;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import pe.app.com.demo.R;

public class GenericAlerts {

    public void mensajeInfo(String titulo,String mensaje,Context ctx){
        new LovelyInfoDialog(ctx)
                .setTopColorRes(R.color.colorPrimary)
                .setIcon(R.mipmap.ic_logo_app_demo)
                .setTitle(titulo)
                .setMessage(mensaje)
                .show();
    }
}
