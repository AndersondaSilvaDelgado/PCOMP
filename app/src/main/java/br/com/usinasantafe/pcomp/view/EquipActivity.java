package br.com.usinasantafe.pcomp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pcomp.PCOMPContext;
import br.com.usinasantafe.pcomp.R;

public class EquipActivity extends Activity {

    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip);

        TextView textViewCodEquip = (TextView) findViewById(R.id.textViewCodEquip);
        TextView textViewDescEquip = (TextView) findViewById(R.id.textViewDescEquip);
        Button buttonOkCaminhao = (Button) findViewById(R.id.buttonOkCaminhao);
        Button buttonCancCaminhao = (Button) findViewById(R.id.buttonCancCaminhao);

        pcompContext = (PCOMPContext) getApplication();

        textViewCodEquip.setText(String.valueOf(pcompContext.getConfigCTR().getEquip().getNroEquip()));
        textViewDescEquip.setText(String.valueOf(pcompContext.getConfigCTR().getEquip().getDescrClasseEquip()));


        buttonOkCaminhao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pcompContext.getMotoMecCTR().setEquipBol();

                Intent it = new Intent(EquipActivity.this, ListaTurnoActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonCancCaminhao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent it = new Intent(EquipActivity.this, FuncionarioActivity.class);
                startActivity(it);
                finish();

            }

        });

    }

    public void onBackPressed()  {
    }

}
