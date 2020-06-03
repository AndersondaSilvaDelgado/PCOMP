package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformacaoActivity extends ActivityGeneric {

    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao);

        pcompContext = (PCOMPContext) getApplication();

        TextView textViewCodLeira = (TextView) findViewById(R.id.textViewCodLeira);
        Button buttonRetMenuPesq = (Button) findViewById(R.id.buttonRetMenuPesq);

        if(pcompContext.getVerPosTela() == 2){
            textViewCodLeira.setText(pcompContext.getCompostoCTR().getLeira().getCodLeira());
        }

//        if(pcompContext.getTipoAplic() == 1){
//
//            if(!pcompContext.isVerTelaLeira()){
//                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
//            }
//
//            PesqBalancaProdTO pesqBalancaProdTO = new PesqBalancaProdTO();
//            List infPesq = pesqBalancaProdTO.all();
//            pesqBalancaProdTO = (PesqBalancaProdTO) infPesq.get(0);
//
//            textViewLeira.setText(pesqBalancaProdTO.getLeira());
//
//        }
//        else if(pcompContext.getTipoAplic() == 2){
//
//            PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();
//            List infPesq = pesqBalancaCompTO.all();
//            pesqBalancaCompTO = (PesqBalancaCompTO) infPesq.get(0);
//
//            textViewLeira.setText(String.valueOf(pesqBalancaCompTO.getCdLeira()));
//
//        }

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
