package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import controller.ListernerConsulta;

import javax.swing.border.LineBorder;
import java.awt.Font;
import java.beans.PropertyVetoException;
import javax.swing.JLayeredPane;

public class FrameConsulta extends JInternalFrame {

	public JTextField txtConsulta;
	protected JPanel pnlMain;
	protected JButton btnConsultar, btnIncluir, btnAlterar, btnExlcuir, btnSair;
	public JComboBox jcbconsultaPor;
	private String condicao[] = {};
	private JLabel lblConsultarPor;
	private JPanel pnlBottom;
	public JButton btnSelecionar;

	public FrameConsulta() {
		super("Título", true, true, true, true);
		//this.setSize(600, 400);
		try {
			this.setMaximum(true); // para abrir o frame maximizado
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Parte superior do painel
		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		pnlMain.add(pnlTop, BorderLayout.NORTH);

		lblConsultarPor = new JLabel("Consultar por:");
		pnlTop.add(lblConsultarPor);
		lblConsultarPor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jcbconsultaPor = new JComboBox(condicao);
		pnlTop.add(jcbconsultaPor);
		txtConsulta = new JTextField(30);
		pnlTop.add(txtConsulta);
		txtConsulta.setHorizontalAlignment(JTextField.LEFT);
		
		btnConsultar = new JButton("Consultar");
		pnlTop.add(btnConsultar);
		
		// Parte central do painel	(Será acrescentada uma Jtable)

		// Parte inferior do painel
		pnlBottom = new JPanel();
		pnlBottom.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		btnSelecionar = new JButton("Selecionar");
		pnlBottom.add(btnSelecionar);

		btnIncluir = new JButton("Incluir");
		pnlBottom.add(btnIncluir);
		btnAlterar = new JButton("Alterar");
		pnlBottom.add(btnAlterar);
		btnExlcuir = new JButton("Excluir");
		pnlBottom.add(btnExlcuir);

		btnSair = new JButton("Sair");
		pnlBottom.add(btnSair);

		pnlMain.add(pnlBottom, BorderLayout.SOUTH);

		setContentPane(pnlMain);

		// aqui é setado o controller desse frame
		ListernerConsulta listener = new ListernerConsulta(this);
		btnSair.addActionListener(listener);

	}
}
