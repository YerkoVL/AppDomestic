package pe.app.com.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistroActivity extends AppCompatActivity {

    @Bind(R.id.btnRegistrar) Button registro;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        ctx = this;

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarMenu = new Intent(ctx.getApplicationContext(),MenuPrincipalActivity.class);
                startActivity(pasarMenu);
            }
        });
    }
}
