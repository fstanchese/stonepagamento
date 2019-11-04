package com.stonepagamento;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import br.com.stone.posandroid.providers.PosPrintReceiptProvider;
import br.com.stone.posandroid.providers.PosTransactionProvider;
import stone.application.enums.Action;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.ReceiptType;
import stone.application.enums.TransactionStatusEnum;

public class PosTransactionActivity extends BaseTransactionActivity<PosTransactionProvider> {

    @Override
    protected PosTransactionProvider buildTransactionProvider() {
        return new PosTransactionProvider(this, transactionObject, getSelectedUserModel());
    }

    @Override
    public void onSuccess() {
        if (transactionObject.getTransactionStatus() == TransactionStatusEnum.APPROVED) {
/*
            final PrintController printMerchant =
                    new PrintController(PosTransactionActivity.this,
                            new PosPrintReceiptProvider(this.getApplicationContext(),
                                    transactionObject, ReceiptType.MERCHANT));

            printMerchant.print();

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Transação aprovada! Deseja imprimir a via do cliente?");

            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final PrintController printClient =
                    new PrintController(PosTransactionActivity.this,
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
                    Toast.makeText(
                            getApplicationContext(),
                            "Erro na transação: \"" + getAuthorizationMessage() + "\"",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        }
    }

    @Override
    public void onError() {
        super.onError();
        if (providerHasErrorEnum(ErrorsEnum.DEVICE_NOT_COMPATIBLE)) {
            Toast.makeText(
                    this,
                    "Dispositivo não compatível ou dependência relacionada não está presente",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    public void onStatusChanged(final Action action) {
        super.onStatusChanged(action);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (action == Action.TRANSACTION_WAITING_CARD) {
                    Toast.makeText(PosTransactionActivity.this, "Insira o cartao", Toast.LENGTH_SHORT).show();
                }
                if (action == Action.TRANSACTION_WAITING_PASSWORD) {
                    Toast.makeText(PosTransactionActivity.this, "Digite a senha", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
