package view;

import java.awt.event.ActionListener;
import controller.ListenerCadastroBairro;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import model.bo.CidadeBO;
import javax.swing.JButton;
import javax.swing.JTextField;

public class FrameCadastroBairro extends FrameCadastro
{
    public int codBairro;
    public int codCidade;
    public JTextField txtBairro;
    public JTextField txtCidade;
    public JTextField txtUf;
    public JButton btnBuscaCidades;
    public FrameConsultaBairro consBairro;
    public CidadeBO cidBO;
    
    public FrameCadastroBairro(final FrameConsultaBairro consBairro) {
        this();
        this.consBairro = consBairro;
        this.codBairro = consBairro.bairroBO.getCodigo();
        this.txtBairro.setText(consBairro.bairroBO.getBairro());
        this.codCidade = consBairro.bairroBO.cidade.getCodigo();
        this.txtCidade.setText(consBairro.bairroBO.cidade.getCidade());
        this.txtUf.setText(consBairro.bairroBO.cidade.getUf());
    }
    
    public FrameCadastroBairro() {
        this.consBairro = null;
        this.setTitle("Cadastro de Bairros");
        final GridBagLayout gridBagLayout = (GridBagLayout)this.pnlCenter.getLayout();
        gridBagLayout.columnWidths = new int[5];
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0 };
        final JLabel lblNome = new JLabel("Bairro:");
        lblNome.setFont(new Font("Tahoma", 0, 12));
        final GridBagConstraints gbc_lblNome = new GridBagConstraints();
        gbc_lblNome.insets = new Insets(5, 5, 5, 5);
        gbc_lblNome.anchor = 13;
        gbc_lblNome.gridx = 0;
        gbc_lblNome.gridy = 0;
        this.pnlCenter.add(lblNome, gbc_lblNome);
        (this.txtBairro = new JTextField()).setFont(new Font("Tahoma", 0, 12));
        final GridBagConstraints gbc_txtBairro = new GridBagConstraints();
        gbc_txtBairro.gridwidth = 4;
        gbc_txtBairro.insets = new Insets(5, 5, 5, 5);
        gbc_txtBairro.fill = 2;
        gbc_txtBairro.gridx = 1;
        gbc_txtBairro.gridy = 0;
        this.pnlCenter.add(this.txtBairro, gbc_txtBairro);
        this.txtBairro.setColumns(10);
        final JLabel lblCidade = new JLabel("Cidade:");
        lblCidade.setFont(new Font("Tahoma", 0, 12));
        final GridBagConstraints gbc_lblCidade = new GridBagConstraints();
        gbc_lblCidade.anchor = 13;
        gbc_lblCidade.insets = new Insets(5, 5, 5, 5);
        gbc_lblCidade.gridx = 0;
        gbc_lblCidade.gridy = 1;
        this.pnlCenter.add(lblCidade, gbc_lblCidade);
        (this.txtCidade = new JTextField()).setEditable(false);
        this.txtCidade.setFont(new Font("Tahoma", 0, 12));
        final GridBagConstraints gbc_txtCidade = new GridBagConstraints();
        gbc_txtCidade.fill = 2;
        gbc_txtCidade.insets = new Insets(5, 5, 5, 5);
        gbc_txtCidade.gridx = 1;
        gbc_txtCidade.gridy = 1;
        this.pnlCenter.add(this.txtCidade, gbc_txtCidade);
        this.txtCidade.setColumns(5);
        (this.btnBuscaCidades = new JButton("")).setIcon(new ImageIcon("buscar.png"));
        final GridBagConstraints gbc_btnBuscaCidades = new GridBagConstraints();
        gbc_btnBuscaCidades.anchor = 17;
        gbc_btnBuscaCidades.insets = new Insets(5, 5, 5, 5);
        gbc_btnBuscaCidades.gridx = 2;
        gbc_btnBuscaCidades.gridy = 1;
        this.pnlCenter.add(this.btnBuscaCidades, gbc_btnBuscaCidades);
        final JLabel lblUf = new JLabel("UF:");
        final GridBagConstraints gbc_lblUf = new GridBagConstraints();
        gbc_lblUf.anchor = 13;
        gbc_lblUf.insets = new Insets(5, 5, 5, 5);
        gbc_lblUf.gridx = 3;
        gbc_lblUf.gridy = 1;
        this.pnlCenter.add(lblUf, gbc_lblUf);
        (this.txtUf = new JTextField()).setEditable(false);
        final GridBagConstraints gbc_txtUf = new GridBagConstraints();
        gbc_txtUf.fill = 2;
        gbc_txtUf.insets = new Insets(5, 5, 5, 5);
        gbc_txtUf.gridx = 4;
        gbc_txtUf.gridy = 1;
        this.pnlCenter.add(this.txtUf, gbc_txtUf);
        this.txtUf.setColumns(2);
        final ListenerCadastroBairro listener = new ListenerCadastroBairro(this);
        this.btnCancelar.addActionListener((ActionListener)listener);
        this.btnOk.addActionListener((ActionListener)listener);
        this.btnBuscaCidades.addActionListener((ActionListener)listener);
    }
}