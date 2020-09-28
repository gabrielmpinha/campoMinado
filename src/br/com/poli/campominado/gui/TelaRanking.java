package br.com.poli.campominado.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.poli.campominado.ranking.Ranking;
import br.com.poli.campominado.ranking.RankingDificil;
import br.com.poli.campominado.ranking.RankingFacil;
import br.com.poli.campominado.ranking.RankingMedio;

public class TelaRanking extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Ranking ranking;
	private JLabel lblNomes;
	private JLabel lblTempos;
	private JButton btnDificil;
	private JButton btnMedio;
	private JButton btnFacil;
	private Fontes fontes;
	private JButton btnSair;
	private TelaMenu menu;
	private ImageIcon fundo;
	private JLabel lblJogador;
	private JLabel lblTempo;
	
	public TelaRanking(ImageIcon iconTela) {
		
		fontes = new Fontes();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 760, 700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Campo Minado");
		this.setIconImage(iconTela.getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		fundo =  new ImageIcon(getClass().getResource("/images/fundoRank.png"));
		configurarBotoes();
		configurarLabels();
		
	}
	
	private void configurarLabels() {//CONFIGURA OS LABELS DA TELA
		lblNomes = new JLabel("");
		lblNomes.setVerticalAlignment(SwingConstants.TOP);
		lblNomes.setFont(fontes.getFontePokemon().deriveFont(30f).deriveFont(Font.BOLD));
		lblNomes.setForeground(Color.BLUE.darker());
		lblNomes.setBounds(89, 236, 277, 424);
		contentPane.add(lblNomes);

		lblTempos = new JLabel("");
		lblTempos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTempos.setVerticalAlignment(SwingConstants.TOP);
		lblTempos.setFont(fontes.getFontePokemon().deriveFont(30f).deriveFont(Font.BOLD));
		lblTempos.setForeground(Color.RED);
		lblTempos.setBounds(424, 236, 230, 424);
		contentPane.add(lblTempos);
		
		lblJogador = new JLabel("Jogador:");
		lblJogador.setBounds(89, 100, 230, 59);
		lblJogador.setFont(fontes.getFontePokemon().deriveFont(35f));
		contentPane.add(lblJogador);
		
		lblTempo = new JLabel("Tempo:");
		lblTempo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTempo.setBounds(424, 100, 230, 59);
		lblTempo.setFont(fontes.getFontePokemon().deriveFont(35f));
		contentPane.add(lblTempo);
		
		JLabel lblFundo = new JLabel("");
		lblFundo.setIcon(fundo);
		lblFundo.setBounds(0, 0, 754, 671);
		contentPane.add(lblFundo);
	}

	private void configurarBotoes() {//CONFIGURA OS BOTOES DA TELA
		
		btnFacil = new JButton("F\u00E1cil");
		btnFacil.addActionListener(this);
		btnFacil.setBounds(89, 41, 89, 23);
		contentPane.add(btnFacil);

		btnMedio = new JButton("M\u00E9dio");
		btnMedio.addActionListener(this);
		btnMedio.setBounds(332, 41, 89, 23);
		contentPane.add(btnMedio);

		btnDificil = new JButton("Dif\u00EDcil");
		btnDificil.addActionListener(this);
		btnDificil.setBounds(565, 41, 89, 23);
		contentPane.add(btnDificil);
		
		btnSair = new JButton("MENU");
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSair.addActionListener(this);
		btnSair.setBounds(332, 7, 89, 23);
		contentPane.add(btnSair);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSair) {//SE FOR BOTAO SAIR ELE VOLTA PRO MENU
			menu = new TelaMenu();
			menu.setVisible(true);
			dispose();
		} else {//SE NAO FOR, ELE INSTANCIA O RANKING E DEPOIS MOSTRA ELE NOS LABELS
			if (e.getSource() == btnFacil) {
				ranking = new RankingFacil();
			} else if (e.getSource() == btnMedio) {
				ranking = new RankingMedio();

			} else if (e.getSource() == btnDificil) {
				ranking = new RankingDificil();
			}
			ranking.lerArquivo();
			lblNomes.setText(ranking.escreverNomes());
			lblTempos.setText(ranking.escreverTempos());
		}
	}
}
