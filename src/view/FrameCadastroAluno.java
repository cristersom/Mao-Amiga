package view;

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import controller.ListenerCadastroAluno;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;

public class FrameCadastroAluno extends FrameCadastro {
    public int idAluno;
    public int idCep;
    public FrameConsultaAluno consAluno;
    public AbaPessoa pnlAluno;
    public AbaEndereco pnlEndereco;
    public AbaAnexos pnlAnexos;
    public AbaAutorizados pnlAutorizados;
    
    public FrameCadastroAluno(FrameConsultaAluno consAluno) {
        this();
        this.consAluno = consAluno;
        this.idAluno = consAluno.alunoBO.getId();
        this.idCep = consAluno.alunoBO.cep.getId();

		this.pnlAluno.jcbTipo.setSelectedItem(consAluno.alunoBO.getTipo());;
		this.pnlAluno.checkboxAutorImatem.setSelected(consAluno.alunoBO.getAutorUsoImagem() == 1 ? true : false);
		this.pnlAluno.jcbDia.setSelectedIndex(consAluno.alunoBO.getDataNascimento().getTime().getDate() - 1);
		this.pnlAluno.jcbMes.setSelectedIndex(consAluno.alunoBO.getDataNascimento().getTime().getMonth());
		this.pnlAluno.jcbAno.setSelectedIndex(consAluno.alunoBO.getDataNascimento().getTime().getYear());
		this.pnlAluno.txtNome.setText(consAluno.alunoBO.getNome());
		this.pnlAluno.txtCpf.setText(consAluno.alunoBO.getCpf());
		this.pnlAluno.txtNomeMae.setText(consAluno.alunoBO.getNomeMae());
		this.pnlAluno.txtRG.setText(consAluno.alunoBO.getRg());
		this.pnlAluno.txtNomePai.setText(consAluno.alunoBO.getNomePai());
		this.pnlAluno.txtNacionalidade.setText(consAluno.alunoBO.getNacionalidade());
		this.pnlAluno.txtCertNascimento.setText(consAluno.alunoBO.getCertNascimento());
		this.pnlAluno.jcbSexo.setSelectedItem(consAluno.alunoBO.getSexo());
		this.pnlAluno.txtIdiomaMaterno.setText(consAluno.alunoBO.getIdiomaMaterno());
		this.pnlAluno.txtFichaSaude.setText(consAluno.alunoBO.getFichaSaude());
		this.pnlAluno.txtInfEducacao.setText(consAluno.alunoBO.getInfoEducacao());
		this.pnlAluno.txtObservacoes.setText(consAluno.alunoBO.getObservacoes());
		
		this.idCep = consAluno.alunoBO.cep.getId();
		this.pnlEndereco.txtCep.setText(consAluno.alunoBO.cep.getCep());
		this.pnlEndereco.txtCidade.setText(consAluno.alunoBO.cep.cidade.getCidade());
		this.pnlEndereco.txtBairro.setText(consAluno.alunoBO.cep.getBairro());
		this.pnlEndereco.txtLogradouro.setText(consAluno.alunoBO.cep.getLogradouro());
		this.pnlEndereco.txtNumero.setText(String.valueOf(consAluno.alunoBO.getNumero()));
		this.pnlEndereco.txtComplemento.setText(consAluno.alunoBO.getComplemento());
		this.pnlEndereco.txtCelular.setText(consAluno.alunoBO.getCelular());
		this.pnlEndereco.txtFoneComercial.setText(consAluno.alunoBO.getFoneComercial());
		this.pnlEndereco.txtEmail.setText(consAluno.alunoBO.getEmail());
		//this.pnlEndereco.txtLocalTrabalho.setText(consAluno.alunoBO.getLocalTrabalho());
    }
    
    public FrameCadastroAluno() {
        setTitle("Cadastro de Aluno");

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
        
        pnlAluno = new AbaPessoa();
        pnlAluno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlAluno.jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { Utils.Tipo.Aluno.toString() }));
        pnlAluno.jcbTipo.setEnabled(false);
        tabbedPane.addTab("Aluno", null, pnlAluno, null);
        
        pnlEndereco = new AbaEndereco();
        pnlEndereco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Endereço", null, pnlEndereco, null);
        pnlEndereco.lblLocalTrabalho.setVisible(false);
        pnlEndereco.txtLocalTrabalho.setVisible(false);
        
        pnlAnexos = new AbaAnexos();
        pnlAnexos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Anexos", null, pnlAnexos, null);
        
        pnlAutorizados = new AbaAutorizados();
        pnlAutorizados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Autorizados", null, pnlAutorizados, null);
        
        ListenerCadastroAluno listener = new ListenerCadastroAluno(this);
        btnOk.addActionListener((ActionListener)listener);
        btnCancelar.addActionListener((ActionListener)listener);
        pnlEndereco.btnBuscaCep.addActionListener((ActionListener)listener);
        pnlAluno.txtCpf.addKeyListener((KeyListener)listener);
    }
}