package br.com.poli.campominado.jogo;

public enum Dificuldade {

	// CONSTANTES
	FACIL(9), MEDIO(16), DIFICIL(32); // cria as constantes FACIL e DIFICIL, com inteiros relacionados a elas

	// ATRIBUTOS
	private int valor;

	// CONSTRUTOR
	private Dificuldade(int valor) {// o valor do construtor corresponte ao inteiro designado a FACIL e DIFICIL
		this.valor = valor;
	}

	// METODOS
	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
