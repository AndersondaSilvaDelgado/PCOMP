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

import br.com.usinasantafe.pcomp.bo.ManipDadosEnvio;
import br.com.usinasantafe.pcomp.bo.ManipDadosReceb;
import br.com.usinasantafe.pcomp.bo.ManipDadosVerif;
import br.com.usinasantafe.pcomp.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcomp.to.tb.estaticas.EquipamentoTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaCompTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaProdTO;

public class MenuAtividadeActivity extends ActivityGeneric {

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
                Intent it = new Intent(MenuAtividadeActivity.this, MotivoParadaActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonRetMotoMec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(MenuAtividadeActivity.this, PrincipalActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    private void listarMenuAtividade() {

        ArrayList listaPesq = new ArrayList();
        MotoMecTO motoMecTO = new MotoMecTO();

        verLeiraComp();

        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("cargoMotoMec");
        if(pcompContext.getTipoFuncao() == 1) {
            pesquisa.setValor(282L);
        }
        else{
            pesquisa.setValor(175L);
        }
        listaPesq.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("tipoMotoMec");
        pesquisa2.setValor(1L);
        listaPesq.add(pesquisa2);

        ArrayList<String> itens = new ArrayList<String>();
        listaAtivMotoMec = motoMecTO.getAndOrderBy(listaPesq, "posicaoMotoMec", true);

        for (int i = 0; i < listaAtivMotoMec.size(); i++) {
            motoMecTO = (MotoMecTO) listaAtivMotoMec.get(i);
            itens.add(motoMecTO.getDescMotoMec());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMenuAtividade);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                MotoMecTO motoMecBD = (MotoMecTO) listaAtivMotoMec.get(position);
                pcompContext.getApontMotoMecTO().setOpcor(motoMecBD.getOpcorMotoMec());

                pos = position;

                if (motoMecBD.getFuncaoMotoMec() == 1) {  // ATIVIDADES NORMAIS

                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
                    alerta.setTitle("ATENÇÃO");

                    alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBD.getDescMotoMec());
                    ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lista.setSelection(pos + 1);
                        }
                    });

