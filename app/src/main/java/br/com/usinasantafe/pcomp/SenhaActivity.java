package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;

public class SenhaActivity extends ActivityGeneric {

    private EditText editTextSenha;
    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        editTextSenha = (EditText)  findViewById(R.id.editTextSenha);
        Button btOkSenha =  (Button) findViewById(R.id.buttonOkSenha);
        Button btCancSenha = (Button) findViewById(R.id.buttonCancSenha);

        pcompContext = (PCOMPContext) getApplication();

        btOkSenha.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();

                if (!configuracaoTO.hasElements()) {

                    Intent it = new Intent(SenhaActivity.this, ConfiguracaoActivity.class);
                    startActivity(it);
                    finish();

                } else {

                    List<ConfiguracaoTO> lista = configuracaoTO.get("senhaConfig", editTextSenha.getText().toString());

                    if (lista.size() > 0) {

                        Intent it = new Intent(SenhaActivity.this, ConfiguracaoActivity.class);
                        startActivity(it);
                        finish();

                    }

                }



            }
        });

        btCancSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(SenhaActivity.this, PrincipalActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
