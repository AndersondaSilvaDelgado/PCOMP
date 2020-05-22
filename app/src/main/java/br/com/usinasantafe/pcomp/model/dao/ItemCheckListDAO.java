package br.com.usinasantafe.pcomp.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ItemCheckListBean;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class ItemCheckListDAO {

    public ItemCheckListDAO() {
    }

    public void atualCheckList(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().setVerTerm(true);
        VerifDadosServ.getInstance().verDados(dado, "CheckList", telaAtual, telaProx, progressDialog);
    }

    public void recDadosCheckList(String result) {

        try {

            if (!result.contains("exceeded")) {

                int pos1 = result.indexOf("_") + 1;
                String objPrinc = result.substring(0, (pos1 - 1));
                String objSeg = result.substring(pos1);

                JSONObject jObj = new JSONObject(objPrinc);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                EquipBean equipBean = new EquipBean();
                equipBean.deleteAll();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Gson gson = new Gson();
                    equipBean = gson.fromJson(objeto.toString(), EquipBean.class);
                    equipBean.insert();

                }

                jObj = new JSONObject(objSeg);
                jsonArray = jObj.getJSONArray("dados");

                ItemCheckListBean itemCheckListBean = new ItemCheckListBean();
                itemCheckListBean.deleteAll();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Gson gson = new Gson();
                    ItemCheckListBean itemCheckList = gson.fromJson(objeto.toString(), ItemCheckListBean.class);
                    itemCheckList.insert();

                }

                VerifDadosServ.getInstance().pulaTelaSemTerm();

            } else {
                VerifDadosServ.getInstance().pulaTelaSemTerm();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            VerifDadosServ.getInstance().pulaTelaSemTerm();
        }

    }

    public int qtdeItem(Long idChecklist){
        ItemCheckListBean itemCheckListBean = new ItemCheckListBean();
        return itemCheckListBean.get("idCheckList", idChecklist).size();
    }

    public List getItemList(EquipBean equipBean){
        ItemCheckListBean itemCheckListBean = new ItemCheckListBean();
        List itemCheckListList = itemCheckListBean.getAndOrderBy("idCheckList", equipBean.getIdCheckList(), "idItemCheckList", true);
        return itemCheckListList;
    }

}
