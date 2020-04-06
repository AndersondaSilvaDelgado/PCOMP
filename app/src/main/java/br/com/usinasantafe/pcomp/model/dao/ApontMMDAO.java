package br.com.usinasantafe.pcomp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ApontMMBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.BoletimMMBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CabecPneuBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ItemPneuBean;
import br.com.usinasantafe.pcomp.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcomp.util.Tempo;

public class ApontMMDAO {

    public ApontMMDAO() {
    }

    public Long getIdApontAberto() {
        ApontMMBean apontMMBean = getApontMMAberto();
        return apontMMBean.getIdApontMM();
    }

    public ApontMMBean getApontMMAberto(){
        ApontMMBean apontMMBean = new ApontMMBean();
        List apontaMMList = apontMMBean.get("statusApontMM", 0L);
        apontMMBean = (ApontMMBean) apontaMMList.get(0);
        apontaMMList.clear();
        return apontMMBean;
    }

    public void updApont(ApontMMBean apontMMBean){
        apontMMBean.setStatusApontMM(1L);
        apontMMBean.update();
    }

    public List getListApontEnvio(Long idBolMM){

        ApontMMBean apontMMBean = new ApontMMBean();

        ArrayList pesqArrayList = new ArrayList();
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApontMM");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        pesqArrayList.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("idBolApontMM");
        pesquisa2.setValor(idBolMM);
        pesquisa2.setTipo(1);
        pesqArrayList.add(pesquisa2);

        return apontMMBean.get(pesqArrayList);

    }

    public List getListApontEnvio() {
        ApontMMBean apontMMBean = new ApontMMBean();
        return apontMMBean.get("statusApontMM", 1L);
    }

    public String dadosEnvioApontMM(List apontaList){

        JsonArray jsonArrayAponta = new JsonArray();
        JsonArray jsonArrayImplemento = new JsonArray();
        JsonArray jsonArrayMovLeira = new JsonArray();
        JsonArray jsonArrayCabecPneu = new JsonArray();
        JsonArray jsonArrayItemPneu = new JsonArray();

        for (int i = 0; i < apontaList.size(); i++) {

            ApontMMBean apontMMBean = (ApontMMBean) apontaList.get(i);
            Gson gson = new Gson();
            jsonArrayAponta.add(gson.toJsonTree(apontMMBean, apontMMBean.getClass()));

            CabecPneuBean cabecPneuBean = new CabecPneuBean();
            List cabecPneuList = cabecPneuBean.get("idApontCabecPneu", apontMMBean.getIdApontMM());

            for (int l = 0; l < cabecPneuList.size(); l++) {

                cabecPneuBean = (CabecPneuBean) cabecPneuList.get(l);
                Gson gsonCabecPneu = new Gson();
                jsonArrayCabecPneu.add(gsonCabecPneu.toJsonTree(cabecPneuBean, cabecPneuBean.getClass()));

                ItemPneuBean itemPneuBean = new ItemPneuBean();
                List itemPneuList = itemPneuBean.get("idCabecItemPneu", cabecPneuBean.getIdCabecPneu());

                for (int m = 0; m < itemPneuList.size(); m++) {
                    itemPneuBean = (ItemPneuBean) itemPneuList.get(m);
                    Gson gsonItemPneu = new Gson();
                    jsonArrayItemPneu.add(gsonItemPneu.toJsonTree(itemPneuBean, itemPneuBean.getClass()));
                }

                itemPneuList.clear();

            }

            cabecPneuList.clear();

        }

        apontaList.clear();


        JsonObject jsonAponta = new JsonObject();
        jsonAponta.add("aponta", jsonArrayAponta);

        JsonObject jsonImplemento = new JsonObject();
        jsonImplemento.add("implemento", jsonArrayImplemento);

        JsonObject jsonMovLeira = new JsonObject();
        jsonMovLeira.add("movleira", jsonArrayMovLeira);

        JsonObject jsonCabecPneu = new JsonObject();
        jsonCabecPneu.add("cabecpneu", jsonArrayCabecPneu);

        JsonObject jsonItemPneu = new JsonObject();
        jsonItemPneu.add("itempneu", jsonArrayItemPneu);

        return jsonAponta.toString() + "|" + jsonImplemento.toString() + "#" + jsonMovLeira.toString() + "?" + jsonCabecPneu.toString() + "@" + jsonItemPneu.toString();


    }

