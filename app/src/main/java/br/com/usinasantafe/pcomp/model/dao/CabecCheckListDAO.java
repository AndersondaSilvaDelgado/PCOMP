package br.com.usinasantafe.pcomp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CabecCLBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;

public class CabecCheckListDAO {

    public CabecCheckListDAO() {
    }

    public boolean verCabecAberto(){
        CabecCLBean cabecCLBean = new CabecCLBean();
        List cabecList = cabecCLBean.get("statusCabCL", 1L);
        Boolean ret = (cabecList.size() > 0);
        cabecList.clear();
        return ret;
    }

    public Long getIdCabecAberto(){
        CabecCLBean cabecCLBean = getCabecAberto();
        return cabecCLBean.getIdCabCL();
    }

    public CabecCLBean getCabecAberto(){
        CabecCLBean cabecCLBean = new CabecCLBean();
        List cabecList = cabecCLBean.get("statusCabCL", 1L);
        cabecCLBean = (CabecCLBean) cabecList.get(0);
        cabecList.clear();
        return cabecCLBean;
    }

    public boolean verAberturaCheckList(Long idTurno){

        ConfigCTR configCTR = new ConfigCTR();
        EquipBean equipBean = configCTR.getEquip();
        ConfigBean configBean = configCTR.getConfig();

        if ((equipBean.getIdCheckList() > 0) &&
                ((configBean.getUltTurnoCLConfig() != idTurno)
                        || ((configBean.getUltTurnoCLConfig() == idTurno)
                        && (!configBean.getDtUltCLConfig().equals(Tempo.getInstance().dataSHora()))))) {
            return true;
        }
        else{
            return false;
        }

    }

    public void createCabecAberto(MotoMecCTR motoMecCTR){

        ConfigCTR configCTR = new ConfigCTR();

        CabecCLBean cabecCLBean = new CabecCLBean();
        cabecCLBean.setDtCabCL(Tempo.getInstance().dataComHora().getDataHora());
        cabecCLBean.setEquipCabCL(configCTR.getEquip().getNroEquip());
        cabecCLBean.setFuncCabCL(motoMecCTR.getFunc());
        cabecCLBean.setTurnoCabCL(motoMecCTR.getTurno());
        cabecCLBean.setStatusCabCL(1L);
        cabecCLBean.insert();

    }

    public void fechaCabec(){
        CabecCLBean cabecCLBean = getCabecAberto();
        cabecCLBean.setStatusCabCL(2L);
        cabecCLBean.update();
    }

    public List bolFechList(){
        CabecCLBean cabecCLBean = new CabecCLBean();
        return cabecCLBean.get("statusCabCL", 2L);
    }

}
