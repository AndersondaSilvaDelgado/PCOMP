package br.com.usinasantafe.pcomp.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcomp.control.ConfigCTR;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class OSDAO {

    public OSDAO() {
    }

    public OSBean getOS(){
        OSBean osBean = new OSBean();
        List osList = osBean.all();
        osBean = (OSBean) osList.get(0);
        return osBean;
    }

    public OSBean getOSAtiv(Long idAtivOS, Long nroOS){
        List ativOSList = ativOSList(idAtivOS, nroOS);
        OSBean osBean = (OSBean) ativOSList.get(0);
        ativOSList.clear();
        return osBean;
    }

    public OSBean getOSLib(Long idLibOS, Long nroOS){
        List libOSList = libOSList(idLibOS, nroOS);
        OSBean osBean = (OSBean) libOSList.get(0);
        libOSList.clear();
        return osBean;
    }

    public boolean verAtivOS(Long idAtivOS, Long nroOS){
        List ativOSList = ativOSList(idAtivOS, nroOS);
        boolean retorno = ativOSList.size() > 0;
        ativOSList.clear();
        return retorno;
    }

    public boolean verLibOS(Long idLibOS, Long nroOS){
        List libOSList = libOSList(idLibOS, nroOS);
        boolean retorno = libOSList.size() > 0;
        libOSList.clear();
        return retorno;
    }

    private List ativOSList(Long idAtivOS, Long nroOS){
        OSBean rAtivOSBean = new OSBean();
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqAtiv(idAtivOS));
        pesqArrayList.add(getPesqNroOS(nroOS));
        return rAtivOSBean.get(pesqArrayList);
    }

    private List libOSList(Long idLibOS, Long nroOS){
        OSBean rAtivOSBean = new OSBean();
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqLib(idLibOS));
        pesqArrayList.add(getPesqNroOS(nroOS));
        return rAtivOSBean.get(pesqArrayList);
    }

    private EspecificaPesquisa getPesqAtiv(Long idAtivOS){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("idAtivOS");
        especificaPesquisa.setValor(idAtivOS);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqLib(Long idLibOS){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("idLibOS");
        especificaPesquisa.setValor(idLibOS);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqNroOS(Long nroOS){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("nroOS");
        especificaPesquisa.setValor(nroOS);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    public void verOS(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().setVerTerm(false);
        VerifDadosServ.getInstance().verDados(dado, "OS", telaAtual, telaProx, progressDialog);
    }

    public void recDadosOS(String result){

        try {

            ConfigCTR configCTR = new ConfigCTR();

            if (!result.contains("exceeded")) {

                JSONObject jObj = new JSONObject(result.trim());
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    OSBean osTO = new OSBean();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        osTO = gson.fromJson(objeto.toString(), OSBean.class);
                        osTO.insert();

                    }

                    configCTR.setStatusConConfig(1L);
                    VerifDadosServ.getInstance().pulaTelaComTerm();

                } else {

                    configCTR.setStatusConConfig(0L);
                    VerifDadosServ.getInstance().msgComTerm("OS INEXISTENTE NA BASE DE DADOS! FAVOR VERIFICA A NUMERAÇÃO.");

                }

            } else {

                configCTR.setStatusConConfig(0L);
                VerifDadosServ.getInstance().msgComTerm("EXCEDEU TEMPO LIMITE DE PESQUISA! POR FAVOR, PROCURE UM PONTO MELHOR DE CONEXÃO DOS DADOS.");

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            VerifDadosServ.getInstance().msgSemTerm("FALHA DE PESQUISA DE OS! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

}
