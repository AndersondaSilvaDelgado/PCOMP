package br.com.usinasantafe.pcomp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.bo.ManipDadosEnvio;
import br.com.usinasantafe.pcomp.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcomp.pst.MotoMecPST;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoMecTO;

public class MotivoParadaActivity extends ActivityGeneric {

    private ListView lista;
    private PCOMPContext pcompContext;
    private List listaMM;
    private MotoMecTO motoMecTO;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_parada);

        Button buttonRetMenuParada = (Button) findViewById(R.id.buttonRetMenuParada);

        pcompContext = (PCOMPContext) getApplication();

        listarMotivoParada();

        buttonRetMenuParada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                for(int i = 0; i < listaMM.size(); i++){
                    motoMecTO = (MotoMecTO) listaMM.get(i);
                    if(motoMecTO.getDescMotoMec().equals("VOLTAR AO TRABALHO")) {
                        pcompContext.getApontMotoMecTO().setOpcor(motoMecTO.getOpcorMotoMec());
                    }
                }

                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                Intent it = new Intent(MotivoParadaActivity.this, MenuAtividadeActivity.class);
                startActivity(it);
                finish();

            }

        });

    }

    public void listarMotivoParada(){

        ArrayList listaPesq = new ArrayList();

        motoMecTO = new MotoMecTO();

        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("cargoMotoMec");
        if(pcompContext.getTipoFuncao() == 1) {
            pesquisa.setValor(282L);
        }
        else{
            pesquisa.setValor(175L);
        }
        listaPesq.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("tipoMotoMec");
        pesquisa2.setValor((long) 2);
        listaPesq.add(pesquisa2);

        MotoMecPST motoMecPST = new MotoMecPST();
        listaMM  = motoMecPST.get(listaPesq);

        ArrayList<String> itens = new ArrayList<String>();

        for(int i = 0; i < listaMM.size(); i++){
            motoMecTO = (MotoMecTO) listaMM.get(i);
            if(!motoMecTO.getDescMotoMec().equals("VOLTAR AO TRABALHO")) {
                itens.add(motoMecTO.getDescMotoMec());
            }
            else if(motoMecTO.getDescMotoMec().equals("VOLTAR AO TRABALHO")) {
                pcompContext.getApontMotoMecTO().setOpcor(motoMecTO.getOpcorMotoMec());
            }
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMotParada);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                pos = position;

                motoMecTO = (MotoMecTO) listaMM.get(pos);
                AlertDialog.Builder alerta = new AlertDialog.Builder(MotivoParadaActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecTO.getDescMotoMec());

                pcompContext.getApontMotoMecTO().setOpcor(motoMecTO.getOpcorMotoMec());
                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lista.setSelection(pos + 1);
                    }
                });

                alerta.show();

            }

        });

    }

    public void onBackPressed()  {
    }

}
