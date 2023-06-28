package view;

import javax.swing.ImageIcon;
import java.awt.Insets;
import java.text.ParseException;
import java.util.Collections;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JPanel;

public class AbaEndereco extends JPanel {
    public JTextField txtCidade;
    public JTextField txtCep;
    public JTextField txtLogradouro;
    public JTextField txtBairro;
    public JTextField txtNumero;
    public JTextField txtComplemento;
    public JTextField txtCelular;
    public JTextField txtFoneComercial;
    public JTextField txtEmail;
    public JTextField txtLocalTrabalho;
    public JLabel lblLocalTrabalho;
    public JButton btnBuscaCep;
    
    public AbaEndereco() {
        this.setLayout(new BorderLayout());
        //this.add(this, BorderLayout.NORTH);
                
        final GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.columnWidths = new int[2];
        gbl_this.rowHeights = new int[2];
        gbl_this.columnWeights = new double[] { 0.0, 0.3, 0.0, 0.0, 0.0, 0.5 };
        gbl_this.rowWeights = new double[] { 0.0, 0.0 };
        this.setLayout(gbl_this);
        
        final JLabel lblCep = new JLabel("CEP:");
        final GridBagConstraints gbc_lblCep = new GridBagConstraints();
        gbc_lblCep.anchor = 13;
        gbc_lblCep.insets = new Insets(5, 5, 5, 5);
        gbc_lblCep.gridx = 0;
        gbc_lblCep.gridy = 0;
        this.add(lblCep, gbc_lblCep);
        (this.txtCep = new JTextField()).setEditable(false);
        final GridBagConstraints gbc_txtCep = new GridBagConstraints();
        gbc_txtCep.fill = 2;
        gbc_txtCep.insets = new Insets(5, 5, 5, 5);
        gbc_txtCep.gridx = 1;
        gbc_txtCep.gridy = 0;
        this.add(this.txtCep, gbc_txtCep);
        (this.btnBuscaCep = new JButton()).setIcon(new ImageIcon("buscar.png"));
        final GridBagConstraints gbc_btnBuscaCep = new GridBagConstraints();
        gbc_btnBuscaCep.anchor = 17;
        gbc_btnBuscaCep.insets = new Insets(5, 5, 5, 5);
        gbc_btnBuscaCep.gridx = 2;
        gbc_btnBuscaCep.gridy = 0;
        this.add(this.btnBuscaCep, gbc_btnBuscaCep);
        final JLabel lblCidade = new JLabel("Cidade:");
        final GridBagConstraints gbc_lblCidade = new GridBagConstraints();
        gbc_lblCidade.anchor = 13;
        gbc_lblCidade.insets = new Insets(5, 5, 5, 5);
        gbc_lblCidade.gridx = 0;
        gbc_lblCidade.gridy = 1;
        this.add(lblCidade, gbc_lblCidade);
        (this.txtCidade = new JTextField()).setEditable(false);
        final GridBagConstraints gbc_txtCidade = new GridBagConstraints();
        gbc_txtCidade.gridwidth = 3;
        gbc_txtCidade.fill = 2;
        gbc_txtCidade.insets = new Insets(5, 5, 5, 5);
        gbc_txtCidade.gridx = 1;
        gbc_txtCidade.gridy = 1;
        this.add(this.txtCidade, gbc_txtCidade);
        final JLabel lblBairro = new JLabel("Bairro:");
        final GridBagConstraints gbc_lblBairro = new GridBagConstraints();
        gbc_lblBairro.anchor = 13;
        gbc_lblBairro.insets = new Insets(5, 5, 5, 5);
        gbc_lblBairro.gridx = 4;
        gbc_lblBairro.gridy = 1;
        this.add(lblBairro, gbc_lblBairro);
        (this.txtBairro = new JTextField()).setEditable(false);
        final GridBagConstraints gbc_txtBairro = new GridBagConstraints();
        gbc_txtBairro.fill = 2;
        gbc_txtBairro.insets = new Insets(5, 5, 5, 5);
        gbc_txtBairro.gridx = 5;
        gbc_txtBairro.gridy = 1;
        this.add(this.txtBairro, gbc_txtBairro);
        final JLabel lblLogradouro = new JLabel("Logradouro:");
        final GridBagConstraints gbc_lblLogradouro = new GridBagConstraints();
        gbc_lblLogradouro.insets = new Insets(5, 5, 5, 5);
        gbc_lblLogradouro.anchor = 13;
        gbc_lblLogradouro.gridx = 0;
        gbc_lblLogradouro.gridy = 2;
        this.add(lblLogradouro, gbc_lblLogradouro);
        (this.txtLogradouro = new JTextField()).setEditable(false);
        final GridBagConstraints gbc_txtLogradouro = new GridBagConstraints();
        gbc_txtLogradouro.gridwidth = 5;
        gbc_txtLogradouro.insets = new Insets(5, 5, 5, 5);
        gbc_txtLogradouro.fill = 2;
        gbc_txtLogradouro.gridx = 1;
        gbc_txtLogradouro.gridy = 2;
        this.add(this.txtLogradouro, gbc_txtLogradouro);
        final JLabel lblNmero = new JLabel("N�mero:");
        final GridBagConstraints gbc_lblNmero = new GridBagConstraints();
        gbc_lblNmero.insets = new Insets(5, 5, 5, 5);
        gbc_lblNmero.gridx = 0;
        gbc_lblNmero.gridy = 3;
        this.add(lblNmero, gbc_lblNmero);
        this.txtNumero = new JTextField();
        final GridBagConstraints gbc_txtNumero = new GridBagConstraints();
        gbc_txtNumero.fill = 2;
        gbc_txtNumero.insets = new Insets(5, 5, 5, 5);
        gbc_txtNumero.gridx = 1;
        gbc_txtNumero.gridy = 3;
        this.add(this.txtNumero, gbc_txtNumero);
        final JLabel lblComplemento = new JLabel("Complemento:");
        final GridBagConstraints gbc_lblComplemento = new GridBagConstraints();
        gbc_lblComplemento.insets = new Insets(5, 5, 5, 5);
        gbc_lblComplemento.gridx = 2;
        gbc_lblComplemento.gridy = 3;
        this.add(lblComplemento, gbc_lblComplemento);
        this.txtComplemento = new JTextField();
        final GridBagConstraints gbc_txtComplemento = new GridBagConstraints();
        gbc_txtComplemento.gridwidth = 3;
        gbc_txtComplemento.fill = 2;
        gbc_txtComplemento.insets = new Insets(5, 5, 5, 5);
        gbc_txtComplemento.gridx = 3;
        gbc_txtComplemento.gridy = 3;
        this.add(this.txtComplemento, gbc_txtComplemento);
        final JLabel lblCelular = new JLabel("Celular:");
        final GridBagConstraints gbc_lblCelular = new GridBagConstraints();
        gbc_lblCelular.insets = new Insets(5, 5, 5, 5);
        gbc_lblCelular.gridx = 0;
        gbc_lblCelular.gridy = 4;
        this.add(lblCelular, gbc_lblCelular);
        //this.txtCelular = new JTextField();
        try {
        	txtCelular = new JFormattedTextField(new MaskFormatter("(##)# ####-####"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        //txtCelular.setFocusTraversalKeys(0, Collections.emptySet());
        final GridBagConstraints gbc_txtCelular = new GridBagConstraints();
        gbc_txtCelular.gridwidth = 2;
        gbc_txtCelular.fill = 2;
        gbc_txtCelular.insets = new Insets(5, 5, 5, 5);
        gbc_txtCelular.gridx = 1;
        gbc_txtCelular.gridy = 4;
        this.add(this.txtCelular, gbc_txtCelular);
        final JLabel lblFoneComercial = new JLabel("Telefone Comercial:");
        final GridBagConstraints gbc_lblFoneComercial = new GridBagConstraints();
        gbc_lblFoneComercial.insets = new Insets(5, 5, 5, 5);
        gbc_lblFoneComercial.gridx = 3;
        gbc_lblFoneComercial.gridy = 4;
        this.add(lblFoneComercial, gbc_lblFoneComercial);
        
        try {
        	txtFoneComercial = new JFormattedTextField(new MaskFormatter("(##)####-####"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        final GridBagConstraints gbc_txtFoneComercial = new GridBagConstraints();
        gbc_txtFoneComercial.gridwidth = 2;
        gbc_txtFoneComercial.fill = 2;
        gbc_txtFoneComercial.insets = new Insets(5, 5, 5, 5);
        gbc_txtFoneComercial.gridx = 4;
        gbc_txtFoneComercial.gridy = 4;
        this.add(this.txtFoneComercial, gbc_txtFoneComercial);
        final JLabel lblEmail = new JLabel("E-mail:");
        final GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.insets = new Insets(5, 5, 5, 5);
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 5;
        this.add(lblEmail, gbc_lblEmail);
        this.txtEmail = new JTextField();
        
        final GridBagConstraints gbc_txtEmail = new GridBagConstraints();
        gbc_txtEmail.gridwidth = 2;
        gbc_txtEmail.fill = 2;
        gbc_txtEmail.insets = new Insets(5, 5, 5, 5);
        gbc_txtEmail.gridx = 1;
        gbc_txtEmail.gridy = 5;
        this.add(this.txtEmail, gbc_txtEmail);
        
        lblLocalTrabalho = new JLabel("Local de Trabalho:");
        final GridBagConstraints gbc_lblLocalTrabalho = new GridBagConstraints();
        gbc_lblLocalTrabalho.insets = new Insets(5, 5, 5, 5);
        gbc_lblLocalTrabalho.gridx = 3;
        gbc_lblLocalTrabalho.gridy = 5;
        this.add(lblLocalTrabalho, gbc_lblLocalTrabalho);
        this.txtLocalTrabalho = new JTextField();
        
        final GridBagConstraints gbc_txtLocalTrabalho = new GridBagConstraints();
        gbc_txtLocalTrabalho.gridwidth = 2;
        gbc_txtLocalTrabalho.fill = 2;
        gbc_txtLocalTrabalho.insets = new Insets(5, 5, 5, 5);
        gbc_txtLocalTrabalho.gridx = 4;
        gbc_txtLocalTrabalho.gridy = 5;
        this.add(this.txtLocalTrabalho, gbc_txtLocalTrabalho);
    }
}