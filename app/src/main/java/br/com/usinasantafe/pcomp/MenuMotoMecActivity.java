package br.com.usinasantafe.pcomp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import br.com.usinasantafe.pcomp.model.bean.estaticas.FuncBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.PesqLeiraCompostoBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.PesqLeiraProdutoBean;
import br.com.usinasantafe.pcomp.util.ConexaoWeb;

public class MenuMotoMecActivity extends ActivityGeneric {

    private ListView motoMecListView;
    private PCOMPContext pcompContext;
    private ProgressDialog progressBar;
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

        FuncBean funcBean = pcompContext.getMotoMecCTR().getMatricNomeFunc();
        textViewMotorista.setText(funcBean.getMatricFunc() + " - " + funcBean.getNomeFunc());

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

        verLeiraComp();

        osBean = pcompContext.getConfigCTR().getOS();

        ArrayList<String> motoMecArrayList = new ArrayList<String>();
        motoMecList = pcompContext.getMotoMecCTR().getMotoMecList();
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
                        }
                        else{

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
                else if (motoMecBean.getCodFuncaoOperMotoMec() == 5) {

                    if (pcompContext.getLeiraCTR().pesqLeiraExibir()) {

                        pcompContext.setVerTelaLeira(true);
                        Intent it = new Intent(MenuMotoMecActivity.this, LeiraActivity.class);
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
                else {

                    if (osBean.getTipoOS() == 1L) {

                        if (motoMecBean.getCodFuncaoOperMotoMec() == 3) {



                        } else if (motoMecBean.getCodFuncaoOperMotoMec() == 4) {

                            if (pcompContext.getConfigCTR().getConfig().getStatusApontConfig() == 2) {

                                configTO.setStatusApontConfig(0L);
                                configTO.update();

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Pesquisando...");
                                progressBar.show();

                                EquipTO equipTO = new EquipTO();
                                List pesq = equipTO.get("idEquipamento", configTO.getEquipConfig());
                                equipTO = (EquipTO) pesq.get(0);


                                pcompContext.setVerTelaLeira(false);
                                Log.i("PCOMP", "VALOR ENVIADO:  " + String.valueOf(configTO.getEquipConfig()));
                                ManipDadosVerif.getInstance().verDados(String.valueOf(configTO.getEquipConfig()), "PesqBalancaProdBean"
                                        , MenuMotoMecActivity.this, LeiraActivity.class, progressBar);

                            } else {

                                String msg = "";

                                if(configTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO E FAÇA O CARREGAMENTO DO PRODUTO!";
                                }
                                else if(configTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO!";
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

                    } else if (osBean.getTipoOS() == 2) {

                        ConfigTO configTO = new ConfigTO();
                        List pesqEquip = configTO.all();
                        configTO = (ConfigTO) pesqEquip.get(0);

                        if (motoMecBD.getCodFuncaoOperMotoMec() == 2) {

                            if (configTO.getStatusApontConfig() == 0) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Atualizando OSs...");
                                progressBar.show();

                                ManipDadosReceb.getInstance().atualizarTabela(progressBar, "OSBean", MenuMotoMecActivity.this, OSActivity.class);

                            } else {

                                String msg = "";

                                if(configTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO E DEPOIS PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }
                                else if(configTO.getStatusApontConfig() == 2) {
                                    msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
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

                        } else if (motoMecBD.getCodFuncaoOperMotoMec() == 3) {

                            if (configTO.getStatusApontConfig() == 1) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
                                enviaApontCarregComp();
                                configTO.setStatusApontConfig(2L);
                                configTO.update();

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                                alerta.setTitle("ATENÇÃO");
                                alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBD.getDescrOperMotoMec());

                                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        motoMecListView.setSelection(posicao + 1);
                                    }
                                });

                                alerta.show();

                            } else {

                                String msg = "";

                                if(configTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO!";
                                }
                                else if(configTO.getStatusApontConfig() == 2) {
                                    msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
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

                        } else if (motoMecBD.getCodFuncaoOperMotoMec() == 4) {

                            if (configTO.getStatusApontConfig() == 2) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
                                configTO.setStatusApontConfig(0L);
                                configTO.update();

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                                alerta.setTitle("ATENÇÃO");
                                alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBD.getDescrOperMotoMec());

                                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        motoMecListView.setSelection(posicao + 1);
                                    }
                                });

                                alerta.show();

                            } else {

                                String msg = "";

                                if(configTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO E FAÇA O CARREGAMENTO DO PRODUTO!";
                                }
                                else if(configTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO!";
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

                    }
                }
            }

        });

    }

    private void verLeiraComp() {

        if ((pcompContext.getTipoAplic() == 2) && (pcompContext.isVerOS())) {

            progressBar = new ProgressDialog(MenuMotoMecActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Pesquisando...");
            progressBar.show();

            ConfigTO configTO = new ConfigTO();
            List listConfig = configTO.all();
            configTO = (ConfigTO) listConfig.get(0);

            PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();
            pesqBalancaCompTO.setEquip(configTO.getEquipConfig());
            pesqBalancaCompTO.setOs(configTO.getOsConfig());

            configTO.setStatusApontConfig(1L);
            configTO.update();

            pcompContext.setVerTelaLeira(false);
            ManipDadosEnvio.getInstance().envioPesqBalancaComp(pesqBalancaCompTO, MenuMotoMecActivity.this,
                    LeiraActivity.class, progressBar);

            pcompContext.setVerOS(false);

        }

    }

    private void enviaApontCarregComp() {

        PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();
        List lPesq = pesqBalancaCompTO.all();
        pesqBalancaCompTO = (PesqBalancaCompTO) lPesq.get(0);

        pcompContext.getCarregTO().setTipoCarreg(2L);
        pcompContext.getCarregTO().setOsCarreg(pesqBalancaCompTO.getOs());
        pcompContext.getCarregTO().setLeiraCarreg(pesqBalancaCompTO.getIdLeira());

        ManipDadosEnvio.getInstance().salvaCarreg(pcompContext.getCarregTO(), pcompContext.getTurnoVarTO());

    }

    public void onBackPressed()  {
    }

}
