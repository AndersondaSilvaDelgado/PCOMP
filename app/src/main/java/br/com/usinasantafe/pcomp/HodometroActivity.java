package br.com.usinasantafe.pcomp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pcomp.control.CheckListCTR;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.util.ConexaoWeb;

public class HodometroActivity extends ActivityGeneric {

    private PCOMPContext pcompContext;
    private Double horimetroNum;
    private OSBean osBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodometro);

        pcompContext = (PCOMPContext) getApplication();

        Button buttonOkHorimetro = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancHorimetro = (Button) findViewById(R.id.buttonCancPadrao);

        osBean = pcompContext.getConfigCTR().getOS();

        TextView textViewHorimetro = (TextView) findViewById(R.id.textViewPadrao);
        if (pcompContext.getVerPosTela() == 1) {
            textViewHorimetro.setText("HODOMETRO INICIAL");
        }
        else if (pcompContext.getVerPosTela() == 8) {
            textViewHorimetro.setText("HODOMETRO FINAL");
        }
        else if (pcompContext.getVerPosTela() == 9) {
            textViewHorimetro.setText("HODOMETRO FINAL");
        }
        else if (pcompContext.getVerPosTela() == 10) {
            textViewHorimetro.setText("HODOMETRO INICIAL");
        }

        buttonOkHorimetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextPadrao.getText().toString().equals("")) {

                    String horimetro = editTextPadrao.getText().toString();
                    horimetroNum = Double.valueOf(horimetro.replace(",", "."));

                    if (horimetroNum >= pcompContext.getConfigCTR().getConfig().getHorimetroConfig()) {
                        verTela();
                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(HodometroActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("O HODOMETRO DIGITADO " + horimetroNum + " É MENOR QUE O HODOMETRO ANTERIOR DA MAQUINA " + pcompContext.getConfigCTR().getConfig().getHorimetroConfig() + ". DESEJA MANTER ESSE VALOR?");

                        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                verTela();
                            }

                        });

                        alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();

                    }

                }

            }
        });

        buttonCancHorimetro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void verTela(){
        if (pcompContext.getVerPosTela() == 1) {
            salvarBoletimAberto();
        }
        else if (pcompContext.getVerPosTela() == 8) {
            salvarBoletimFechado();
        }
    }

    public void salvarBoletimAberto() {

        pcompContext.getConfigCTR().setHorimetroConfig(horimetroNum);

        pcompContext.getMotoMecCTR().setHodometroInicialBol(horimetroNum, getLongitude(), getLatitude());
        pcompContext.getMotoMecCTR().salvarBolAbertoMM();

        CheckListCTR checkListCTR = new CheckListCTR();
        if(checkListCTR.verAberturaCheckList(pcompContext.getMotoMecCTR().getTurno())){
            Long statusCon;
            ConexaoWeb conexaoWeb = new ConexaoWeb();
            if (conexaoWeb.verificaConexao(HodometroActivity.this)) {
                statusCon = 1L;
            }
            else{
                statusCon = 0L;
            }
            pcompContext.getMotoMecCTR().insParadaCheckList(getLongitude(), getLatitude(), statusCon, osBean.getTipoOS() + 2);
            pcompContext.setPosCheckList(1);
            checkListCTR.createCabecAberto(pcompContext.getMotoMecCTR());
            if (pcompContext.getVerAtualCL().equals("N_AC")) {
                Intent it = new Intent(HodometroActivity.this, PergAtualCheckListActivity.class);
                startActivity(it);
                finish();
            } else {
                Intent it = new Intent(HodometroActivity.this, ItemCheckListActivity.class);
                startActivity(it);
                finish();
            }
        }
        else{
            Intent it = new Intent(HodometroActivity.this, MenuMotoMecActivity.class);
            startActivity(it);
            finish();
        }
    }

    public void salvarBoletimFechado() {

        pcompContext.getConfigCTR().setHorimetroConfig(horimetroNum);
        pcompContext.getMotoMecCTR().setHodometroFinalBol(horimetroNum);
        pcompContext.getMotoMecCTR().salvarBolFechadoMM();

        if (pcompContext.getVerPosTela() == 8) {
            Intent it = new Intent(HodometroActivity.this, MenuInicialActivity.class);
            startActivity(it);
            finish();
        } else if (pcompContext.getVerPosTela() == 9) {
            pcompContext.setVerPosTela(10);
            Intent it = new Intent(HodometroActivity.this, FuncionarioActivity.class);
            startActivity(it);
            finish();
        }

    }

    public void onBackPressed() {
        if (pcompContext.getVerPosTela() == 1) {
            Intent it = new Intent(HodometroActivity.this, ListaAtividadeActivity.class);
            startActivity(it);
            finish();
        }
        else {
            Intent it = new Intent(HodometroActivity.this, MenuMotoMecActivity.class);
            startActivity(it);
            finish();
        }
    }

}
