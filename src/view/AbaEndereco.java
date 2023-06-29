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
	public JTextField txtCidade, txtCep, txtLogradouro, txtBairro, txtNumero, txtComplemento, txtCelular, txtFoneComercial, txtEmail, txtLocalTrabalho;
    public JLabel lblLocalTrabalho;
    public JButton btnBuscaCep;
    
    public AbaEndereco() {
    	//super();
        this.setLayout(new BorderLayout());
        //this.add(this, BorderLayout.NORTH);
                
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 0, 0 };
		gbl_this.rowHeights = new int[] { 0, 0 };
		gbl_this.columnWeights = new double[] { 0.0, 0.3, 0.0, 0.0, 0.0, 0.5 };
		gbl_this.rowWeights = new double[] { 0.0, 0.0 };
		this.setLayout(gbl_this);
        
		JLabel lblCep = new JLabel("CEP:");
		GridBagConstraints gbc_lblCep = new GridBagConstraints();
		gbc_lblCep.anchor = GridBagConstraints.EAST;
		gbc_lblCep.insets = new Insets(5, 5, 5, 5);
		gbc_lblCep.gridx = 0;
		gbc_lblCep.gridy = 0;
		this.add(lblCep, gbc_lblCep);

		txtCep = new JTextField();
		txtCep.setEditable(false);
		GridBagConstraints gbc_txtCep = new GridBagConstraints();
		gbc_txtCep.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCep.insets = new Insets(5, 5, 5, 5);
		gbc_txtCep.gridx = 1;
		gbc_txtCep.gridy = 0;
		this.add(txtCep, gbc_txtCep);

		btnBuscaCep = new JButton();
		btnBuscaCep.setIcon(new ImageIcon("buscar.png"));
		GridBagConstraints gbc_btnBuscaCep = new GridBagConstraints();
		gbc_btnBuscaCep.anchor = GridBagConstraints.WEST;
		gbc_btnBuscaCep.insets = new Insets(5, 5, 5, 5);
		gbc_btnBuscaCep.gridx = 2;
		gbc_btnBuscaCep.gridy = 0;
		this.add(btnBuscaCep, gbc_btnBuscaCep);
		
		JLabel lblCidade = new JLabel("Cidade:");
		GridBagConstraints gbc_lblCidade = new GridBagConstraints();
		gbc_lblCidade.anchor = GridBagConstraints.EAST;
		gbc_lblCidade.insets = new Insets(5, 5, 5, 5);
		gbc_lblCidade.gridx = 0;
		gbc_lblCidade.gridy = 1;
		this.add(lblCidade, gbc_lblCidade);

		txtCidade = new JTextField();
		txtCidade.setEditable(false);
		GridBagConstraints gbc_txtCidade = new GridBagConstraints();
		gbc_txtCidade.gridwidth = 3;
		gbc_txtCidade.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCidade.insets = new Insets(5, 5, 5, 5);
		gbc_txtCidade.gridx = 1;
		gbc_txtCidade.gridy = 1;
		this.add(txtCidade, gbc_txtCidade);

		JLabel lblBairro = new JLabel("Bairro:");
		GridBagConstraints gbc_lblBairro = new GridBagConstraints();
		gbc_lblBairro.anchor = GridBagConstraints.EAST;
		gbc_lblBairro.insets = new Insets(5, 5, 5, 5);
		gbc_lblBairro.gridx = 4;
		gbc_lblBairro.gridy = 1;
		this.add(lblBairro, gbc_lblBairro);

		txtBairro = new JTextField();
		txtBairro.setEditable(false);
		GridBagConstraints gbc_txtBairro = new GridBagConstraints();
		gbc_txtBairro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBairro.insets = new Insets(5, 5, 5, 5);
		gbc_txtBairro.gridx = 5;
		gbc_txtBairro.gridy = 1;
		this.add(txtBairro, gbc_txtBairro);
		
		JLabel lblLogradouro = new JLabel("Logradouro:");
		GridBagConstraints gbc_lblLogradouro = new GridBagConstraints();
		gbc_lblLogradouro.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogradouro.anchor = GridBagConstraints.EAST;
		gbc_lblLogradouro.gridx = 0;
		gbc_lblLogradouro.gridy = 2;
		this.add(lblLogradouro, gbc_lblLogradouro);

		txtLogradouro = new JTextField();
		txtLogradouro.setEditable(false);
		GridBagConstraints gbc_txtLogradouro = new GridBagConstraints();
		gbc_txtLogradouro.gridwidth = 5;
		gbc_txtLogradouro.insets = new Insets(5, 5, 5, 5);
		gbc_txtLogradouro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLogradouro.gridx = 1;
		gbc_txtLogradouro.gridy = 2;
		this.add(txtLogradouro, gbc_txtLogradouro);

		JLabel lblNmero = new JLabel("Número:");
		GridBagConstraints gbc_lblNmero = new GridBagConstraints();
		gbc_lblNmero.insets = new Insets(5, 5, 5, 5);
		gbc_lblNmero.gridx = 0;
		gbc_lblNmero.gridy = 3;
		this.add(lblNmero, gbc_lblNmero);

		txtNumero = new JTextField();
		GridBagConstraints gbc_txtNumero = new GridBagConstraints();
		gbc_txtNumero.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumero.insets = new Insets(5, 5, 5, 5);
		gbc_txtNumero.gridx = 1;
		gbc_txtNumero.gridy = 3;
		this.add(txtNumero, gbc_txtNumero);

		JLabel lblComplemento = new JLabel("Complemento:");
		GridBagConstraints gbc_lblComplemento = new GridBagConstraints();
		gbc_lblComplemento.insets = new Insets(5, 5, 5, 5);
		gbc_lblComplemento.gridx = 2;
		gbc_lblComplemento.gridy = 3;
		this.add(lblComplemento, gbc_lblComplemento);

		txtComplemento = new JTextField();
		GridBagConstraints gbc_txtComplemento = new GridBagConstraints();
		gbc_txtComplemento.gridwidth = 3;
		gbc_txtComplemento.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtComplemento.insets = new Insets(5, 5, 5, 5);
		gbc_txtComplemento.gridx = 3;
		gbc_txtComplemento.gridy = 3;
		this.add(txtComplemento, gbc_txtComplemento);
		
		JLabel lblCelular = new JLabel("Celular:");
		GridBagConstraints gbc_lblCelular = new GridBagConstraints();
		gbc_lblCelular.insets = new Insets(5, 5, 5, 5);
		gbc_lblCelular.gridx = 0;
		gbc_lblCelular.gridy = 4;
		this.add(lblCelular, gbc_lblCelular);
		
        //txtCelular = new JTextField();
        try {
        	txtCelular = new JFormattedTextField(new MaskFormatter("(##)# ####-####"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        //txtCelular.setFocusTraversalKeys(0, Collections.emptySet());
		GridBagConstraints gbc_txtCelular = new GridBagConstraints();
		gbc_txtCelular.gridwidth = 2;
		gbc_txtCelular.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCelular.insets = new Insets(5, 5, 5, 5);
		gbc_txtCelular.gridx = 1;
		gbc_txtCelular.gridy = 4;
		this.add(txtCelular, gbc_txtCelular);
        
		JLabel lblFoneComercial = new JLabel("Telefone Comercial:");
		GridBagConstraints gbc_lblFoneComercial = new GridBagConstraints();
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
		GridBagConstraints gbc_txtFoneComercial = new GridBagConstraints();
		gbc_txtFoneComercial.gridwidth = 2;
		gbc_txtFoneComercial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFoneComercial.insets = new Insets(5, 5, 5, 5);
		gbc_txtFoneComercial.gridx = 4;
		gbc_txtFoneComercial.gridy = 4;
		this.add(txtFoneComercial, gbc_txtFoneComercial);
		
		JLabel lblEmail = new JLabel("E-mail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(5, 5, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 5;
		this.add(lblEmail, gbc_lblEmail);
        
		txtEmail = new JTextField();
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.gridwidth = 2;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(5, 5, 5, 5);
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 5;
		this.add(txtEmail, gbc_txtEmail);
        
		lblLocalTrabalho = new JLabel("Local de Trabalho:");
		GridBagConstraints gbc_lblLocalTrabalho = new GridBagConstraints();
		gbc_lblLocalTrabalho.insets = new Insets(5, 5, 5, 5);
		gbc_lblLocalTrabalho.gridx = 3;
		gbc_lblLocalTrabalho.gridy = 5;
		this.add(lblLocalTrabalho, gbc_lblLocalTrabalho);

		txtLocalTrabalho = new JTextField();
		GridBagConstraints gbc_txtLocalTrabalho = new GridBagConstraints();
		gbc_txtLocalTrabalho.gridwidth = 2;
		gbc_txtLocalTrabalho.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLocalTrabalho.insets = new Insets(5, 5, 5, 5);
		gbc_txtLocalTrabalho.gridx = 4;
		gbc_txtLocalTrabalho.gridy = 5;
		this.add(txtLocalTrabalho, gbc_txtLocalTrabalho);
    }
}