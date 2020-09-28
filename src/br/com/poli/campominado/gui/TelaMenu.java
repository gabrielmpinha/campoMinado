package br.com.poli.campominado.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.poli.campominado.jogo.Dificuldade;

public class TelaMenu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TelaJogo1 tela1;
	
	private JTextField txtNomeJogador;
	private JComboBox<String> comboBoxDificuldade;
	private JButton btnEntrar;
	private JLabel lblCampoMinado;
	private ImageIcon iconTela;
	private JButton btnRanking;
	private TelaRanking ranking;
	private Fontes fontes;
	private ImageIcon fundoMenu;
	private JLabel lblFundoMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaMenu frame = new TelaMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaMenu() {
		fontes = new Fontes();
		configurarTela();

	}

	private void configurarTextField() {//CONFIGURA O TEXTFIELD
		
		txtNomeJogador = new JTextField();
		txtNomeJogador.setBounds(307, 255, 140, 35);
		contentPane.add(txtNomeJogador);
		txtNomeJogador.setColumns(10);

		JLabel lblIndicarNome = new JLabel("Nome(Max. 10 caracteres):");
		lblIndicarNome.setBounds(79, 255, 218, 35);
		contentPane.add(lblIndicarNome);
		lblIndicarNome.setForeground(Color.ORANGE);
		lblIndicarNome.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		lblFundoMenu = new JLabel("");
		lblFundoMenu.setBounds(0, 0, 754, 671);
		contentPane.add(lblFundoMenu);
		lblFundoMenu.setIcon(fundoMenu);
		
	}

	private void configurarLabel() { //CONFIGURA O LABEL
		lblCampoMinado = new JLabel("<html>CAMPO<br/>MINADO</html>");
		lblCampoMinado.setBounds(108, 11, 537, 151);
		contentPane.add(lblCampoMinado);
		//lblCampoMinado.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 54));
		lblCampoMinado.setFont(fontes.getFontePokemon());
		lblCampoMinado.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampoMinado.setForeground(new Color(255, 204, 0));
	}

	private void configurarBotoes() {//CONFIGURA OS BOTOES

		btnEntrar = new JButton("Jogar");
		btnEntrar.setFont(fontes.getFontePokemon());
		btnEntrar.setForeground(Color.ORANGE.darker());
		btnEntrar.setBounds(229, 350, 296, 81);
		contentPane.add(btnEntrar);
		btnEntrar.setOpaque(false);
		btnEntrar.setContentAreaFilled(false);
		btnEntrar.setBorderPainted(false);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomeJogador = (txtNomeJogador.getText());
				if (!txtNomeJogador.getText().isEmpty() && txtNomeJogador.getText().length() <= 10) {//CHECA SE O NOME FOI MAIOR QUE 10 CARACTERES
					
					if (comboBoxDificuldade.getSelectedIndex() == 0) {//SE FOR A PRIMEIRA OPCAO DO COMBOBOX ELE CRIA UMA TELAJOGO FACIL ETC
						tela1 = new TelaJogo1(Dificuldade.FACIL, nomeJogador, iconTela);
					} else if (comboBoxDificuldade.getSelectedIndex() == 1) {
						tela1 = new TelaJogo1(Dificuldade.MEDIO, nomeJogador, iconTela);
					} else if (comboBoxDificuldade.getSelectedIndex() == 2) {
						tela1 = new TelaJogo1(Dificuldade.DIFICIL, nomeJogador, iconTela);
					}
					tela1.setVisible(true);
					dispose();
					
				}
			}
		});
		
		btnRanking = new JButton("Ranking");
		btnRanking.setBounds(229, 465, 296, 81);
		btnRanking.setFont(fontes.getFontePokemon());
		btnRanking.setForeground(Color.ORANGE.darker());
		btnRanking.setOpaque(false);
		btnRanking.setBorderPainted(false);
		btnRanking.setContentAreaFilled(false);
		contentPane.add(btnRanking);
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//ENTRAR NA TELA DO RANKING
				ranking = new TelaRanking(iconTela);
				ranking.setVisible(true);
				dispose();
			}
		});
		
	}

	private void configurarPanelPrincipal() {//COLOCAR UMA IMAGEM NO PAINEL PRINCIPAL
		contentPane.setLayout(null);
		fundoMenu = new ImageIcon(getClass().getResource("/images/fundoMenu.gif"));
		


	}

	private void configurarTela() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 760, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		setContentPane(contentPane);
		this.setResizable(false);

		
		iconTela = new ImageIcon(getClass().getResource("/images/iconBombaFacil.png"));
		this.setIconImage(iconTela.getImage());
		this.setTitle("Campo Minado");

		// criarFonte();

		configurarPanelPrincipal();

		criarComboBox();

		configurarBotoes();

		configurarLabel();

		configurarTextField();

		this.setLocationRelativeTo(null);

	}

	private void criarComboBox() {//CRIA O COMBOBOX
		comboBoxDificuldade = new JComboBox<String>();
		comboBoxDificuldade.setBounds(307, 188, 140, 35);
		contentPane.add(comboBoxDificuldade);
		comboBoxDificuldade.setModel(
				new DefaultComboBoxModel<String>(new String[] { "F\u00E1cil", "M\u00E9dio", "Dif\u00EDcil" }));
	}

}
