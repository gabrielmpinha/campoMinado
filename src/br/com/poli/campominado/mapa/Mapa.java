package br.com.poli.campominado.mapa;

import java.util.Random;

public abstract class Mapa {

	// ATRIBUTOS
	private Celula[][] campo;
	private boolean fimDeJogo;
	private boolean ganhouJogo;
	private int bombas;
	private int celulasVisiveis;
	private boolean primeiraJogada;//SERVE PARA VER SE EH A PRIMEIRA JOGADA NO TABULEIRO

	// CONSTRUTOR
	public Mapa(int tamanho, int bombas) {

		this.fimDeJogo = false;
		this.ganhouJogo = false;
		this.bombas = bombas;
		this.primeiraJogada = true;
		this.campo = new Celula[tamanho][tamanho]; // inicializa a matriz campo com
													// o tamanho escolhido
		this.inicializarCelulas();
		Celula.buscarVizinhos(campo);
		//this.distribuirBombas(this.bombas);
		//this.contarBombas();
		//Celula.buscarVizinhos(campo); // DA FORMA QUE FUNCIONA AQUI ACREDITO SERIA MELHOR UM METODO STATIC,
										// POIS O
										// OBJETO QUE CHAMA NAO IMPORTA, E SIM O PARAMETRO, NAO COLOQUEI STATIC
										// POIS NAO ESTA NO PDF
	}

	

	// METODOS PARTE 3
	public Celula getCelula(int linha, int coluna) {
		return campo[linha][coluna];
	}

	private void revelarEspacos(Celula celulaEscolhida) {// FAZ A MESMA COISA DO VERIFICARZERO MAS DE FORMA DIFERENTE
		if (!celulaEscolhida.isVisivel() && !celulaEscolhida.isBandeira()) {// SO ENTRA SE FOR INVISIVEL
																							// A CELULA

			celulaEscolhida.setVisivel(true);
			this.celulasVisiveis++;

			if (celulaEscolhida.isEmBranco()) {// SE FOR UMA CELULA EM BRANCO ENTRA PRA OLHAR AS VIZINHAS

				for (int i = 0; i < celulaEscolhida.getVizinhas().size(); i++) {// ESSE FOR PERCORRE AS VIZINHAS DA
																				// CELULAESCOLHIDA
					this.revelarEspacos(celulaEscolhida.getVizinhas().get(i));// FAZ A RECURSAO COM A VIZINHA ATUAL
				} // get (i) FUNCIONA COMO O COMANDO [] DE UM VETOR, ACESSAR O INTERIOR DESSE
					// VETOR. NO CASO É UMA LISTA
			}
		}
	}

	private boolean verificarGanhouJogo() { //VERIFICA SE O JOGADOR GANHOU O JOGO
		if (this.celulasVisiveis >= (this.campo.length * this.campo.length) - this.bombas) {
			System.out.println("Você ganhou o jogo!!!!!");
			return true;
		} else
			return false;
	}

	public boolean isFimDeJogo() {
		return this.fimDeJogo;
	}

	public boolean isGanhouJogo() {
		return this.ganhouJogo;
	}

	// METODOS PARTE 2
	private void distribuirBombas(int bombas) {// SE NAO FOR UMA CELULA INICIAL E NAO FOR UMA BOMBA ELE COLOCA COMO BOMBA RANDOMICAMENTE
		int contador = 0;
		int linha, coluna;
		do {
			linha = new Random().nextInt(this.campo.length);// SELECIONA UM NUMERO ALEATORIO PARA POSICAO DE LINHA E
			// COLUNA
			coluna = new Random().nextInt(this.campo.length);
			if (!this.campo[linha][coluna].isBomba() && !this.campo[linha][coluna].isCelulaInicial()) {// SE
																														// NAO
																														// TIVER
																														// UMA
																														// BOMBA
																														// NAS
																														// COORDENADAS
																														// ESCOLHIDAS,
																														// É
				// COLOCADA UMA
				// BOMBA E O CONTADOR ADICIONA 1
				this.campo[linha][coluna].setBomba(true);
				this.campo[linha][coluna].setQtdBombasVizinhas(-1); // PARA APARECER -1 COMO UMA BOMBA NA MATRIZ, APENAS
																	// PARA FACILITAR NA VIZUALIZAÇAO NO CONSOLE
				contador++;
			}
		} while (contador < bombas);

	}

	private void inicializarCelulas() {

		for (int i = 0; i < this.campo.length; i++) {
			for (int j = 0; j < this.campo.length; j++) {
				this.campo[i][j] = new Celula(false, false, false, i, j);// INICIALIZA CADA CELULA COM UM VALOR "PADRAO"
			}
		}
	}

