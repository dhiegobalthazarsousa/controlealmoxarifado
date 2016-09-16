 package Model;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class Model {

	ObjectContainer materiais = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), 
			"./materiais.db4o");
	ObjectContainer movimentacoes = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
			"./movimentacoes.db4o");
	ObjectContainer nls = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
			"./nls.db4o");
	
	
	public boolean verificaNl(String pNl){
		boolean verificacao = false;
		ObjectSet<Nl> result = getAllNl();
		if(!result.isEmpty()){
			for(Nl nl : result){
				if(nl.toString().equals(pNl))
					verificacao = true;
			}
		}
		return verificacao;
	}
	
	public boolean addNl(Nl pNl){
		boolean status = false;
		if(!verificaNl(pNl.toString())){
			nls.store(pNl);
			nls.commit();
			status = true;
		}
		return status;
	}
	
	public ObjectSet<Nl> getAllNl(){
		Query query = nls.query();
		query.constrain(Nl.class);
		ObjectSet<Nl> result = query.execute();
		return result;
	}
	
	public ObjectSet<Nl> searchNl(final String pNl) {
		ObjectSet<Nl> result = nls.query(new Predicate<Nl>() {
			public boolean match(Nl nl) {
				return nl.toString().equals(pNl);
			}
		});
		return result;
	}
	
	/*
	 * METODO QUE VERIFICA SE O MATERIAL J� ESTA SALVO NO BANCO
	 */
	public boolean verifica(Material pMat) {
		boolean verificacao = false;
		ObjectSet<Material> result = getAllMateriais();
		if (!result.isEmpty()) {
			for (Material mat : result) {
				if (mat.getEspecMaterial().matches(pMat.getEspecMaterial().getDescricao()))
					verificacao = true;
			}
		}
		return verificacao;
	}
	
	public boolean addMaterial(Material pMat) {	
		boolean status = false;
		if(!verifica(pMat)){
			materiais.store(pMat);
			materiais.commit();
			status = true;
		}
		return status;
	}
	
	public boolean deleteMaterial(String pDesc){
		boolean permissao = false;
		ObjectSet<Material> result = searchMaterial(pDesc);
		if(!result.isEmpty()){
			for(Material mat : result){
				materiais.delete(mat);
				materiais.commit();
				permissao = true;
			}
		}
		return permissao;
	}
	
	public ObjectSet<Material> getAllMateriais() {
		Query query = materiais.query();
		query.constrain(Material.class);
		ObjectSet<Material> result = query.execute();
		return result;
	}
	
	public ObjectSet<Material> searchMaterial(final String pDesc) {
		ObjectSet<Material> result = materiais.query(new Predicate<Material>() {
			public boolean match(Material mat) {
				return mat.getEspecMaterial().getDescricao().contains(pDesc);
			}
		});
		return result;
	}
	
	
	public void addMovimentacao(Movimentacao pMov) {
		movimentacoes.store(pMov);
		movimentacoes.commit();
	}
	
	public ObjectSet<Movimentacao> getAllMovimentacoes() {
		Query query = movimentacoes.query();
		query.constrain(Movimentacao.class);
		ObjectSet<Movimentacao> result = query.execute();
		return result;
	}
	
	public ObjectSet<Movimentacao> searchMovimentacao(final String pData){
		ObjectSet<Movimentacao> result = movimentacoes.query(new Predicate<Movimentacao>() {
			public boolean match(Movimentacao mov) {
				return mov.getEspecMovimentacao().matches(pData);
			}
		});
		return result;
	}
	
	public ObjectSet<Movimentacao> searchMovimentacaoByNl(final String pNl){
		ObjectSet<Movimentacao> result = movimentacoes.query(new Predicate<Movimentacao>(){
			public boolean match(Movimentacao mov){
				return mov.getEspecMovimentacao().matchesNl(pNl);
			}
		});
		return result;
	}
	
	public void alterarMaterial(Material pMat, int novaQuantidade){
		pMat.setQuantidade(novaQuantidade);
		double novoSaldo = pMat.calcularSaldo(pMat.getValorUnitario(), pMat.getQuantidade());
		pMat.setSaldo(novoSaldo);
		deleteMaterial(pMat.getEspecMaterial().getDescricao());
		addMaterial(pMat);
	}
	
	public void alterarMaterial(Material pMat, double pValor){
		pMat.setValorUnitario(pValor);
		double novoSaldo = pMat.calcularSaldo(pMat.getValorUnitario(), pMat.getQuantidade());
		pMat.setSaldo(novoSaldo);
		deleteMaterial(pMat.getEspecMaterial().getDescricao());
		addMaterial(pMat);
	}
}
