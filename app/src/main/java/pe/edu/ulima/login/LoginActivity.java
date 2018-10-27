package pe.edu.ulima.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText eteUsuario;
    private EditText etePassword;
    private Button butLogin;

    private DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eteUsuario = findViewById(R.id.eteUsuario);
        etePassword = findViewById(R.id.etePassword);
        butLogin = findViewById(R.id.butLogin);

        butLogin.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("usuarios");

    }

    @Override
    public void onClick(View v) {
        // Se va a ejecutar este codigo cuando alguien haga click
        final String usuario = eteUsuario.getText().toString();
        final String password = etePassword.getText().toString();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Codigo luego que recibimos la data de la base de datos
                boolean encontro = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String user = ds.child("username").getValue().toString();
                    String pass = ds.child("password").getValue().toString();
                    if (user.equals(usuario) && pass.equals(password)){
                        // Login es correcto
                        encontro = true;
                        break;
                    }
                    Log.i("LOGIN", ds.getKey());
                }
                if (encontro){
                    // Login ha sido correcto
                    Intent intent = new Intent();
                    intent.putExtra("NOMBRE_USUARIO", usuario);
                    intent.setClass(LoginActivity.this, AlumnosActivity.class);
                    startActivity(intent);
                }else{
                    // Login ha sido incorrecto
                    Toast.makeText(LoginActivity.this,"Error en Login", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Codigo si es que sucede en la conexion
                Log.e("LOGIN_ERROR", databaseError.getMessage());
            }
        });

    }
}
