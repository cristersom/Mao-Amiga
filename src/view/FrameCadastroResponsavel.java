package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;

import controller.ListenerCadastroResponsavel;

public class FrameCadastroResponsavel extends FrameCadastro {    
	public int idResponsavel, idCep;
	public AbaPessoa pnlResponsavel;
	public AbaEndereco pnlEndereco;
	public FrameConsultaResponsavel consResponsavel = null;

	@SuppressWarnings("deprecation")
	public FrameCadastroResponsavel(FrameConsultaResponsavel consResponsavel) {
		this();
		this.consResponsavel = consResponsavel;
		this.idResponsavel = consResponsavel.responsavelBO.getId();
		
		this.pnlResponsavel.jcbTipo.setSelectedItem(consResponsavel.responsavelBO.getTipo());;
		this.pnlResponsavel.checkboxAutorImatem.setSelected(consResponsavel.responsavelBO.getAutorUsoImagem() == 1 ? true : false);
		this.pnlResponsavel.jcbDia.setSelectedIndex(consResponsavel.responsavelBO.getDataNascimento().getTime().getDate() - 1);
		this.pnlResponsavel.jcbMes.setSelectedIndex(consResponsavel.responsavelBO.getDataNascimento().getTime().getMonth());
		this.pnlResponsavel.jcbAno.setSelectedIndex(consResponsavel.responsavelBO.getDataNascimento().getTime().getYear());
		this.pnlResponsavel.txtNome.setText(consResponsavel.responsavelBO.getNome());
		this.pnlResponsavel.txtCpf.setText(consResponsavel.responsavelBO.getCpf());
		this.pnlResponsavel.txtNomeMae.setText(consResponsavel.responsavelBO.getNomeMae());
		this.pnlResponsavel.txtRG.setText(consResponsavel.responsavelBO.getRg());
		this.pnlResponsavel.txtNomePai.setText(consResponsavel.responsavelBO.getNomePai());
		this.pnlResponsavel.txtNacionalidade.setText(consResponsavel.responsavelBO.getNacionalidade());
		this.pnlResponsavel.txtCertNascimento.setText(consResponsavel.responsavelBO.getCertNascimento());
		this.pnlResponsavel.jcbSexo.setSelectedItem(consResponsavel.responsavelBO.getSexo());
		//this.pnlResponsavel.txtIdiomaMaterno.setText(consResponsavel.responsavelBO.getIdiomaMaterno());
		
		this.idCep = consResponsavel.responsavelBO.cep.getId();
		this.pnlEndereco.txtCep.setText(consResponsavel.responsavelBO.cep.getCep());
		this.pnlEndereco.txtCidade.setText(consResponsavel.responsavelBO.cep.cidade.getCidade());
		this.pnlEndereco.txtBairro.setText(consResponsavel.responsavelBO.cep.getBairro());
		this.pnlEndereco.txtLogradouro.setText(consResponsavel.responsavelBO.cep.getLogradouro());
		this.pnlEndereco.txtNumero.setText(String.valueOf(consResponsavel.responsavelBO.getNumero()));
		this.pnlEndereco.txtComplemento.setText(consResponsavel.responsavelBO.getComplemento());
		this.pnlEndereco.txtCelular.setText(consResponsavel.responsavelBO.getCelular());
		this.pnlEndereco.txtFoneComercial.setText(consResponsavel.responsavelBO.getFoneComercial());
		this.pnlEndereco.txtEmail.setText(consResponsavel.responsavelBO.getEmail());
		this.pnlEndereco.txtLocalTrabalho.setText(consResponsavel.responsavelBO.getLocalTrabalho());
	}

	public FrameCadastroResponsavel() {
		setTitle("Cadastro de Responsável");

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
		
        pnlResponsavel = new AbaPessoa();
        pnlResponsavel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlResponsavel.jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { Utils.Tipo.Responsável_Financeiro.toString()
        			 , Utils.Tipo.Responsável_Pedagógico.toString(), Utils.Tipo.Ambos.toString() }));
        pnlResponsavel.lblInfEducacao.setVisible(false);
        pnlResponsavel.txtInfEducacao.setVisible(false);
        pnlResponsavel.lblFichaSaude.setVisible(false);
        pnlResponsavel.txtFichaSaude.setVisible(false);
        pnlResponsavel.lblObservacoes.setVisible(false);
        pnlResponsavel.txtObservacoes.setVisible(false);
        pnlResponsavel.lblIdiomaMaterno.setVisible(false);
        pnlResponsavel.txtIdiomaMaterno.setVisible(false);

        
        tabbedPane.addTab("Responsavel", null, pnlResponsavel, null);

        pnlEndereco = new AbaEndereco();
        pnlEndereco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Endereço", null, pnlEndereco, null);


		// aqui é setado o controller desse frame
		ListenerCadastroResponsavel listener = new ListenerCadastroResponsavel(this);
		btnOk.addActionListener(listener);
		btnCancelar.addActionListener(listener);
		pnlEndereco.btnBuscaCep.addActionListener(listener);
		pnlResponsavel.txtCpf.addKeyListener(listener);

	}
}