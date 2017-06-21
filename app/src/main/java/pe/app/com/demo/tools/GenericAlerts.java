package pe.app.com.demo.tools;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

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

    public void mensajeUnaOpcion(String titulo, String mensaje, Context ctx){
        new LovelyStandardDialog(ctx)
                .setTopColorRes(R.color.colorFondoDefault)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.ic_logo_app)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}
