package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TipoFuncaoActivity extends ActivityGeneric {

    private ListView lista;
    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_funcao);

        pcompContext = (PCOMPContext) getApplication();
        listarMenuInicial();

    }

    public void listarMenuInicial(){

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("CARREG. DE TORTA/CINZA");
        itens.add("CARREG. DE COMPOSTO");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listaTipoFuncao);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    pcompContext.setTipoFuncao(1);
                } else if (position == 1) {
                    pcompContext.setTipoFuncao(2);
                }
                Intent it = new Intent(TipoFuncaoActivity.this, MenuAtividadeActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

}
