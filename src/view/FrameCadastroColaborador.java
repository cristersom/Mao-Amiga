package view;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import controller.ListenerCadastroColaborador;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;

public class FrameCadastroColaborador extends FrameCadastro {
	public int codColaborador, codCep;
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
		
		this.pnlColaborador.jcbTipo.setSelectedItem(consColaborador.colaboradorBO.getTipo());;
		this.pnlColaborador.checkboxAutorImatem.setText(String.valueOf(consColaborador.colaboradorBO.getAutorUsoImagem()));
		this.pnlColaborador.jcbDia.setSelectedIndex(consColaborador.colaboradorBO.getDataNascimento().getTime().getDate() - 1);
		this.pnlColaborador.jcbMes.setSelectedIndex(consColaborador.colaboradorBO.getDataNascimento().getTime().getMonth());
		this.pnlColaborador.jcbAno.setSelectedIndex(consColaborador.colaboradorBO.getDataNascimento().getTime().getYear());
		this.pnlColaborador.txtNome.setText(consColaborador.colaboradorBO.getNome());
		this.pnlColaborador.txtCpf.setText(consColaborador.colaboradorBO.getCpf());
		this.pnlColaborador.txtNomeMae.setText(consColaborador.colaboradorBO.getNomeMae());
		this.pnlColaborador.txtRG.setText(consColaborador.colaboradorBO.getRg());
		this.pnlColaborador.txtNomePai.setText(consColaborador.colaboradorBO.getNomePai());
		this.pnlColaborador.txtNacionalidade.setText(consColaborador.colaboradorBO.getNacionalidade());
		this.pnlColaborador.txtCertNascimento.setText(consColaborador.colaboradorBO.getCertNascimento());
		this.pnlColaborador.jcbSexo.setSelectedItem(consColaborador.colaboradorBO.getSexo());
		//this.pnlColaborador.txtIdiomaMaterno.setText(consColaborador.colaboradorBO.getIdiomaMaterno());
		
		this.codCep = consColaborador.colaboradorBO.cep.getCodigo();
		this.pnlEndereco.txtCep.setText(consColaborador.colaboradorBO.cep.getCep());
		this.pnlEndereco.txtCidade.setText(consColaborador.colaboradorBO.cep.cidade.getCidade());
		this.pnlEndereco.txtBairro.setText(consColaborador.colaboradorBO.cep.getBairro());
		this.pnlEndereco.txtLogradouro.setText(consColaborador.colaboradorBO.cep.getLogradouro());
		this.pnlEndereco.txtNumero.setText(String.valueOf(consColaborador.colaboradorBO.getNumero()));
		this.pnlEndereco.txtComplemento.setText(consColaborador.colaboradorBO.getComplemento());
		this.pnlEndereco.txtCelular.setText(consColaborador.colaboradorBO.getCelular());
		this.pnlEndereco.txtFoneComercial.setText(consColaborador.colaboradorBO.getFoneComercial());
		this.pnlEndereco.txtEmail.setText(consColaborador.colaboradorBO.getEmail());
		//this.pnlEndereco.txtLocalTrabalho.setText(consColaborador.colaboradorBO.getLocalTrabalho());
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
        pnlColaborador.jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { Utils.Tipo.MONITOR.toString(), Utils.Tipo.PROFESSOR.toString() }));
        pnlColaborador.lblInfEducacao.setVisible(false);
        pnlColaborador.txtInfEducacao.setVisible(false);
        pnlColaborador.lblFichaSaude.setVisible(false);
        pnlColaborador.txtFichaSaude.setVisible(false);
        pnlColaborador.lblObservacoes.setVisible(false);
        pnlColaborador.txtObservacoes.setVisible(false);
        pnlColaborador.lblIdiomaMaterno.setVisible(false);
        pnlColaborador.txtIdiomaMaterno.setVisible(false);

        
        tabbedPane.addTab("Colaborador", null, pnlColaborador, null);

        pnlEndereco = new AbaEndereco();
        pnlEndereco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Endereço", null, pnlEndereco, null);
        pnlEndereco.lblLocalTrabalho.setVisible(false);
        pnlEndereco.txtLocalTrabalho.setVisible(false);


		// aqui é setado o controller desse frame
		ListenerCadastroColaborador listener = new ListenerCadastroColaborador(this);
		btnOk.addActionListener(listener);
		btnCancelar.addActionListener(listener);
		pnlEndereco.btnBuscaCep.addActionListener(listener);
		pnlColaborador.txtCpf.addKeyListener(listener);

	}

}
