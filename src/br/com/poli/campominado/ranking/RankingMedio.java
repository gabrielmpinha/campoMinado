package br.com.poli.campominado.ranking;

import java.io.File;

public class RankingMedio extends Ranking {

	public static final File RANK = new File("rankingMedio.data");
	
	public RankingMedio() {
		super(RANK);
		// TODO Auto-generated constructor stub
	}

	public File getRank() {
		return RANK;
	}

}
