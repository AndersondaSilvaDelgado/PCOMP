package br.com.usinasantafe.pcomp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pcomp.PCOMPContext;
import br.com.usinasantafe.pcomp.R;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;

public class InformacaoActivity extends ActivityGeneric {

    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao);

        pcompContext = (PCOMPContext) getApplication();

        TextView textViewDescrInfor = (TextView) findViewById(R.id.textViewDescrInfor);
        Button buttonRetMenuPesq = (Button) findViewById(R.id.buttonRetMenuPesq);

        if(pcompContext.getVerPosTela() == 2){
            textViewDescrInfor.setText("LEIRA PARA DESCARREG. = " + pcompContext.getCompostoCTR().getRecebLeiraComp().getCodLeiraCarreg());
        }
        else if(pcompContext.getVerPosTela() == 3){
            CarregBean carregBean = pcompContext.getCompostoCTR().getOrdCarreg();
            textViewDescrInfor.setText("LEIRA PARA CARREG. = " + carregBean.getCodLeiraCarreg() + "\n\n\n" +
                    "COD. ORD. CARREG. = " + carregBean.getIdOrdCarreg() + "\n" +
                    "PESO ENTRADA = " + carregBean.getPesoEntradaCarreg() + "\n" +
                    "PESO SAÍDA = " + carregBean.getPesoSaidaCarreg() + "\n" +
                    "PESO LÍQUIDO = " + carregBean.getPesoLiquidoCarreg() + "\n");
        }
        else if(pcompContext.getVerPosTela() == 4){
            CarregBean carregBean = pcompContext.getCompostoCTR().getOrdCarreg();
            textViewDescrInfor.setText("COD. ORD. CARREG. = " + carregBean.getIdOrdCarreg() + "\n" +
                                        "PESO ENTRADA = " + carregBean.getPesoEntradaCarreg() + "\n" +
                                        "PESO SAÍDA = " + carregBean.getPesoSaidaCarreg() + "\n" +
                                        "PESO LÍQUIDO = " + carregBean.getPesoLiquidoCarreg() + "\n");
        }
        else if(pcompContext.getVerPosTela() == 5){
            CarregBean carregBean = pcompContext.getCompostoCTR().carregLeiraExibir();
            textViewDescrInfor.setText("LEIRA PARA CARREG. OU DESCARREG. = " + carregBean.getCodLeiraCarreg());
        }


        pcompContext.setVerTelaLeira(false);

        buttonRetMenuPesq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(InformacaoActivity.this, MenuMotoMecActivity.class);
                startActivity(it);
                finish();

            }
        });

    }

    public void onBackPressed()  {
    }

}
