package br.com.usinasantafe.pcomp.model.dao;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.com.usinasantafe.pcomp.InformacaoActivity;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.LeiraBean;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class LeiraDAO {

    public LeiraDAO() {
    }

    public boolean pesqLeiraExibir(){

//        PesqLeiraProdutoBean pesqBalancaProdTO = new PesqLeiraProdutoBean();
//        PesqLeiraCompostoBean pesqBalancaCompTO = new PesqLeiraCompostoBean();
//        if ((pesqBalancaProdTO.count() > 0) || (pesqBalancaCompTO.count() > 0)) {
            return true;
//        }
//        else {
//            return false;
//        }
    }

    public void pesqLeiraComposto(ConfigBean configBean, OSBean osBean, Context telaAtual){

        VerifDadosServ.getInstance().setVerTerm(false);
        JsonArray jsonArray = new JsonArray();

        LeiraBean leiraBean = new LeiraBean();
        leiraBean.setIdEquip(configBean.getEquipConfig());
        leiraBean.setIdOS(osBean.getIdOS());

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(leiraBean, leiraBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        VerifDadosServ.getInstance().verDados(json.toString(), "LeiraComposto", telaAtual);

    }

    public void recLeiraComposto(String result) {

        try {

            if (!result.contains("exceeded")) {

                JSONObject jObj = new JSONObject(result);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    LeiraBean leiraBean = new LeiraBean();
                    leiraBean.deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        leiraBean = gson.fromJson(objeto.toString(), LeiraBean.class);
                        leiraBean.insert();

                    }
                     VerifDadosServ.getInstance().pulaTela(InformacaoActivity.class);
                }

            } else {
                VerifDadosServ.getInstance().envioDados();
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().envioDados();
        }

    }

    public LeiraBean getLeira(){
        LeiraBean leiraBean = new LeiraBean();
        List leiraList = leiraBean.all();
        leiraBean = (LeiraBean) leiraList.get(0);
        leiraList.clear();
        return leiraBean;
    }

}