    public void updateApont(String retorno) {

        try{

            int pos1 = retorno.indexOf("_") + 1;
            int pos2 = retorno.indexOf("|") + 1;

            String objPrinc = retorno.substring(pos1, pos2);
//            String objSeg = retorno.substring(pos2);

            JSONObject jObjApontMM = new JSONObject(objPrinc);
            JSONArray jsonArrayApontMM = jObjApontMM.getJSONArray("apont");

            if (jsonArrayApontMM.length() > 0) {

                ArrayList<Long> rList = new ArrayList<Long>();
                ApontMMBean apontMMBean = new ApontMMBean();

                for (int i = 0; i < jsonArrayApontMM.length(); i++) {

                    JSONObject objApont = jsonArrayApontMM.getJSONObject(i);
                    Gson gsonApont = new Gson();
                    apontMMBean = gsonApont.fromJson(objApont.toString(), ApontMMBean.class);

                    rList.add(apontMMBean.getIdApontMM());

                }

                List apontMMList = apontMMBean.in("idApontMM", rList);

                for (int i = 0; i < apontMMList.size(); i++) {

                    apontMMBean = (ApontMMBean) apontMMList.get(i);
                    apontMMBean.setStatusApontMM(2L);
                    apontMMBean.update();

                }

                CabecPneuBean cabecPneuBean = new CabecPneuBean();
                List cabecPneuList = cabecPneuBean.in("idApontCabecPneu", rList);

                for (int l = 0; l < cabecPneuList.size(); l++) {

                    cabecPneuBean = (CabecPneuBean) cabecPneuList.get(l);

                    ItemPneuBean itemPneuBean = new ItemPneuBean();
                    List itemPneuList = itemPneuBean.get("idCabecItemPneu", cabecPneuBean.getIdCabecPneu());

                    for (int m = 0; m < itemPneuList.size(); m++) {
                        itemPneuBean = (ItemPneuBean) itemPneuList.get(m);
                        itemPneuBean.delete();
                    }

                    cabecPneuBean.delete();
                }

                rList.clear();

            }

        }
        catch(Exception e){
            Tempo.getInstance().setEnvioDado(true);
        }

    }

    public void salvarApont(MotoMecBean motoMecBean, ConfigBean configBean, BoletimMMBean boletimMMBean,
                            Double longitude, Double latitude, Long statusCon){

        ApontMMBean apontMMBean = new ApontMMBean();
        apontMMBean.setOsApontMM(configBean.getOsConfig());
        apontMMBean.setIdExtBolApontMM(boletimMMBean.getIdExtBolMM());
        apontMMBean.setIdBolApontMM(boletimMMBean.getIdBolMM());
        if(motoMecBean.getFuncaoOperMotoMec() == 1){
            apontMMBean.setAtivApontMM(motoMecBean.getIdOperMotoMec());
            apontMMBean.setParadaApontMM(0L);
        }
        else if(motoMecBean.getFuncaoOperMotoMec() == 2){
            apontMMBean.setAtivApontMM(configBean.getAtivConfig());
            apontMMBean.setParadaApontMM(motoMecBean.getIdOperMotoMec());
        }
        apontMMBean.setLongitudeApontMM(longitude);
        apontMMBean.setLatitudeApontMM(latitude);
        apontMMBean.setStatusApontMM(1L);
        apontMMBean.setDthrApontMM(Tempo.getInstance().dataComHora().getDataHora());
        apontMMBean.setStatusDtHrApontMM(Tempo.getInstance().dataComHora().getStatus());
        apontMMBean.setStatusConApontMM(statusCon);
        apontMMBean.setTransbApontMM(0L);
        configBean.update();
        apontMMBean.insert();

    }

}
