package com.evaluacion12.ricardoruiz.evaluacion12;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;


public class MainActivity extends ActionBarActivity {

    // Constantes Banner
    private AdView adView;


    // Constantes SDK  Facebook
    private CallbackManager cM;
    private LoginButton lB;

    @Override
    protected void onResume() {

        if(adView != null){
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {

        if(adView != null){
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if(adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // SDK Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        cM = CallbackManager.Factory.create();

        getFbKeyHash("QfKM+EtfoR4X8NqC71LZsuAw5vQ=");

        setContentView(R.layout.activity_main);

        lB = (LoginButton)findViewById(R.id.login_facebook);

        lB.registerCallback(cM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(MainActivity.this, "¡Inicio de sesión exitoso!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {

                Toast.makeText(MainActivity.this, "¡Inicio de sesión cancelado!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(MainActivity.this, "¡Inicio de sesión NO exitoso!", Toast.LENGTH_LONG).show();

            }
        });

        //Banner

        adView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
    }

    public void getFbKeyHash(String packageName){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }

    }
    protected void onActivityResult(int reqCode, int resCode, Intent i){
        cM.onActivityResult(reqCode, resCode, i);
    }

}
