package br.com.poli.campominado.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Temporizador extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int s, m, tempoTotal;
	private String tempo = "0:0";
	private Timer timer;
	private Fontes fontes;
	
	public Temporizador() {
		fontes = new Fontes();
		setHorizontalAlignment(SwingConstants.CENTER);
		
		//setFont(new Font("Tahoma", Font.BOLD, 5));
		setFont(fontes.getFonteTempo().deriveFont(30f).deriveFont(Font.BOLD));
		setForeground(Color.RED);
		setText(tempo);
		setVisible(true);
		iniciaRelogio();

		
	}
	
	
	public String getTempo(){
		return tempo;
	}
	
	public int getTempoTotal() {
		return tempoTotal;
	}
	
	public String formato() {//FORMATA O TEMPO PARA APARECER NA TELA COMO MIN:SEG
		if(s==60) {
			s=0;
			m++;
		}
		tempo = m+":"+s;
		return tempo;
	}
	
	public void iniciaRelogio() {
		
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {//A CADA CICLO DO TIMER ELE SOMA 1 NOS 'S' E 1 NO TEMPOTOTAL, 'S' É PARA OS SEGUNDOS E APOS 60 SEGUNDOS ELE RESETA PRA 0, TEMPOTOTAL EH USADO PARA ORDENAR RANKING
				tempoTotal++;
				s++;
				setText(formato());
			}
		};
		timer = new Timer(1000, action);//A CADA 1 SEGUNDO ELE FAZ A AÇAO
		timer.setInitialDelay(1000);//SO INICIA O TIMER APOS 1 SEGUNDO NA TELA
		timer.setRepeats(true);//DEIXAR EM UM LOOP
		timer.start();//INICIA O TIMER
	}
	public void paraRelogio() {//PARAR O RELOGIO QND ACABAR O JOGO
		timer.stop();
	}
	
	
	
}
