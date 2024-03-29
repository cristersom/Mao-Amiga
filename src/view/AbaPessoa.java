package view;

import java.util.Collections;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.TextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class AbaPessoa extends JPanel {
    public JTextField txtNome, txtCpf, txtRG, txtCertNascimento, txtNomePai, txtNomeMae, txtNacionalidade, txtIdiomaMaterno;
    public TextArea txtFichaSaude, txtInfEducacao, txtObservacoes;
    public JComboBox<Integer> jcbDia, jcbAno;
    public JComboBox<String> jcbMes, jcbTipo, jcbSexo;
    public JCheckBox checkboxAutorImatem;
    protected JLabel lblIdiomaMaterno, lblFichaSaude, lblInfEducacao, lblObservacoes;
    private int i;
    
    public AbaPessoa() {
        this.setLayout(new BorderLayout());
        
        GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.columnWidths = new int[] { 71, 0, 50, 0, 0 };
        gbl_this.rowHeights = new int[14];
        gbl_this.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_this.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        this.setLayout(gbl_this);
        
        
        JLabel lblTipo = new JLabel("Tipo:");
        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.insets = new Insets(5, 5, 5, 5);
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.gridx = 0;
        gbc_lblTipo.gridy = 0;
        this.add(lblTipo, gbc_lblTipo);
        
        jcbTipo = new JComboBox<String>();
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.anchor = GridBagConstraints.WEST;
        gbc_comboBox.insets = new Insets(5, 5, 5, 5);
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        this.add(this.jcbTipo, gbc_comboBox);
            
        JLabel lblSexo = new JLabel("Sexo:");
        GridBagConstraints gbc_lblSexo = new GridBagConstraints();
        gbc_lblSexo.anchor = GridBagConstraints.EAST;
        gbc_lblSexo.insets = new Insets(5, 5, 5, 5);
        gbc_lblSexo.gridx = 3;
        gbc_lblSexo.gridy = 0;
        this.add(lblSexo, gbc_lblSexo);
        
        (jcbSexo = new JComboBox<String>()).setToolTipText("");
        jcbSexo.setModel(new DefaultComboBoxModel<String>(new String[] { Utils.Sexo.Masculino.toString(), Utils.Sexo.Feminino.toString() }));
        GridBagConstraints gbc_jcbSexo = new GridBagConstraints();
        gbc_jcbSexo.anchor = GridBagConstraints.EAST;
        gbc_jcbSexo.insets = new Insets(5, 5, 5, 5);
        gbc_jcbSexo.gridx = 4;
        gbc_jcbSexo.gridy = 0;
        this.add(jcbSexo, gbc_jcbSexo);
        
        JLabel lblNascimento = new JLabel("Nascimento:");
        GridBagConstraints gbc_lblNascimento = new GridBagConstraints();
        gbc_lblNascimento.anchor = GridBagConstraints.EAST;
        gbc_lblNascimento.insets = new Insets(5, 5, 5, 5);
        gbc_lblNascimento.gridx = 5;
        gbc_lblNascimento.gridy = 0;
        this.add(lblNascimento, gbc_lblNascimento);
        
        jcbDia = new JComboBox<Integer>();
        GridBagConstraints gbc_jcbDia = new GridBagConstraints();
        gbc_jcbDia.fill = GridBagConstraints.HORIZONTAL;
        gbc_jcbDia.insets = new Insets(5, 5, 5, 5);
        gbc_jcbDia.gridx = 6;
        gbc_jcbDia.gridy = 0;
        i = 1;
        while (i < 32) {
            jcbDia.addItem(i);
            ++i;
        }
        this.add(jcbDia, gbc_jcbDia);
        
        jcbMes = new JComboBox<String>();
        GridBagConstraints gbc_jcbMes = new GridBagConstraints();
        gbc_jcbMes.fill = GridBagConstraints.HORIZONTAL;
        gbc_jcbMes.insets = new Insets(5, 5, 5, 5);
        gbc_jcbMes.gridx = 7;
        gbc_jcbMes.gridy = 0;
        this.add(jcbMes, gbc_jcbMes);
		jcbMes.setModel(new DefaultComboBoxModel<String>(new String[] { "JANEIRO", "FEVEREIRO", "MAR�O", "ABRIL",
				"MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO" }));

        
		jcbAno = new JComboBox<Integer>();
		GridBagConstraints gbc_jcbAno = new GridBagConstraints();
		gbc_jcbAno.fill = GridBagConstraints.HORIZONTAL;
		gbc_jcbAno.insets = new Insets(5, 5, 5, 5);
		gbc_jcbAno.gridx = 8;
		gbc_jcbAno.gridy = 0;
		this.add(jcbAno, gbc_jcbAno);
		for (i = 1900; i < 2100; i++)
			jcbAno.addItem(i);
		jcbAno.setSelectedIndex(118);
        
        
        JLabel lblNome = new JLabel("Nome:");
        GridBagConstraints gbc_lblNome = new GridBagConstraints();
        gbc_lblNome.insets = new Insets(5, 5, 5, 5);
        gbc_lblNome.anchor = GridBagConstraints.EAST;
        gbc_lblNome.gridx = 0;
        gbc_lblNome.gridy = 1;
        this.add(lblNome, gbc_lblNome);
        
        txtNome = new JTextField();
        GridBagConstraints gbc_txtNome = new GridBagConstraints();
        gbc_txtNome.fill = 2;
        gbc_txtNome.gridwidth = 4;
        gbc_txtNome.insets = new Insets(5, 5, 5, 5);
        gbc_txtNome.gridx = 1;
        gbc_txtNome.gridy = 1;
        this.add(txtNome, gbc_txtNome);
        
        JLabel lblCpf = new JLabel("CPF:");
        GridBagConstraints gbc_lblCpf = new GridBagConstraints();
        gbc_lblCpf.insets = new Insets(5, 5, 5, 5);
        gbc_lblCpf.anchor = GridBagConstraints.EAST;
        gbc_lblCpf.gridx = 5;
        gbc_lblCpf.gridy = 1;
        this.add(lblCpf, gbc_lblCpf);
        try {
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        txtCpf.setFocusTraversalKeys(0, Collections.emptySet());
        GridBagConstraints gbc_txtCpf = new GridBagConstraints();
        gbc_txtCpf.gridwidth = 3;
        gbc_txtCpf.fill = 2;
        gbc_txtCpf.insets = new Insets(5, 5, 5, 0);
        gbc_txtCpf.gridx = 6;
        gbc_txtCpf.gridy = 1;
        this.add(txtCpf, gbc_txtCpf);
        txtCpf.setColumns(15);
        
        JLabel lblNomeMae = new JLabel("Nome da M�e:");
        GridBagConstraints gbc_lblNomeMae = new GridBagConstraints();
        gbc_lblNomeMae.anchor = GridBagConstraints.EAST;
        gbc_lblNomeMae.insets = new Insets(5, 5, 5, 5);
        gbc_lblNomeMae.gridx = 0;
        gbc_lblNomeMae.gridy = 2;
        this.add(lblNomeMae, gbc_lblNomeMae);
        
        txtNomeMae = new JTextField();
        GridBagConstraints gbc_txtNomeMae = new GridBagConstraints();
        gbc_txtNomeMae.gridwidth = 4;
        gbc_txtNomeMae.insets = new Insets(5, 5, 5, 5);
        gbc_txtNomeMae.fill = 2;
        gbc_txtNomeMae.gridx = 1;
        gbc_txtNomeMae.gridy = 2;
        this.add(this.txtNomeMae, gbc_txtNomeMae);
        
        JLabel lblRg = new JLabel("RG:");
        GridBagConstraints gbc_lblRg = new GridBagConstraints();
        gbc_lblRg.anchor = GridBagConstraints.EAST;
        gbc_lblRg.insets = new Insets(5, 5, 5, 5);
        gbc_lblRg.gridx = 5;
        gbc_lblRg.gridy = 2;
        this.add(lblRg, gbc_lblRg);
        
        txtRG = new JTextField();
        GridBagConstraints gbc_txtRG = new GridBagConstraints();
        gbc_txtRG.gridwidth = 3;
        gbc_txtRG.insets = new Insets(5, 5, 5, 0);
        gbc_txtRG.fill = 2;
        gbc_txtRG.gridx = 6;
        gbc_txtRG.gridy = 2;
        this.add(this.txtRG, gbc_txtRG);
        
        JLabel lblNomePai = new JLabel("Nome do Pai:");
        GridBagConstraints gbc_lblNomePai = new GridBagConstraints();
        gbc_lblNomePai.anchor = GridBagConstraints.EAST;
        gbc_lblNomePai.insets = new Insets(5, 5, 5, 5);
        gbc_lblNomePai.gridx = 0;
        gbc_lblNomePai.gridy = 3;
        this.add(lblNomePai, gbc_lblNomePai);
        
        txtNomePai = new JTextField();
        GridBagConstraints gbc_txtNomePai = new GridBagConstraints();
        gbc_txtNomePai.gridwidth = 4;
        gbc_txtNomePai.insets = new Insets(5, 5, 5, 5);
        gbc_txtNomePai.fill = 2;
        gbc_txtNomePai.gridx = 1;
        gbc_txtNomePai.gridy = 3;
        this.add(this.txtNomePai, gbc_txtNomePai);
        
        JLabel lblNacionalidade = new JLabel("Nacionalidade:");
        GridBagConstraints gbc_lblNacionalidade = new GridBagConstraints();
        gbc_lblNacionalidade.anchor = GridBagConstraints.EAST;
        gbc_lblNacionalidade.insets = new Insets(5, 5, 5, 5);
        gbc_lblNacionalidade.gridx = 5;
        gbc_lblNacionalidade.gridy = 3;
        this.add(lblNacionalidade, gbc_lblNacionalidade);
        
        txtNacionalidade = new JTextField();
        GridBagConstraints gbc_txtNacionalidade = new GridBagConstraints();
        gbc_txtNacionalidade.gridwidth = 3;
        gbc_txtNacionalidade.insets = new Insets(5, 5, 5, 0);
        gbc_txtNacionalidade.fill = 2;
        gbc_txtNacionalidade.gridx = 6;
        gbc_txtNacionalidade.gridy = 3;
        this.add(this.txtNacionalidade, gbc_txtNacionalidade);
        
        JLabel lblCertNascimento = new JLabel("N� Cert. Nascimento:");
        GridBagConstraints gbc_lblCertNascimento = new GridBagConstraints();
        gbc_lblCertNascimento.anchor = GridBagConstraints.EAST;
        gbc_lblCertNascimento.insets = new Insets(5, 5, 5, 5);
        gbc_lblCertNascimento.gridx = 0;
        gbc_lblCertNascimento.gridy = 4;
        this.add(lblCertNascimento, gbc_lblCertNascimento);
        
        txtCertNascimento = new JTextField();
        GridBagConstraints gbc_txtCertNascimento = new GridBagConstraints();
        gbc_txtCertNascimento.insets = new Insets(5, 5, 5, 5);
        gbc_txtCertNascimento.fill = 2;
        gbc_txtCertNascimento.gridx = 1;
        gbc_txtCertNascimento.gridy = 4;
        this.add(this.txtCertNascimento, gbc_txtCertNascimento);
 
        JLabel lblAutorImagem = new JLabel("Autoriza o uso da imagem.");
        GridBagConstraints gbc_lblAutorImagem = new GridBagConstraints();
        gbc_lblAutorImagem.insets = new Insets(5, 5, 5, 5);
        gbc_lblAutorImagem.anchor = GridBagConstraints.EAST;
        gbc_lblAutorImagem.gridx = 4;
        gbc_lblAutorImagem.gridy = 4;
        this.add(lblAutorImagem, gbc_lblAutorImagem);
        
        checkboxAutorImatem = new JCheckBox("");
        GridBagConstraints gbc_checkboxAutorImatem = new GridBagConstraints();
        gbc_checkboxAutorImatem.anchor = GridBagConstraints.EAST;
        gbc_checkboxAutorImatem.insets = new Insets(5, 5, 5, 5);
        gbc_checkboxAutorImatem.gridx = 3;
        gbc_checkboxAutorImatem.gridy = 4;
        this.add(checkboxAutorImatem, gbc_checkboxAutorImatem);
        
        lblIdiomaMaterno = new JLabel("Idioma Materno:");
        GridBagConstraints gbc_lblIdiomaMaterno = new GridBagConstraints();
        gbc_lblIdiomaMaterno.anchor = GridBagConstraints.EAST;
        gbc_lblIdiomaMaterno.insets = new Insets(5, 5, 5, 5);
        gbc_lblIdiomaMaterno.gridx = 5;
        gbc_lblIdiomaMaterno.gridy = 4;
        this.add(lblIdiomaMaterno, gbc_lblIdiomaMaterno);
        
        txtIdiomaMaterno = new JTextField();
        GridBagConstraints gbc_txtIdiomaMaterno = new GridBagConstraints();
        gbc_txtIdiomaMaterno.gridwidth = 3;
        gbc_txtIdiomaMaterno.insets = new Insets(5, 5, 5, 5);
        gbc_txtIdiomaMaterno.fill = 2;
        gbc_txtIdiomaMaterno.gridx = 6;
        gbc_txtIdiomaMaterno.gridy = 4;
        this.add(txtIdiomaMaterno, gbc_txtIdiomaMaterno);
        
        lblFichaSaude = new JLabel("Ficha de Sa�de:");
        GridBagConstraints gbc_lblFichaSaude = new GridBagConstraints();
        gbc_lblFichaSaude.anchor = GridBagConstraints.EAST;
        gbc_lblFichaSaude.insets = new Insets(5, 5, 5, 5);
        gbc_lblFichaSaude.gridx = 0;
        gbc_lblFichaSaude.gridy = 8;
        this.add(lblFichaSaude, gbc_lblFichaSaude);
       
        (txtFichaSaude = new TextArea(5, 1)).setCaretPosition(8);
        GridBagConstraints gbc_txtFichaSaude = new GridBagConstraints();
        gbc_txtFichaSaude.gridwidth = 8;
        gbc_txtFichaSaude.gridheight = 5;
        gbc_txtFichaSaude.insets = new Insets(5, 5, 5, 5);
        gbc_txtFichaSaude.gridx = 1;
        gbc_txtFichaSaude.gridy = 6;
        gbc_txtFichaSaude.fill = 1;
        this.add(this.txtFichaSaude, gbc_txtFichaSaude);
        
        lblInfEducacao = new JLabel("<html>Informa��es sobre<br>Educa��o Pr�via:<html>");
        GridBagConstraints gbc_lblInfEducacao = new GridBagConstraints();
        gbc_lblInfEducacao.anchor = GridBagConstraints.EAST;
        gbc_lblInfEducacao.insets = new Insets(5, 5, 5, 5);
        gbc_lblInfEducacao.gridx = 0;
        gbc_lblInfEducacao.gridy = 13;
        this.add(lblInfEducacao, gbc_lblInfEducacao);
        
        (txtInfEducacao = new TextArea(5, 1)).setCaretPosition(8);
        GridBagConstraints gbc_txtInfEducacao = new GridBagConstraints();
        gbc_txtInfEducacao.gridwidth = 8;
        gbc_txtInfEducacao.gridheight = 5;
        gbc_txtInfEducacao.insets = new Insets(5, 5, 5, 5);
        gbc_txtInfEducacao.gridx = 1;
        gbc_txtInfEducacao.gridy = 11;
        gbc_txtInfEducacao.fill = 1;
        this.add(this.txtInfEducacao, gbc_txtInfEducacao);
        
        lblObservacoes = new JLabel("Observa��es:");
        GridBagConstraints gbc_lblObservacoes = new GridBagConstraints();
        gbc_lblObservacoes.anchor = GridBagConstraints.EAST;
        gbc_lblObservacoes.insets = new Insets(5, 5, 5, 5);
        gbc_lblObservacoes.gridx = 0;
        gbc_lblObservacoes.gridy = 17;
        this.add(lblObservacoes, gbc_lblObservacoes);
        
        (txtObservacoes = new TextArea(5, 1)).setCaretPosition(8);
        GridBagConstraints gbc_txtObservacoes = new GridBagConstraints();
        gbc_txtObservacoes.gridwidth = 8;
        gbc_txtObservacoes.gridheight = 5;
        gbc_txtObservacoes.insets = new Insets(5, 5, 5, 5);
        gbc_txtObservacoes.gridx = 1;
        gbc_txtObservacoes.gridy = 16;
        gbc_txtObservacoes.fill = 1;
        this.add(this.txtObservacoes, gbc_txtObservacoes);
    }
}
