package br.com.poli.campominado.jogo;

import java.util.Scanner;

import br.com.poli.campominado.mapa.*;
import br.com.poli.campominado.ranking.Jogador;

public class CampoMinado {

	// ATRIBUTOS
	private Jogador jogador;
	private Mapa mapa;
	private Dificuldade dificuldade;

	// CONSTRUTOR
	public CampoMinado(String nome, Dificuldade dificuldade) {
		this.jogador = new Jogador(nome);
		this.dificuldade = dificuldade;

		switch (dificuldade) {
		case FACIL:
			this.mapa = new MapaFacil();
			break;
		case MEDIO:
			this.mapa = new MapaMedio();
			break;
		case DIFICIL:
			this.mapa = new MapaDificil();

		}
	}

	// METODOS

	public void iniciarJogo() {
		Scanner posicao = new Scanner(System.in);
		int linha, coluna;
		this.mapa.imprimirTela(false);
		while (this.mapa.isGanhouJogo() == false && this.mapa.isFimDeJogo() == false) {
			System.out.print("Largura: ");
			coluna = posicao.nextInt();
			System.out.print("Altura: ");
			linha = posicao.nextInt();
			this.mapa.escolherPosicao(linha, coluna);
		}
		posicao.close();// FECHA O SCANNER
		if (this.mapa.isFimDeJogo()) {// SERVE PARA MOSTRAR COMO É O MAPA COMPLETO CASO O JOGADOR PERCA
			System.out.println("Mapa Original:");
			this.mapa.imprimirTela(true);
		}
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

}
