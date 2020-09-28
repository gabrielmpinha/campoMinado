package br.com.poli.campominado;

import br.com.poli.campominado.jogo.CampoMinado;
import br.com.poli.campominado.jogo.Dificuldade;


public class Testes  {

	public static void main(String[] args) {
		
		
		
		
		//TESTE PARTE 4
		
		CampoMinado testeia = new CampoMinado("IA", Dificuldade.FACIL);
		IA ia = new IA(testeia.getMapa(),true);
		ia.inicio();
		
		/*
		//TESTES PARTE 3
		System.out.println("A MATRIZ INICIA NO NUMERO 0");
		CampoMinado campo = new CampoMinado("Gabriel", Dificuldade.FACIL);
		System.out.println("Dificuldade FACIL");
		System.out.println("Jogador(a): " + campo.getJogador().getNome());
		campo.iniciarJogo();
		
		*/
		
		
		/*System.out.println("Dificuldade MEDIO");
		CampoMinado campo2 = new CampoMinado("Pessoa", Dificuldade.MEDIO);
		System.out.println("Jogador(a): " + campo2.getJogador().getNome());
		campo2.iniciarJogo();
		
		System.out.println("Dificuldade DIFICIL");
		CampoMinado campo3 = new CampoMinado("Sofia", Dificuldade.DIFICIL);
		System.out.println("Jogador(a): " + campo3.getJogador().getNome());
		campo3.iniciarJogo();*/
		
		
		
		
		
		//TESTES PARTE 2
		/*System.out.println("Jogador(a): " + campo.getJogador().getNome());
		campo.getMapa().imprimirTela(false);
		System.out.println("Jogador 1 Jogando:");
		campo.getMapa().escolherPosicao(3, 3);
		System.out.println("Teste do Jogador 1:");
		campo.getMapa().imprimirTela(true);

		System.out.println("******************************************************************");

		System.out.println("Dificuldade MEDIO");
		CampoMinado campo2 = new CampoMinado("Pessoa", Dificuldade.MEDIO);
		System.out.println("Jogador(a): " + campo2.getJogador().getNome());
		System.out.println();
		campo2.getMapa().imprimirTela(false);
		System.out.println("Jogador 2 Jogando:");
		campo2.getMapa().escolherPosicao(5, 7);
		System.out.println("Teste do Jogador 2:");
		campo2.getMapa().imprimirTela(true);

		System.out.println("******************************************************************");

		System.out.println("Dificuldade DIFICIL");
		CampoMinado campo3 = new CampoMinado("Sofia", Dificuldade.DIFICIL);
		System.out.println("Jogador(a): " + campo3.getJogador().getNome());
		campo3.getMapa().imprimirTela(false);
		System.out.println("Jogador 3 Jogando:");
		campo3.getMapa().escolherPosicao(0, 12);
		System.out.println("Teste do Jogador 3:");
		campo3.getMapa().imprimirTela(true);*/

	}

}
