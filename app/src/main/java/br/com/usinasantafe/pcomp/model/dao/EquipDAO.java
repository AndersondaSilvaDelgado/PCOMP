package br.com.usinasantafe.pcomp.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.com.usinasantafe.ecm.control.ConfigCTR;
import br.com.usinasantafe.ecm.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.ecm.model.bean.estaticas.REquipAtivBean;
import br.com.usinasantafe.ecm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.ecm.util.VerifDadosServ;

public class EquipDAO {

    public EquipDAO() {
    }

    public void verEquip(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().setVerTerm(true);
        VerifDadosServ.getInstance().verDados(dado, "Equip", telaAtual, telaProx, progressDialog);
    }

    public void recDadosEquip(String result){
        try {

            int pos1 = result.indexOf("#") + 1;
            int pos2 = result.indexOf("_") + 1;
            String objPrinc = result.substring(0, (pos1 - 1));
            String objSeg = result.substring(pos1, (pos2 - 1));
            String objTerc = result.substring(pos2);

            JSONObject jObj = new JSONObject(objPrinc);
            JSONArray jsonArray = jObj.getJSONArray("dados");

            if (jsonArray.length() > 0) {

                EquipBean equipBean = new EquipBean();
                equipBean.deleteAll();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Gson gson = new Gson();
                    equipBean = gson.fromJson(objeto.toString(), EquipBean.class);
                    equipBean.insert();

                }

                jObj = new JSONObject(objSeg);
                jsonArray = jObj.getJSONArray("dados");

                REquipAtivBean rEquipAtivBean = new REquipAtivBean();
                rEquipAtivBean.deleteAll();

                for (int j = 0; j < jsonArray.length(); j++) {

                    JSONObject objeto = jsonArray.getJSONObject(j);
                    Gson gson = new Gson();
                    REquipAtivBean rEquipAtiv = gson.fromJson(objeto.toString(), REquipAtivBean.class);
                    rEquipAtiv.insert();

                }

                jObj = new JSONObject(objTerc);
                jsonArray = jObj.getJSONArray("dados");

                REquipPneuBean rEquipPneuBean = new REquipPneuBean();
                rEquipPneuBean.deleteAll();

                for (int j = 0; j < jsonArray.length(); j++) {

                    JSONObject objeto = jsonArray.getJSONObject(j);
                    Gson gson = new Gson();
                    REquipPneuBean rEquipPneu = gson.fromJson(objeto.toString(), REquipPneuBean.class);
                    rEquipPneu.insert();

                }

                ConfigCTR configCTR = new ConfigCTR();
                configCTR.setEquipConfig(equipBean);

                VerifDadosServ.getInstance().pulaTelaSemTerm();

            } else {
                VerifDadosServ.getInstance().msgSemTerm("EQUIPAMENTO INEXISTENTE NA BASE DE DADOS! FAVOR VERIFICA A NUMERAÇÃO.");
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().msgSemTerm("FALHA DE PESQUISA DE EQUIPAMENTO! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }
    }

    public String dadosEnvioEquip(){

        EquipBean equipBean = new EquipBean();
        List equipList = equipBean.all();
        JsonArray equipJsonArray = new JsonArray();

        equipBean = (EquipBean) equipList.get(0);
        Gson gson = new Gson();
        equipJsonArray.add(gson.toJsonTree(equipBean, equipBean.getClass()));
        equipList.clear();

        JsonObject equipJsonObj = new JsonObject();
        equipJsonObj.add("equip", equipJsonArray);

        return equipJsonObj.toString();

    }

}
