package br.com.poli.campominado.ranking;

import java.io.Serializable;

public class Jogador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ATRIBUTOS
	private String nome;
	private String tempo;//TEMPO FORMATADO EM MINUTOS:SEGUNDOS
	private int tempoTotal;//TEMPO TOTAL INTEIRO, USADO PARA COMPARAR NO RANKING
	
	
	// CONSTRUTOR
	
	public Jogador(String nome) {//ESSE SO USA NOS TESTES DO CONSOLE
		this.nome = nome;
	}
	
	public Jogador(String nome, String tempo, int tempoTotal) {
		this.nome = nome;
		this.tempo = tempo;
		this.tempoTotal = tempoTotal;
		//ranking = new Ranking(this);
	}

	// METODOS
	
	@Override
	public String toString() {
		return this.nome + "   " + this.tempo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public int getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(int tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

}
