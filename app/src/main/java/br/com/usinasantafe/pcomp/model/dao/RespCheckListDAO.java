package br.com.usinasantafe.pcomp.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ItemCheckListBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.RespItemCLBean;
import br.com.usinasantafe.pcomp.model.pst.EspecificaPesquisa;

public class RespCheckListDAO {

    public RespCheckListDAO() {
    }

    public void clearRespItem(Long idCabCL){
        RespItemCLBean respItemCLTO = new RespItemCLBean();
        if (respItemCLTO.hasElements()) {
            List respList = respItemCLTO.get("idCabecItemCheckList", idCabCL);
            for (int i = 0; i < respList.size(); i++) {
                respItemCLTO = (RespItemCLBean) respList.get(i);
                respItemCLTO.delete();
            }
            respList.clear();
        }
    }

    public ItemCheckListBean getItemCheckList(int pos, EquipBean equipBean){

        ItemCheckListBean itemCheckListBean = new ItemCheckListBean();
        List itemCheckList = itemCheckListBean.get("idCheckList", equipBean.getIdCheckList());
        itemCheckListBean = (ItemCheckListBean) itemCheckList.get(pos - 1);
        itemCheckList.clear();

        return itemCheckListBean;

    }

    public void salvarRespCheckList(Long idCabCL, RespItemCLBean respItemCLBean){

        ArrayList pesqArrayList = new ArrayList();
        EspecificaPesquisa pesquisa1 = new EspecificaPesquisa();
        pesquisa1.setCampo("idCabItCL");
        pesquisa1.setValor(idCabCL);
        pesquisa1.setTipo(1);
        pesqArrayList.add(pesquisa1);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("idItBDItCL");
        pesquisa2.setValor(respItemCLBean.getIdItBDItCL());
        pesquisa2.setTipo(1);
        pesqArrayList.add(pesquisa2);

        List respList = respItemCLBean.get(pesqArrayList);
        if(respList.size() > 0) {
            Long opcao = respItemCLBean.getOpItCL();
            respItemCLBean = (RespItemCLBean) respList.get(0);
            respItemCLBean.setOpItCL(opcao);
            respItemCLBean.update();
        }
        else{
            respItemCLBean.setIdCabItCL(idCabCL);
            respItemCLBean.insert();
        }
        respList.clear();
    }

    public List respItemList(Long idCabCL){
        RespItemCLBean respItemCLBean = new RespItemCLBean();
        return respItemCLBean.get("idCabItCL", idCabCL);
    }

}
