package Model;

public class EspecMaterial {
	private String descricao;

	public EspecMaterial(String pDescricao) {
		descricao = pDescricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return descricao;
	}
	
	public boolean matches(String pDesc){
		if(this.descricao.contains(pDesc)) return true;
		return false;
	}

}
