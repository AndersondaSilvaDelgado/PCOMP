package br.com.usinasantafe.pcomp;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.usinasantafe.pcomp.control.CheckListCTR;
import br.com.usinasantafe.pcomp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.FuncionarioBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.util.ConexaoWeb;
import br.com.usinasantafe.pcomp.util.EnvioDadosServ;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;
import br.com.usinasantafe.pcomp.zxing.CaptureActivity;

public class MenuInicialActivity extends ActivityGeneric {

    private ListView listaMenuInicial;
    private PCOMPContext pcompContext;
    private ProgressDialog progressBar;

    private TextView textViewProcesso;
    private Handler customHandler = new Handler();
    private boolean verTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        pcompContext = (PCOMPContext) getApplication();

        textViewProcesso = (TextView) findViewById(R.id.textViewProcesso);

        verif();

        if (!checkPermission(Manifest.permission.CAMERA)) {
            String[] PERMISSIONS = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        customHandler.postDelayed(updateTimerThread, 0);

        progressBar = new ProgressDialog(this);
        CheckListCTR checkListCTR = new CheckListCTR();

        if(pcompContext.getMotoMecCTR().verBolAberto()){
            if(checkListCTR.verCabecAberto()){
                startTimer("N_NAC");
                checkListCTR.clearRespCabecAberto();
                pcompContext.setPosCheckList(1);
                Intent it = new Intent(MenuInicialActivity.this, ItemCheckListActivity.class);
                startActivity(it);
                finish();
            }
            else{
                if(pcompContext.getPneuCTR().verCalibAberto()){
                    startTimer("N_NAC");
                    Intent it = new Intent(MenuInicialActivity.this, ListaPosPneuActivity.class);
                    startActivity(it);
                    finish();
                }
                else {
                    verTela = true;
                    atualizarAplic();
                }
            }
        }
        else{
            verTela = false;
            atualizarAplic();
        }

        listarMenuInicial();

    }

    public void listarMenuInicial(){

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("CONFIGURAÇÃO");
        itens.add("SAIR");

        final AdapterList adapterList = new AdapterList(this, itens);
        listaMenuInicial = (ListView) findViewById(R.id.listaMenuInicial);
        listaMenuInicial.setAdapter(adapterList);


        listaMenuInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                if (position == 0) {

                    FuncionarioBean funcionarioBean = new FuncionarioBean();
                    if(funcionarioBean.hasElements() && pcompContext.getConfigCTR().hasElements()){
                        pcompContext.setVerPosTela(1);
                        customHandler.removeCallbacks(updateTimerThread);
                        Intent it = new Intent(MenuInicialActivity.this, FuncionarioActivity.class);
                        startActivity(it);
                        finish();
                    }

                } else if (position == 1) {
                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();
                } else if (position == 2) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

        });

    }

    public void onBackPressed()  {
    }

    public void startTimer(String verAtual) {

        Log.i("PMM", "VERATUAL = " + verAtual);

        String verAtualCL;
        if(verAtual.equals("N_NAC")){
            verAtualCL = verAtual;
        }
        else{
            int pos1 = verAtual.indexOf("#") + 1;
            verAtualCL = verAtual.substring(0, (pos1 - 1));
            String dthr = verAtual.substring(pos1);
            pcompContext.getConfigCTR().setDtServConfig(dthr);
        }

        pcompContext.setVerAtualCL(verAtualCL);
        Intent intent = new Intent(this, ReceberAlarme.class);
        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE) == null);

        if(progressBar.isShowing()){
            progressBar.dismiss();
        }

        if(alarmeAtivo){

            Log.i("PMM", "NOVO TIMER");
            PendingIntent p = PendingIntent.getBroadcast(getApplicationContext(), 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 1);

            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, p);

        }
        else{
            Log.i("PMM", "TIMER já ativo");
        }

        if(verTela){
            Intent it = new Intent(MenuInicialActivity.this, MenuMotoMecActivity.class);
            startActivity(it);
            finish();
        }

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void atualizarAplic(){
        ConexaoWeb conexaoWeb = new ConexaoWeb();
        if (conexaoWeb.verificaConexao(this)) {
            if (pcompContext.getConfigCTR().hasElements()) {
                progressBar.setCancelable(true);
                progressBar.setMessage("Buscando Atualização...");
                progressBar.show();
                VerifDadosServ.getInstance().verAtualAplic(pcompContext.versaoAplic, this, progressBar);
            }
        } else {
            startTimer("N_NAC");
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            if (pcompContext.getConfigCTR().hasElements()) {
                if (EnvioDadosServ.getInstance().getStatusEnvio() == 1) {
                    textViewProcesso.setTextColor(Color.YELLOW);
                    textViewProcesso.setText("Enviando Dados...");
                } else if (EnvioDadosServ.getInstance().getStatusEnvio() == 2) {
                    textViewProcesso.setTextColor(Color.RED);
                    textViewProcesso.setText("Existem Dados para serem Enviados");
                } else if (EnvioDadosServ.getInstance().getStatusEnvio() == 3) {
                    textViewProcesso.setTextColor(Color.GREEN);
                    textViewProcesso.setText("Todos os Dados já foram Enviados");
                }
            } else {
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Aparelho sem Equipamento");
            }
            customHandler.postDelayed(this, 10000);
        }
    };

    public void verif(){

        ConfigBean configBean = new ConfigBean();
        List configList = configBean.all();

        Log.i("PMM", "Config");

        for (int i = 0; i < configList.size(); i++) {

            configBean = (ConfigBean) configList.get(i);
            Log.i("PCOMP", "idEquip = " + configBean.getEquipConfig());
            Log.i("PCOMP", "senha = " + configBean.getSenhaConfig());

        }

        EquipBean equipBean = new EquipBean();
        List equipList = equipBean.all();

        Log.i("PMM", "Equip");

        for (int i = 0; i < equipList.size(); i++) {

            equipBean = (EquipBean) equipList.get(i);
            Log.i("PCOMP", "idEquip = " + equipBean.getIdEquip());

        }

    }

}
