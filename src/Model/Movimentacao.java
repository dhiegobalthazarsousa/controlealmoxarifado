package Model;



public class Movimentacao {
	private EspecMovimentacao especMovimentacao;
	private int quantidade;
	private double total;
	
	//cada material tem a sua movimentação
	public Movimentacao (EspecMovimentacao pEspecMovimentacao, int pQuantidade){
		especMovimentacao = pEspecMovimentacao;
		quantidade = pQuantidade;
		total = calcularTotal(pEspecMovimentacao.getMaterial().getValorUnitario(), pQuantidade);
	}
	
	public double calcularTotal(double valorUnitario, int quantidade){
		return valorUnitario * quantidade;
	}
	
	public EspecMovimentacao getEspecMovimentacao() {
		return especMovimentacao;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	public double getTotal() {
		return total;
	}
	
	public String toString(){
		return especMovimentacao +" "+ quantidade +" "+ total;
	}
	
}
