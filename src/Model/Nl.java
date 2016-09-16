package Model;

public class Nl {
	
	private long numero;
	private String ano;
	private String cessionario;
	
	public Nl(long pNumero, String pCessionario){
		ano = Data.getAno();
		numero = pNumero;
		cessionario = pCessionario;
	}
	
	public String getCessionario(){
		return cessionario;
	}

	public long getNumero() {
		return numero;
	}
	
	public String getAno() {
		return ano;
	}
	
	public String toString(){
		return numero + "/" + ano;
	}
}
