package br.com.usinasantafe.pcomp.view;

import android.os.Bundle;
import android.widget.TextView;

import br.com.usinasantafe.pcomp.PCOMPContext;
import br.com.usinasantafe.pcomp.R;

public class EsperaInfActivity extends ActivityGeneric {

    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera_inf);

        pcompContext = (PCOMPContext) getApplication();

        TextView textEspInfo = (TextView) findViewById(R.id.textEspInfo);

        if(pcompContext.getVerPosTela() == 2){
            textEspInfo.setText("BUSCANDO LEIRA...");
            pcompContext.getCompostoCTR().pesqLeiraComposto(this);
        }
        else if(pcompContext.getVerPosTela() == 3){
            textEspInfo.setText("BUSCANDO LEIRA/CARREGAMENTO...");
            pcompContext.getCompostoCTR().pesqCarregProduto(this);
        }
        else if(pcompContext.getVerPosTela() == 4){
            textEspInfo.setText("PESQUISANDO CARREGAMENTO...");
            pcompContext.getCompostoCTR().pesqCarregComposto(this);
        }

    }
}
