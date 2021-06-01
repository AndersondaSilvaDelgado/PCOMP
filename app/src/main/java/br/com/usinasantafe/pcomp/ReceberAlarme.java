package br.com.usinasantafe.pcomp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.usinasantafe.pcomp.model.pst.DatabaseHelper;
import br.com.usinasantafe.pcomp.util.EnvioDadosServ;
import br.com.usinasantafe.pcomp.util.Tempo;

/**
 * BroadcastReceiver para receber o alarme depois do tempo especificado
 * 
 * @author ricardo
 * 
 */
public class ReceberAlarme extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if(DatabaseHelper.getInstance() == null){
			new DatabaseHelper(context);
		}

		Log.i("ECM", "DATA HORA = " + Tempo.getInstance().dataComHora().getDataHora());
		if(EnvioDadosServ.getInstance().verifDadosEnvio()){
			Log.i("ECM", "ENVIANDO");
			EnvioDadosServ.getInstance().envioDados(context);
		}
	}
}