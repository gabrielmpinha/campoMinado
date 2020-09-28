package br.com.poli.campominado.ranking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Ranking {//DEVE SER INSTANCIADO POR RANKINGFACIL/MEDIO/DIFICIL

	private Jogador novoJogador;
	private List<Jogador> listaJogadores;
	private File rank;

	public Ranking(File rank) {
		this.rank = rank;
		this.listaJogadores = new ArrayList<Jogador>();
	}
	
	
	public void iniciarRanking(Jogador novoJogador) {//METODO CHAMADO QUANDO UM JOGADOR GANHAR UMA PARTIDA
		//this.lerArquivo();
		this.novoJogador = novoJogador;
		this.adicionarNaLista();
		this.escreverNoArquivo();
		System.out.println(listaJogadores.toString());
	}
	
	private void adicionarNaLista() {//LE O ARQUIVO PARA PEGAR A LISTA SALVA NELE, ADICIONA O NOVOJOGADOR NELA, ORDENA A LISTA E COMPARA OS ABAIXO DO 5 LUGAR
		this.lerArquivo();
		listaJogadores.add(novoJogador);
		Collections.sort(listaJogadores, new CompararJogadores());
		
		if(listaJogadores.size() > 5) {//SE TIVER MAIS DO QUE 5 ELEMENTOS NA LISTA ORDENADA, ELE EXCLUI OS ULTIMOS
			for(int i = 5; i<listaJogadores.size();i++) {
				listaJogadores.remove(i);
			}
		}
		
	}

	private void escreverNoArquivo() {//ESCREVE O OBJETO LISTAJOGADORES NO ARQUIVO

		try {
			
			FileOutputStream fileOutput = new FileOutputStream(rank);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(listaJogadores);
			objectOutput.close();
			System.out.println("O Jogador foi salvo no arquivo");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String escreverNomes() {//ESCREVE OS NOMES NUMA STRING USANDO HTML PARA FORMATAÇAO
		String nomeJogadores = "<html>";
		for(int i = 0; i < listaJogadores.size(); i++) {
			nomeJogadores = nomeJogadores + listaJogadores.get(i).getNome() + "<br>";
		}
		nomeJogadores = nomeJogadores + "</html>";
		
		return nomeJogadores;
	}
	
	public String escreverTempos() {//ESCREVE OS TEMPOS NUMA STRING USANDO HTML PARA FORMATAÇAO
		String tempoJogadores = "<html>";
		for(int i = 0; i < listaJogadores.size(); i++) {
			tempoJogadores = tempoJogadores + listaJogadores.get(i).getTempo() + "<br>";
		}
		tempoJogadores = tempoJogadores + "</html>";
		
		return tempoJogadores;
	}
	
	
	@SuppressWarnings("unchecked")
	public void lerArquivo() {//LE O ARQUIVO E ATRIBUI A LISTA SALVA NELA À LISTAJOGADORES DA CLASSE, PARA ESCREVER NOVOS JOGADORES NELA OU COLOCAR SEU CONTEUDO EM STRING
		try {
			FileInputStream fileInput = new FileInputStream(rank);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			listaJogadores = (ArrayList<Jogador>) objectInput.readObject();
			objectInput.close();
			System.out.println(escreverNomes());
			System.out.println(escreverTempos());

		}catch(FileNotFoundException f) { //SE NAO TIVER ARQUIVO CRIADO ELE SAI DO MÉTODO
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
