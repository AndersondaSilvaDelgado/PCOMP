package br.com.usinasantafe.pcomp.pst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.usinasantafe.pcomp.to.tb.estaticas.DataTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.EquipamentoTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.MotoristaTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.OSTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.ProdutoTO;
import br.com.usinasantafe.pcomp.to.tb.estaticas.TurnoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontCarregTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontMotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaCompTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.PesqBalancaProdTO;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static DatabaseHelper instance;
	
	public static DatabaseHelper getInstance(){
		return instance;
	}
	
	public DatabaseHelper(Context context) {
		
		super(context, Database.FORCA_DB_NAME,
				null, Database.FORCA_BD_VERSION);
		
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

			TableUtils.createTable(cs, MotoristaTO.class);
			TableUtils.createTable(cs, EquipamentoTO.class);
			TableUtils.createTable(cs, ProdutoTO.class);
			TableUtils.createTable(cs, ConfiguracaoTO.class);
			TableUtils.createTable(cs, OSTO.class);
			TableUtils.createTable(cs, ApontCarregTO.class);
			TableUtils.createTable(cs, PesqBalancaProdTO.class);
			TableUtils.createTable(cs, PesqBalancaCompTO.class);
			TableUtils.createTable(cs, MotoMecTO.class);
			TableUtils.createTable(cs, TurnoTO.class);
			TableUtils.createTable(cs, ApontMotoMecTO.class);
			TableUtils.createTable(cs, DataTO.class);

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
