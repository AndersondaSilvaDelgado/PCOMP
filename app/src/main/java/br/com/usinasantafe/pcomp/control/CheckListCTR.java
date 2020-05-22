package br.com.usinasantafe.pcomp.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.ItemCheckListBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CabecCLBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.RespItemCLBean;
import br.com.usinasantafe.pcomp.model.dao.CabecCheckListDAO;
import br.com.usinasantafe.pcomp.model.dao.ItemCheckListDAO;
import br.com.usinasantafe.pcomp.model.dao.RespCheckListDAO;

public class CheckListCTR {

    public CheckListCTR() {
    }

    public boolean verCabecAberto(){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        return cabecCheckListDAO.verCabecAberto();
    }

    public void clearRespCabecAberto(){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        CabecCLBean cabecCLTO = cabecCheckListDAO.getCabecAberto();
        RespCheckListDAO respItemCLDAO = new RespCheckListDAO();
        respItemCLDAO.clearRespItem(cabecCLTO.getIdCabCL());
    }

    public void createCabecAberto(MotoMecCTR motoMecCTR){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        cabecCheckListDAO.createCabecAberto(motoMecCTR);
    }

    public boolean verAberturaCheckList(Long turno){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        return cabecCheckListDAO.verAberturaCheckList(turno);
    }

    public ItemCheckListBean getItemCheckList(int pos){
        ConfigCTR configCTR = new ConfigCTR();
        RespCheckListDAO respCheckListDAO = new RespCheckListDAO();
        return respCheckListDAO.getItemCheckList(pos, configCTR.getEquip());
    }

    public List getItemList(){
        ConfigCTR configCTR = new ConfigCTR();
        ItemCheckListDAO itemCheckListDAO = new ItemCheckListDAO();
        List itemCheckListList = itemCheckListDAO.getItemList(configCTR.getEquip());
        return itemCheckListList;
    }

    public int qtdeItemCheckList(){
        ConfigCTR configCTR = new ConfigCTR();
        ItemCheckListDAO itemCheckListDAO = new ItemCheckListDAO();
        return itemCheckListDAO.qtdeItem(configCTR.getEquip().getIdCheckList());
    }

    public void insResp(RespItemCLBean respItemCLBean){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        RespCheckListDAO respCheckListDAO = new RespCheckListDAO();
        respCheckListDAO.salvarRespCheckList(cabecCheckListDAO.getIdCabecAberto(), respItemCLBean);
    }

    public void fechaCabec(){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        cabecCheckListDAO.fechaCabec();
    }

    public void recDadosCheckList(String result) {
        ItemCheckListDAO itemCheckListDAO = new ItemCheckListDAO();
        itemCheckListDAO.recDadosCheckList(result);
    }

    public List bolFechList(){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        return cabecCheckListDAO.bolFechList();
    }

    public String dadosEnvio(){

        List cabecCheckListList = bolFechList();

        JsonArray jsonArrayCabec = new JsonArray();
        JsonArray jsonArrayItem = new JsonArray();

        for (int i = 0; i < cabecCheckListList.size(); i++) {

            CabecCLBean cabecCLBean = (CabecCLBean) cabecCheckListList.get(i);
            Gson gsonCabec = new Gson();
            jsonArrayCabec.add(gsonCabec.toJsonTree(cabecCLBean, cabecCLBean.getClass()));

            RespCheckListDAO respItemCLDAO = new RespCheckListDAO();
            List respItemList = respItemCLDAO.respItemList(cabecCLBean.getIdCabCL());

            for (int j = 0; j < respItemList.size(); j++) {
                RespItemCLBean respItemCLBean = (RespItemCLBean) respItemList.get(j);
                Gson gsonItem = new Gson();
                jsonArrayItem.add(gsonItem.toJsonTree(respItemCLBean, respItemCLBean.getClass()));
            }

            respItemList.clear();

        }

        cabecCheckListList.clear();

        JsonObject jsonCabec = new JsonObject();
        jsonCabec.add("cabecalho", jsonArrayCabec);

        JsonObject jsonItem = new JsonObject();
        jsonItem.add("item", jsonArrayItem);

        return jsonCabec.toString() + "_" + jsonItem.toString();

    }

    public void delChecklist() {

        CabecCLBean cabecCLBean = new CabecCLBean();
        List cabecCheckList = cabecCLBean.get("statusCabCL", 2L);
        ArrayList<Long> rLista = new ArrayList<Long>();

        for (int i = 0; i < cabecCheckList.size(); i++) {
            cabecCLBean = (CabecCLBean) cabecCheckList.get(i);
            rLista.add(cabecCLBean.getIdCabCL());
        }

        RespItemCLBean respItemCLBean = new RespItemCLBean();
        List respItemList = respItemCLBean.in("idCabItCL", rLista);

        for (int j = 0; j < respItemList.size(); j++) {
            respItemCLBean = (RespItemCLBean) respItemList.get(j);
            respItemCLBean.delete();
        }

        for (int i = 0; i < cabecCheckList.size(); i++) {
            cabecCLBean = (CabecCLBean) cabecCheckList.get(i);
            cabecCLBean.delete();
        }

    }

    public boolean verEnvioDados(){
        CabecCheckListDAO cabecCheckListDAO = new CabecCheckListDAO();
        return bolFechList().size() > 0;
    }

}
