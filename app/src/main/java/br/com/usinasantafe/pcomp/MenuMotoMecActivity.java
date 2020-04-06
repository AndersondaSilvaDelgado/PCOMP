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

import br.com.usinasantafe.pcomp.model.dao.ManipDadosEnvio;
import br.com.usinasantafe.pcomp.model.dao.ManipDadosReceb;
import br.com.usinasantafe.pcomp.model.dao.ManipDadosVerif;
import br.com.usinasantafe.pcomp.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcomp.to.tb.estaticas.EquipTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.OperMotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfigTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaCompTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaProdTO;

public class MenuMotoMecActivity extends ActivityGeneric {

    private ListView lista;
    private PCOMPContext pcompContext;
    private ProgressDialog progressBar;
    private List listaAtivMotoMec;
    private TextView textViewMotorista;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_atividade);

        pcompContext = (PCOMPContext) getApplication();
        pcompContext.setVerTelaLeira(false);

        Button buttonParadaMotoMec = (Button) findViewById(R.id.buttonParadaMotoMec);
        Button buttonRetMotoMec = (Button) findViewById(R.id.buttonRetMotoMec);
        textViewMotorista = (TextView) findViewById(R.id.textViewMotorista);

        MotoristaTO motoristaTO = new MotoristaTO();
        List lMotorista = motoristaTO.get("idMotorista", pcompContext.getTurnoVarTO().getMatriculaMotoristaTurno());
        motoristaTO = (MotoristaTO) lMotorista.get(0);

        Long codMotorista = motoristaTO.getMatricMotorista();
        String nomeMotorista = motoristaTO.getNomeMotorista();

        textViewMotorista.setText(codMotorista + " - " + nomeMotorista);

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

        ArrayList listaPesq = new ArrayList();
        OperMotoMecTO operMotoMecTO = new OperMotoMecTO();

        verLeiraComp();

        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("aplicOperMotoMec");
        pesquisa.setValor(pcompContext.getTipoAplic());
        listaPesq.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("tipoOperMotoMec");
        pesquisa2.setValor(1L);
        listaPesq.add(pesquisa2);

        ArrayList<String> itens = new ArrayList<String>();
        listaAtivMotoMec = operMotoMecTO.getAndOrderBy(listaPesq, "posOperMotoMec", true);

        for (int i = 0; i < listaAtivMotoMec.size(); i++) {
            operMotoMecTO = (OperMotoMecTO) listaAtivMotoMec.get(i);
            itens.add(operMotoMecTO.getDescrOperMotoMec());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMenuAtividade);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                OperMotoMecTO motoMecBD = (OperMotoMecTO) listaAtivMotoMec.get(position);
                pcompContext.getApontMotoMecTO().setOpcor(motoMecBD.getCodOperMotoMec());

                pos = position;

                if (motoMecBD.getCodFuncaoOperMotoMec() == 1) {  // ATIVIDADES NORMAIS

                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMotoMecActivity.this);
                    alerta.setTitle("ATENÇÃO");

                    alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBD.getDescrOperMotoMec());
                    ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lista.setSelection(pos + 1);
                        }
                    });

                    alerta.show();

                } else if (motoMecBD.getCodFuncaoOperMotoMec() == 5) {

                    PesqBalancaProdTO pesqBalancaProdTO = new PesqBalancaProdTO();
                    PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();

                    if ((pesqBalancaProdTO.count() > 0) || (pesqBalancaCompTO.count() > 0)) {

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

                        ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lista.setSelection(pos + 1);
                            }
                        });

                        alerta.show();

                    }

                } else {

                    if (pcompContext.getTipoAplic() == 1) {

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
                                        lista.setSelection(pos + 1);
                                    }
                                });

                                alerta.show();

                            }

                        } else if (motoMecBD.getCodFuncaoOperMotoMec() == 3) {

                            if (configTO.getStatusApontConfig() == 1) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
                                Intent it = new Intent(MenuMotoMecActivity.this, ProdutoActivity.class);
                                startActivity(it);
                                finish();

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
                                        lista.setSelection(pos + 1);
                                    }
                                });

                                alerta.show();

                            }

                        } else if (motoMecBD.getCodFuncaoOperMotoMec() == 4) {

                            if (configTO.getStatusApontConfig() == 2) {

                                configTO.setStatusApontConfig(0L);
                                configTO.update();

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Pesquisando...");
                                progressBar.show();

                                EquipTO equipTO = new EquipTO();
                                List pesq = equipTO.get("idEquipamento", configTO.getEquipConfig());
                                equipTO = (EquipTO) pesq.get(0);

//                                if (equipTO.getTipoEquip() == 1) {
//                                    pcompContext.getApontMotoMecTO().setOpcor(175L);
//                                } else {
//                                    pcompContext.getApontMotoMecTO().setOpcor(282L);
//                                }

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
                                        lista.setSelection(pos + 1);
                                    }
                                });

                                alerta.show();

                            }

                        }


                    } else if (pcompContext.getTipoAplic() == 2) {

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
                                        lista.setSelection(pos + 1);
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
                                        lista.setSelection(pos + 1);
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
                                        lista.setSelection(pos + 1);
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
                                        lista.setSelection(pos + 1);
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
                                        lista.setSelection(pos + 1);
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
