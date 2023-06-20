package view;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import controller.ListenerCadastroCurso;
import java.awt.GridBagLayout;

public class FrameCadastroCurso extends FrameCadastro{
	public int codCurso;
	public JTextField txtCurso, txtDescricao;
	public FrameConsultaCurso consCurso = null;
	
	public FrameCadastroCurso (FrameConsultaCurso consCurso) {
		this();
		this.consCurso = consCurso;
		this.codCurso = consCurso.cursoBO.getCodigo();
		this.txtCurso.setText(consCurso.cursoBO.getCurso());
		this.txtDescricao.setText(consCurso.cursoBO.getDescricao());
	}
	
	public FrameCadastroCurso() {
		setTitle("Cadastro de Curso");
		
		GridBagLayout gridBagLayout = (GridBagLayout) pnlCenter.getLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
		
		JLabel lblCurso = new JLabel("Curso:");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.insets = new Insets(5, 5, 5, 5);
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.gridx = 0;
		gbc_lblCurso.gridy = 1;
		pnlCenter.add(lblCurso, gbc_lblCurso);
		
		txtCurso = new JTextField();
		GridBagConstraints gbc_txtCurso = new GridBagConstraints();
		gbc_txtCurso.gridwidth = 4;
		gbc_txtCurso.insets = new Insets(5, 5, 5, 5);
		gbc_txtCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurso.gridx = 1;
		gbc_txtCurso.gridy = 1;
		pnlCenter.add(txtCurso, gbc_txtCurso);
		txtCurso.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
		gbc_lblDescrio.insets = new Insets(5, 5, 0, 5);
		gbc_lblDescrio.anchor = GridBagConstraints.EAST;
		gbc_lblDescrio.gridx = 0;
		gbc_lblDescrio.gridy = 2;
		pnlCenter.add(lblDescrio, gbc_lblDescrio);
		
		txtDescricao = new JTextField();
		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.gridwidth = 4;
		gbc_txtDescricao.insets = new Insets(5, 5, 5, 5);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 1;
		gbc_txtDescricao.gridy = 2;
		pnlCenter.add(txtDescricao, gbc_txtDescricao);
		txtDescricao.setColumns(10);
		
		// aqui é setado o controller desse frame
		ListenerCadastroCurso listener = new ListenerCadastroCurso(this);
		btnOk.addActionListener(listener);
		btnCancelar.addActionListener(listener);
	}

}
