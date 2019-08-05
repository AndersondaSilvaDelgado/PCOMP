package br.com.usinasantafe.pcomp;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.usinasantafe.pcomp.bo.ManipDadosEnvio;
import br.com.usinasantafe.pcomp.bo.ManipDadosReceb;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;

public class PrincipalActivity extends ActivityGeneric {

    private ListView lista;
    private PCOMPContext pcompContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pcompContext = (PCOMPContext) getApplication();

        if(!checkPermission(Manifest.permission.CAMERA)){
            String[] PERMISSIONS = {android.Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        timer();
        listarMenuInicial();

    }

    public void listarMenuInicial(){

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("CONFIGURAÇÃO");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listaMenuInicial);
        lista.setAdapter(adapterList);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                if (position == 0) {

                    MotoristaTO motoristaTO = new MotoristaTO();
                    if(motoristaTO.hasElements()){
                        Intent it = new Intent(PrincipalActivity.this, MotoristaActivity.class);
                        startActivity(it);
                        finish();
                    }

                } else if (position == 1) {
                    Intent it = new Intent(PrincipalActivity.this, SenhaActivity.class);
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

    public void timer() {

        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarmeAtivo){

            ManipDadosReceb.getInstance().tempo();
            Log.i("PCOMP", "NOVO ALARME");

            Intent intent = new Intent("EXECUTAR_ALARME");
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 1);

            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, p);

        }
        else{
            Log.i("PCOMP", "Alarme já ativo");
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}