                    alerta.show();

                } else if (motoMecBD.getFuncaoMotoMec() == 5) {

                    PesqBalancaProdTO pesqBalancaProdTO = new PesqBalancaProdTO();
                    PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();

                    if ((pesqBalancaProdTO.count() > 0) || (pesqBalancaCompTO.count() > 0)) {

                        pcompContext.setVerTelaLeira(true);
                        Intent it = new Intent(MenuAtividadeActivity.this, LeiraActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
                        alerta.setTitle("ATENÇÃO");

                        if (pcompContext.getTipoFuncao() == 1) {
                            alerta.setMessage("NÃO CONTÉM NENHUMA LEIRA PARA DESCARREGAMENTO.");
                        } else if (pcompContext.getTipoFuncao() == 2) {
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

                    if (pcompContext.getTipoFuncao() == 1) {

                        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
                        List pesqEquip = configuracaoTO.all();
                        configuracaoTO = (ConfiguracaoTO) pesqEquip.get(0);

                        if (motoMecBD.getFuncaoMotoMec() == 2) {

                            if (configuracaoTO.getStatusApontConfig() == 0) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Atualizando OSs...");
                                progressBar.show();

                                ManipDadosReceb.getInstance().atualizarTabela(progressBar, "OSTO", MenuAtividadeActivity.this, OSActivity.class);

                            } else {

                                String msg = "";

                                if(configuracaoTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO E DEPOIS PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }
                                else if(configuracaoTO.getStatusApontConfig() == 2) {
                                    msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
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

                        } else if (motoMecBD.getFuncaoMotoMec() == 3) {

                            if (configuracaoTO.getStatusApontConfig() == 1) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
                                Intent it = new Intent(MenuAtividadeActivity.this, ProdutoActivity.class);
                                startActivity(it);
                                finish();

                            } else {

                                String msg = "";

                                if(configuracaoTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO!";
                                }
                                else if(configuracaoTO.getStatusApontConfig() == 2) {
                                    msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
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

                        } else if (motoMecBD.getFuncaoMotoMec() == 4) {

                            if (configuracaoTO.getStatusApontConfig() == 2) {

                                configuracaoTO.setStatusApontConfig(0L);
                                configuracaoTO.update();

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Pesquisando...");
                                progressBar.show();

                                EquipamentoTO equipamentoTO = new EquipamentoTO();
                                List pesq = equipamentoTO.get("idEquipamento", configuracaoTO.getEquipConfig());
                                equipamentoTO = (EquipamentoTO) pesq.get(0);

                                if (equipamentoTO.getClassifEquipamento() == 1) {
                                    pcompContext.getApontMotoMecTO().setOpcor(175L);
                                } else {
                                    pcompContext.getApontMotoMecTO().setOpcor(282L);
                                }

                                pcompContext.setVerTelaLeira(false);
                                Log.i("PCOMP", "VALOR ENVIADO:  " + String.valueOf(configuracaoTO.getEquipConfig()));
                                ManipDadosVerif.getInstance().verDados(String.valueOf(configuracaoTO.getEquipConfig()), "PesqBalancaProdTO"
                                        , MenuAtividadeActivity.this, LeiraActivity.class, progressBar);

                            } else {

                                String msg = "";

                                if(configuracaoTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO E FAÇA O CARREGAMENTO DO PRODUTO!";
                                }
                                else if(configuracaoTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO!";
                                }

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
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


                    } else if (pcompContext.getTipoFuncao() == 2) {

                        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
                        List pesqEquip = configuracaoTO.all();
                        configuracaoTO = (ConfiguracaoTO) pesqEquip.get(0);

                        if (motoMecBD.getFuncaoMotoMec() == 2) {

                            if (configuracaoTO.getStatusApontConfig() == 0) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Atualizando OSs...");
                                progressBar.show();

                                ManipDadosReceb.getInstance().atualizarTabela(progressBar, "OSTO", MenuAtividadeActivity.this, OSActivity.class);

                            } else {

                                String msg = "";

                                if(configuracaoTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO E DEPOIS PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }
                                else if(configuracaoTO.getStatusApontConfig() == 2) {
                                    msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
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

                        } else if (motoMecBD.getFuncaoMotoMec() == 3) {

                            if (configuracaoTO.getStatusApontConfig() == 1) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
                                enviaApontCarregComp();
                                configuracaoTO.setStatusApontConfig(2L);
                                configuracaoTO.update();

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
                                alerta.setTitle("ATENÇÃO");
                                alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBD.getDescMotoMec());

                                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        lista.setSelection(pos + 1);
                                    }
                                });

                                alerta.show();

                            } else {

                                String msg = "";

                                if(configuracaoTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO!";
                                }
                                else if(configuracaoTO.getStatusApontConfig() == 2) {
                                    msg = "POR FAVOR, PASSE NA BALANÇA PARA FAZER A PESAGEM CARREGADO!";
                                }

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
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

                        } else if (motoMecBD.getFuncaoMotoMec() == 4) {

                            if (configuracaoTO.getStatusApontConfig() == 2) {

                                ManipDadosEnvio.getInstance().salvaMotoMec(pcompContext.getTurnoVarTO(), pcompContext.getApontMotoMecTO());
                                configuracaoTO.setStatusApontConfig(0L);
                                configuracaoTO.update();

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
                                alerta.setTitle("ATENÇÃO");
                                alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBD.getDescMotoMec());

                                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        lista.setSelection(pos + 1);
                                    }
                                });

                                alerta.show();

                            } else {

                                String msg = "";

                                if(configuracaoTO.getStatusApontConfig() == 0) {
                                    msg = "POR FAVOR, TIRE A PESAGEM TARA DO EQUIPAMENTO E FAÇA O CARREGAMENTO DO PRODUTO!";
                                }
                                else if(configuracaoTO.getStatusApontConfig() == 1) {
                                    msg = "POR FAVOR, FAÇA O CARREGAMENTO DO PRODUTO!";
                                }

                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAtividadeActivity.this);
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

        if ((pcompContext.getTipoFuncao() == 2) && (pcompContext.isVerOS())) {

            progressBar = new ProgressDialog(MenuAtividadeActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Pesquisando...");
            progressBar.show();

            ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
            List listConfig = configuracaoTO.all();
            configuracaoTO = (ConfiguracaoTO) listConfig.get(0);

            PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();
            pesqBalancaCompTO.setEquip(configuracaoTO.getEquipConfig());
            pesqBalancaCompTO.setOs(configuracaoTO.getOsConfig());

            configuracaoTO.setStatusApontConfig(1L);
            configuracaoTO.update();

            pcompContext.setVerTelaLeira(false);
            ManipDadosEnvio.getInstance().envioPesqBalancaComp(pesqBalancaCompTO, MenuAtividadeActivity.this,
                    LeiraActivity.class, progressBar);

            pcompContext.setVerOS(false);

        }

    }

    private void enviaApontCarregComp() {

        PesqBalancaCompTO pesqBalancaCompTO = new PesqBalancaCompTO();
        List lPesq = pesqBalancaCompTO.all();
        pesqBalancaCompTO = (PesqBalancaCompTO) lPesq.get(0);

        pcompContext.getApontCarregTO().setTipoApontCarreg(2L);
        pcompContext.getApontCarregTO().setOsApontCarreg(pesqBalancaCompTO.getOs());
        pcompContext.getApontCarregTO().setLeiraApontCarreg(pesqBalancaCompTO.getIdLeira());

        ManipDadosEnvio.getInstance().salvaCarreg(pcompContext.getApontCarregTO(), pcompContext.getTurnoVarTO());

    }

    public void onBackPressed()  {
    }

}
