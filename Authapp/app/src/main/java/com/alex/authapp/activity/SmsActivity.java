package com.alex.authapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.authapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SmsActivity extends AppCompatActivity {
    private EditText campoTelefone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        recuperarIds();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                logarViaCredencial(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };


    }

    private void logarViaCredencial(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            chegarStatusLogin();
                        }
                    }
                });
    }

    private void chegarStatusLogin() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Toast.makeText(this, "Usuario ja Logado", Toast.LENGTH_SHORT).show();
        }else{

            irTelaHome();
        }
    }

    private void irTelaHome() {

        Intent intent = new Intent(SmsActivity.this, HomeActivity.class);
        startActivity(intent);
        }

    public void validarCampo(View view){

        String textoTelefone = campoTelefone.getText().toString();

        if(!textoTelefone.isEmpty()){
            verificarCodigoAtivacao();
        }

    }

    private void verificarCodigoAtivacao() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                campoTelefone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }



    private void recuperarIds() {
        campoTelefone = findViewById(R.id.editTelefone);

    }
}
