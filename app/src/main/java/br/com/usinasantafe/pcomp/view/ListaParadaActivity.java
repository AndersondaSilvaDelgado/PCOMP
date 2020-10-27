package br.com.usinasantafe.pcomp.view;

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

import br.com.usinasantafe.pcomp.PCOMPContext;
import br.com.usinasantafe.pcomp.R;
import br.com.usinasantafe.pcomp.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.util.ConexaoWeb;

public class ListaParadaActivity extends ActivityGeneric {

    private ListView paradaListView;
    private PCOMPContext pcompContext;
    private List paradaList;
    private int posicao;
    private OSBean osBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_parada);

        pcompContext = (PCOMPContext) getApplication();

        Button buttonRetMenuParada = (Button) findViewById(R.id.buttonRetMenuParada);

        osBean = pcompContext.getConfigCTR().getOS();

        ArrayList<String> itens = new ArrayList<String>();
        paradaList = pcompContext.getMotoMecCTR().getParadaList(osBean.getTipoOS() + 2);
        for(int i = 0; i < paradaList.size(); i++){
            MotoMecBean motoMecBean = (MotoMecBean) paradaList.get(i);
            itens.add(motoMecBean.getDescrOperMotoMec());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        paradaListView = (ListView) findViewById(R.id.listViewMotParada);
        paradaListView.setAdapter(adapterList);

        paradaListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                posicao = position;
                MotoMecBean motoMecBean = (MotoMecBean) paradaList.get(posicao);
                pcompContext.getMotoMecCTR().setMotoMecBean(motoMecBean);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaParadaActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBean.getDescrOperMotoMec());

                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long statusCon;
                        ConexaoWeb conexaoWeb = new ConexaoWeb();
                        if (conexaoWeb.verificaConexao(ListaParadaActivity.this)) {
                            statusCon = 1L;
                        }
                        else{
                            statusCon = 0L;
                        }
                        pcompContext.getMotoMecCTR().insApontMM(getLongitude(), getLatitude(), statusCon);
                        paradaListView.setSelection(posicao + 1);
                    }
                });

                alerta.show();

            }

        });

        buttonRetMenuParada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Long statusCon;
                ConexaoWeb conexaoWeb = new ConexaoWeb();
                if (conexaoWeb.verificaConexao(ListaParadaActivity.this)) {
                    statusCon = 1L;
                }
                else{
                    statusCon = 0L;
                }
                pcompContext.getMotoMecCTR().insVoltaTrab(getLongitude(), getLatitude(), statusCon, osBean.getTipoOS() + 2);
                Intent it = new Intent(ListaParadaActivity.this, MenuMotoMecActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
