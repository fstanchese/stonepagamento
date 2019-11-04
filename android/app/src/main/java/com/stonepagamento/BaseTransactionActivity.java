package com.stonepagamento;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import stone.application.enums.Action;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.InstalmentTransactionEnum;
import stone.application.enums.TypeOfTransactionEnum;
import stone.application.interfaces.StoneActionCallback;
import stone.database.transaction.TransactionObject;
import stone.providers.BaseTransactionProvider;
import stone.user.UserModel;
import stone.utils.Stone;

public abstract class BaseTransactionActivity<T extends BaseTransactionProvider> extends AppCompatActivity implements StoneActionCallback {
    private BaseTransactionProvider transactionProvider;
    protected final TransactionObject transactionObject = new TransactionObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionObject.setInstalmentTransaction(InstalmentTransactionEnum.ONE_INSTALMENT);

        // Verifica a forma de pagamento selecionada.
        TypeOfTransactionEnum transactionType = TypeOfTransactionEnum.CREDIT;


//      Defina o ITK da sua transação
//      transactionObject.setInitiatorTransactionKey("SEU_IDENTIFICADOR_UNICO_AQUI");

        transactionObject.setTypeOfTransaction(transactionType);
        transactionObject.setCapture(true);
        transactionObject.setAmount("100");

        transactionObject.setSubMerchantCity("city"); //Cidade do sub-merchant
        transactionObject.setSubMerchantPostalAddress("00000000"); //CEP do sub-merchant (Apenas números)
        transactionObject.setSubMerchantRegisteredIdentifier("00000000"); // Identificador do sub-merchant
        transactionObject.setSubMerchantTaxIdentificationNumber("33368443000199"); // CNPJ do sub-merchant (apenas números)

//      Seleciona o mcc do lojista.
//      transactionObject.setSubMerchantCategoryCode("123");

//      Seleciona o endereço do lojista.
//      transactionObject.setSubMerchantAddress("address");]

        transactionProvider = buildTransactionProvider();
        transactionProvider.useDefaultUI(true);
        transactionProvider.setConnectionCallback(this);
        transactionProvider.execute();
    }

    protected String getAuthorizationMessage() {
        return transactionProvider.getMessageFromAuthorize();
    }

    protected abstract T buildTransactionProvider();

    protected boolean providerHasErrorEnum(ErrorsEnum errorsEnum) {
        return transactionProvider.theListHasError(errorsEnum);
    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseTransactionActivity.this, "Erro: " + transactionProvider.getListOfErrors(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStatusChanged(final Action action) {
    }

    protected UserModel getSelectedUserModel() {
        return Stone.getUserModel(0);
    }
}
