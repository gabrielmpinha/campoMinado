package br.com.poli.campominado.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class BotaoJogo extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int linha;
	private int coluna;
	public BotaoJogo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));//DEFINIR FONTE, BACKGROUND E DIMENSAO DO BOTAO
		this.setBackground(new Color(41, 140, 74));
		this.setPreferredSize(new Dimension(10, 10));
		

		

	}
	

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

}