	public void imprimirTela(boolean teste) {

		for (int i = 0; i < this.campo.length; i++) {
			for (int j = 0; j < this.campo.length; j++) {

				System.out.print(" ");

				if (teste) { // SE FOR TRUE PRINTA TUDO, BOMBAS COM -1 E NAO-BOMBAS COM O NUMERO DE BOMBAS
										// VIZINHAS
					if (!this.campo[i][j].isBomba())// SE NAO FOR BOMBA PRINTA UM ESPAÇO ANTES PRA FICAR BONITO
						System.out.print(" ");// por causa do -
					System.out.print(this.campo[i][j].getQtdBombasVizinhas());
				}

				else { // SE FOR FALSE SO PRINTA AS CELULAS VISIVEIS

					if (this.campo[i][j].isBandeira()) {
						System.out.print(" b");
					} else if (this.campo[i][j].isVisivel()) {
						if (!this.campo[i][j].isBomba())// SE NAO FOR BOMBA COLOCAR UM ESPAÇO A MAIS PRA FICAR BONITINHO
							System.out.print(" ");
						System.out.print(this.campo[i][j].getQtdBombasVizinhas());
					} else
						System.out.print(" -");// ISSO EH PRA PODER VIZUALIZAR O TABULEIRO QND TIVER TUDO INVISIVEL
				}
			}
			System.out.println();
		}

	}

	
	private void jogadaInicial(int linha, int coluna) {//SE FOR A PRIMEIRA JOGADA, ELE DEIXA VERDADEIRO O ATRIBUTO CELULAINICIAL DA CELULA ESCOLHIDA E SUAS VIZINHAS, PARA BLOQUEAR ELAS DE SEREM BOMBAS
		if (this.primeiraJogada) {
			this.primeiraJogada = false;//DEIXA COMO FALSO JA QUE JA ESCOLHEU
			this.campo[linha][coluna].setCelulaInicial(true);
			for (int i = 0; i < this.campo[linha][coluna].getVizinhas().size(); i++)
				this.campo[linha][coluna].getVizinhas().get(i).setCelulaInicial(true);
			
			this.distribuirBombas(this.bombas);//DISTRIBUI AS BOMBAS
			this.contarBombas();//CONTA A BOMBAS
		}
	}
	
	public void escolherPosicao(int linha, int coluna) {

		this.jogadaInicial(linha, coluna);//VERIFICAR SE FOI A PRIMEIRA JOGADA

		// OLHA SE A POSICAO ESCOLHIDA TA DENTRO DA MATRIZ
		if (!this.campo[linha][coluna].isBandeira()) {
			if (linha >= 0 && linha < this.campo.length && coluna >= 0 && coluna < this.campo.length) {

				if (!this.campo[linha][coluna].isVisivel()) { // SO VAI PODER ESCOLHERPOSICAO SE A CELULA NAO FOR
																		// VISIVEL
					if (this.campo[linha][coluna].isBomba()) {// SE FOR BOMBA
						this.campo[linha][coluna].setVisivel(true);
						this.celulasVisiveis++;
						this.fimDeJogo = true; // CASO SELECIONE UMA BOMBA, O JOGO ACABA

						for (int i = 0; i < this.campo.length; i++)
							for (int j = 0; j < this.campo.length; j++)
								this.campo[i][j].setVisivel(true);

						System.out.println("Fim de jogo. Você perdeu!!");
					}

					else if (this.campo[linha][coluna].isEmBranco() == false) {// SE NAO FOR BOMBA NEM 0
						this.campo[linha][coluna].setVisivel(true);
						this.celulasVisiveis++;

					}

					else {// SE FOR EM BRANCO
						this.revelarEspacos(this.campo[linha][coluna]);
					}

					this.imprimirTela(false);// DPS QUE ESCOLHE A POSICAO IMPRIME A TELA DE COMO FICOU
					System.out.println();
					this.ganhouJogo = this.verificarGanhouJogo();
				}
			}
		}

	}

	public void contarBombas() {

		for (int linha = 0; linha < this.campo.length; linha++) { // PROCURANDO BOMBAS
			for (int coluna = 0; coluna < this.campo.length; coluna++) {
				if (this.campo[linha][coluna].isBomba()) { // ACHOU

					for (int i = -1; i <= 1; i++) {// OLHA AO REDOR DA BOMBA
						for (int j = -1; j <= 1; j++) {

							if (!(linha + i < 0 || linha + i > this.campo.length - 1 || coluna + j < 0
									|| coluna + j > this.campo.length - 1)) { // VERIFICA SE ESTA DENTRO DA MATRIZ
								if (this.campo[linha + i][coluna + j].isBomba() == false) {// SE NAO FOR BOMBA, SOMA +1
																							// NA QNTDD DE BOMBAS
																							// VIZINHAS
									this.campo[linha + i][coluna + j].setQtdBombasVizinhas(
											this.campo[linha + i][coluna + j].getQtdBombasVizinhas() + 1);

								}
							}

						}

					}

				}
			}
		}

	}

	public Celula[][] getCampo() {
		return campo;
	}

	public void setCampo(Celula[][] campo) {
		this.campo = campo;
	}

	public int getBombas() {
		return bombas;
	}

	public boolean isPrimeiraJogada() {
		return primeiraJogada;
	}

}
