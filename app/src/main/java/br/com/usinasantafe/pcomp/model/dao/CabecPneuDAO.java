package br.com.usinasantafe.pcomp.model.dao;

import java.util.List;

import br.com.usinasantafe.ecm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.ecm.model.bean.variaveis.CabecPneuBean;
import br.com.usinasantafe.ecm.util.Tempo;

public class CabecPneuDAO {

    public CabecPneuDAO() {
    }

    public boolean verCabecPneuAberto() {
        CabecPneuBean cabecPneuBean = new CabecPneuBean();
        List cabecPneuList = cabecPneuBean.get("statusCabecPneu", 1L);
        boolean ret = (cabecPneuList.size() > 0);
        cabecPneuList.clear();
        return ret;
    }

    public CabecPneuBean getCabecPneuAberto(){
        CabecPneuBean cabecPneuBean = new CabecPneuBean();
        List cabecPneuList = cabecPneuBean.get("statusCabecPneu", 1L);
        cabecPneuBean = (CabecPneuBean) cabecPneuList.get(0);
        cabecPneuList.clear();
        return cabecPneuBean;
    }

    public Long getIdCabecAberto(){
        CabecPneuBean cabecPneuBean = getCabecPneuAberto();
        return cabecPneuBean.getIdCabecPneu();
    }

    public void salvarDados(Long func, Long equip, Long idApont){
        CabecPneuBean cabecPneuBean = new CabecPneuBean();
        cabecPneuBean.setIdApontCabecPneu(idApont);
        cabecPneuBean.setEquipCabecPneu(equip);
        cabecPneuBean.setFuncCabecPneu(func);
        cabecPneuBean.setDthrCabecPneu(Tempo.getInstance().dataComHora().getDataHora());
        cabecPneuBean.setStatusCabecPneu(1L);
        cabecPneuBean.insert();
    }

    public boolean verFechCabec(List itemMedPneuList){
        REquipPneuBean rEquipPneuBean = new REquipPneuBean();
        List rEquipPneuList = rEquipPneuBean.all();
        boolean verif = (rEquipPneuList.size() == itemMedPneuList.size());
        if(verif){
            CabecPneuBean cabecPneuBean = getCabecPneuAberto();
            cabecPneuBean.setStatusCabecPneu(2L);
            cabecPneuBean.update();
        }
        rEquipPneuList.clear();
        itemMedPneuList.clear();
        return verif;
    }

}
