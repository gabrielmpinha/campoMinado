package br.com.poli.campominado.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class Fontes {//CLASSE USADA PARA FACILITAR NA UTILIZACAO DE FONTES PERSONALIZADAS, QUANDO É INSTANCIADA JA CRIA TODAS AS FONTES QUE TEM

	private Font fontePokemon;
	private Font fonteTempo;
	
	public Fontes() {
		criarFontePokemon();
		criarFonteTempo();
	}
	
	private void criarFontePokemon() {
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/Pokemon Solid.ttf");
			fontePokemon = Font.createFont(Font.TRUETYPE_FONT, is)
					.deriveFont(54f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(fontePokemon);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}

	
	public void criarFonteTempo() {
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/LCD.ttf");
			fonteTempo = Font.createFont(Font.TRUETYPE_FONT, is)
					.deriveFont(54f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(fonteTempo);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	public Font getFonteTempo() {
		return fonteTempo;
	}

	public Font getFontePokemon() {
		return fontePokemon;
	}
	
}
