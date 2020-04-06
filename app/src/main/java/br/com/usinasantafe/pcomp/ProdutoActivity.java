package br.com.usinasantafe.pcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pcomp.model.dao.ManipDadosEnvio;
import br.com.usinasantafe.pcomp.to.tb.estaticas.ProdutoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfigTO;

public class ProdutoActivity extends ActivityGeneric {

    public static final int REQUEST_CODE = 0;
    private TextView txResult;
    private ProdutoTO produtoTO;
    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        pcompContext = (PCOMPContext) getApplication();
        produtoTO = new ProdutoTO();

        txResult = (TextView) findViewById(R.id.txResult);

        Button buttonOkOS = (Button) findViewById(R.id.buttonOkProd);
        Button buttonCancOS = (Button) findViewById(R.id.buttonCancProd);

        buttonOkOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(!txResult.getText().equals("PRODUTO:")) {

                    pcompContext.getCarregTO().setTipoCarreg(1L);
                    pcompContext.getCarregTO().setProdCarreg(produtoTO.getIdProduto());
                    ManipDadosEnvio.getInstance().salvaCarreg(pcompContext.getCarregTO(), pcompContext.getTurnoVarTO());

                    ConfigTO configTO = new ConfigTO();
                    List listConfig = configTO.all();
                    configTO = (ConfigTO) listConfig.get(0);
                    configTO.setStatusApontConfig(2L);
                    configTO.update();

                    Intent it = new Intent(ProdutoActivity.this, MenuMotoMecActivity.class);
                    startActivity(it);
                    finish();

                }

            }
        });

        buttonCancOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(ProdutoActivity.this, MenuMotoMecActivity.class);
                startActivity(it);
                finish();

            }
        });

    }

    public void callZXing(View view){
        Intent it = new Intent(ProdutoActivity.this, br.com.usinasantafe.pcomp.zxing.CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){

            produtoTO.setCodProduto(data.getStringExtra("SCAN_RESULT"));
            List listProduto = produtoTO.get("codProduto", produtoTO.getCodProduto());

            if (listProduto.size() > 0) {
                produtoTO = (ProdutoTO) listProduto.get(0);
                txResult.setText("PRODUTO: " + produtoTO.getCodProduto() + "\n" + produtoTO.getDescProduto());
            }

        }
    }

    public void onBackPressed()  {
    }

}
