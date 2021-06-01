package br.com.usinasantafe.pcomp.model.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.control.MotoMecCTR;
import br.com.usinasantafe.pcomp.model.bean.estaticas.FuncBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ApontMMBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.BoletimMMBean;
import br.com.usinasantafe.pcomp.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcomp.util.Tempo;

public class BoletimMMDAO {

    public BoletimMMDAO() {
    }

    public boolean verBolAberto(){
        BoletimMMBean boletimMMBean = new BoletimMMBean();
        List boletimMMList = boletimMMBean.get("statusBolMM", 1L);
        boolean ret = (boletimMMList.size() > 0);
        boletimMMList.clear();
        return ret;
    }

    public BoletimMMBean getBolMMAberto(){
        BoletimMMBean boletimMMBean = new BoletimMMBean();
        List boletimMMList = boletimMMBean.get("statusBolMM", 1L);
        boletimMMBean = (BoletimMMBean) boletimMMList.get(0);
        boletimMMList.clear();
        return boletimMMBean;
    }

    public Long getIdBolAberto(){
        BoletimMMBean boletimMMBean = getBolMMAberto();
        return boletimMMBean.getIdBolMM();
    }

    public void salvarBolAberto(BoletimMMBean boletimMMBean){
        boletimMMBean.setStatusBolMM(1L);
        boletimMMBean.setDthrInicialBolMM(Tempo.getInstance().dataComHora().getDataHora());
        boletimMMBean.setStatusDtHrInicialBolMM(Tempo.getInstance().dataComHora().getStatus());
        boletimMMBean.setQtdeApontBolMM(0L);
        boletimMMBean.insert();
    }

    public void salvarBolFechado(BoletimMMBean boletimMMBean) {

        BoletimMMBean boletimMMTOBD = getBolMMAberto();

        boletimMMTOBD.setDthrFinalBolMM(Tempo.getInstance().dataComHora().getDataHora());
        boletimMMTOBD.setStatusDtHrFinalBolMM(Tempo.getInstance().dataComHora().getStatus());
        boletimMMTOBD.setStatusBolMM(2L);
        boletimMMTOBD.setHodometroFinalBolMM(boletimMMBean.getHodometroFinalBolMM());
        boletimMMTOBD.update();

    }

    public List bolFechadoList() {
        BoletimMMBean boletimMMBean = new BoletimMMBean();
        return boletimMMBean.get("statusBolMM", 2L);
    }

    public List bolAbertoSemEnvioList() {

        BoletimMMBean boletimMMBean = new BoletimMMBean();
        ArrayList listaPesq = new ArrayList();

        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolMM");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        listaPesq.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("idExtBolMM");
        pesquisa2.setValor(0);
        pesquisa2.setTipo(1);
        listaPesq.add(pesquisa2);

        return boletimMMBean.get(listaPesq);

    }

    public String dadosEnvioBolAberto(){

        BoletimMMBean boletimMMBean = getBolMMAberto();

        Gson gsonCabec = new Gson();
        JsonArray jsonArrayBoletim = new JsonArray();
        jsonArrayBoletim.add(gsonCabec.toJsonTree(boletimMMBean, boletimMMBean.getClass()));

        MotoMecCTR motoMecCTR = new MotoMecCTR();
        String dadosEnvioApont = motoMecCTR.dadosEnvioApontBolMM(boletimMMBean.getIdBolMM());

        JsonObject jsonBoletim = new JsonObject();
        jsonBoletim.add("boletim", jsonArrayBoletim);

        return jsonBoletim.toString() + "_" + dadosEnvioApont;

    }

    public String dadosEnvioBolFechado(){

        List boletimMMList = bolFechadoList();

        JsonArray jsonArrayBoletim = new JsonArray();
        String dadosEnvioApont = "";
        JsonArray jsonArrayRendimento = new JsonArray();

        for (int i = 0; i < boletimMMList.size(); i++) {

            BoletimMMBean boletimMMBean = (BoletimMMBean) boletimMMList.get(i);
            Gson gsonCabec = new Gson();
            jsonArrayBoletim.add(gsonCabec.toJsonTree(boletimMMBean, boletimMMBean.getClass()));

            MotoMecCTR motoMecCTR = new MotoMecCTR();
            dadosEnvioApont = motoMecCTR.dadosEnvioApontBolMM(boletimMMBean.getIdBolMM());

        }

        boletimMMList.clear();

        JsonObject jsonBoletim = new JsonObject();
        jsonBoletim.add("boletim", jsonArrayBoletim);

        JsonObject jsonRend = new JsonObject();
        jsonRend.add("rendimento", jsonArrayRendimento);

        return jsonBoletim.toString() + "_" + dadosEnvioApont + "=" + jsonRend.toString();

    }

