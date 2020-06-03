package br.com.usinasantafe.pcomp.model.pst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.usinasantafe.pcomp.model.bean.estaticas.AtividadeBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.DataBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.FuncionarioBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ItemCheckListBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.PneuBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ProdutoBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.REquipAtivBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ROSAtivBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.TurnoBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ApontMMBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.BoletimMMBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CabecCLBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CabecPneuBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.InfColheitaBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.InfPlantioBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ItemPneuBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.LeiraBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.RespItemCLBean;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String FORCA_DB_NAME = "ecm_db";
	public static final int FORCA_BD_VERSION = 1;

	private static DatabaseHelper instance;
	
	public static DatabaseHelper getInstance(){
		return instance;
	}
	
	public DatabaseHelper(Context context) {
		
		super(context, FORCA_DB_NAME,
				null, FORCA_BD_VERSION);
		
		// TODO Auto-generated constructor stub
		instance = this;
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		
		instance = null;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		// TODO Auto-generated method stub
		
		try{
			TableUtils.createTable(cs, AtividadeBean.class);
			TableUtils.createTable(cs, DataBean.class);
			TableUtils.createTable(cs, EquipBean.class);
			TableUtils.createTable(cs, FuncionarioBean.class);
			TableUtils.createTable(cs, ItemCheckListBean.class);
			TableUtils.createTable(cs, MotoMecBean.class);
			TableUtils.createTable(cs, OSBean.class);
			TableUtils.createTable(cs, PneuBean.class);
			TableUtils.createTable(cs, ProdutoBean.class);
			TableUtils.createTable(cs, REquipAtivBean.class);
			TableUtils.createTable(cs, REquipPneuBean.class);
			TableUtils.createTable(cs, ROSAtivBean.class);
			TableUtils.createTable(cs, TurnoBean.class);

			TableUtils.createTable(cs, ApontMMBean.class);
			TableUtils.createTable(cs, BoletimMMBean.class);
			TableUtils.createTable(cs, CabecCLBean.class);
			TableUtils.createTable(cs, CabecPneuBean.class);
			TableUtils.createTable(cs, CarregBean.class);
			TableUtils.createTable(cs, ConfigBean.class);
			TableUtils.createTable(cs, InfColheitaBean.class);
			TableUtils.createTable(cs, InfPlantioBean.class);
			TableUtils.createTable(cs, ItemPneuBean.class);
			TableUtils.createTable(cs, LeiraBean.class);
			TableUtils.createTable(cs, RespItemCLBean.class);

		}
		catch(Exception e){
			Log.e(DatabaseHelper.class.getName(),
					"Erro criando banco de dados...",
					e);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,
			ConnectionSource cs,
			int oldVersion,
			int newVersion) {
		
		try {
			
			if(oldVersion == 1 && newVersion == 2){
				oldVersion = 2;
			}
			
			
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Erro atualizando banco de dados...", e);
		}
		
	}

}
