package com.stonepagamento;

import android.Manifest;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.ArrayList;
import java.util.List;

import br.com.stone.posandroid.providers.PosTransactionProvider;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import stone.application.StoneStart;
import stone.application.enums.Action;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.InstalmentTransactionEnum;
import stone.application.enums.TransactionStatusEnum;
import stone.application.enums.TypeOfTransactionEnum;
import stone.application.interfaces.StoneActionCallback;
import stone.application.interfaces.StoneCallbackInterface;
import stone.database.transaction.TransactionObject;
import stone.providers.ActiveApplicationProvider;
import stone.user.UserModel;
import stone.utils.Stone;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;
import static stone.environment.Environment.SANDBOX;

public class StonePagamentoModule extends ReactContextBaseJavaModule implements StoneActionCallback {
    private static final String TAG = "ValidationActivity";
    private String stoneCodeEditText;
    private PosTransactionProvider transactionProvider;
    protected final TransactionObject transactionObject = new TransactionObject();
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
        List<UserModel> user = StoneStart.init(context);

        if (user != null) {
            Toast.makeText(context, user.get(0).getStoneCode(), Toast.LENGTH_SHORT).show();

            transactionProvider = new PosTransactionProvider(context, transactionObject, getSelectedUserModel());
            transactionObject.setInstalmentTransaction(InstalmentTransactionEnum.ONE_INSTALMENT);

            // Verifica a forma de pagamento selecionada.
            TypeOfTransactionEnum transactionType = TypeOfTransactionEnum.CREDIT;


            // Defina o ITK da sua transação
            // transactionObject.setInitiatorTransactionKey("SEU_IDENTIFICADOR_UNICO_AQUI");

            transactionObject.setTypeOfTransaction(transactionType);
            transactionObject.setCapture(true);
            transactionObject.setAmount("100");

            transactionObject.setSubMerchantCity("city"); //Cidade do sub-merchant
            transactionObject.setSubMerchantPostalAddress("00000000"); //CEP do sub-merchant (Apenas números)
            transactionObject.setSubMerchantRegisteredIdentifier("00000000"); // Identificador do sub-merchant
            transactionObject.setSubMerchantTaxIdentificationNumber("33368443000199"); // CNPJ do sub-merchant (apenas números)

            // Seleciona o mcc do lojista.
            // transactionObject.setSubMerchantCategoryCode("123");

            // Seleciona o endereço do lojista.
            // transactionObject.setSubMerchantAddress("address");]

            transactionProvider = buildTransactionProvider();
            transactionProvider.useDefaultUI(true);
            transactionProvider.setConnectionCallback(this);
            transactionProvider.execute();
        } else {
            Toast.makeText(context, "Validaçao", Toast.LENGTH_LONG).show();
            Stone.setEnvironment(SANDBOX);
            Stone.setAppName("PayParty Stone");
            stoneCodeEditText = "187031363";

            List<String> stoneCodeList = new ArrayList<>();
            stoneCodeList.add(stoneCodeEditText);

            final ActiveApplicationProvider provider = new ActiveApplicationProvider(context);
            provider.setDialogMessage("Ativando o aplicativo...");
            provider.setDialogTitle("Aguarde...");
            provider.useDefaultUI(false);
            provider.setConnectionCallback(new StoneCallbackInterface() {
                public void onSuccess() {
                    Toast.makeText(context, "Ativado com sucesso, iniciando o aplicativo", Toast.LENGTH_LONG).show();
                }

                public void onError() {
                    Toast.makeText(context, "Erro na ativacao do aplicativo, verifique a lista de erros do provider", Toast.LENGTH_SHORT).show();
                }
            });
            Stone.setAppName("PayParty Stone");
            provider.activate(stoneCodeList);
        }
    }

    protected PosTransactionProvider buildTransactionProvider() {
        return new PosTransactionProvider(context, transactionObject, getSelectedUserModel());
    }

    protected UserModel getSelectedUserModel() {
        return Stone.getUserModel(0);
    }

    @Override
    public void onStatusChanged(Action action) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (action == Action.TRANSACTION_WAITING_CARD) {
                    Toast.makeText(context, "Insira o cartao", Toast.LENGTH_LONG).show();
                }
                if (action == Action.TRANSACTION_WAITING_PASSWORD) {
                    Toast.makeText(context, "Digite a senha", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onSuccess() {
        if (transactionObject.getTransactionStatus() == TransactionStatusEnum.APPROVED) {
/*
            final PrintController printMerchant =
                    new PrintController(ValidationActivity.this,
                            new PosPrintReceiptProvider(this.getApplicationContext(),
                                    transactionObject, ReceiptType.MERCHANT));

            printMerchant.print();

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Transação aprovada! Deseja imprimir a via do cliente?");

            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final PrintController printClient =
                    new PrintController(ValidationActivity.this,
                            new PosPrintReceiptProvider(getApplicationContext(),
                                    transactionObject, ReceiptType.CLIENT));
                    printClient.print();
                }
            });

            builder.setNegativeButton(android.R.string.no, null);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    builder.show();

                }
            });

 */
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText( context,"Erro na transação: \"" + getAuthorizationMessage() + "\"", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    protected String getAuthorizationMessage() {
        return transactionProvider.getMessageFromAuthorize();
    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Erro: " + transactionProvider.getListOfErrors(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
