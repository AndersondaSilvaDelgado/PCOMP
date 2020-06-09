package br.com.usinasantafe.pcomp.model.dao;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.InformacaoActivity;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.util.EnvioDadosServ;
import br.com.usinasantafe.pcomp.util.Tempo;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class CarregDAO {

    public CarregDAO() {
    }

    private List getBuscaLeiraCompList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.get("statusCarreg", 1L);
        return carregList;
    }

    private List getRecebLeiraCompList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.get("statusCarreg", 2L);
        return carregList;
    }

    private List getEnviaCarregList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.get("statusCarreg", 3L);
        return carregList;
    }

    private List getRetCarregList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.get("statusCarreg", 4L);
        return carregList;
    }

    private List getOrdCarregList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.getAndOrderBy("statusCarreg", 5L, "idCarreg", false);
        return carregList;
    }

    private CarregBean getBuscaLeiraComp(){
        List carregList = getBuscaLeiraCompList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    public CarregBean getRecebLeiraComp(){
        List carregList = getRecebLeiraCompList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    private CarregBean getEnviaCarreg(){
        List carregList = getEnviaCarregList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    private CarregBean getRetCarreg(){
        List carregList = getRetCarregList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    public CarregBean getOrdCarreg(){
        List carregList = getOrdCarregList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    public boolean verEnviaCarreg(){
        List carregList = getEnviaCarregList();
        boolean ret = carregList.size() > 0;
        carregList.clear();
        return ret;
    }

    public void delAbertoCarreg(){
        List carregList = getRecebLeiraCompList();
        for (int i = 0; i < carregList.size(); i++) {
            CarregBean carregBean = (CarregBean) carregList.get(i);
            carregBean.delete();
        }
        carregList.clear();
    }

    public void apontCarreg(Long matricFunc, Context context){

        CarregBean carregBean = getRecebLeiraComp();
        carregBean.setDataCarreg(Tempo.getInstance().dataComHora().getDataHora());
        carregBean.setMotoCarreg(matricFunc);
        carregBean.setTipoCarreg(2L);
        carregBean.setStatusCarreg(3L);
        carregBean.update();

        EnvioDadosServ.getInstance().envioDados(context);

    }

    public void apontCarreg(Long matricFunc, Long idEquip, Long idProd, Context context){

        CarregBean carregBean = new CarregBean();
        carregBean.setDataCarreg(Tempo.getInstance().dataComHora().getDataHora());
        carregBean.setEquipCarreg(idEquip);
        carregBean.setProdCarreg(idProd);
        carregBean.setMotoCarreg(matricFunc);
        carregBean.setTipoCarreg(1L);
        carregBean.setStatusCarreg(3L);
        carregBean.insert();

        EnvioDadosServ.getInstance().envioDados(context);

    }

    public void pesqCarregProduto(ConfigBean configBean, Context telaAtual){
        VerifDadosServ.getInstance().verDados(String.valueOf(configBean.getEquipConfig()), "CarregProduto", telaAtual);
    }

    public void pesqCarregComposto(ConfigBean configBean, Context telaAtual){
        VerifDadosServ.getInstance().verDados(String.valueOf(configBean.getEquipConfig()), "CarregComposto", telaAtual);
    }

    public void recCarreg(String result) {

        try {

            if (!result.contains("exceeded")) {

                JSONObject jObj = new JSONObject(result);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        CarregBean carregBean = gson.fromJson(objeto.toString(), CarregBean.class);
                        List carregList = carregBean.get("dataCarreg", carregBean.getDataCarreg());
                        if(carregList.size() > 0){
                            CarregBean carregBeanBD = (CarregBean) carregList.get(0);
                            carregBeanBD.setIdLeiraCarreg(carregBean.getIdLeiraCarreg());
                            carregBeanBD.setCodLeiraCarreg(carregBean.getCodLeiraCarreg());
                            carregBeanBD.setIdOrdCarreg(carregBean.getIdOrdCarreg());
                            carregBeanBD.setPesoEntradaCarreg(carregBean.getPesoEntradaCarreg());
                            carregBeanBD.setPesoSaidaCarreg(carregBean.getPesoSaidaCarreg());
                            carregBeanBD.setPesoLiquidoCarreg(carregBean.getPesoLiquidoCarreg());
                            carregBeanBD.setStatusCarreg(5L);
                            carregBeanBD.update();
                        }
                        else{
                            carregBean.setStatusCarreg(5L);
                            carregBean.insert();
                        }


                    }
                    VerifDadosServ.getInstance().setVerTerm(false);
                    VerifDadosServ.getInstance().pulaTela(InformacaoActivity.class);
                }

            } else {
                VerifDadosServ.getInstance().envioDados();
            }

        } catch (Exception e) {
            Log.i("PCOMP", "ERRO = " + e);
            VerifDadosServ.getInstance().envioDados();
        }

    }

    public void pesqLeiraComposto(ConfigBean configBean, OSBean osBean, Context telaAtual){

        VerifDadosServ.getInstance().setVerTerm(false);
        JsonArray jsonArray = new JsonArray();

        delAbertoCarreg();

        CarregBean carregBean = new CarregBean();
        carregBean.setEquipCarreg(configBean.getEquipConfig());
        carregBean.setOsCarreg(osBean.getIdOS());
        carregBean.setStatusCarreg(1L);
        carregBean.insert();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(carregBean, carregBean.getClass()));

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

                    JSONObject objeto = jsonArray.getJSONObject(0);
                    Gson gson = new Gson();
                    CarregBean carregBean = gson.fromJson(objeto.toString(), CarregBean.class);

                    CarregBean carregBeanBD = getBuscaLeiraComp();
                    carregBeanBD.setIdLeiraCarreg(carregBean.getIdLeiraCarreg());
                    carregBeanBD.setCodLeiraCarreg(carregBean.getCodLeiraCarreg());
                    carregBeanBD.setStatusCarreg(2L);
                    carregBeanBD.update();

                    VerifDadosServ.getInstance().pulaTela(InformacaoActivity.class);
                }

            } else {
                VerifDadosServ.getInstance().envioDados();
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().envioDados();
        }

    }

    public String dadosEnvioCarreg(){

        JsonArray carregJsonArray = new JsonArray();

        CarregBean carregBean = getEnviaCarreg();
        Gson gson = new Gson();
        carregJsonArray.add(gson.toJsonTree(carregBean, carregBean.getClass()));

        JsonObject carregJsonObj = new JsonObject();
        carregJsonObj.add("carreg", carregJsonArray);

        return carregJsonObj.toString();

    }

    public void updateCarreg(String retorno) {

        try{

            int pos1 = retorno.indexOf("_") + 1;

            String obj = retorno.substring(pos1);

            JSONObject jObjCarreg = new JSONObject(obj);
            JSONArray jsonArrayCarreg = jObjCarreg.getJSONArray("carreg");

            JSONObject objCarreg = jsonArrayCarreg.getJSONObject(0);
            Gson gsonCarreg = new Gson();
            CarregBean carregBean = gsonCarreg.fromJson(objCarreg.toString(), CarregBean.class);

            List carregList = carregBean.get("idCarreg", carregBean.getIdCarreg());
            CarregBean carregBeanBD = (CarregBean) carregList.get(0);
            carregBeanBD.setStatusCarreg(4L);
            carregBeanBD.update();

        }
        catch(Exception e){
            Tempo.getInstance().setEnvioDado(true);
        }

    }

}
