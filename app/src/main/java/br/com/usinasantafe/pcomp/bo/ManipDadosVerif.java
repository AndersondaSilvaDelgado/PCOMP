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

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcomp.conWEB.ConHttpPostVerGenerico;
import br.com.usinasantafe.pcomp.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pcomp.pst.GenericRecordable;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontMotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaProdTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.TurnoVarTO;

/**
 * Created by anderson on 16/11/2015.
 */
public class ManipDadosVerif {

    private static ManipDadosVerif instance = null;
    private GenericRecordable genericRecordable;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private String variavel;
    private int qtde;
    private ProgressDialog progressDialog;
    private String dado;
    private String tipo;

    public ManipDadosVerif() {
        //genericRecordable = new GenericRecordable();
    }

    public static ManipDadosVerif getInstance() {
        if (instance == null)
            instance = new ManipDadosVerif();
        return instance;
    }

    public void manipularDadosHttp(String result, String tipo) {

        if (!result.equals("")) {

            if (tipo.equals("PesqBalancaProdTO")) {
                retornoPesqBalanca(result, tipo);
            } else {
                retornoVerifNormal(result, tipo);
            }

        }

    }

    public String manipLocalClasse(String classe) {
        if (classe.contains("TO")) {
            classe = urlsConexaoHttp.localPSTEstatica + classe;
        }
        return classe;
    }

    public void verDados(String dado, String tipo, Context telaAtual, Class telaProx, ProgressDialog progressDialog) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.dado = dado;
        this.tipo = tipo;

        envioDados();

    }

    public void verDados(String dado, String tipo, Context telaAtual, Class telaProx, String variavel) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.variavel = variavel;
        this.dado = dado;
        this.tipo = tipo;

        envioDados();

    }

    public void envioDados() {

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", String.valueOf(dado));

        ConHttpPostVerGenerico conHttpPostVerGenerico = new ConHttpPostVerGenerico();
        conHttpPostVerGenerico.setParametrosPost(parametrosPost);
        conHttpPostVerGenerico.setTipo(tipo);
        conHttpPostVerGenerico.execute(url);

    }

    public void retornoVerifNormal(String result, String tipo) {

        try {

            JSONObject jObj = new JSONObject(result);
            JSONArray jsonArray = jObj.getJSONArray("dados");
            Class classe = Class.forName(manipLocalClasse(tipo));

            qtde = jsonArray.length();

            if (qtde > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Gson gson = new Gson();
                    genericRecordable = new GenericRecordable();
                    genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);

                }

                Intent it = new Intent(telaAtual, telaProx);
                telaAtual.startActivity(it);

            } else {

                AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage(variavel + " INEXISTENTE NA BASE DE DADOS.");

                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });
                alerta.show();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("ERRO", "Erro Manip1 = " + e);
        }

    }

    public void retornoPesqBalanca(String result, String tipo) {

        try {

            JSONObject jObj = new JSONObject(result);
            JSONArray jsonArray = jObj.getJSONArray("dados");

            JSONObject objeto = jsonArray.getJSONObject(0);
            Gson gson = new Gson();
            PesqBalancaProdTO pesqBalancaProdTO = gson.fromJson(objeto.toString(), PesqBalancaProdTO.class);
            pesqBalancaProdTO.insert();

            this.progressDialog.dismiss();
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("ERRO", "Erro Manip2 = " + e);
        }

    }

}
