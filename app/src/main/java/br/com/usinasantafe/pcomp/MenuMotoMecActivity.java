package br.com.usinasantafe.pcomp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.FuncionarioBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.util.ConexaoWeb;

public class MenuMotoMecActivity extends ActivityGeneric {

    private ListView motoMecListView;
    private PCOMPContext pcompContext;
    private TextView textViewMotorista;
    private int posicao;
    private List motoMecList;
    private OSBean osBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_moto_mec);

        pcompContext = (PCOMPContext) getApplication();
        pcompContext.setVerTelaLeira(false);

        Button buttonParadaMotoMec = (Button) findViewById(R.id.buttonParadaMotoMec);
        Button buttonRetMotoMec = (Button) findViewById(R.id.buttonRetMotoMec);
        textViewMotorista = (TextView) findViewById(R.id.textViewMotorista);

        FuncionarioBean funcionarioBean = pcompContext.getMotoMecCTR().getMatricNomeFunc();
        textViewMotorista.setText(funcionarioBean.getMatricFunc() + " - " + funcionarioBean.getNomeFunc());

        listarMenuAtividade();

        buttonParadaMotoMec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(MenuMotoMecActivity.this, ListaParadaActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonRetMotoMec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(MenuMotoMecActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    private void listarMenuAtividade() {

        osBean = pcompContext.getConfigCTR().getOS();

        ArrayList<String> motoMecArrayList = new ArrayList<String>();
        motoMecList = pcompContext.getMotoMecCTR().getMotoMecList(osBean.getTipoOS() + 2);
        for (int i = 0; i < motoMecList.size(); i++) {
            MotoMecBean motoMecBean = (MotoMecBean) motoMecList.get(i);
            motoMecArrayList.add(motoMecBean.getDescrOperMotoMec());
        }

        AdapterList adapterList = new AdapterList(this, motoMecArrayList);
        motoMecListView = (ListView) findViewById(R.id.motoMecListView);
        motoMecListView.setAdapter(adapterList);

        motoMecListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                MotoMecBean motoMecBean = (MotoMecBean) motoMecList.get(position);
                pcompContext.getMotoMecCTR().setMotoMecBean(motoMecBean);

                Log.i("ECM", "CodFuncaoOperMotoMec = " + motoMecBean.getCodFuncaoOperMotoMec());

                posicao = position;

                if (motoMecBean.getCodFuncaoOperMotoMec() == 1) {  // ATIVIDADES NORMAIS

                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBean.getDescrOperMotoMec());

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Long statusCon;
                            ConexaoWeb conexaoWeb = new ConexaoWeb();
                            if (conexaoWeb.verificaConexao(MenuMotoMecActivity.this)) {
                                statusCon = 1L;
                            }
                            else{
                                statusCon = 0L;
                            }

                            pcompContext.getMotoMecCTR().insApontMM(getLongitude(), getLatitude(), statusCon);
                            motoMecListView.setSelection(posicao + 1);

                        }
                    });

                    alerta.show();

                }
                else if (motoMecBean.getCodFuncaoOperMotoMec() == 2) {

                    if (pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 0) {

                        pcompContext.setVerPosTela(2);
                        Intent it = new Intent(MenuMotoMecActivity.this, OSActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        String msg = "";

                        if(pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 1) {
                            msg = "POR FAVOR, CARREGUE O EQUIPAMENTO E DEPOIS PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                        }
                        else if(pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 2) {
                            msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM DO EQUIPAMENTO CARREGADO!";
                        }

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage(msg);
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                motoMecListView.setSelection(posicao + 1);
                            }
                        });

                        alerta.show();

                    }

                }
                else if (motoMecBean.getCodFuncaoOperMotoMec() == 3) {

                    if (pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 1) {

                        if (osBean.getTipoOS() == 1L) {

                            Intent it = new Intent(MenuMotoMecActivity.this, ProdutoActivity.class);
                            startActivity(it);
                            finish();

                        } else{

                            Long statusCon;
                            ConexaoWeb conexaoWeb = new ConexaoWeb();
                            if (conexaoWeb.verificaConexao(MenuMotoMecActivity.this)) {
                                statusCon = 1L;
                            }
                            else{
                                statusCon = 0L;
                            }

                            pcompContext.getMotoMecCTR().insApontMM(getLongitude(), getLatitude(), statusCon);
                            pcompContext.getCompostoCTR().apontCarreg(MenuMotoMecActivity.this);

                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBean.getDescrOperMotoMec());

                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    motoMecListView.setSelection(posicao + 1);
                                }
                            });

                            alerta.show();

                        }

                    } else {

                        String msg = "";

                        if(pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 0) {
                            msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO!";
                        }
                        else if(pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 2) {
                            msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM DO EQUIPAMENTO CARREGADO!";
                        }

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage(msg);
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                motoMecListView.setSelection(posicao + 1);
                            }
                        });

                        alerta.show();

                    }

                }
                else if (motoMecBean.getCodFuncaoOperMotoMec() == 4) {

                    if (pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 2) {

                        if (osBean.getTipoOS() == 1L) {
                            pcompContext.setVerPosTela(3);
                        } else {
                            pcompContext.setVerPosTela(4);
                        }

                        Intent it = new Intent(MenuMotoMecActivity.this, EsperaInfActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        String msg = "";

                        if(pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 0) {
                            msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO!";
                        }
                        else if(pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 1) {
                            msg = "POR FAVOR, CARREGUE O EQUIPAMENTO E DEPOIS PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                        }

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage(msg);
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                motoMecListView.setSelection(posicao + 1);
                            }
                        });

                        alerta.show();

                    }

                }
                else if (motoMecBean.getCodFuncaoOperMotoMec() == 5) {

                    if (pcompContext.getCompostoCTR().pesqLeiraExibir()) {

                        pcompContext.setVerTelaLeira(true);
                        Intent it = new Intent(MenuMotoMecActivity.this, InformacaoActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                        alerta.setTitle("ATENÇÃO");

                        if (pcompContext.getTipoAplic() == 2) {
                            alerta.setMessage("NÃO CONTÉM NENHUMA LEIRA PARA DESCARREGAMENTO.");
                        } else if (pcompContext.getTipoAplic() == 3) {
                            alerta.setMessage("NÃO CONTÉM NENHUMA LEIRA PARA CARREGAMENTO.");
                        }

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Long statusCon;
                                ConexaoWeb conexaoWeb = new ConexaoWeb();
                                if (conexaoWeb.verificaConexao(MenuMotoMecActivity.this)) {
                                    statusCon = 1L;
                                }
                                else{
                                    statusCon = 0L;
                                }
                                pcompContext.getMotoMecCTR().insApontMM(getLongitude(), getLatitude(), statusCon);
                                motoMecListView.setSelection(posicao + 1);
                            }
                        });

                        alerta.show();

                    }

                }
            }

        });

    }

    public void onBackPressed()  {
    }

}
