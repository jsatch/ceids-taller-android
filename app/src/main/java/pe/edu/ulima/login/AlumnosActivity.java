package pe.edu.ulima.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlumnosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        Intent intent = getIntent();
        String nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO");
    }
}
