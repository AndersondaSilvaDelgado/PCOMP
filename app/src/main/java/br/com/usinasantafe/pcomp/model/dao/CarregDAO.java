package br.com.usinasantafe.pcomp.model.dao;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.com.usinasantafe.pcomp.InformacaoActivity;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.LeiraBean;
import br.com.usinasantafe.pcomp.util.EnvioDadosServ;
import br.com.usinasantafe.pcomp.util.Tempo;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class CarregDAO {

    public CarregDAO() {
    }

    public boolean verCarreg(){
        List carregList = getCarreg();
        boolean retorno = carregList.size() > 0;
        carregList.clear();
        return retorno;
    }

    public void apontCarreg(Long idOSAgric, Long matricFunc, Long idEquip, Context context){

        LeiraBean leiraBean = new LeiraBean();
        List leiraList = leiraBean.all();
        leiraBean = (LeiraBean) leiraList.get(0);

        CarregBean carregBean = new CarregBean();
        carregBean.setDataCarreg(Tempo.getInstance().dataComHora().getDataHora());
        carregBean.setEquipCarreg(idEquip);
        carregBean.setIdLeiraCarreg(leiraBean.getIdLeira());
        carregBean.setMotoCarreg(matricFunc);
        carregBean.setOsCarreg(idOSAgric);
        carregBean.setTipoCarreg(2L);
        carregBean.insert();

        EnvioDadosServ.getInstance().envioDados(context);

    }

    public void apontCarreg(Long idOSAgric, Long matricFunc, Long idEquip, Long idProd, Context context){

        CarregBean carregBean = new CarregBean();
        carregBean.setDataCarreg(Tempo.getInstance().dataComHora().getDataHora());
        carregBean.setEquipCarreg(idEquip);
        carregBean.setProdCarreg(idProd);
        carregBean.setMotoCarreg(matricFunc);
        carregBean.setOsCarreg(idOSAgric);
        carregBean.setTipoCarreg(1L);
        carregBean.insert();

        EnvioDadosServ.getInstance().envioDados(context);

    }

    private List getCarreg(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.all();
        return carregList;
    }

    public String dadosEnvioCarreg(){

        List carregList = getCarreg();
        JsonArray preCECJsonArray = new JsonArray();

        for (int i = 0; i < carregList.size(); i++) {

            CarregBean carregBean = (CarregBean) carregList.get(i);
            Gson gson = new Gson();
            preCECJsonArray.add(gson.toJsonTree(carregBean, carregBean.getClass()));

        }

        carregList.clear();

        JsonObject preCECJsonObj = new JsonObject();
        preCECJsonObj.add("carreg", preCECJsonArray);

        return preCECJsonObj.toString();

    }

    public void pesqCarregComposto(ConfigBean configBean, Context telaAtual){
        VerifDadosServ.getInstance().verDados(String.valueOf(configBean.getEquipConfig()), "CarregComposto", telaAtual);
    }

    public void recCarregComposto(String result) {

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

}
