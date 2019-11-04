package com.stonepagamento;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class StonePagamentoModule extends ReactContextBaseJavaModule {

    ReactApplicationContext context = getReactApplicationContext();

    public StonePagamentoModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "Pagamento";
    }

    @ReactMethod
    public void iniciar() {
        Intent intent = new Intent(context, ValidationActivity.class);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
