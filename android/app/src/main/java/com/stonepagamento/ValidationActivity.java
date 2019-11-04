package com.stonepagamento;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import stone.application.StoneStart;
import stone.application.interfaces.StoneCallbackInterface;
import stone.environment.Environment;
import stone.providers.ActiveApplicationProvider;
import stone.user.UserModel;
import stone.utils.Stone;

import static stone.environment.Environment.SANDBOX;
import static stone.environment.Environment.valueOf;

@RuntimePermissions
public class ValidationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ValidationActivity";
    private EditText stoneCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initiateApp();

        setContentView(R.layout.activity_validation);

        Stone.setEnvironment(SANDBOX);
        Stone.setAppName("PayParty Stone");

        findViewById(R.id.activateButton).setOnClickListener(this);

        stoneCodeEditText = findViewById(R.id.stoneCodeEditText);
        Spinner environmentSpinner = findViewById(R.id.environmentSpinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        for (Environment env : Environment.values()) {
            adapter.add(env.name());
        }
        environmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Environment environment = valueOf(adapter.getItem(position));
                Stone.setEnvironment(environment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Stone.setEnvironment(SANDBOX);
            }
        });
        Stone.setAppName("PayParty Stone");
        environmentSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        List<String> stoneCodeList = new ArrayList<>();
        stoneCodeList.add(stoneCodeEditText.getText().toString());

        final ActiveApplicationProvider provider = new ActiveApplicationProvider(this);
        provider.setDialogMessage("Ativando o aplicativo...");
        provider.setDialogTitle("Aguarde...");
        provider.useDefaultUI(false);
        provider.setConnectionCallback(new StoneCallbackInterface() {
            public void onSuccess() {
                Toast.makeText(ValidationActivity.this, "Ativado com sucesso, iniciando o aplicativo", Toast.LENGTH_SHORT).show();
                continueApplication();
            }

            public void onError() {
                Toast.makeText(ValidationActivity.this, "Erro na ativacao do aplicativo, verifique a lista de erros do provider", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onError: " + provider.getListOfErrors().toString());
            }
        });
        provider.activate(stoneCodeList);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void initiateApp() {
        List<UserModel> user = StoneStart.init(this);
        if (user != null) {
            continueApplication();
        }
    }

    private void continueApplication() {
        Intent mainIntent = new Intent(ValidationActivity.this, PosTransactionActivity.class);
        startActivity(mainIntent);
        finish();
    }
}

