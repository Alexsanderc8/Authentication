package com.alex.authapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alex.authapp.R;
import com.alex.authapp.activity.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button botaoSms, botaoEmail;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recuperarIds();

        botaoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaEmail();
            }
        });

        botaoSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirTelaSms();

            }
        });
    }

    private void abrirTelaSms() {
        Intent intent = new Intent(MainActivity.this, SmsActivity.class);
        startActivity(intent);
    }

    private void abrirTelaEmail() {
        Intent intent = new Intent(MainActivity.this, EmailActivity.class);
        startActivity(intent);
    }




    private void recuperarIds() {
        botaoEmail = findViewById(R.id.btnEmail);
        botaoSms = findViewById(R.id.btnSms);
    }
}