    public void updateBolAberto(String retorno){

        try{

            int pos1 = retorno.indexOf("_") + 1;
            int pos2 = retorno.indexOf("|") + 1;

            String objPrinc = retorno.substring(pos1, pos2);
            String objSeg = retorno.substring(pos2);

            JSONObject jObjBolMM = new JSONObject(objPrinc);
            JSONArray jsonArrayBolMM = jObjBolMM.getJSONArray("boletim");

            JSONObject objBol = jsonArrayBolMM.getJSONObject(0);
            Gson gsonBol = new Gson();
            BoletimMMBean boletimMMBean = gsonBol.fromJson(objBol.toString(), BoletimMMBean.class);

            List bolMMList = boletimMMBean.get("idBolMM", boletimMMBean.getIdBolMM());
            BoletimMMBean boletimMMTOBD = (BoletimMMBean) bolMMList.get(0);
            bolMMList.clear();

            Log.i("ECM", "CHEGOU AKI 1");

            boletimMMTOBD.setIdExtBolMM(boletimMMBean.getIdExtBolMM());
            boletimMMTOBD.update();

            JSONObject jObjApontMM = new JSONObject(objSeg);
            JSONArray jsonArrayApontMM = jObjApontMM.getJSONArray("apont");

            if (jsonArrayApontMM.length() > 0) {

                ArrayList<Long> apontaArrayList = new ArrayList<Long>();
                ApontMMBean apontMMBean = new ApontMMBean();

                for (int i = 0; i < jsonArrayApontMM.length(); i++) {

                    JSONObject objApont = jsonArrayApontMM.getJSONObject(i);
                    Gson gsonApont = new Gson();
                    apontMMBean = gsonApont.fromJson(objApont.toString(), ApontMMBean.class);

                    apontaArrayList.add(apontMMBean.getIdApontMM());

                }

                List apontMMList = apontMMBean.in("idApontMM", apontaArrayList);

                for (int i = 0; i < apontMMList.size(); i++) {

                    apontMMBean = (ApontMMBean) apontMMList.get(i);
                    apontMMBean.setIdExtBolApontMM(boletimMMBean.getIdExtBolMM());
                    apontMMBean.setStatusApontMM(2L);
                    apontMMBean.update();

                }

                apontaArrayList.clear();

            }

        }
        catch(Exception e){
            Tempo.getInstance().setEnvioDado(true);
        }

    }

    public void deleteBolFechado(String retorno) {

        try{

            int pos1 = retorno.indexOf("_") + 1;
            String objPrinc = retorno.substring(pos1);

            JSONObject jObjBolMM = new JSONObject(objPrinc);
            JSONArray jsonArrayBolMM = jObjBolMM.getJSONArray("boletim");

            for (int i = 0; i < jsonArrayBolMM.length(); i++) {

                JSONObject objBol = jsonArrayBolMM.getJSONObject(i);
                Gson gsonBol = new Gson();
                BoletimMMBean boletimMMBean = gsonBol.fromJson(objBol.toString(), BoletimMMBean.class);

                List bolMMList = boletimMMBean.get("idBolMM", boletimMMBean.getIdBolMM());
                BoletimMMBean boletimMMTOBD = (BoletimMMBean) bolMMList.get(0);
                bolMMList.clear();

                if(boletimMMTOBD.getQtdeApontBolMM() == boletimMMBean.getQtdeApontBolMM()){

                    ApontMMBean apontMMBean = new ApontMMBean();
                    List apontaList = apontMMBean.get("idBolApontMM", boletimMMTOBD.getIdBolMM());

                    for (int j = 0; j < apontaList.size(); j++) {

                        apontMMBean = (ApontMMBean) apontaList.get(j);
                        apontMMBean.delete();

                    }

                    apontaList.clear();

                    boletimMMTOBD.delete();

                }

            }


        }
        catch(Exception e){
            Tempo.getInstance().setEnvioDado(true);
        }

    }

    public void atualQtdeApontBol(){
        BoletimMMBean boletimMMBean = getBolMMAberto();
        boletimMMBean.setQtdeApontBolMM(boletimMMBean.getQtdeApontBolMM() + 1L);
        boletimMMBean.update();
    }

    public FuncBean getMatricNomeFunc(){
        BoletimMMBean boletimMMBean = getBolMMAberto();
        FuncBean funcBean = new FuncBean();
        List colabList = funcBean.get("matricFunc", boletimMMBean.getMatricFuncBolMM());
        funcBean = (FuncBean) colabList.get(0);
        colabList.clear();
        return funcBean;
    }

}
