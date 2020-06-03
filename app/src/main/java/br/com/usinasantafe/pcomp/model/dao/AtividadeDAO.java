package br.com.usinasantafe.pcomp.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.usinasantafe.pcomp.model.bean.estaticas.AtividadeBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.REquipAtivBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ROSAtivBean;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class AtividadeDAO {

    public AtividadeDAO() {
    }

    public void verAtiv(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().setVerTerm(true);
        VerifDadosServ.getInstance().verDados(dado, "Atividade", telaAtual, telaProx, progressDialog);
    }

    public void recDadosAtiv(String result) {

        try {

            if (!result.contains("exceeded")) {

                int pos1 = result.indexOf("_") + 1;
                int pos2 = result.indexOf("|") + 1;
                String objPrim = result.substring(0, (pos1 - 1));
                String objSeg = result.substring(pos1, (pos2 - 1));
                String objTerc = result.substring(pos2);

                JSONObject jObj = new JSONObject(objPrim);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                REquipAtivBean rEquipAtivBean = new REquipAtivBean();
                rEquipAtivBean.deleteAll();

                for (int j = 0; j < jsonArray.length(); j++) {

                    JSONObject objeto = jsonArray.getJSONObject(j);
                    Gson gson = new Gson();
                    REquipAtivBean rEquipAtiv = gson.fromJson(objeto.toString(), REquipAtivBean.class);
                    rEquipAtiv.insert();

                }

                jObj = new JSONObject(objSeg);
                jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject objeto = jsonArray.getJSONObject(j);
                        Gson gson = new Gson();
                        OSBean osBean = gson.fromJson(objeto.toString(), OSBean.class);
                        osBean.insert();

                    }

                }

                jObj = new JSONObject(objTerc);
                jsonArray = jObj.getJSONArray("dados");

                AtividadeBean atividadeBean = new AtividadeBean();
                atividadeBean.deleteAll();

                for (int j = 0; j < jsonArray.length(); j++) {

                    JSONObject objeto = jsonArray.getJSONObject(j);
                    Gson gson = new Gson();
                    AtividadeBean atividade = gson.fromJson(objeto.toString(), AtividadeBean.class);
                    atividade.insert();

                }

                VerifDadosServ.getInstance().pulaTelaSemTerm();

            } else {
                VerifDadosServ.getInstance().msgSemTerm("EXCEDEU TEMPO LIMITE DE PESQUISA! POR FAVOR, PROCURE UM PONTO MELHOR DE CONEXÃO DOS DADOS.");
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().msgSemTerm("FALHA DE PESQUISA DE OS! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

    public ArrayList retAtivArrayList(Long equip, Long nroOS){

        ArrayList atividadeArrayList = new ArrayList();

        REquipAtivBean rEquipAtivBean = new REquipAtivBean();
        List rEquipAtivList = rEquipAtivBean.get("idEquip", equip);

        ArrayList<Long> rEquipAtivArrayList = new ArrayList<Long>();

        for (int i = 0; i < rEquipAtivList.size(); i++) {
            rEquipAtivBean = (REquipAtivBean) rEquipAtivList.get(i);
            rEquipAtivArrayList.add(rEquipAtivBean.getIdAtiv());
        }

        AtividadeBean atividadeBean = new AtividadeBean();
        List atividadeList = atividadeBean.in("idAtiv", rEquipAtivArrayList);

        ROSAtivBean rOSAtivBean = new ROSAtivBean();
        List rOSAtivList = rOSAtivBean.get("nroOS", nroOS);

        if (rOSAtivList.size() > 0) {

            for (int i = 0; i < atividadeList.size(); i++) {
                atividadeBean = (AtividadeBean) atividadeList.get(i);
                for (int j = 0; j < rOSAtivList.size(); j++) {
                    rOSAtivBean = (ROSAtivBean) rOSAtivList.get(j);
                    if (Objects.equals(atividadeBean.getIdAtiv(), rOSAtivBean.getIdAtiv())) {
                        atividadeArrayList.add(atividadeBean);
                    }
                }
            }

        } else {
            for (int i = 0; i < atividadeList.size(); i++) {
                atividadeBean = (AtividadeBean) atividadeList.get(i);
                atividadeArrayList.add(atividadeBean);
            }
        }

        return atividadeArrayList;

    }


}
