package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TipoAplicActivity extends ActivityGeneric {

    private ListView lista;
    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_aplic);

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
                    pcompContext.setTipoAplic(2);
                } else if (position == 1) {
                    pcompContext.setTipoAplic(3);
                }
                Intent it = new Intent(TipoAplicActivity.this, MenuMotoMecActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

}
