package br.com.usinasantafe.pcomp.bo;

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

import br.com.usinasantafe.pcomp.conWEB.ConHttpPostCadGenerico;
import br.com.usinasantafe.pcomp.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pcomp.to.tb.estaticas.EquipamentoTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.OSTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontMotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontCarregTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaCompTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.TurnoVarTO;

public class ManipDadosEnvio {

    private static ManipDadosEnvio instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private ProgressDialog progressDialog;
    private PesqBalancaCompTO pesqBalancaCompTO;

    public ManipDadosEnvio() {
        // TODO Auto-generated constructor stub
        urlsConexaoHttp = new UrlsConexaoHttp();
    }

    public static ManipDadosEnvio getInstance() {
        if (instance == null) {
            instance = new ManipDadosEnvio();
        }
        return instance;
    }

    public void salvaMotoMec(TurnoVarTO turnoTO, ApontMotoMecTO apontMotoMecTO) {

        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
        List listaConfig = configuracaoTO.all();
        configuracaoTO = (ConfiguracaoTO) listaConfig.get(0);

        int qtde = apontMotoMecTO.count();
        qtde = qtde + 1;

        apontMotoMecTO.setId((long) qtde);

        EquipamentoTO equipamentoTO = new EquipamentoTO();
        List listaEquip = equipamentoTO.get("idEquipamento", configuracaoTO.getEquipConfig());
        equipamentoTO = (EquipamentoTO) listaEquip.get(0);

        apontMotoMecTO.setVeic(equipamentoTO.getNroEquipamento());

        MotoristaTO motoristaTO = new MotoristaTO();
        List listaMotorista = motoristaTO.get("idMotorista", turnoTO.getMatriculaMotoristaTurno());
        motoristaTO = (MotoristaTO) listaMotorista.get(0);

        apontMotoMecTO.setMotorista(motoristaTO.getMatricMotorista());
        apontMotoMecTO.setDihi(Tempo.getInstance().data());

        OSTO osTO = new OSTO();
        List osList = osTO.get("idOS", configuracaoTO.getOsConfig());
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

        ConHttpPostCadGenerico conHttpPostCadGenerico = new ConHttpPostCadGenerico();
        conHttpPostCadGenerico.setParametrosPost(parametrosPost);
        conHttpPostCadGenerico.execute(url);

    }


    public void salvaCarreg(ApontCarregTO apontCarregTO, TurnoVarTO turnoVarTO) {

        int qtde = apontCarregTO.count();
        qtde = qtde + 1;

        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
        List lEquip = configuracaoTO.all();
        configuracaoTO = (ConfiguracaoTO) lEquip.get(0);

        apontCarregTO.setIdApontCarreg((long) qtde);

        apontCarregTO.setDataApontCarreg(Tempo.getInstance().data());
        apontCarregTO.setEquipApontCarreg(configuracaoTO.getEquipConfig());
        apontCarregTO.setMotoApontCarreg(turnoVarTO.getMatriculaMotoristaTurno());

        apontCarregTO.insert();

        List listAponta = verifApontaCarreg();
        envioApontaCarreg(listAponta);

    }

    public List verifApontaCarreg() {

        ApontCarregTO apontCarregTORec = new ApontCarregTO();
        List listaDados = apontCarregTORec.all();
        return listaDados;

    }

    public void envioApontaCarreg(List listaDados) {

        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < listaDados.size(); i++) {
            ApontCarregTO apontCarregTO = (ApontCarregTO) listaDados.get(i);
            Gson gson = new Gson();
            jsonArray.add(gson.toJsonTree(apontCarregTO, apontCarregTO.getClass()));
        }

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PCOMP", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.getsApontaCarreg()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        ConHttpPostCadGenerico conHttpPostCadGenerico = new ConHttpPostCadGenerico();
        conHttpPostCadGenerico.setParametrosPost(parametrosPost);
        conHttpPostCadGenerico.execute(url);

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

        String[] url = {urlsConexaoHttp.getsPesqBalProd()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        ConHttpPostCadGenerico conHttpPostCadGenerico = new ConHttpPostCadGenerico();
        conHttpPostCadGenerico.setParametrosPost(parametrosPost);
        conHttpPostCadGenerico.execute(url);

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

        ApontCarregTO apontCarregTO = new ApontCarregTO();
        apontCarregTO.deleteAll();

    }

    public void delApontMotoMec() {

        ApontMotoMecTO apontMotoMecTO = new ApontMotoMecTO();
        apontMotoMecTO.deleteAll();

    }

}
