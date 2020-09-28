package br.com.poli.campominado;

import br.com.poli.campominado.mapa.*;
import java.util.Random;

import java.util.ArrayList;
/*
DIVIDIMOS A IA EM 3 PARTES PRINCIPAIS
PARTE 1:
	APLICAR BOMBA SE O NUMERO DE CASAS N VIZIVEIS VIZINHAS FOR EQUIVALENTE AO NUMERO DE BOMBAS FALTANDO
PARTE 2:
	LIMPAR (TORNAR VISIVEL) AS CASAS VIZINHAS DAS QUE JA FORAM ENCONTRADAS AS BOMBAS (BANDEIRAS APLICADAS)
PARTE 3:
	SE O MUNDO FOSSE PERFEITO, APENAS A PARTE 1 E 2 SERIAM NECESSARIAS :D
	A PARTE 3 É USADA PARA LIDAR COM AS CASOS ESPECIAIS, OU SEJA, A VERDADEIRA PARTE COMPLICADA DA IA.
	POR ISSO A IA SO USARA A PARTE 3 NO MODO COMPLEXO (CONTINUE LENDO Q EXPLICAREMOS MELHOR)
*/

public class IA {
	private Mapa mapa; //RECEBER O MAPA QUE SERA TRABALHADO
	private int tMapa; //SALVA O TAMANHO DO MAPA TRABALHADO (POUPA TEMPO E LINHA DE CÓDIGO)
	private boolean complexa; //SABER SE FAREMOS A IA USANDO TUDO Q TEM OU SÓ PARTE DELA
	private boolean taTudoBem; 
	/*TATUDOBEM É USADO PARA EVITAR A EXISTENCIA DE UM LOOP ETERNO
	 SE INICIA COMO FALSO E AO LONGO DO PROCESSO VIRA TRUE (SE ALGO FOR FEITO
	 AO VARRER A MATRIZ). SE NÃO, INSTATANEAMENTE IREMOS QUEBRAR O LOOP*/
	
	public IA(Mapa mapa, boolean complexa) {
		this.mapa = mapa;
		this.tMapa = this.mapa.getCampo().length;
		this.taTudoBem = true;
		this.complexa = complexa;

	}
	
	
	
	private void limparBandeiras() { //SERVE PARA LIMPAR BANDEIRAS QUE O USUARIO POSSA TER COLOCADO ERRADO ANTES DE USAR A IA
		for (int i = 0; i < mapa.getCampo().length; i++) {
			for(int j = 0; j< mapa.getCampo().length; j++) {
				if(mapa.getCelula(i, j).isBandeira())
					mapa.getCelula(i, j).setBandeira(false);
			}
		}
	}

	private void testarInicio() { //SO FAZ SE O JOGADOR SELECIONAR A IA SEM TER ESCOLHIDO NENHUMA CASA ANTES
		if(mapa.isPrimeiraJogada()) {
			int linha = new Random().nextInt(this.tMapa);
			int coluna = new Random().nextInt(this.tMapa);
			// TESTE PARA SABER QUAL FOI O NUMERO ESCOLHIDO
			System.out.println(linha + "," + coluna);
			//
			this.mapa.escolherPosicao(linha, coluna);
			
		}
	}
	
	public void inicio() {
		//IGNORA ISSO:
		/*int linha = new Random().nextInt(this.tMapa);
		int coluna = new Random().nextInt(this.tMapa);
		// TESTE PARA SABER QUAL FOI O NUMERO ESCOLHIDO
		System.out.println(linha + "," + coluna);
		//
		this.mapa.escolherPosicao(linha, coluna);*/
		//AGORA PODE PRESTAR ATENÇÃO:
		
		this.testarInicio();
		this.limparBandeiras();
		
		while(!this.mapa.isGanhouJogo()) {	
			//A IA SO DEVE CONTINUAR A JOGAR ENQUANTO NÃO TIVER TERMINADO O JOGO (CLARO)
			if (this.taTudoBem) {
				this.taTudoBem = false;
				//ZERAMOS TATUDOBEM
				this.varrer();
				//PARA QUE POSSA SE TORNAR TRUE OU NAO DENTRO DO VARRER
			}else {
				if (this.complexa) {
					
					//SÓ TRABALHARÁ COM CASOS ESPECIAIS SE ESTIVER USANDO TUDO DA IA (COMPLEXA = TRUE)
					this.procurarCasosEspeciais();
					if(!this.taTudoBem) {
						System.out.println("Impossível IA prosseguir");
						break;
						/*
						EXEMPLO UMA SITUAÇÃO EM QUE TODAS AS CELULAS VISIVEIS NA BORDA SO TEEM
						UMA BOMBA FALTANDO PARA SER ENCONTRADA
						*/

					}
				}else {
					System.out.println();
					System.out.println("Impossível IA prosseguir");
					break;
					//AQUELA SITUAÇÃO QUE FALEI ANTES (CASO ESTEJAMOS NO NÃO COMPLEXA)
					
				}
				
			}
		}
		
		System.out.println();
		this.mapa.imprimirTela(false);
		

	}
	//////////////// METODOS DE AUXILIO
	
