package br.com.poli.campominado.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import br.com.poli.campominado.IA;
import br.com.poli.campominado.jogo.Dificuldade;
import br.com.poli.campominado.mapa.Celula;
import br.com.poli.campominado.mapa.Mapa;
import br.com.poli.campominado.mapa.MapaDificil;
import br.com.poli.campominado.mapa.MapaFacil;
import br.com.poli.campominado.mapa.MapaMedio;
import br.com.poli.campominado.ranking.Jogador;
import br.com.poli.campominado.ranking.Ranking;
import br.com.poli.campominado.ranking.RankingDificil;
import br.com.poli.campominado.ranking.RankingFacil;
import br.com.poli.campominado.ranking.RankingMedio;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TelaJogo1 extends JFrame implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numeroBombas;
	private JPanel painelJogo;
	private JPanel painelJogo2;
	private Mapa mapa;
	private Dificuldade dificuldade;
	private BotaoJogo[][] botoes;
	private JButton botaoSair;
	private JLabel[][] lblNumeros;
	private JLabel lblNumeroBombas;
	private TelaMenu menu;
	private Temporizador timer;
	private ImageIcon iconBomba;
	private ImageIcon iconBandeira;
	private ImageIcon iconBotao;
	private ImageIcon iconContador;
	private ImageIcon iconIa;
	private JButton btnIa;
	private JLayeredPane layeredPane;
	private String nomeJogador;
	private Ranking rank;
	private boolean primeiraJogada;
	private IA ia;
	private boolean usouIa;
	
	public TelaJogo1(Dificuldade dificuldade, String nomeJogador, ImageIcon iconTela) {
		this.primeiraJogada = true;
		//this.dificuldade = Dificuldade.FACIL; // USAR ESSE SO PRA PODER MEXER NO
		// DESIGN
		this.nomeJogador = nomeJogador;
		this.dificuldade = dificuldade;
		this.usouIa = false;
		this.setIconImage(iconTela.getImage());
		iniciarJogo(this.dificuldade);
	}

	private void configuraFonteNumeros(JLabel lblNumeros) {//CONFIGURA O TAMANHO DA FONTE DOS NUMEROS

		switch (dificuldade) {
		case FACIL:
			lblNumeros.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
			break;
		case MEDIO:
			lblNumeros.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
			break;
		case DIFICIL:
			lblNumeros.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			break;
		}

	}

	private void configurarNumeros(int i, int j) {//CONFIGURA A COR E O TEXTO DOS NUMEROS
		lblNumeros[i][j].setText(Integer.toString(this.mapa.getCelula(i, j).getQtdBombasVizinhas()));
		switch (this.mapa.getCelula(i, j).getQtdBombasVizinhas()) {
		case 1:
			lblNumeros[i][j].setForeground(Color.BLUE.brighter().brighter());
			break;
		case 2:
			lblNumeros[i][j].setForeground(Color.GREEN.darker());
			break;
		case 3:
			lblNumeros[i][j].setForeground(Color.RED);
			break;
		case 4:
			lblNumeros[i][j].setForeground(Color.BLUE.darker().darker());
			break;
		case 5:
			lblNumeros[i][j].setForeground(Color.RED.darker());
			break;
		case 6:
			lblNumeros[i][j].setForeground(Color.MAGENTA);
			break;
		case 7:
			lblNumeros[i][j].setForeground(Color.YELLOW);
			break;
		case 8:
			lblNumeros[i][j].setForeground(Color.CYAN);
			break;
		}
	}

	private void configurarIcones() {//CONFIGURA TODOS OS ICONES DA TELA
		switch (dificuldade) {
		case FACIL:
			iconBomba = new ImageIcon(getClass().getResource("/images/iconBombaFacil.png"));
			iconBandeira = new ImageIcon(getClass().getResource("/images/iconBandeiraFacil.png"));
			iconBotao = new ImageIcon(getClass().getResource("/images/iconBotaoFacil.png"));
			break;
		case MEDIO:
			iconBomba = new ImageIcon(getClass().getResource("/images/iconBombaMedio.png"));
			iconBandeira = new ImageIcon(getClass().getResource("/images/iconBandeiraMedio.png"));
			iconBotao = new ImageIcon(getClass().getResource("/images/iconBotaoMedio.png"));
			break;
		case DIFICIL:
			iconBomba = new ImageIcon(getClass().getResource("/images/iconBombaDificil.png"));
			iconBandeira = new ImageIcon(getClass().getResource("/images/iconBandeiraDificil.png"));
			iconBotao = new ImageIcon(getClass().getResource("/images/iconBotaoDificil.png"));
			break;
		}
		iconContador = new ImageIcon(getClass().getResource("/images/iconBombaMedio.png"));
		iconIa = new ImageIcon(getClass().getResource("/images/iconIa.png"));
	}

	
	private void montarLabelNumeros() {//MONTA A LABEL QUE MOSTRA A QTDD DE BOMBAS VIZINHAS

		lblNumeros = new JLabel[this.dificuldade.getValor()][this.dificuldade.getValor()];

		Border borda = BorderFactory.createLineBorder(Color.BLACK, 2);

		for (int i = 0; i < lblNumeros.length; i++) {
			for (int j = 0; j < lblNumeros.length; j++) {
				lblNumeros[i][j] = new JLabel("", SwingConstants.CENTER);
				lblNumeros[i][j].setVisible(true);

				configuraFonteNumeros(lblNumeros[i][j]);//CHAMA PRA CONFIGURAR A FONTE BASEADO NA DIFICULDADE

				this.painelJogo2.add(lblNumeros[i][j]);//ADICIONA NO PAINEL
				if (this.mapa.getCelula(i, j).isBomba() == false) {//
					if (this.mapa.getCelula(i, j).isEmBranco() == false) {//SE NAO FOR BOMBA NEM EM BRANCO CONFIGURA OS NUMEROS DAQUELE LABEL
						configurarNumeros(i, j);
					} 
				} else {//SE FOR BOMBA, DEIXA OPACO, DEIXA O BACKGROUND VERMELHO E COLOCA O ICONE
					lblNumeros[i][j].setOpaque(true);
					lblNumeros[i][j].setBackground(Color.RED);
					lblNumeros[i][j].setIcon(iconBomba);
				}
				lblNumeros[i][j].setBorder(borda);//COLOCA UMA BORDA NO LABEL
				lblNumeros[i][j].setVisible(false);//DEIXA INVISIVEL, SO FICA VISIVEL QND O BOTAO DA MESMA POSICAO EH ABERTO
			}
		}
	}

	private void montarTela() {

		this.setTitle("Campo Minado");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		// this.definirTamanhoTela();
		this.setBounds(0, 0, 760, 700);
		this.configurarIcones();
		this.montarLayeredPane();
		this.montarPainelSuperior();
		this.montarPainelJogo(this.dificuldade);
		this.montarBotoesJogo();
		//lblNumeros = new JLabel[this.dificuldade.getValor()][this.dificuldade.getValor()];
		//this.montarLabelNumeros();
		this.atualizarNumeroBombas();
		pack();
		this.setLocationRelativeTo(null);

	}

	private void montarLayeredPane() {//MONTA O LAYEREDPANE, QUE CONTEM OUTROS PANELS
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane, BorderLayout.NORTH);
		layeredPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

		layeredPane.setOpaque(true);
		layeredPane.setBackground(Color.WHITE.darker());
	}

	public void montarPainelSuperior() {//MONTA O PAINEL SUPERIOR QUE CONTEM TIMER, CONTADOR DE BOMBAS, BOTAO DA IA E BOTAO DE SAIR
		timer = new Temporizador();//INICIA O TEMPORIZADOR
		JPanel painelSuperior = new JPanel();
		
		painelSuperior.setBounds(12, 11, 738, 55);
		painelSuperior.setOpaque(false);
		layeredPane.add(painelSuperior);
		botaoSair = new JButton("MENU");
		botaoSair.setFont(new Font("Tahoma", Font.BOLD, 11));
		botaoSair.addActionListener(this);
		lblNumeroBombas = new JLabel();
		lblNumeroBombas.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumeroBombas.setFont(timer.getFont());
		lblNumeroBombas.setForeground(Color.ORANGE);
		lblNumeroBombas.setIcon(iconContador);
		
		btnIa = new JButton("AJUDA");
		btnIa.setIcon(iconIa);
		btnIa.setOpaque(false);
		btnIa.setContentAreaFilled(false);
		btnIa.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
		btnIa.addActionListener(this);

		GroupLayout gl_painelSuperior = new GroupLayout(painelSuperior);
		gl_painelSuperior.setHorizontalGroup(
			gl_painelSuperior.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelSuperior.createSequentialGroup()
					.addContainerGap()
					.addComponent(botaoSair)
					.addGap(35)
					.addComponent(btnIa, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
					.addComponent(timer, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addGap(168)
					.addComponent(lblNumeroBombas, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addGap(62))
		);
		gl_painelSuperior.setVerticalGroup(
			gl_painelSuperior.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelSuperior.createSequentialGroup()
					.addGap(4)
					.addComponent(timer, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(gl_painelSuperior.createSequentialGroup()
					.addGap(5)
					.addComponent(botaoSair, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addGap(8))
				.addGroup(gl_painelSuperior.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNumeroBombas, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_painelSuperior.createSequentialGroup()
					.addGap(9)
					.addComponent(btnIa, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		painelSuperior.setLayout(gl_painelSuperior);
	}

	private void montarPainelJogo(Dificuldade dificuldade) {//MONTA O PAINEL QUE CONTEM OS BOTOES E O PAINEL QUE CONTEM LABELS COM NUMEROS

		// PAINELJOGO
		this.painelJogo = new JPanel(new GridLayout(dificuldade.getValor(), dificuldade.getValor()));
		painelJogo.setBounds(12, 77, 738, 612);
		painelJogo.setOpaque(false);
		layeredPane.add(painelJogo);

		// PAINEL DOS NUMEROS DE CASAS
		this.painelJogo2 = new JPanel(new GridLayout(dificuldade.getValor(), dificuldade.getValor()));
		painelJogo2.setBounds(12, 77, 738, 612);
		painelJogo2.setOpaque(false);
		layeredPane.add(painelJogo2);

	}

	private void montarBotoesJogo() {//MONTA OS BOTOES DO CAMPO MINADO

		botoes = new BotaoJogo[dificuldade.getValor()][dificuldade.getValor()];

		for (int i = 0; i < botoes.length; i++) {
			for (int j = 0; j < botoes.length; j++) {
				botoes[i][j] = new BotaoJogo(i, j);
				botoes[i][j].setDisabledIcon(iconBandeira);
				botoes[i][j].addActionListener(this);
				botoes[i][j].addMouseListener(this);
				botoes[i][j].setIcon(iconBotao);
				this.painelJogo.add(botoes[i][j]);

			}
		}

	}

	private void iniciarJogo(Dificuldade dificuldade) {//INICIALIZA O MAPA BASEADO NA DIFICULDADE

		switch (dificuldade) {
		case FACIL:
			this.mapa = new MapaFacil();
			break;
		case MEDIO:
			this.mapa = new MapaMedio();
			break;
		case DIFICIL:
			this.mapa = new MapaDificil();
			break;
		}
		this.numeroBombas = mapa.getBombas();//SALVA O NUMERO DE BOMBAS
		this.montarTela();//MONTA A TELA

	}

	private void atualizarNumeroBombas() {//ATUALIZA O NUMERO DE BOMBAS DO CONTADOR
		lblNumeroBombas.setText(Integer.toString(this.numeroBombas));
	}


	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		

	}

	private void atualizarBandeirasIa() {//ATUALIZA AS BANDEIRAS QUANDO A IA RODA, APOS A IA JOGAR TUDO ELE OLHA QUAIS CELULAS SAO BANDEIRAS E COLOCA O ICONE NELA E MUDA O VALOR DO NUMEROBOMBAS
		numeroBombas = mapa.getBombas();
		for(int i = 0; i < this.mapa.getCampo().length; i++) {
			for(int j = 0; j < this.mapa.getCampo().length; j++) {
				if(celulaEscolhida(botoes[i][j]).isBandeira()) {
					botoes[i][j].setEnabled(false);
					numeroBombas--;
				}
				else {
					botoes[i][j].setEnabled(true);
				}
			}
		}
		System.out.println(numeroBombas);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {// COLOCA E TIRA A BANDEIRA
		

		BotaoJogo botao = (BotaoJogo) e.getSource();

		if (SwingUtilities.isRightMouseButton(e)) {//SE FOR O BOTAO DIREITO DO MOUSE

			if (celulaEscolhida(botao).isBandeira() == false) {//SE NAO FOR UMA BANDEIRA
				if (this.numeroBombas > 0) {//CHECA SE PODE COLOCAR BANDEIRA
					celulaEscolhida(botao).setBandeira(true);//COLOCA BANDEIRA E IMPEDE DE ESCOLHER O BOTAO E SUBTRAI DO NUMEROBOMBAS
					botao.setEnabled(false);
					this.numeroBombas--;
				}

			} else {//O CONTRARIO DO DE CIMA
				celulaEscolhida(botao).setBandeira(false);
				botao.setEnabled(true);
				this.numeroBombas++;
			}
			
			this.atualizarNumeroBombas();//ATUALIZA O LABEL QUE TEM O CONTADOR DE BOMBAS
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof BotaoJogo) {
			botaoJogoActionPerformed(e);
		} else if (e.getSource() == botaoSair) {
			this.voltarMenu();
		}else if(e.getSource() == btnIa) 
			this.jogarIa();

	}
	
	private void jogarIa() {//FAZ A IA JOGAR O JOGO ATE O POSSIVEL
		ia = new IA(this.mapa,false);
		ia.inicio();
		this.usouIa = true;
		atualizarBandeirasIa();//ATUALIZA AS BANDEIRAS PARA DEPOIS QUE ELA JOGOU
		atualizarNumeroBombas();//ATUALIZA O NUMERO DE BOMBAS PARA DPS QUE ELA JOGOU
		atualizarTela();
		if(ia.isTaTudoBem() == false)
			JOptionPane.showMessageDialog(this, "IMPOSSIVEL PROSSEGUIR", "IMPOSSIVEL ENCONTRAR CASAS CONCLUSIVAS", JOptionPane.INFORMATION_MESSAGE);
	}

	private void voltarMenu() {//FECHA O JOGO E VOLTA PRO MENU
		menu = new TelaMenu();
		menu.setVisible(true);
		this.dispose();
	}

	private void esconderBotao() {//ESCONDE OS BOTOES APOS ABRIR AS CELULAS QUANDO ESCOLHE A POSICAO
		for (int i = 0; i < dificuldade.getValor(); i++) {// OLHAM TODOS OS BOTOES
			for (int j = 0; j < dificuldade.getValor(); j++) {
				if (celulaEscolhida(botoes[i][j]).isVisivel() && botoes[i][j].isVisible()) {// SE A CELULA FOR VISIVEL E
																							// O BOTAO FOR VISIVEL,
																							// ESCONDE O BOTAO
					botoes[i][j].setVisible(false);//DEIXA BOTAO INVISIVEL
					lblNumeros[i][j].setVisible(true);//DEIXA O LABEL ATRAS DO BOTAO VISIVEL
				}
			}
		}
	}

	private void iniciarRanking() {//INICIA O RANKING NA DIFICULDADE SELECIONADA
		if(!usouIa) {
			switch (this.dificuldade) {
			case FACIL:
				rank = new RankingFacil();
				break;
			case MEDIO:
				rank = new RankingMedio();
				break;
			case DIFICIL:
				rank = new RankingDificil();
				break;
			}
			
			rank.iniciarRanking(new Jogador(this.nomeJogador, timer.getTempo(), timer.getTempoTotal()));//MANDA O NOME DO JOGADOR, O TEMPO FORMATADO E O TEMPO TOTAL PRO RANKING
		}
	}

	private void acabarJogo() {//VERIFICA SE O JOGO ACABOU(GANHOU OU VENCEU)
		if (mapa.isFimDeJogo() || mapa.isGanhouJogo()) {

			this.timer.paraRelogio();

			if (mapa.isFimDeJogo()) {//SE PERDEU
				JOptionPane.showMessageDialog(this, "VOCÊ PERDEU", "FIM DE JOGO", JOptionPane.INFORMATION_MESSAGE);
			} else {//SE GANHOU
				JOptionPane.showMessageDialog(this,
						"PARABÉNS " + this.nomeJogador + "! " + "TODAS AS BOMBAS FORAM ENCONTRADAS", "VOCÊ GANHOU",
						JOptionPane.INFORMATION_MESSAGE);
				iniciarRanking();//SE VENCEU MANDA O JOGADOR PRO RANKING
			}
			this.voltarMenu();//VOLTA PRO MENU

		}
	}

	
	private void atualizarTela() {//ATUALIZA A TELA A CADA JOGADA(OU APOS IA JOGAR)
		if(this.primeiraJogada == true) {//SE FOR A PRIMEIRA JOGADA ELE MONTA O LABEL DOS NUMEROS APOS ESCOLHER A POSICAO PELA PRIMEIRA VEZ
			this.primeiraJogada = false;
			this.montarLabelNumeros();
		}

		this.esconderBotao();
		
		this.acabarJogo();
	}
	
	private void botaoJogoActionPerformed(ActionEvent e) {
		BotaoJogo botao = (BotaoJogo) e.getSource();//PEGA O BOTAO ESCOLHIDO
		System.out.println(botao.getLinha() + " " + botao.getColuna());
		
		mapa.escolherPosicao(botao.getLinha(), botao.getColuna()); // ESCOLHE A POSICAO DO BOTAO APERTADO
		botao = null;
		atualizarTela();//ATUALIZA A TELA

	}

	private Celula celulaEscolhida(BotaoJogo botao) { // RETORNA A CELULA DA MESMA POSICAO DO BOTAO
		return mapa.getCelula(botao.getLinha(), botao.getColuna());
	}

	public boolean isUsouIa() {
		return usouIa;
	}
	
}
