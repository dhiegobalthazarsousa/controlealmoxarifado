package Model;

public class EspecMovimentacao {
	private String data;
	private String hora;
	private Material material;
	private Nl nl;
	
	public EspecMovimentacao(Material pMaterial, Nl pNl){
		data = Data.getData();
		hora = Data.getHora();
		material = pMaterial;
		nl = pNl;
	}
	
	public String getData() {
		return data;
	}
	
	public String getHora(){
		return hora;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Nl getNl(){
		return nl;
	}
	
	public boolean matches(String pData){
		if(data.equals(pData)) return true;
		return false;
	}
	
	public boolean matchesNl(String pNl){
		if(nl.equals(pNl)) return true;
		return false;
	}
	
	public String toString(){
		return getMaterial().getEspecMaterial().getDescricao() +" "+ getData() +" "+ getHora()+ " " +getNl();
	}
	
}
