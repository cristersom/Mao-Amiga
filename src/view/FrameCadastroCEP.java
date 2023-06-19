package view;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import controller.ListenerCadastroCEP;
import model.bo.CidadeBO;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.util.Collections;

public class FrameCadastroCEP extends FrameCadastro {
	public int codCep;
	public JTextField txtUf, txtCodigo, txtCidade, txtCep, txtLogradouro, txtBairro;
	public JButton btnBuscaCidades;
	public FrameConsultaCEP consCep = null;
	public FrameConsultaCidade consCid = null;
	public CidadeBO cidBO;
	
	public FrameCadastroCEP (FrameConsultaCEP consCep) {
		this();
		this.consCep = consCep;
		this.codCep = consCep.cepBO.getCodigo();
		this.txtCep.setText(consCep.cepBO.getCep());
		this.txtCodigo.setText(String.valueOf(consCep.cepBO.cidade.getCodigo()));
		this.txtCidade.setText(consCep.cepBO.cidade.getCidade());
		this.txtUf.setText(consCep.cepBO.cidade.getUf());
		this.txtLogradouro.setText(consCep.cepBO.getLogradouro());
		this.txtBairro.setText(consCep.cepBO.getBairro());
	}
	
	public FrameCadastroCEP() {
		setTitle("Cadastro de CEP");

		GridBagLayout gridBagLayout = (GridBagLayout) pnlCenter.getLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblCep = new GridBagConstraints();
		gbc_lblCep.anchor = GridBagConstraints.EAST;
		gbc_lblCep.insets = new Insets(5, 5, 5, 5);
		gbc_lblCep.gridx = 0;
		gbc_lblCep.gridy = 0;
		pnlCenter.add(lblCep, gbc_lblCep);

		txtCep = new JTextField();
		txtCep.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET); // para
																											// capturar
																											// a ação do
																											// TAB

		txtCep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtCep = new GridBagConstraints();
		gbc_txtCep.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCep.insets = new Insets(5, 5, 5, 5);
		gbc_txtCep.gridx = 1;
		gbc_txtCep.gridy = 0;
		pnlCenter.add(txtCep, gbc_txtCep);
		txtCep.setColumns(10);

		JLabel lblUf = new JLabel("UF:");
		GridBagConstraints gbc_lblUf = new GridBagConstraints();
		gbc_lblUf.anchor = GridBagConstraints.EAST;
		gbc_lblUf.insets = new Insets(5, 5, 5, 5);
		gbc_lblUf.gridx = 2;
		gbc_lblUf.gridy = 0;
		pnlCenter.add(lblUf, gbc_lblUf);

		txtUf = new JTextField();
		txtUf.setEditable(false);
		GridBagConstraints gbc_txtUf = new GridBagConstraints();
		gbc_txtUf.gridwidth = 2;
		gbc_txtUf.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUf.insets = new Insets(5, 5, 5, 5);
		gbc_txtUf.gridx = 3;
		gbc_txtUf.gridy = 0;
		pnlCenter.add(txtUf, gbc_txtUf);
		txtUf.setColumns(5);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblCidade = new GridBagConstraints();
		gbc_lblCidade.anchor = GridBagConstraints.EAST;
		gbc_lblCidade.insets = new Insets(5, 5, 5, 5);
		gbc_lblCidade.gridx = 0;
		gbc_lblCidade.gridy = 1;
		pnlCenter.add(lblCidade, gbc_lblCidade);

		txtCidade = new JTextField();
		txtCidade.setEditable(false);
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtCidade = new GridBagConstraints();
		gbc_txtCidade.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCidade.insets = new Insets(5, 5, 5, 5);
		gbc_txtCidade.gridx = 1;
		gbc_txtCidade.gridy = 1;
		pnlCenter.add(txtCidade, gbc_txtCidade);
		txtCidade.setColumns(15);

		JLabel lblCodigo = new JLabel("Código:");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.EAST;
		gbc_lblCodigo.insets = new Insets(5, 5, 5, 5);
		gbc_lblCodigo.gridx = 2;
		gbc_lblCodigo.gridy = 1;
		pnlCenter.add(lblCodigo, gbc_lblCodigo);

		txtCodigo = new JTextField();
		GridBagConstraints gbc_txtCodigo = new GridBagConstraints();
		gbc_txtCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCodigo.insets = new Insets(5, 5, 5, 5);
		gbc_txtCodigo.gridx = 3;
		gbc_txtCodigo.gridy = 1;
		pnlCenter.add(txtCodigo, gbc_txtCodigo);
		txtCodigo.setColumns(5);

		btnBuscaCidades = new JButton("");
		btnBuscaCidades.setIcon(new ImageIcon("buscar.png"));
		GridBagConstraints gbc_btnBuscaCidades = new GridBagConstraints();
		gbc_btnBuscaCidades.anchor = GridBagConstraints.WEST;
		gbc_btnBuscaCidades.insets = new Insets(5, 5, 5, 5);
		gbc_btnBuscaCidades.gridx = 4;
		gbc_btnBuscaCidades.gridy = 1;
		pnlCenter.add(btnBuscaCidades, gbc_btnBuscaCidades);

		JLabel lblLogradouro = new JLabel("Logradouro:");
		lblLogradouro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblLogradouro = new GridBagConstraints();
		gbc_lblLogradouro.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogradouro.anchor = GridBagConstraints.EAST;
		gbc_lblLogradouro.gridx = 0;
		gbc_lblLogradouro.gridy = 2;
		pnlCenter.add(lblLogradouro, gbc_lblLogradouro);

		txtLogradouro = new JTextField();
		txtLogradouro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtLogradouro = new GridBagConstraints();
		gbc_txtLogradouro.gridwidth = 4;
		gbc_txtLogradouro.insets = new Insets(5, 5, 5, 5);
		gbc_txtLogradouro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLogradouro.gridx = 1;
		gbc_txtLogradouro.gridy = 2;
		pnlCenter.add(txtLogradouro, gbc_txtLogradouro);
		txtLogradouro.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblBairro = new GridBagConstraints();
		gbc_lblBairro.insets = new Insets(5, 5, 5, 5);
		gbc_lblBairro.anchor = GridBagConstraints.EAST;
		gbc_lblBairro.gridx = 0;
		gbc_lblBairro.gridy = 3;
		pnlCenter.add(lblBairro, gbc_lblBairro);

		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtBairro = new GridBagConstraints();
		gbc_txtBairro.gridwidth = 4;
		gbc_txtBairro.anchor = GridBagConstraints.NORTH;
		gbc_txtBairro.insets = new Insets(5, 5, 5, 5);
		gbc_txtBairro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBairro.gridx = 1;
		gbc_txtBairro.gridy = 3;
		pnlCenter.add(txtBairro, gbc_txtBairro);
		txtBairro.setColumns(10);

		// aqui é setado o controller desse frame
		ListenerCadastroCEP listener = new ListenerCadastroCEP(this);
		btnCancelar.addActionListener(listener);
		btnBuscaCidades.addActionListener(listener);
		btnOk.addActionListener(listener);
		txtCep.addKeyListener(listener);
		txtCep.addFocusListener(listener);
		txtCodigo.addKeyListener(listener);
		txtCodigo.addFocusListener(listener);

	}

}
