package br.com.poli.campominado.ranking;

import java.io.File;

public class RankingDificil extends Ranking {
	
	public static final File RANK = new File("rankingDificil.data");

	public RankingDificil() {
		super(RANK);
		// TODO Auto-generated constructor stub
	}

	public File getRank() {
		return RANK;
	}

}
