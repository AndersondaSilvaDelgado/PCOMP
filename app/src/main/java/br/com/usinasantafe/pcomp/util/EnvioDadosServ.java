package br.com.usinasantafe.pcomp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcomp.control.CheckListCTR;
import br.com.usinasantafe.pcomp.control.CompostoCTR;
import br.com.usinasantafe.pcomp.control.MotoMecCTR;
import br.com.usinasantafe.pcomp.util.connHttp.PostCadGenerico;
import br.com.usinasantafe.pcomp.util.connHttp.UrlsConexaoHttp;

public class EnvioDadosServ {

    private static EnvioDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private ProgressDialog progressDialog;
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

//    public void envioMotoMec(List listaDados) {
//
//        JsonArray jsonArray = new JsonArray();
//
//        for (int i = 0; i < listaDados.size(); i++) {
//            ApontMotoMecTO apontMotoMecTON = (ApontMotoMecTO) listaDados.get(i);
//            Gson gson = new Gson();
//            jsonArray.add(gson.toJsonTree(apontMotoMecTON, apontMotoMecTON.getClass()));
//        }
//
//        JsonObject json = new JsonObject();
//        json.add("dados", jsonArray);
//
//        Log.i("PCOMP", "LISTA = " + json.toString());
//
//        String[] url = {urlsConexaoHttp.getsInsertMotoMec()};
//        Map<String, Object> parametrosPost = new HashMap<String, Object>();
//        parametrosPost.put("dado", json.toString());
//
//        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
//        postCadGenerico.setParametrosPost(parametrosPost);
//        postCadGenerico.execute(url);
//
//    }
//
//    public void salvaCarregComposto(String dados) {
//
//        int qtde = carregTO.count();
//        qtde = qtde + 1;
//
//        ConfigTO configTO = new ConfigTO();
//        List lEquip = configTO.all();
//        configTO = (ConfigTO) lEquip.get(0);
//
//        carregTO.setIdCarreg((long) qtde);
//
//        carregTO.setDataCarreg(Tempo.getInstance().data());
//        carregTO.setEquipCarreg(configTO.getEquipConfig());
//        carregTO.setMotoCarreg(turnoVarTO.getMatriculaMotoristaTurno());
//
//        carregTO.insert();
//
//        List listAponta = verifApontaCarreg();
//        envioApontaCarreg(listAponta);
//
//    }
//
//    public void envioCarregComposto() {
//
//        JsonArray jsonArray = new JsonArray();
//
//        for (int i = 0; i < listaDados.size(); i++) {
//            CarregTO carregTO = (CarregTO) listaDados.get(i);
//            Gson gson = new Gson();
//            jsonArray.add(gson.toJsonTree(carregTO, carregTO.getClass()));
//        }
//
//        JsonObject json = new JsonObject();
//        json.add("dados", jsonArray);
//
//        Log.i("PCOMP", "LISTA = " + json.toString());
//
//        String[] url = {urlsConexaoHttp.getsInserirCarreg()};
//        Map<String, Object> parametrosPost = new HashMap<String, Object>();
//        parametrosPost.put("dado", json.toString());
//
//        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
//        postCadGenerico.setParametrosPost(parametrosPost);
//        postCadGenerico.execute(url);
//
//    }
//
//    public void salvaCarreg(CarregTO carregTO, TurnoVarTO turnoVarTO) {
//
//        int qtde = carregTO.count();
//        qtde = qtde + 1;
//
//        ConfigTO configTO = new ConfigTO();
//        List lEquip = configTO.all();
//        configTO = (ConfigTO) lEquip.get(0);
//
//        carregTO.setIdCarreg((long) qtde);
//
//        carregTO.setDataCarreg(Tempo.getInstance().data());
//        carregTO.setEquipCarreg(configTO.getEquipConfig());
//        carregTO.setMotoCarreg(turnoVarTO.getMatriculaMotoristaTurno());
//
//        carregTO.insert();
//
//        List listAponta = verifApontaCarreg();
//        envioApontaCarreg(listAponta);
//
//    }
//
//    public List verifApontaCarreg() {
//
//        CarregTO carregTORec = new CarregTO();
//        List listaDados = carregTORec.all();
//        return listaDados;
//
//    }
//
//    public void envioApontaCarreg(List listaDados) {
//
//        JsonArray jsonArray = new JsonArray();
//
//        for (int i = 0; i < listaDados.size(); i++) {
//            CarregTO carregTO = (CarregTO) listaDados.get(i);
//            Gson gson = new Gson();
//            jsonArray.add(gson.toJsonTree(carregTO, carregTO.getClass()));
//        }
//
//        JsonObject json = new JsonObject();
//        json.add("dados", jsonArray);
//
//        Log.i("PCOMP", "LISTA = " + json.toString());
//
//        String[] url = {urlsConexaoHttp.getsInserirCarreg()};
//        Map<String, Object> parametrosPost = new HashMap<String, Object>();
//        parametrosPost.put("dado", json.toString());
//
//        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
//        postCadGenerico.setParametrosPost(parametrosPost);
//        postCadGenerico.execute(url);
//
//    }
//
//    public void envioPesqBalancaComp(PesqBalancaCompTO pesqBalancaCompTO, Context telaAtual, Class telaProx, ProgressDialog progressDialog) {
//
//        this.telaAtual = telaAtual;
//        this.telaProx = telaProx;
//        this.progressDialog = progressDialog;
//        this.pesqBalancaCompTO = pesqBalancaCompTO;
//
//        envioComp();
//
//    }
//
//    public void envioComp(){
//
//        JsonArray jsonArray = new JsonArray();
//
//        Gson gson = new Gson();
//        jsonArray.add(gson.toJsonTree(pesqBalancaCompTO, pesqBalancaCompTO.getClass()));
//
//        JsonObject json = new JsonObject();
//        json.add("dados", jsonArray);
//
//        Log.i("PCOMP", "LISTA = " + json.toString());
//
//        String[] url = {urlsConexaoHttp.getsPesqLeiraProd()};
//        Map<String, Object> parametrosPost = new HashMap<String, Object>();
//        parametrosPost.put("dado", json.toString());
//
//        br.com.usinasantafe.pcomp.conWEB.PostCadGenerico postCadGenerico = new br.com.usinasantafe.pcomp.conWEB.PostCadGenerico();
//        postCadGenerico.setParametrosPost(parametrosPost);
//        postCadGenerico.execute(url);
//
//    }
//
//
//    public void salvarDadosPesqBalancaComp(String result) {
//
//        try {
//
//            JSONObject jObj = new JSONObject(result);
//            JSONArray jsonArray = jObj.getJSONArray("dados");
//
//            JSONObject objeto = jsonArray.getJSONObject(0);
//            Gson gson = new Gson();
//            PesqBalancaCompTO pesqBalancaCompTO = gson.fromJson(objeto.toString(), PesqBalancaCompTO.class);
//            int qtde = pesqBalancaCompTO.count();
//            qtde = qtde + 1;
//
//            pesqBalancaCompTO.setId((long) qtde);
//            pesqBalancaCompTO.insert();
//
//            this.progressDialog.dismiss();
//
//            Intent it = new Intent(telaAtual, telaProx);
//            telaAtual.startActivity(it);
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Log.i("ERRO", "Erro Manip1 = " + e);
//        }
//
//    }

