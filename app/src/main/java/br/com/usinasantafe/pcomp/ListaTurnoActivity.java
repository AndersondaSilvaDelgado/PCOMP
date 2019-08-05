package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.to.tb.estaticas.TurnoTO;

public class ListaTurnoActivity extends ActivityGeneric {

    private ListView lista;
    private PCOMPContext pcompContext;
    private List listaTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_turno);

        pcompContext = (PCOMPContext) getApplication();
        Button buttonRetListaTurno = (Button) findViewById(R.id.buttonRetListaTurno);

        listarMenu();

        buttonRetListaTurno.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(ListaTurnoActivity.this, MotoristaActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void listarMenu(){

        TurnoTO turnoBD = new TurnoTO();
        listaTurno =  turnoBD.all();
        ArrayList<String> itens = new ArrayList<String>();

        for(int i = 0; i < listaTurno.size(); i++){
            turnoBD = (TurnoTO) listaTurno.get(i);
            itens.add(turnoBD.getDescTurno());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listaTurno);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                TurnoTO turnoBD = (TurnoTO) listaTurno.get(position);
                pcompContext.getTurnoVarTO().setCodTurno(turnoBD.getIdTurno());

                Intent it = new Intent(ListaTurnoActivity.this, TipoFuncaoActivity.class);
                startActivity(it);
                finish();

            }

        });


    }

    public void onBackPressed()  {
    }

}
