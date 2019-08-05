package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;

public class MotoristaActivity extends ActivityGeneric {

    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista);

        pcompContext = (PCOMPContext) getApplication();

        Button buttonOkMotorista = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancMotorista = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkMotorista.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (!editTextPadrao.getText().toString().equals("")) {

                    MotoristaTO motoristaBDPesq = new MotoristaTO();
                    List listaMotorista = motoristaBDPesq.get("matricMotorista", Long.parseLong(editTextPadrao.getText().toString()));

                    if (listaMotorista.size() > 0) {

                        MotoristaTO motoristaBD = (MotoristaTO) listaMotorista.get(0);
                        pcompContext.getTurnoVarTO().setMatriculaMotoristaTurno(motoristaBD.getIdMotorista());

                        Intent it = new Intent(MotoristaActivity.this, ListaTurnoActivity.class);
                        startActivity(it);
                        finish();

                    }
                }

            }

        });

        buttonCancMotorista.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                } else {
                    Intent it = new Intent(MotoristaActivity.this, PrincipalActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        });

    }

    public void onBackPressed()  {
    }

}
