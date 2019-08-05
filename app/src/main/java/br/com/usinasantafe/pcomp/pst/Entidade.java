package br.com.usinasantafe.pcomp.pst;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;
	private Dao dao;
	
	private Dao daoImpl(){
		
		try {
			
			if(dao == null){
				DatabaseHelper instance = DatabaseHelper.getInstance();
				dao = instance.getDao(getClass());
			}
			
			return dao;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	public void insert() {
		try {
			this.daoImpl().create(this);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update() {
		
		try {
			this.daoImpl().update(this);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete()  {
		try {
			this.daoImpl().delete(this);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteAll()  {
		try {
//			this.daoImpl().delete(all());
			DeleteBuilder<String, Object> deleteBuilder = this.daoImpl().deleteBuilder();
			deleteBuilder.delete();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List all() {
		try {
			return this.daoImpl().queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List get(String campo, Object valor) {
		try {
			return this.daoImpl().queryForEq(campo, valor);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List get(ArrayList<EspecificaPesquisa> lista) {
		try {
			QueryBuilder<String, Object> queryBuilder =
					this.daoImpl().queryBuilder();
			Where<String, Object> where = queryBuilder.where();

			EspecificaPesquisa pesquisa = (EspecificaPesquisa) lista.get(0);
			where.eq(pesquisa.getCampo(), pesquisa.getValor());
			
			for(int i = 1; i < lista.size(); i++){
				pesquisa = (EspecificaPesquisa) lista.get(i);
				where.and();
				where.eq(pesquisa.getCampo(), pesquisa.getValor());
			}
			
			PreparedQuery preparedQuery = queryBuilder.prepare();
			return this.daoImpl().query(preparedQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List getAndOrderBy(ArrayList<EspecificaPesquisa> lista, String campo, boolean order) {
		try {
			QueryBuilder<String, Object> queryBuilder =
					this.daoImpl().queryBuilder();
			Where<String, Object> where = queryBuilder.where();

			EspecificaPesquisa pesquisa = (EspecificaPesquisa) lista.get(0);
			where.eq(pesquisa.getCampo(), pesquisa.getValor());
			
			for(int i = 1; i < lista.size(); i++){
				pesquisa = (EspecificaPesquisa) lista.get(i);
				where.and();
				where.eq(pesquisa.getCampo(), pesquisa.getValor());
			}
			
			queryBuilder.orderBy(campo, order);
			PreparedQuery preparedQuery = queryBuilder.prepare();
			return this.daoImpl().query(preparedQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List orderBy(String campo, boolean order){
		try {
			
			QueryBuilder<String, Object> queryBuilder =
					this.daoImpl().queryBuilder();
			queryBuilder.orderBy(campo, order);
			PreparedQuery preparedQuery = queryBuilder.prepare();
			return this.daoImpl().query(preparedQuery);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public boolean exists(String campo, Object valor){
		try {
			if(this.daoImpl().queryForEq(campo, valor).size() > 0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean hasElements() {
		// TODO Auto-generated method stub
		return all().size() > 0;
	}

	public int count() {
		// TODO Auto-generated method stub
		return all().size();
	}
	
}
