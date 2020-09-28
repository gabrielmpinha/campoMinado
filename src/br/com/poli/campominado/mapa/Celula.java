package br.com.poli.campominado.mapa;

import java.util.ArrayList;
import java.util.List;

public class Celula {

	// ATRIBUTOS
	private boolean bandeira;
	private boolean bomba;
	private int qtdBombasVizinhas; // NAO PRECISA COLOCAR NO CONSTRUTOR, POIS COMO É UM INT JA INICIALIZA TUDO COM
									// 0
	private boolean visivel;
	private boolean celulaInicial; //OLHA SE FOI A PRIMEIRA CELULA QUE A PESSOA CLICOU OU SUAS VIZINHAS
	private List<Celula> vizinhas;
	private int linha;
	private int coluna;
	/////////////// ATRIBUTOS MODIFICADOS APENAS PELA IA
	private boolean qualificada;
	private int casoQualifiacao;
	private ArrayList<Celula> vizinhasQualificadas;
	private	ArrayList<Celula> bandeirasVisinhas;
	/////////////// É SO ESSES 4
	/*
	 QUALIFICADA:
	 	SE A CELULA PODE SER APLICADA A UM CASO ESPECIAL DA IA (FAVOR LER TODA A CLASSE IA PARA MELHOR COMPREESAO
	 	
	 CASO QUALIFICADA:
	 	QUE TIPO É. OU SEJA, SE É TIPO 1 (3 CASAS NAO VIZIVEIS), OU TIPO 2 (APENAS 2)
	 	
	 VIZINHAS QUALIFICADAS:
	 	FAZER A LIGAÇÃO ENTRE DUAS CELULAS QUALIFICADAS
	 	
	 BANDEIRAS VIZINHAS:
	 	UTILIZADA PARA SABER AS BANDEIRAS VIZINHAS E TAMBEM SABER QUANTAS BOMBAS FALTAM (ATRAVEZ DE SIZE())
	 */

	// CONSTRUTOR
	public Celula(boolean bandeira, boolean bomba, boolean visivel, int linha, int coluna) {
		this.celulaInicial = false;
		this.bandeira = bandeira;
		this.bomba = bomba;
		this.visivel = visivel;
		this.vizinhas = new ArrayList<Celula>();
		this.vizinhasQualificadas = new ArrayList<Celula>();
		this.bandeirasVisinhas = new ArrayList<Celula>();
		this.linha = linha;
		this.coluna = coluna;
		this.qualificada = false;
		this.casoQualifiacao = 0;
		

	}

	// METODOS

	public boolean isEmBranco() {
		if (this.qtdBombasVizinhas == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void buscarVizinhos(Celula[][] campo) {// METODO PERCORRE A MATRIZ CAMPO E ADICIONA OS VIZINHOS EM CADA LISTA

		for (int linha = 0; linha < campo.length; linha++) {// CONJUNTO DE FOR'S PERCORRE A MATRIZ CAMPO E ADICIONA AS
															// VIZINHAS NA LISTA
			for (int coluna = 0; coluna < campo.length; coluna++) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (!(linha + i < 0 || linha + i > campo.length - 1 || coluna + j < 0
								|| coluna + j > campo.length - 1)) {// VERIFICA SE TA DENTRO DA MATRIZ CAMPO
							if (campo[linha][coluna] != campo[linha + i][coluna + j])
								campo[linha][coluna].vizinhas.add(campo[linha + i][coluna + j]);// COLOCA O VIZINHO
																								// ESCOLHIDO DENTRO DA
																								// LISTA
																								// QUE PERTENCE A CELULA
																								// ATUAL

						}
					}
				}
			}
		}
	}

	public boolean isBandeira() {
		return bandeira;
	}

	public void setBandeira(boolean bandeira) {
		this.bandeira = bandeira;
	}

	public boolean isBomba() {
		return bomba;
	}

	public void setBomba(boolean bomba) {
		this.bomba = bomba;
	}

	public int getQtdBombasVizinhas() {
		return qtdBombasVizinhas;
	}

	public void setQtdBombasVizinhas(int qtdBombasVizinhas) {
		this.qtdBombasVizinhas = qtdBombasVizinhas;
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public List<Celula> getVizinhas() {
		return vizinhas;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}


	public boolean isCelulaInicial() {
		return celulaInicial;
	}

	public void setCelulaInicial(boolean celulaInicial) {
		this.celulaInicial = celulaInicial;
	}

	public boolean isQualificada() {
		return qualificada;
	}

	public void setQualificada(boolean qualificada) {
		this.qualificada = qualificada;
	}

	public ArrayList<Celula> getVizinhasQualificadas() {
		return vizinhasQualificadas;
	}

	public void setVizinhasQualificadas(ArrayList<Celula> vizinhasQualificadas) {
		this.vizinhasQualificadas = vizinhasQualificadas;
	}

	public int getCasoQualifiacao() {
		return casoQualifiacao;
	}

	public void setCasoQualifiacao(int casoQualifiacao) {
		this.casoQualifiacao = casoQualifiacao;
	}

	public ArrayList<Celula> getBandeirasVisinhas() {
		return bandeirasVisinhas;
	}

	public void setBandeirasVisinhas(ArrayList<Celula> bandeirasVisinhas) {
		this.bandeirasVisinhas = bandeirasVisinhas;
	}
	
	
}
