package br.com.usinasantafe.pcomp.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.com.usinasantafe.pcomp.control.ConfigCTR;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ROSAtivBean;
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

    public boolean verOS(Long nroOS){
        OSBean osBean = new OSBean();
        List<OSBean> osList = osBean.get("nroOS", nroOS);
        boolean ret = (osList.size() > 0);
        osList.clear();
        return ret;
    }

    public void verOS(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().setVerTerm(false);
        VerifDadosServ.getInstance().verDados(dado, "OS", telaAtual, telaProx, progressDialog);
    }

    public void recDadosOS(String result){

        try {

            ConfigCTR configCTR = new ConfigCTR();

            if (!result.contains("exceeded")) {

                int posicao = result.indexOf("#") + 1;
                String objPrinc = result.substring(0, result.indexOf("#"));
                String objSeg = result.substring(posicao);

                JSONObject jObj = new JSONObject(objPrinc);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    OSBean osTO = new OSBean();
                    osTO.deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        osTO = gson.fromJson(objeto.toString(), OSBean.class);
                        osTO.insert();

                    }

                    jObj = new JSONObject(objSeg);
                    jsonArray = jObj.getJSONArray("dados");

                    ROSAtivBean rosAtivBean = new ROSAtivBean();
                    rosAtivBean.deleteAll();

                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject objeto = jsonArray.getJSONObject(j);
                        Gson gson = new Gson();
                        rosAtivBean = gson.fromJson(objeto.toString(), ROSAtivBean.class);
                        rosAtivBean.insert();

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
            VerifDadosServ.getInstance().msgSemTerm("FALHA DE PESQUISA DE OS! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

}