	public int diferenca(int x, int y) {//TIRA A DIFERENÇA EM MODULO
		if (x>y) {
			return x-y;
		}else{
			return y-x;
		}
		/* APENAS PQ EU N TAVA AFIM DE FICAR PENSANDO NA ORDEM CERTA DA SUBTRAÇÃO
		 * (QUAL ELEMENTO É MAIOR OU MENOR PRA N FICAR NEGATIVO) */
	}
	public void imprimirUltimaTecla(int linha, int coluna, String ondeEstas) {
		System.out.println(linha+","+coluna+ " "+ ondeEstas);
		/*
		ESTAVAMOS USANDO COMO FORMA DE "DEBUGAR". POIS, COMO A IA TRABALHA MT RAPIDO, FICAVA DIFICL
		DE SABER ONDE ELA ESTAVA ERRANDO. DEBUGAR USANDO A FERRAMENTA CORRETA SERIA MT LENTO
		*/
	}
	///////////////  FIM DE METODOS DE AUXILIO
	
	/////////////// METODOS DA IA NUM TODO (COMPLEXA OU NAO)
	public void varrer() {
		int i = 0, j = 0;
		for (i = 0; i < this.tMapa; i++) {
			for (j = 0; j < this.tMapa; j++) {
				int bombasVizinhas = this.mapa.getCampo()[i][j].getQtdBombasVizinhas();
				int casasProxNaoVisivel = 0;
				int bandeiraPerto = 0;
				//EXPLICO MELHOR  MAIS TARDE
				
				if (this.mapa.getCampo()[i][j].isVisivel() && this.mapa.getCampo()[i][j].getQtdBombasVizinhas() != 0) {
					// CASA VISIVEL ACHADA (IGNORANDO CASAS 0)
					
					ArrayList<Celula> vizinhasNaoVisiveis = new  ArrayList<Celula>();
					//EXPLICO MELHOR  MAIS TARDE
					for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
						if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {
							if(this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()) {
								bandeiraPerto++;
								/*AO OLHAR AS CASAS VIZINHAS DA "CELULA VISIVEL DIFERENTE DE 0 NO MAPA",
								 SE ENCONTRARMOS BOMBAS AO REDOR, SALVA QUANTAS ACHAMOS*/
							}else {
								vizinhasNaoVisiveis.add(this.mapa.getCampo()[i][j].getVizinhas().get(k));
								casasProxNaoVisivel++;//NAO VISIVEL E NAO BANDEIRA
								
								/*SE A CELULA VIZINHA NÃO FOR VISIVEL E JÁ NÃO TIVER UMA BANDEIRA, GUARDA ELA 
								 * NA LISTA "VIZINHASPROXNAOVISIVEIS",/TALVEZ POSSAMOS COLOCAR BANDEIRA AÍ DEPOIS */
							}
						}

					}
					if (this.diferenca(bombasVizinhas, bandeiraPerto) == casasProxNaoVisivel) {
						
						for (int k = 0; k < vizinhasNaoVisiveis.size(); k++) {
							vizinhasNaoVisiveis.get(k).setBandeira(true);
							this.mapa.getCampo()[i][j].getBandeirasVisinhas().add(vizinhasNaoVisiveis.get(k));
							
							/*CASO O NUMERO DE CASAS PROX NÃO VISIVEIS SEJA IGUAL AO NUMERO DE BOMBAS VIZINHAS MENOS 
							 * NUMERO DE BANDEIRAS (OU SEJA, QUANTAS BOMBAS FALTAM), QUER DIZER QUE SÃO BOMBAS*/
							this.taTudoBem = true;
							
							/*ALGO FOI REALIZADO. ENTÃO N VAI OCORRER DE OLHAR TODA A MATRIZ E FAZER NADA,
							CONSEQUENTIMENTE N VAI OCORRER DE GERAR LOOP ETERNO
							(ESSE MESMO COMENTARIO SE APLICA AOS FUTUROS TATUDOBEM)*/
						}	
					}//FIM DA PARTE 1
					else {
						this.limpar(i, j);//INICIO DA PARTE 2
					}

				} 

			}
			
		}

	}//FIM METODO
	
	public void limpar(int i, int j) {
		int bandeiraExistente = 0;
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			if (this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()) {
				bandeiraExistente++;//QUANTAS BANDEIRAS VIZINHAS FORAM ENCONTRADAS
			}
		}
		if (bandeiraExistente==this.mapa.getCampo()[i][j].getQtdBombasVizinhas()) {
			//SE A PARTE 2 FOR APLICAVEL... (JA ACHAMOS AS BOMBAS, OU SEJA, JA APLICAMOS AS BANDEIRAS CORRETAMENTE)
			for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
				if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()&&!this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {
					int linha = this.mapa.getCampo()[i][j].getVizinhas().get(k).getLinha();
					int coluna = this.mapa.getCampo()[i][j].getVizinhas().get(k).getColuna();
					this.imprimirUltimaTecla(linha, coluna, "limpar");// AQUELE "DEBUGAR" Q FALEI
					this.mapa.escolherPosicao(linha, coluna);
					
					this.taTudoBem = true; // FEZ ALGO 
					//PROCURAMOS AS CASAS VIZINHAS QUE AINDA N FORAM ABERTAS E CHAMAMOS O "ESCOLHERPOSICAO" PRA ABRIR ELAS
				}
			}
			
		}//PARTE 2, FEITO
		else if (this.complexa){
			this.qualificacao(i, j);
			/*
			CASO NÃO SE APLICASSE A NENHUM DOS CASOS ANTERIORES (PARTE 1 E 2), DARÍAMOS INICIO A PARTE 3.
			TECNICAMENTE N É BEM O INICIO. O METODO QUALIFICACAO SERVE COMO FORMA DE PREPARA A CELULA FUTURAMENTE
			PARA UMA POSSIVEL APLICAÇÃO DA PARTE 3 E SEUS CASOS ESPECIAIS. 
			QUALIFICAMOS AS CELULAS EM 2 TIPOS DE CASOS ESPECIAIS. ESPERA Q EU EXPLICO MELHOR
			*/
		}
	}
	
	
