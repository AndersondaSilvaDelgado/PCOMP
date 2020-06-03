package br.com.usinasantafe.pcomp.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pcomp.model.pst.EspecificaPesquisa;

public class MotoMecDAO {

    public MotoMecDAO() {
    }

    public List getMotoMecList(Long aplic){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqBeanAplic(aplic));
        pesqArrayList.add(getPesqBeanMotoMec());

        MotoMecBean motoMecBean = new MotoMecBean();
        return motoMecBean.getAndOrderBy(pesqArrayList, "posOperMotoMec", true);

    }

    public MotoMecBean getCheckList(Long aplic){

        MotoMecBean motoMecBean = new MotoMecBean();
        ArrayList pesqArrayList = new ArrayList();

        pesqArrayList.add(getPesqBeanAplic(aplic));
        pesqArrayList.add(getPesqBeanParada());

        EspecificaPesquisa especificaPesquisa3 = new EspecificaPesquisa();
        especificaPesquisa3.setCampo("codFuncaoOperMotoMec");
        especificaPesquisa3.setValor(9L);
        especificaPesquisa3.setTipo(1);
        pesqArrayList.add(especificaPesquisa3);

        List motoMecList = motoMecBean.get(pesqArrayList);
        motoMecBean = (MotoMecBean) motoMecList.get(0);
        motoMecList.clear();

        return motoMecBean;

    }

    public MotoMecBean getVoltaTrabalho(Long aplic){

        MotoMecBean motoMecBean = new MotoMecBean();
        ArrayList pesqArrayList = new ArrayList();

        pesqArrayList.add(getPesqBeanAplic(aplic));
        pesqArrayList.add(getPesqBeanInvisivel());

        EspecificaPesquisa especificaPesquisa3 = new EspecificaPesquisa();
        especificaPesquisa3.setCampo("codFuncaoOperMotoMec");
        especificaPesquisa3.setValor(8L);
        especificaPesquisa3.setTipo(1);
        pesqArrayList.add(especificaPesquisa3);

        List motoMecList = motoMecBean.get(pesqArrayList);
        motoMecBean = (MotoMecBean) motoMecList.get(0);
        motoMecList.clear();

        return motoMecBean;

    }

    public List getParadaList(Long aplic){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqBeanAplic(aplic));
        pesqArrayList.add(getPesqBeanParada());
        MotoMecBean motoMecBean = new MotoMecBean();
        return motoMecBean.getAndOrderBy(pesqArrayList, "posOperMotoMec", true);

    }

    private EspecificaPesquisa getPesqBeanAplic(Long aplic){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("aplicOperMotoMec");
        especificaPesquisa.setValor(aplic);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqBeanMotoMec(){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("tipoOperMotoMec");
        especificaPesquisa.setValor(1L);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqBeanParada(){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("tipoOperMotoMec");
        especificaPesquisa.setValor(2L);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqBeanInvisivel(){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("tipoOperMotoMec");
        especificaPesquisa.setValor(0L);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

}
