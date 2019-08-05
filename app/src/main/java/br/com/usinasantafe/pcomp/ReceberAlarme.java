package br.com.usinasantafe.pcomp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.usinasantafe.pcomp.bo.ManipDadosEnvio;
import br.com.usinasantafe.pcomp.bo.Tempo;
import br.com.usinasantafe.pcomp.pst.DatabaseHelper;

/**
 * BroadcastReceiver para receber o alarme depois do tempo especificado
 * 
 * @author ricardo
 * 
 */
public class ReceberAlarme extends BroadcastReceiver {

	private DatabaseHelper databaseHelper = null;

	@Override
	public void onReceive(Context context, Intent intent) {

		if(DatabaseHelper.getInstance() == null){
			new DatabaseHelper(context);
		}

		if(Tempo.getInstance().getDatahora() != null) {
			Tempo.getInstance().getDatahora().setTime(Tempo.getInstance().getDatahora().getTime() + 60000L);
		}

		Log.i("PIA", "DATA HORA = " + Tempo.getInstance().data());
		if(ManipDadosEnvio.getInstance().verifDadosEnvio()){
			Log.i("PIA", "ENVIANDO");
			ManipDadosEnvio.getInstance().envioDados(context);
		}
	}
}