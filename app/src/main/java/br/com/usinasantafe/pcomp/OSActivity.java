package br.com.usinasantafe.pcomp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.usinasantafe.pcomp.to.tb.estaticas.OSTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaCompTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaProdTO;

public class OSActivity extends ActivityGeneric {

    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os);

        pcompContext = (PCOMPContext) getApplication();

        Button buttonOkOS = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancOS = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!editTextPadrao.getText().toString().equals("")) {

                    OSTO osTO = new OSTO();
                    List listOS = osTO.get("nroOS", Long.parseLong(editTextPadrao.getText().toString()));

                    if (listOS.size() > 0) {

                        osTO = (OSTO) listOS.get(0);

                        if (pcompContext.getTipoFuncao() == 1) {
                            pcompContext.setVerOS(false);
                        } else if (pcompContext.getTipoFuncao() == 2) {
                            pcompContext.setVerOS(true);
                        }

                        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
                        List listConfig = configuracaoTO.all();
                        configuracaoTO = (ConfiguracaoTO) listConfig.get(0);
                        configuracaoTO.setStatusApontConfig(1L);
                        configuracaoTO.setOsConfig(osTO.getIdOS());
                        configuracaoTO.update();

                        PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();
                        pesqBalancaCompTO.deleteAll();

                        PesqBalancaProdTO pesqBalancaProdTO = new PesqBalancaProdTO();
                        pesqBalancaProdTO.deleteAll();

                        Intent it = new Intent(OSActivity.this, MenuAtividadeActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(OSActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("O.S. INEXISTENTE NA BASE DE DADOS.");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                editTextPadrao.setText("");
                            }
                        });
                        alerta.show();

                    }

                }
            }
        });

        buttonCancOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                } else {
                    Intent it = new Intent(OSActivity.this, MenuAtividadeActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        });

    }

    public void onBackPressed()  {
    }

}
