package br.com.poli.campominado.ranking;

import java.io.File;

public class RankingFacil extends Ranking {

	public static final File RANK = new File("rankingFacil.data");
	
	public RankingFacil() {
		super(RANK);
		// TODO Auto-generated constructor stub
	}
	
	public File getRank() {
		return RANK;
	}

}
