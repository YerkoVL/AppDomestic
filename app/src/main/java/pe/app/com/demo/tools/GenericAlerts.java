package pe.app.com.demo.tools;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import pe.app.com.demo.R;

public class GenericAlerts {

    public void mensajeInfo(String titulo,String mensaje,Context ctx){
        new LovelyInfoDialog(ctx)
                .setTopColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.ic_logo_app)
                .setTitle(titulo)
                .setMessage(mensaje)
                .show();
    }
}
