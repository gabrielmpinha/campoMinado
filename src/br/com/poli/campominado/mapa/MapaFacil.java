package br.com.poli.campominado.mapa;

import br.com.poli.campominado.jogo.Dificuldade;

public class MapaFacil extends Mapa {

	public static final Dificuldade TAMANHO = Dificuldade.FACIL;
	public static final int BOMBAS = 10;

	public MapaFacil() {
		super(TAMANHO.getValor(), BOMBAS);
	}

	public static Dificuldade getTamanho() {
		return TAMANHO;
	}

}
