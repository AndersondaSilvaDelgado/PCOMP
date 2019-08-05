package br.com.usinasantafe.pcomp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pcomp.bo.ConexaoWeb;
import br.com.usinasantafe.pcomp.bo.ManipDadosReceb;
import br.com.usinasantafe.pcomp.to.tb.estaticas.EquipamentoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;

public class ConfiguracaoActivity extends ActivityGeneric {

    private Context context;
    private ProgressDialog progressBar;
    private EditText editTextCamConfig;
    private EditText editTextSenhaConfig;
    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        context = this;

        Button btOkConfig =  (Button) findViewById(R.id.buttonSalvarConfig );
        Button btCancConfig = (Button) findViewById(R.id.buttonCancConfig);
        Button btAtualBDConfig = (Button) findViewById(R.id.buttonAtualizarBD);
        editTextCamConfig = (EditText)  findViewById(R.id.editTextCamConfig );
        editTextSenhaConfig = (EditText)  findViewById(R.id.editTextSenhaConfig);

        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();

        if (configuracaoTO.hasElements()) {

            List configuracaoList = configuracaoTO.all();
            configuracaoTO = (ConfiguracaoTO) configuracaoList.get(0);
            EquipamentoTO equipamentoTO = new EquipamentoTO();
            List lista = equipamentoTO.get("idEquipamento", configuracaoTO.getEquipConfig());
            equipamentoTO = (EquipamentoTO) lista.get(0);

            editTextCamConfig.setText(String.valueOf(equipamentoTO.getNroEquipamento()));
            editTextSenhaConfig.setText(configuracaoTO.getSenhaConfig());

        }

        btOkConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(!editTextCamConfig.getText().toString().equals("") &&
                        !editTextSenhaConfig.getText().toString().equals("")){

                    EquipamentoTO equipamentoTO = new EquipamentoTO();

                    if(equipamentoTO.hasElements()){

                        List lista = equipamentoTO.get("nroEquipamento", Long.valueOf(editTextCamConfig.getText().toString()));

                        if(lista.size() > 0){

                            equipamentoTO = (EquipamentoTO) lista.get(0);

                            ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
                            configuracaoTO.setEquipConfig(equipamentoTO.getIdEquipamento());
                            configuracaoTO.setSenhaConfig(editTextSenhaConfig.getText().toString());
                            configuracaoTO.setOsConfig(0L);
                            configuracaoTO.setStatusApontConfig(0L);
                            configuracaoTO.deleteAll();
                            configuracaoTO.insert();

                            Intent it = new Intent(ConfiguracaoActivity.this, PrincipalActivity.class);
                            startActivity(it);
                            finish();

                        }

                    }

                }

            }
        });

        btCancConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(ConfiguracaoActivity.this, PrincipalActivity.class);
                startActivity(it);
                finish();

            }
        });


        btAtualBDConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(context)) {
                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    ManipDadosReceb.getInstance().setContext(ConfiguracaoActivity.this);
                    ManipDadosReceb.getInstance().atualizarBD(progressBar);

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracaoActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();
                }
            }
        });

    }

    public void onBackPressed()  {
    }

}