    ////////////////////////////////// ENVIAR DADOS //////////////////////////////////////////////

    public void enviarChecklist() {

        CheckListCTR checkListCTR = new CheckListCTR();
        String dados = checkListCTR.dadosEnvio();

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertChecklist()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        Log.i("ECM", "DADOS CHECKLIST = " + dados);

        PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void envioCarreg() {

        CompostoCTR compostoCTR = new CompostoCTR();
        String dados = compostoCTR.dadosEnvioCarreg();

        String[] url = {urlsConexaoHttp.getsInsertCarreg()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        Log.i("ECM", "CARREG = " + dados);

        PostCadGenerico postCadGenerico = new PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(url);

    }

    public void enviarBolFechadosMM() {

        MotoMecCTR motoMecCTR = new MotoMecCTR();
        String dados = motoMecCTR.dadosEnvioBolFechadoMM();

        Log.i("PMM", "FECHADO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolFechadoMM()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void enviarBolAbertosMM() {

        MotoMecCTR motoMecCTR = new MotoMecCTR();
        String dados = motoMecCTR.dadosEnvioBolAbertoMM();

        Log.i("PMM", "ABERTO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolAbertoMM()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void envioApontMM() {

        MotoMecCTR motoMecCTR = new MotoMecCTR();
        String dados = motoMecCTR.dadosEnvioApontMM();

        Log.i("PMM", "APONTAMENTO = " + dados);

        String[] url = {urlsConexaoHttp.getsInsertApontaMM()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        PostCadGenerico postCadGenerico = new PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(url);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////VERIFICAÇÃO DE DADOS//////////////////////////////////////

    public boolean verifCheckList() {
        CheckListCTR checkListCTR = new CheckListCTR();
        return checkListCTR.verEnvioDados();
    }

    public boolean verifCarreg() {
        CompostoCTR compostoCTR = new CompostoCTR();
        return compostoCTR.verCarreg();
    }

    public Boolean verifBolFechadoMM() {
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        return motoMecCTR.verEnvioBolFechMM();
    }

    public Boolean verifBolAbertoSemEnvioMM() {
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        return motoMecCTR.verEnvioBolAbertoMM();
    }

    public Boolean verifApontMM() {
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        return motoMecCTR.verEnvioDadosApont();
    }

//    public Boolean verifInfor() {
//        boolean ret = false;
//        ConfigCTR configCTR = new ConfigCTR();
//        if(configCTR.hasElements()){
//            if(configCTR.getVerInforConfig() == 1){
//                ret = true;
//            }
//        }
//        return ret;
//    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////MECANISMO DE ENVIO///////////////////////////////////////

    public void envioDados(Context context) {
        enviando = true;
        ConexaoWeb conexaoWeb = new ConexaoWeb();
        if (conexaoWeb.verificaConexao(context)) {
            envioDadosPrinc();
        }
        else{
            enviando = false;
        }

    }

    public void envioDadosPrinc() {

//        if(verifInfor()){
//            VerifDadosServ.getInstance().verDadosInfor();
//        }
//        else {
            if (verifCheckList()) {
                enviarChecklist();
            } else {
                if (verifCarreg()) {
                    envioCarreg();
                } else {
                    if (verifBolFechadoMM()) {
                        enviarBolFechadosMM();
                    } else {
                        if (verifBolAbertoSemEnvioMM()) {
                            enviarBolAbertosMM();
                        } else {
                            if (verifApontMM()) {
                                envioApontMM();
                            }
                        }
                    }
                }
            }
//        }
    }

    public boolean verifDadosEnvio() {
        if ( (!verifCheckList())
                && (!verifCarreg())
                && (!verifBolFechadoMM())
                && (!verifBolAbertoSemEnvioMM())
                && (!verifApontMM())){
            enviando = false;
            return false;
        } else {
            return true;
        }
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
