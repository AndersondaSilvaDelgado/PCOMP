package br.com.usinasantafe.pcomp.model.dao;

import android.content.Context;

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

    public CarregBean getCarregAberto(){
        List carregList = getCarregAbertList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    public CarregBean getCarregFechado(){
        List carregList = getCarregFechadoList();
        CarregBean carregBean = (CarregBean) carregList.get(0);
        carregList.clear();
        return carregBean;
    }

    public boolean verCarreg(){
        List carregList = getCarregList();
        boolean retorno = carregList.size() > 0;
        carregList.clear();
        return retorno;
    }

    public void delAbertoCarreg(){
        List carregList = getCarregAbertList();
        for (int i = 0; i < carregList.size(); i++) {
            CarregBean carregBean = (CarregBean) carregList.get(i);
            carregBean.delete();
        }
        carregList.clear();
    }

    public void apontCarreg(Long matricFunc, Context context){

        CarregBean carregBean = getCarregAberto();
        carregBean.setDataCarreg(Tempo.getInstance().dataComHora().getDataHora());
        carregBean.setMotoCarreg(matricFunc);
        carregBean.setTipoCarreg(2L);
        carregBean.update();

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

    private List getCarregAbertList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.get("statusCarreg", 1L);
        return carregList;
    }

    private List getCarregFechadoList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.get("statusCarreg", 2L);
        return carregList;
    }

    private List getCarregList(){
        CarregBean carregBean = new CarregBean();
        List carregList = carregBean.all();
        return carregList;
    }

    public String dadosEnvioCarreg(){

        List carregList = getCarregList();
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

                    CarregBean carregBean = new CarregBean();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        carregBean = gson.fromJson(objeto.toString(), CarregBean.class);
                        List carregList = carregBean.get("dataCarreg", carregBean.getDataCarreg());
                        if(carregList.size() > 0){
                            CarregBean carregBeanBD = (CarregBean) carregList.get(0);
                            carregBeanBD.setIdLeiraCarreg(carregBean.getIdLeiraCarreg());
                            carregBeanBD.setIdOrdCarreg(carregBean.getIdOrdCarreg());
                            carregBeanBD.setPesoEntradaCarreg(carregBean.getPesoEntradaCarreg());
                            carregBeanBD.setPesoSaidaCarreg(carregBean.getPesoSaidaCarreg());
                            carregBeanBD.setPesoLiquidoCarreg(carregBean.getPesoLiquidoCarreg());
                            carregBeanBD.update();
                        }
                        else{
                            carregBean.insert();
                        }


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

                    CarregBean carregBeanBD = getCarregAberto();
                    carregBeanBD.setIdLeiraCarreg(carregBean.getIdLeiraCarreg());
                    carregBeanBD.setCodLeiraCarreg(carregBean.getCodLeiraCarreg());
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

    public void updateCarreg(String retorno) {

        try{

            JSONObject jObjCarreg = new JSONObject(retorno);
            JSONArray jsonArrayCarreg = jObjCarreg.getJSONArray("carreg");

            if (jsonArrayCarreg.length() > 0) {

                ArrayList<Long> rList = new ArrayList<Long>();
                CarregBean carregBean = new CarregBean();

                for (int i = 0; i < jsonArrayCarreg.length(); i++) {

                    JSONObject objCarreg = jsonArrayCarreg.getJSONObject(i);
                    Gson gsonCarreg = new Gson();
                    carregBean = gsonCarreg.fromJson(objCarreg.toString(), CarregBean.class);

                    rList.add(carregBean.getIdCarreg());

                }

                List apontMMList = carregBean.in("idCarreg", rList);

                for (int i = 0; i < apontMMList.size(); i++) {

                    carregBean = (CarregBean) apontMMList.get(i);
                    carregBean.setStatusCarreg(2L);
                    carregBean.update();

                }

                rList.clear();

            }

        }
        catch(Exception e){
            Tempo.getInstance().setEnvioDado(true);
        }

    }

}
