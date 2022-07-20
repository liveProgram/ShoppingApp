package com.developer.live.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.live.shoppingapp.databinding.ActivityMainBinding;
import com.developer.live.shoppingapp.store.LocalStorage;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    MaterialRadioButton radioBtnRegister, radioBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        radioBtnLogin = binding.radioLogin;
        radioBtnRegister = binding.radioRegister;
        // for reference
        if(new LocalStorage(MainActivity.this).IsLogged()){
            startActivity(new Intent(MainActivity.this, DrawerNavActivity.class));
            MainActivity.this.finish();
        }


        if(radioBtnRegister.isSelected()){
            binding.linearLayout1.getRoot().setVisibility(View.VISIBLE);
            binding.linearLayout2.getRoot().setVisibility(View.INVISIBLE);
            configureSignUpViews();
        }
        else{
            binding.linearLayout2.getRoot().setVisibility(View.VISIBLE);
            binding.linearLayout1.getRoot().setVisibility(View.INVISIBLE);
            configureSignInViews();
        }
        
    }

    private void configureSignInViews() {
        TextInputLayout emailLayout = binding.linearLayout2.layoutUsername;
        TextInputLayout passLayout = binding.linearLayout2.layoutPassword;
        TextInputEditText emailInput = binding.linearLayout2.editUsername;
        TextInputEditText passInput = binding.linearLayout2.editPassword;
        Button btnSignIn = binding.linearLayout2.btnSignIn;

        btnSignIn.setOnClickListener(view -> {
            String email = String.valueOf(emailInput.getText());
            String pwd = String.valueOf(passInput.getText());
            emailLayout.setError(null);
            passLayout.setError(null);

            if (!validateInfo(email, pwd, emailLayout, passLayout)){
                forSigningIn(email,pwd);
            }
        });

    }

    private void forSigningIn(String email, String pwd) {
        // code for authenticating a register used
        FirebaseAuth _auth = FirebaseAuth.getInstance();
        _auth.signInWithEmailAndPassword(email, pwd)
                .addOnSuccessListener(authResult -> {

                    if(authResult.getUser() != null) {
                        if (authResult.getUser().isEmailVerified()) {
                            String userId = authResult.getUser().getUid();
                            Snackbar.make(binding.getRoot(), "Login successful " + userId, Snackbar.LENGTH_SHORT).show();
                            new LocalStorage(MainActivity.this).storeLogin(email);
                            Intent intent = new Intent(MainActivity.this, DrawerNavActivity.class);
                            startActivity(intent);
                            // to destroy the screen after navigation
                            MainActivity.this.finishAffinity();
                        }
                    }
                    else
                        Snackbar.make(binding.getRoot(),"Login unsuccessful",Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "The error: "+e.getMessage(),Toast.LENGTH_SHORT).show()
                );
    }

    private boolean validateInfo(String email, String pwd, TextInputLayout emailLayout, TextInputLayout passLayout) {
        if(email.isEmpty())
            emailLayout.setError("Username will be the registered email id");
        else if(pwd.isEmpty())
            passLayout.setError("Password must not be blank");
        else
            return false;
        return true;
    }

    private void configureSignUpViews() {
    }
}