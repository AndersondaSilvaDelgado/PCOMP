package br.com.usinasantafe.pcomp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcomp.view.MenuInicialActivity;
import br.com.usinasantafe.pcomp.control.ConfigCTR;
import br.com.usinasantafe.pcomp.model.bean.AtualAplicBean;
import br.com.usinasantafe.pcomp.model.dao.CarregDAO;
import br.com.usinasantafe.pcomp.model.dao.EquipDAO;
import br.com.usinasantafe.pcomp.model.dao.OSDAO;
import br.com.usinasantafe.pcomp.util.connHttp.PostVerGenerico;
import br.com.usinasantafe.pcomp.util.connHttp.UrlsConexaoHttp;

/**
 * Created by anderson on 16/11/2015.
 */
public class VerifDadosServ {

    private static VerifDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private ProgressDialog progressDialog;
    private String dado;
    private String tipo;
    private MenuInicialActivity menuInicialActivity;
    private PostVerGenerico postVerGenerico;
    private boolean verTerm;

    public VerifDadosServ() {
        //genericRecordable = new GenericRecordable();
    }

    public static VerifDadosServ getInstance() {
        if (instance == null)
            instance = new VerifDadosServ();
        return instance;
    }

    public void manipularDadosHttp(String result, String tipo) {

        if (!result.equals("")) {

            if (this.tipo.equals("Equip")) {
                EquipDAO equipDAO = new EquipDAO();
                equipDAO.recDadosEquip(result);
            }
            else if(this.tipo.equals("LeiraComposto")){
                CarregDAO carregDAO = new CarregDAO();
                carregDAO.recLeiraComposto(result);
            }
            else if (this.tipo.equals("Atualiza")) {
                String verAtual = result.trim();
                if (verAtual.equals("S")) {
                    AtualizarAplicativo atualizarAplicativo = new AtualizarAplicativo();
                    atualizarAplicativo.setContext(this.menuInicialActivity);
                    atualizarAplicativo.execute();
                } else {
                    this.menuInicialActivity.startTimer(verAtual);
                }
            }
            else if(tipo.equals("CarregProduto")) {
                CarregDAO carregDAO = new CarregDAO();
                carregDAO.recCarreg(result);
            }
            else if(tipo.equals("CarregComposto")) {
                CarregDAO carregDAO = new CarregDAO();
                carregDAO.recCarreg(result);
            }
            else if (this.tipo.equals("OS")) {
                OSDAO osDAO = new OSDAO();
                osDAO.recDadosOS(result);
            }

        }

    }

    public String manipLocalClasse(String classe) {
        if (classe.contains("Bean")) {
            classe = urlsConexaoHttp.localPSTEstatica + classe;
        }
        return classe;
    }

    public void verAtualAplic(String versaoAplic, MenuInicialActivity menuInicialActivity, ProgressDialog progressDialog) {

        AtualAplicBean atualAplicBean = new AtualAplicBean();
        ConfigCTR configCTR = new ConfigCTR();
        atualAplicBean.setIdEquipAtualizacao(configCTR.getEquip().getNroEquip());
        atualAplicBean.setIdCheckList(configCTR.getEquip().getIdCheckList());
        atualAplicBean.setVersaoAtual(versaoAplic);

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.progressDialog = progressDialog;
        this.tipo = "Atualiza";
        this.menuInicialActivity = menuInicialActivity;

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualAplicBean, atualAplicBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PMM", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        postVerGenerico = new PostVerGenerico();
        postVerGenerico.setParametrosPost(parametrosPost);
        postVerGenerico.execute(url);

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

    public void verDados(String dado, String tipo, Context telaAtual) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.dado = dado;
        this.tipo = tipo;
        this.telaAtual = telaAtual;

        envioDados();

    }

    public void envioDados() {

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", String.valueOf(dado));

        Log.i("PCOMP", "PESQUISA = " + dado);

        PostVerGenerico postVerGenerico = new PostVerGenerico();
        postVerGenerico.setParametrosPost(parametrosPost);
        postVerGenerico.setTipo(tipo);
        postVerGenerico.execute(url);

    }

    public void pulaTelaSemTerm(){
        this.progressDialog.dismiss();
        Intent it = new Intent(telaAtual, telaProx);
        telaAtual.startActivity(it);
    }

    public void msgSemTerm(String texto){
        this.progressDialog.dismiss();
        AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
        alerta.setTitle("ATENÇÃO");
        alerta.setMessage(texto);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();
    }

    public void pulaTelaComTerm(){
        if(!verTerm){
            this.progressDialog.dismiss();
            this.verTerm = true;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void msgComTerm(String texto){
        if(!verTerm){
            this.progressDialog.dismiss();
            this.verTerm = true;
            AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
            alerta.setTitle("ATENÇÃO");
            alerta.setMessage(texto);
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alerta.show();
        }
    }

    public void pulaTelaComTermSemBarra(){
        if(!verTerm){
            this.verTerm = true;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void cancelVer() {
        verTerm = true;
        if (postVerGenerico.getStatus() == AsyncTask.Status.RUNNING) {
            postVerGenerico.cancel(true);
        }
    }

    public void pulaTelaDadosInfor(Class telaProx){
        if(!verTerm){
            this.progressDialog.dismiss();
            this.verTerm = true;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void pulaTela(Class telaProx){
        if(!verTerm){
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public boolean isVerTerm() {
        return verTerm;
    }

    public void setVerTerm(boolean verTerm) {
        this.verTerm = verTerm;
    }

}
