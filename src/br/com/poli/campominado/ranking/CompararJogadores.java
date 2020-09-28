package br.com.poli.campominado.ranking;

import java.util.Comparator;

public class CompararJogadores implements Comparator<Jogador>{//CLASSE QUE SERVE PARA COMPARAR OS JOGADORES DA LISTA BASEDO NO TEMPO TOTAL DE JOGO DELES

	
	@Override
	public int compare(Jogador a, Jogador b) {
		return a.getTempoTotal() - b.getTempoTotal(); //
	}
	
}

