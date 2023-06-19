package view;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import controller.ListenerCadastroCidade;
import javax.swing.DefaultComboBoxModel;

public class FrameCadastroCidade extends FrameCadastro {
	public JTextField txtCidade;
	public JTextField txtEstado;
	public JComboBox<String> jcbUf;
	public int codCidade;
	public FrameConsultaCidade consCid = null;
	public String estados[] = { "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
			"Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Pará",
			"Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul",
			"Rondônia", "Roraima", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins" };

	public FrameCadastroCidade(FrameConsultaCidade consCid) {
		this(); // invoca o construtor principal
		this.consCid = consCid;
		this.codCidade = consCid.cidBO.getCodigo();
		this.txtCidade.setText(consCid.cidBO.getCidade());
		this.jcbUf.setSelectedItem(consCid.cidBO.getUf());
	}

	public FrameCadastroCidade() {
		setTitle("Cadastro de Cidades");

		GridBagLayout gridBagLayout = (GridBagLayout) pnlCenter.getLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0 };

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblCidade = new GridBagConstraints();
		gbc_lblCidade.insets = new Insets(5, 5, 5, 5);
		gbc_lblCidade.anchor = GridBagConstraints.EAST;
		gbc_lblCidade.gridx = 0;
		gbc_lblCidade.gridy = 0;
		pnlCenter.add(lblCidade, gbc_lblCidade);

		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtCidade = new GridBagConstraints();
		gbc_txtCidade.gridwidth = 3;
		gbc_txtCidade.insets = new Insets(5, 5, 5, 5);
		gbc_txtCidade.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCidade.gridx = 1;
		gbc_txtCidade.gridy = 0;
		pnlCenter.add(txtCidade, gbc_txtCidade);
		txtCidade.setColumns(10);

		JLabel lblUf = new JLabel("UF:");
		lblUf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblUf = new GridBagConstraints();
		gbc_lblUf.anchor = GridBagConstraints.EAST;
		gbc_lblUf.insets = new Insets(5, 5, 5, 5);
		gbc_lblUf.gridx = 0;
		gbc_lblUf.gridy = 1;
		pnlCenter.add(lblUf, gbc_lblUf);

		jcbUf = new JComboBox<String>();
		jcbUf.setModel(new DefaultComboBoxModel<String>(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		jcbUf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_jcbUf = new GridBagConstraints();
		gbc_jcbUf.anchor = GridBagConstraints.WEST;
		gbc_jcbUf.insets = new Insets(5, 5, 5, 5);
		gbc_jcbUf.gridx = 1;
		gbc_jcbUf.gridy = 1;
		pnlCenter.add(jcbUf, gbc_jcbUf);

		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setText(estados[jcbUf.getSelectedIndex()]);
		GridBagConstraints gbc_txtEstado = new GridBagConstraints();
		gbc_txtEstado.gridwidth = 2;
		gbc_txtEstado.insets = new Insets(5, 5, 5, 5);
		gbc_txtEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEstado.gridx = 2;
		gbc_txtEstado.gridy = 1;
		pnlCenter.add(txtEstado, gbc_txtEstado);
		txtEstado.setColumns(30);

		// aqui é setado o controller desse frame
		ListenerCadastroCidade listener = new ListenerCadastroCidade(this);
		jcbUf.addActionListener(listener);
		btnCancelar.addActionListener(listener);
		btnOk.addActionListener(listener);

	}

}
