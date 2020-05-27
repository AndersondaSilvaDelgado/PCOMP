package br.com.usinasantafe.pcomp.model.dao;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.LeiraBean;
import br.com.usinasantafe.pcomp.util.EnvioDadosServ;
import br.com.usinasantafe.pcomp.util.Tempo;

public class CarregDAO {

    public CarregDAO() {
    }

    public boolean verCarreg(){
        List carregList = getCarreg();
        boolean retorno = carregList.size() > 0;
        carregList.clear();
        return retorno;
    }

    public void apontCarregComposto(Long idOSAgric, Long matricFunc, Context context){

        LeiraBean leiraBean = new LeiraBean();
        List leiraList = leiraBean.all();
        leiraBean = (LeiraBean) leiraList.get(0);

        CarregBean carregBean = new CarregBean();
        carregBean.setDataCarreg(Tempo.getInstance().dataComHora().getDataHora());
        carregBean.setEquipCarreg(leiraBean.getIdEquip());
        carregBean.setLeiraCarreg(leiraBean.getIdLeira());
        carregBean.setMotoCarreg(matricFunc);
        carregBean.setOsCarreg(idOSAgric);
        carregBean.setTipoCarreg(2L);
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

}
