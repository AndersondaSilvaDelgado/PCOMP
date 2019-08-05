package br.com.usinasantafe.pcomp.bo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.com.usinasantafe.pcomp.conWEB.ConHttpGetBDGenerico;
import br.com.usinasantafe.pcomp.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pcomp.pst.GenericRecordable;
import br.com.usinasantafe.pcomp.to.tb.estaticas.DataTO;

public class ManipDadosReceb {

	private ArrayList tabelaAtualizar;
	private static ManipDadosReceb instance = null;
	private int contAtualizaBD = 0;
	private String classe = "";
	private ProgressDialog progressDialog;
	private int qtdeBD = 0;
	private GenericRecordable genericRecordable;
	private Context context;
	private int tipoReceb;
	private UrlsConexaoHttp urlsConexaoHttp;
	private Context telaAtual;
	private Class telaProx;
	
	public ManipDadosReceb() {
		// TODO Auto-generated constructor stub
		genericRecordable = new GenericRecordable();
	}
	
    public static ManipDadosReceb getInstance() {
        if (instance == null)
        instance = new ManipDadosReceb();
        return instance;
    }
	
	@SuppressWarnings("unchecked")
	public void manipularDadosHttp(String tipo, String result){
		
		if(!result.equals("")){
		
			if(tipo.equals("datahorahttp")){
				Log.i("PCOMP", "TIPO -> " + tipo);
				Log.i("PCOMP", "RESULT -> " + result);
				Tempo.getInstance().manipDataHora(result);
			}
			else{
				
				try{

					Log.i("PCOMP", "TIPO -> " + tipo);
					Log.i("PCOMP", "RESULT -> " + result);
					
					JSONObject jObj = new JSONObject(result);
					JSONArray jsonArray = jObj.getJSONArray("dados");
					Class classe = Class.forName(manipLocalClasse(tipo));
					genericRecordable.deleteAll(classe);
					
					for(int i = 0; i < jsonArray.length(); i++){
						
						JSONObject objeto = jsonArray.getJSONObject(i);
						Gson gson = new Gson();
						genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);
						
					}
					
					Log.i("PCOMP", " SALVOU DADOS ");
					
					if(contAtualizaBD > 0){
						atualizandoBD();
					}
				
				} 
				catch (Exception e) {
				// TODO Auto-generated catch block
				Log.i("ERRO", "Erro Manip = " + e);
				}	
				
			}
			
		}
		else{
			encerrar();
		}
		
	}

	public void atualizarBD(ProgressDialog progressDialog){
		
		try {
			
			this.tipoReceb = 1;
			this.progressDialog = progressDialog;
			tabelaAtualizar = new ArrayList();
	        Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl); 

	        for (Field field : retClasse.getDeclaredFields()) {
	            String campo = field.getName();
	            Log.i("ERRO", "Campo = " + campo);
	            if(campo.contains("TO")){
	            	tabelaAtualizar.add(campo);
	            }
	            
	        }
	        
	        classe = (String) tabelaAtualizar.get(contAtualizaBD);
			
	        String[] url = {classe};
			
		    contAtualizaBD++;

	        ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
	        conHttpGetBDGenerico.execute(url);
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("ERRO", "Erro Manip2 = " + e);
		}
        
	}

	public void atualizarBD() {

		try {

			this.tipoReceb = 2;
			tabelaAtualizar = new ArrayList();
			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				Log.i("ERRO", "Campo = " + campo);
				if (campo.contains("TO")) {
					tabelaAtualizar.add(campo);
				}

			}

			classe = (String) tabelaAtualizar.get(contAtualizaBD);

			String[] url = {classe};

			contAtualizaBD++;

			ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
			conHttpGetBDGenerico.execute(url);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("ERRO", "Erro Manip2 = " + e);
		}

	}

	public void atualizarTabela(ProgressDialog progressDialog, String tabela, Context telaAtual, Class telaProx){

		try {

			this.tipoReceb = 3;
			this.telaAtual = telaAtual;
			this.telaProx = telaProx;
			this.progressDialog = progressDialog;

			String[] url = {tabela};
			contAtualizaBD = 1;

			ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
			conHttpGetBDGenerico.execute(url);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("ERRO", "Erro Manip2 = " + e);
		}

	}

	public void atualizandoBD(){

		if(this.tipoReceb == 1){
		
			qtdeBD = tabelaAtualizar.size();
			
			if(contAtualizaBD < tabelaAtualizar.size()){
				
				this.progressDialog.setProgress((contAtualizaBD * 100) / qtdeBD);
		        classe = (String) tabelaAtualizar.get(contAtualizaBD);
				String[] url = {classe};
				contAtualizaBD++;

				ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
		        conHttpGetBDGenerico.execute(url);
		        
			}
			else
			{
				this.progressDialog.dismiss();
				contAtualizaBD = 0;
				AlertDialog.Builder alerta = new AlertDialog.Builder(this.context);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("FOI ATUALIZADO COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
	
					}
				});
				
				alerta.show();
			}
		
		}
		else if(this.tipoReceb == 2){
			
			qtdeBD = tabelaAtualizar.size();
			
			if(contAtualizaBD < tabelaAtualizar.size()){
				
		        classe = (String) tabelaAtualizar.get(contAtualizaBD);
				String[] url = {classe};
				contAtualizaBD++;

				ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
		        conHttpGetBDGenerico.execute(url);
		        
			}
			else
			{
				contAtualizaBD = 0;
			}
			
		}
		else if(this.tipoReceb == 3){

			this.progressDialog.dismiss();
			contAtualizaBD = 0;

			Intent it = new Intent(telaAtual, telaProx);
			telaAtual.startActivity(it);

		}

	}
	
	
	public void encerrar(){
		
		if(this.tipoReceb == 1){
			
			this.progressDialog.dismiss();
			AlertDialog.Builder alerta = new AlertDialog.Builder(this.context);
			alerta.setTitle("ATENCAO");
			alerta.setMessage("FALHA NA CONEXAO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
			alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
	
				}
			});
			
			alerta.show();
			
		}
	}

	public void tempo(){

		try {

			DataTO dataTO = new DataTO();
			dataTO.deleteAll();

			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);
			tabelaAtualizar = new ArrayList();

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				Log.i("ERRO", "Campo = " + campo);
				if (campo.equals("datahorahttp")) {
					Log.i("ERRO", "Campo = " + campo);
					tabelaAtualizar.add(campo);
				}

			}

			classe = (String) tabelaAtualizar.get(contAtualizaBD);

			String[] url = {classe};

			ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
			conHttpGetBDGenerico.execute(url);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("ERRO", "Erro Manip2 = " + e);
		}

	}

	public String manipLocalClasse(String classe){
	    if(classe.contains("TO")){
	    	classe = urlsConexaoHttp.localPSTEstatica + classe;
	    }
		return classe;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
}
