package Model;

public class Material {
	private int quantidade;
	private double valorUnitario;
	private double saldo;
	private EspecMaterial especMaterial;
	
	public Material(EspecMaterial pEspecMaterial, double pValorUnitario, int pQuantidade){
		especMaterial = pEspecMaterial;
		valorUnitario = pValorUnitario;
		quantidade = pQuantidade;
		saldo = calcularSaldo(pValorUnitario, pQuantidade);
	}
	
	public int getQuantidade(){
		return quantidade;
	}
	
	public void setQuantidade(int pQuantidade){
		quantidade = pQuantidade;
	}
	
	public double getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public EspecMaterial getEspecMaterial() {
		return especMaterial;
	}
	
	public double calcularSaldo(double valorUnitario, int quantidade){
		return valorUnitario * quantidade;
	}
	
	public String toString(){
		return especMaterial + " " + quantidade + " " + Formata.formatOut(valorUnitario) + " " + Formata.formatOut(saldo);
	}
}
