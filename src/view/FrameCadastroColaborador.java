package view;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ListenerCadastroColaborador;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.util.Collections;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class FrameCadastroColaborador extends FrameCadastro {
	public int codColaborador, codCep;
	public JTextField txtNome, txtCidade, txtCep, txtLogradouro, txtBairro, txtNumero, txtComplemento;
	public JComboBox<Integer> jcbDia, jcbAno;
	public JComboBox<String> jcbMes;
	private int i;
	public AbaPessoa pnlColaborador;
	public AbaEndereco pnlEndereco;
	public FrameConsultaColaborador consColaborador = null;

	@SuppressWarnings("deprecation")
	public FrameCadastroColaborador(FrameConsultaColaborador consColaborador) {
		this();
		this.consColaborador = consColaborador;
		this.codColaborador = consColaborador.colaboradorBO.getCodigo();
		this.codCep = consColaborador.colaboradorBO.cep.getCodigo();
		this.txtNome.setText(consColaborador.colaboradorBO.getNome());
		this.pnlColaborador.txtCpf.setText(consColaborador.colaboradorBO.getCpf());
		
		this.jcbDia.setSelectedIndex(consColaborador.colaboradorBO.getDataNascimento().getTime().getDate() - 1);
		this.jcbMes.setSelectedIndex(consColaborador.colaboradorBO.getDataNascimento().getTime().getMonth());
		this.jcbAno.setSelectedIndex(consColaborador.colaboradorBO.getDataNascimento().getTime().getYear());
		
		this.pnlEndereco.txtCep.setText(consColaborador.colaboradorBO.cep.getCep());
		this.pnlEndereco.txtCidade.setText(consColaborador.colaboradorBO.cep.cidade.getCidade());
		this.pnlEndereco.txtBairro.setText(consColaborador.colaboradorBO.cep.getBairro());
		this.pnlEndereco.txtLogradouro.setText(consColaborador.colaboradorBO.cep.getLogradouro());
		this.pnlEndereco.txtNumero.setText(String.valueOf(consColaborador.colaboradorBO.getNumero()));
		this.pnlEndereco.txtComplemento.setText(consColaborador.colaboradorBO.getComplemento());
		//this.pnlEndereco.txtCelular.setText(consColaborador.colaboradorBO.get);
		//FALTA COISA AQUI
		
		


	}

	public FrameCadastroColaborador() {
		setTitle("Cadastro de Colaboradores");

		GridBagLayout gridBagLayout = (GridBagLayout) pnlCenter.getLayout();
		gridBagLayout.rowWeights = new double[] { 1.0 };
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };


		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		pnlCenter.add(tabbedPane, gbc_tabbedPane);
		
        pnlColaborador = new AbaPessoa();
        pnlColaborador.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlColaborador.jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { Utils.Tipo.MONITOR.toString(), "Professor" }));
        pnlColaborador.lblInfEducacao.setVisible(false);
        pnlColaborador.txtInfEducacao.setVisible(false);
        pnlColaborador.lblFichaSaude.setVisible(false);
        pnlColaborador.txtFichaSaude.setVisible(false);
        pnlColaborador.lblObservacoes.setVisible(false);
        pnlColaborador.txtObservacoes.setVisible(false);
        tabbedPane.addTab("Colaborador", null, pnlColaborador, null);

        pnlEndereco = new AbaEndereco();
        pnlEndereco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Endereço", null, pnlEndereco, null);


		// aqui é setado o controller desse frame
		ListenerCadastroColaborador listener = new ListenerCadastroColaborador(this);
		btnOk.addActionListener(listener);
		btnCancelar.addActionListener(listener);
		pnlEndereco.btnBuscaCep.addActionListener(listener);
		pnlColaborador.txtCpf.addKeyListener(listener);

	}

}
