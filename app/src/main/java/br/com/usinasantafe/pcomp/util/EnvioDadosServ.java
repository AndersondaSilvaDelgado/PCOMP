package br.com.usinasantafe.pcomp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.usinasantafe.pcomp.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pcomp.to.tb.estaticas.EquipTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.OSTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontMotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.CarregTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfigTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaCompTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.TurnoVarTO;

public class EnvioDadosServ {

    private static EnvioDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private ProgressDialog progressDialog;
    private PesqBalancaCompTO pesqBalancaCompTO;
    private int statusEnvio; //1 - Enviando; 2 - Existe Dados para Enviar; 3 - Todos os Dados Enviados
    private boolean enviando = false;

    public EnvioDadosServ() {
        // TODO Auto-generated constructor stub
        urlsConexaoHttp = new UrlsConexaoHttp();
    }

    public static EnvioDadosServ getInstance() {
        if (instance == null) {
            instance = new EnvioDadosServ();
        }
        return instance;
    }

    public void salvaMotoMec(TurnoVarTO turnoTO, ApontMotoMecTO apontMotoMecTO) {

        ConfigTO configTO = new ConfigTO();
        List listaConfig = configTO.all();
        configTO = (ConfigTO) listaConfig.get(0);

        int qtde = apontMotoMecTO.count();
        qtde = qtde + 1;

        apontMotoMecTO.setId((long) qtde);

        EquipTO equipTO = new EquipTO();
        List listaEquip = equipTO.get("idEquipamento", configTO.getEquipConfig());
        equipTO = (EquipTO) listaEquip.get(0);

        apontMotoMecTO.setVeic(equipTO.getNroEquip());

        MotoristaTO motoristaTO = new MotoristaTO();
        List listaMotorista = motoristaTO.get("idMotorista", turnoTO.getMatriculaMotoristaTurno());
        motoristaTO = (MotoristaTO) listaMotorista.get(0);

        apontMotoMecTO.setMotorista(motoristaTO.getMatricMotorista());
        apontMotoMecTO.setDihi(Tempo.getInstance().data());

        OSTO osTO = new OSTO();
        List osList = osTO.get("idOS", configTO.getOsConfig());
        if(osList.size() > 0){
            osTO = (OSTO) osList.get(0);
            apontMotoMecTO.setCaux(osTO.getAtivOS());
        }
        else{
            apontMotoMecTO.setCaux(0L);
        }

        apontMotoMecTO.insert();

        List motoMec = verifDadosMotoMec();
        envioMotoMec(motoMec);

    }

    private List verifDadosMotoMec() {

        ApontMotoMecTO apontMotoMecTORec = new ApontMotoMecTO();
        List listaDados = apontMotoMecTORec.all();
        return listaDados;

    }

    public void envioMotoMec(List listaDados) {

        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < listaDados.size(); i++) {
            ApontMotoMecTO apontMotoMecTON = (ApontMotoMecTO) listaDados.get(i);
            Gson gson = new Gson();
            jsonArray.add(gson.toJsonTree(apontMotoMecTON, apontMotoMecTON.getClass()));
        }

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PCOMP", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.getsInsertMotoMec()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(url);

    }


    public void salvaCarreg(CarregTO carregTO, TurnoVarTO turnoVarTO) {

        int qtde = carregTO.count();
        qtde = qtde + 1;

        ConfigTO configTO = new ConfigTO();
        List lEquip = configTO.all();
        configTO = (ConfigTO) lEquip.get(0);

        carregTO.setIdCarreg((long) qtde);

        carregTO.setDataCarreg(Tempo.getInstance().data());
        carregTO.setEquipCarreg(configTO.getEquipConfig());
        carregTO.setMotoCarreg(turnoVarTO.getMatriculaMotoristaTurno());

        carregTO.insert();

        List listAponta = verifApontaCarreg();
        envioApontaCarreg(listAponta);

    }

    public List verifApontaCarreg() {

        CarregTO carregTORec = new CarregTO();
        List listaDados = carregTORec.all();
        return listaDados;

    }

    public void envioApontaCarreg(List listaDados) {

        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < listaDados.size(); i++) {
            CarregTO carregTO = (CarregTO) listaDados.get(i);
            Gson gson = new Gson();
            jsonArray.add(gson.toJsonTree(carregTO, carregTO.getClass()));
        }

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PCOMP", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.getsInserirCarreg()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(url);

    }

    public void envioPesqBalancaComp(PesqBalancaCompTO pesqBalancaCompTO, Context telaAtual, Class telaProx, ProgressDialog progressDialog) {

        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.pesqBalancaCompTO = pesqBalancaCompTO;

        envioComp();

    }

    public void envioComp(){

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(pesqBalancaCompTO, pesqBalancaCompTO.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PCOMP", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.getsPesqLeiraProd()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(url);

    }


    public void salvarDadosPesqBalancaComp(String result) {

        try {

            JSONObject jObj = new JSONObject(result);
            JSONArray jsonArray = jObj.getJSONArray("dados");

            JSONObject objeto = jsonArray.getJSONObject(0);
            Gson gson = new Gson();
            PesqBalancaCompTO pesqBalancaCompTO = gson.fromJson(objeto.toString(), PesqBalancaCompTO.class);
            int qtde = pesqBalancaCompTO.count();
            qtde = qtde + 1;

            pesqBalancaCompTO.setId((long) qtde);
            pesqBalancaCompTO.insert();

            this.progressDialog.dismiss();

            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("ERRO", "Erro Manip1 = " + e);
        }

    }

    public boolean verifDadosEnvio() {

        if ((verifApontaCarreg().size() == 0) && (verifDadosMotoMec().size() == 0)) {
            return false;
        } else {
            return true;
        }

    }

    public void envioDados(Context context) {

        Log.i("ECM", "EnvioDados");
        ConexaoWeb conexaoWeb = new ConexaoWeb();

        if (conexaoWeb.verificaConexao(context)) {
            List apontaCarreg = verifApontaCarreg();
            if (apontaCarreg.size() > 0) {
                envioApontaCarreg(apontaCarreg);
            } else {
                List motoMec = verifDadosMotoMec();
                if (motoMec.size() > 0) {
                    envioMotoMec(motoMec);
                }
            }

        }

    }

    public void delApontaCarreg() {

        CarregTO carregTO = new CarregTO();
        carregTO.deleteAll();

    }

    public void delApontMotoMec() {

        ApontMotoMecTO apontMotoMecTO = new ApontMotoMecTO();
        apontMotoMecTO.deleteAll();

    }

    public int getStatusEnvio() {
        if (enviando) {
            statusEnvio = 1;
        } else {
            if (!verifDadosEnvio()) {
                statusEnvio = 3;
            } else {
                statusEnvio = 2;
            }
        }
        return statusEnvio;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    public void setEnviando(boolean enviando) {
        this.enviando = enviando;
    }

    public void setStatusEnvio(int statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

}