//////////////// A PARTIR DAQUI SÃO APENAS METODOS UTILIZADOS NA IA COMPLEXA

/*
 LEGENDA:
 	N = N SEI. N TA VIZIVEL
   	A = ABERTO
  	B = BOMBA
  	
  	
 VAMO LA. APLICAMOS DOIS TIPOS DE CASOS ESPECIAIS (TEM MAIS MAS SO FIZEMOS ESSES DOIS)
	 O CASO 1 SERIA:
	 	.DUAS CELULAS COM APENAS TREZ CASAS NAO VISIVEIS VIZINHAS
	 	.ESSAS CASAS TERIAM APENAS DUAS DESSAS CASAS EM COMUM
	 	.UMA DAS CELULAS FALTA 1 BOMBA PRA ACHAR
	 	.A OUTRA FALTA 2 BOMBAS PRA ACHAR
	 	.NO FIM FICA 4 CELULAS NÃO VIZIVEIS ALINHADAS
	 EXEMPLO:
	 	1 2
	  N N N N
	  
 PODEMOS CONCLUIR Q O 1 "N" NÃO TEM BOMBA. E QUE O ULTIMO "N" TEM.
 
	 	1 2
	  A N N B
  
	 O CASO 2.1 SERIA:
	 	.UMA CELULA COM APENAS TREZ CASAS NAO VISIVEIS VIZINHAS
	 	.OUTRA CELULA COM APENAS DUAS CASAS NAO VISIVEIS VIZINHAS
	 	.ESSAS CASAS TERIAM APENAS DUAS DESSAS CASAS EM COMUM
	 	.AS DUAS CELULAS TEM A MESMA QUANTIDADE DE BAMBAS FANTANDO PRA ACHAR
	 	.NO FIM FICA 3 CELULAS NÃO VIZIVEIS ALINHADAS
	 EXEMPLO:
	 	1 1
	 	N N N
	 	
 PODEMOS CONCLUIR Q O ULTIMO "N" NÃO TEM BOMBA.
 		1 1
	 	N N A
	 	
	 O CASO 2.2 SERIA A MESMA COISA Q O 2.1. SO QUE A QUANTIDADE DE BOMBAS FICA DIFEFENTE
	 
	 	1 2
	 	N N N
	 	
PODEMOS CONCLUIR Q O ULTIMO "N" TEM BOMBA.

 		1 2
	 	N N B
 
 */
	
	public void qualificacao(int i, int j) {
		ArrayList<Celula> tresCasasNaoVisiveis = this.mapa.getCampo()[i][j].getVizinhasQualificadas();
		tresCasasNaoVisiveis.clear();
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {
				tresCasasNaoVisiveis.add(this.mapa.getCampo()[i][j].getVizinhas().get(k));
			}
		}
		if (tresCasasNaoVisiveis.size()==3) { //APENAS 3 CASAS VIZINHAS NAO VIZIVEIS
			
			if(tresCasasNaoVisiveis.get(0).getLinha() == tresCasasNaoVisiveis.get(1).getLinha() && tresCasasNaoVisiveis.get(1).getLinha() == tresCasasNaoVisiveis.get(2).getLinha()) {
				//PRA SABER SE ESTÃO NO MESMO EIXO (X OU Y)
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(1);// CASO 1 (3 CELULAS)
				//JOptionPane.showMessageDialog(null, "qualificada linha");
			}
			else if(tresCasasNaoVisiveis.get(0).getColuna() == tresCasasNaoVisiveis.get(1).getColuna() && tresCasasNaoVisiveis.get(1).getColuna() == tresCasasNaoVisiveis.get(2).getColuna()){
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(1);// CASO 1 (3 CELULAS)
				//JOptionPane.showMessageDialog(null, "qualificada coluna");
			}
			
		}else if (tresCasasNaoVisiveis.size()==2) { //APENAS 2 CASAS VIZINHAS NAO VIZIVEIS
			
			if(tresCasasNaoVisiveis.get(0).getLinha() == tresCasasNaoVisiveis.get(1).getLinha()) {
				//TAMBEM PRA SABER SE ESTÃO NO MESMO EIXO (X OU Y)
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(2);// CASO 2 (2 CELULAS)
				
			}else if(tresCasasNaoVisiveis.get(0).getColuna() == tresCasasNaoVisiveis.get(1).getColuna()){
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(2);// CASO 2 (2 CELULAS)
			}
		}
			/*PODE SIM OCORRER DE UMA CELULA SER QUALIFICADA E DPOIS DE UM TEMPO ELA DEIXAR DE SER,
			 * CASO, AO RODAR O MAPA, DESCOBRIR ALGUMA BOMBA OU ALGO DO TIPO.
			 * FIZEMOS ESSE METODO DE QUALIFICAÇÃO PARA UMA ESPECIE DE "MATCH" ENTRE AS CELULAS*/
		
	}
	
	
	
	public void procurarCasosEspeciais() {
		//SE RODAR O MAPA TODO E NAO FIZER NADA (TATUDOBEM VIER FALSO), APELAMOS PARA OS CASOS ESPECIAIS.
		for (int i = 0; i < this.tMapa; i++) {
			for (int j = 0; j < this.tMapa; j++) {
				if (this.mapa.getCampo()[i][j].isQualificada()) {
					if (this.mapa.getCampo()[i][j].getCasoQualifiacao()==1) {
						this.verificarCaso1(i, j);
						
					}else if (this.mapa.getCampo()[i][j].getCasoQualifiacao()==2) {
						this.verificarCaso2(i, j);
					}
				}
			}
		}
		/*
		 ESSE METODO SERVE APENAS PARA PROCURAR CELULAS QUALIFICADAS VIZINHAS 
		 E REDIRECIONAR PARA VERIFICAR SE É REALMENTE UM CASO ESPECIAL (SEJA O 1 OU O 2).
		 SE FOR REAL, DENTRO DO PRÓPIO METODO VERIFICAR, SERA REDIRECIONADO A APLICAÇÃO DAQUELE METODO.
		 */
	}
	
	
	public void verificarCaso1(int i, int j) {
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			int bombasFaltandoA = this.diferenca(this.mapa.getCampo()[i][j].getQtdBombasVizinhas(), this.mapa.getCampo()[i][j].getBandeirasVisinhas().size());
			int bombasFaltandoB = this.diferenca(this.mapa.getCampo()[i][j].getVizinhas().get(k).getQtdBombasVizinhas(), this.mapa.getCampo()[i][j].getVizinhas().get(k).getBandeirasVisinhas().size());
			//AS DUAS INT SAO A DIFERENÇA DA QUANTIDADE DE BOMBAS VIZINHAS COM Q QUANTIDADE DE BANDEIRAS VIZINHAS. 
			//OU SEJA, QUANTAS BOMBAS FALTAM
			
			if (this.mapa.getCampo()[i][j].getVizinhas().get(k).getCasoQualifiacao()==1 && bombasFaltandoA != bombasFaltandoB) {
				//SE A CELULA VIZINHA TAMBEM FOR DO TIPO 1 E NÃO TIVER A MESMA QUANTIDADE DE BOMBAS FALTANDO, QUER DIZER QUE ENCONTRAMOS UM CASO ESPECIAL 1 
				Celula a = this.mapa.getCampo()[i][j];
				Celula b = this.mapa.getCampo()[i][j].getVizinhas().get(k);
				if (a.getVizinhasQualificadas().get(1) == b.getVizinhasQualificadas().get(0) || a.getVizinhasQualificadas().get(0) == b.getVizinhasQualificadas().get(1))
				this.casoEspecial1(a,b);
			}
		}
	}
	
	public void verificarCaso2(int i, int j) {
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			int bombasFaltandoA = this.diferenca(this.mapa.getCampo()[i][j].getQtdBombasVizinhas(), this.mapa.getCampo()[i][j].getBandeirasVisinhas().size());
			int bombasFaltandoB = this.diferenca(this.mapa.getCampo()[i][j].getVizinhas().get(k).getQtdBombasVizinhas(), this.mapa.getCampo()[i][j].getVizinhas().get(k).getBandeirasVisinhas().size());
			// A MESMA DIFERENCA Q DESCREVI NO VERIFICAR CASO 1
			if (this.mapa.getCampo()[i][j].getVizinhas().get(k).getCasoQualifiacao()==1) {
				//SE A CELULA VIZINHA FOR TIPO 1, ENCONTRAMOS UM CASO 2
				//(ONDE ESSE METODO É CHAMADO JA FOI CERTIFICADO QUE A OUTRA CELULA QUALIFICADA É TIPO 2)
				Celula a = this.mapa.getCampo()[i][j];
				Celula b = this.mapa.getCampo()[i][j].getVizinhas().get(k);
				if (a.getVizinhasQualificadas().get(1) == b.getVizinhasQualificadas().get(0)) {
					if (bombasFaltandoA==bombasFaltandoB) {
						this.casoEspecial2(a, b, true); // CASO 2.1
					}else {
						this.casoEspecial2(a, b, false); // CASO 2.2
					}
				}
			}
		}
	}
	
	public void casoEspecial1(Celula a, Celula b ) {
		//JOptionPane.showMessageDialog(null, a.getLinha()+ ","+ a.getColuna()+ "caso 1");
		Celula maisBomba = a; // TEM MAIS BOMBA FALTANDO
		Celula menosBomba = b; // TEM MENOS BOMBA FALTANDO
		if (this.diferenca(b.getQtdBombasVizinhas(), b.getBandeirasVisinhas().size())>this.diferenca(a.getQtdBombasVizinhas(), a.getBandeirasVisinhas().size())) {
			// SE VIER INVERTIDO, RESOLVEMOS AQUI
			maisBomba = b;
			menosBomba = a;
		}
		if (maisBomba.getVizinhasQualificadas().get(0)!= menosBomba.getVizinhasQualificadas().get(1)) {
			maisBomba.getVizinhasQualificadas().get(0).setBandeira(true);
			int linha = menosBomba.getVizinhasQualificadas().get(2).getLinha();
			int coluna = menosBomba.getVizinhasQualificadas().get(2).getColuna();
			this.imprimirUltimaTecla(linha, coluna, "caso1-1");
			this.mapa.escolherPosicao(linha, coluna);
			
			//ABRE A CASA Q SABEMOS Q N TEM BOMBA 
		}else {
			maisBomba.getVizinhasQualificadas().get(2).setBandeira(true);
			int linha = menosBomba.getVizinhasQualificadas().get(0).getLinha();
			int coluna = menosBomba.getVizinhasQualificadas().get(0).getColuna();
			this.imprimirUltimaTecla(linha, coluna, "caso 1-2");
			this.mapa.escolherPosicao(linha, coluna);
			//ABRE A CASA Q SABEMOS Q N TEM BOMBA 
		}
		/*ESSES IF TODOS SAO SO PARA SABER SE A CELULA COM MAIS BOMBA TA NA DIREITA OU ESQUERDA || CIMA OU BAIXO
		 * EM RELAÇÃO A CELULA COM MENOS BOMBA*/
		maisBomba.setCasoQualifiacao(0);
		maisBomba.setQualificada(false);
		maisBomba.getVizinhasQualificadas().clear();
		menosBomba.setCasoQualifiacao(0);
		menosBomba.setQualificada(false);
		menosBomba.getVizinhasQualificadas().clear();
		//AGORA ESSA CELULA É APENAS UMA CELULA NORMAL.
		this.taTudoBem = true; // FEZ ALGO
		
	}
	public void casoEspecial2(Celula tipo2, Celula tipo1, boolean bombasfaltandoiguais) {
		//JOptionPane.showMessageDialog(null, tipo2.getLinha()+ ","+ tipo2.getColuna()+ "caso 2");
		//TIPO 2 TEM MENOS CASAS (DUAS)
		//TIPO 1 TEM MAIS CASAS (TRES)
		if (bombasfaltandoiguais) { // O NOME JA DIZ
				// CASO 2.1
			if (tipo1.getVizinhasQualificadas().get(0) == tipo2.getVizinhasQualificadas().get(0)) {
				int linha = tipo1.getVizinhasQualificadas().get(2).getLinha();
				int coluna = tipo1.getVizinhasQualificadas().get(2).getColuna();
				this.imprimirUltimaTecla(linha, coluna, "caso 2 tipo1 direita");
				this.mapa.escolherPosicao(linha, coluna);
			}else {
				int linha = tipo1.getVizinhasQualificadas().get(0).getLinha();
				int coluna = tipo1.getVizinhasQualificadas().get(0).getColuna();
				this.imprimirUltimaTecla(linha, coluna, "caso 2 tipo1 esquerda");
				this.mapa.escolherPosicao(linha, coluna);
			}
		}else {
				//CASO 2.2
			if (tipo1.getVizinhasQualificadas().get(0) == tipo2.getVizinhasQualificadas().get(0)) {
				tipo1.getVizinhasQualificadas().get(2).setBandeira(true);
				
			}else {
				tipo1.getVizinhasQualificadas().get(0).setBandeira(true);
			}
		}
		/*ESSES IF TODOS SAO SO PARA SABER SE A CELULA TIPO 1 TA NA DIREITA OU ESQUERDA || CIMA OU BAIXO
		 * EM RELAÇÃO A CELULA TIPO 2*/
		tipo1.setCasoQualifiacao(0);
		tipo1.setQualificada(false);
		tipo1.getVizinhasQualificadas().clear();
		tipo2.setCasoQualifiacao(0);
		tipo2.setQualificada(false);
		tipo2.getVizinhasQualificadas().clear();
		//AGORA ESSA CELULA É APENAS UMA CELULA NORMAL. 
		this.taTudoBem = true;// FEZ ALGO
		
	}



	public boolean isTaTudoBem() {
		return taTudoBem;
	}
			

}// FIM DA CLASSE

