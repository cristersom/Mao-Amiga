package view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import controller.ListenerCadastroAluno;
import controller.ListenerCadastroTurma;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class AbaAutorizados extends JPanel {
    public JTextField txtNomeAutor, txtCelular, txtFoneComercial, txtDataIni, txtDataFim;
    public JComboBox<String> jcbTipo;
    public JButton btnIncluir;
    public JButton btnExcluir;
    public JTable tabela;
    public ModeloTabela modelo;
    
    public AbaAutorizados() {
        this.setLayout(new BorderLayout());
        
        
        JPanel pnlTop = new JPanel();
        pnlTop.setBorder(BorderFactory.createBevelBorder(1));
        GridBagLayout gbl_pnlTop = new GridBagLayout();
        gbl_pnlTop.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_pnlTop.columnWeights = new double[]{0.0, 0.2, 0.0, 0.0, 0.2, 0.0, 0.0, 0.0};
        pnlTop.setLayout(gbl_pnlTop);
        this.add(pnlTop, "South");
        
        JLabel lblNomeAutor = new JLabel("Nome:");
        GridBagConstraints gbc_lblNomeAutor = new GridBagConstraints();
        gbc_lblNomeAutor.insets = new Insets(5, 5, 5, 5);
        gbc_lblNomeAutor.anchor = GridBagConstraints.EAST;
        gbc_lblNomeAutor.gridx = 0;
        gbc_lblNomeAutor.gridy = 0;
        pnlTop.add(lblNomeAutor, gbc_lblNomeAutor);
        
        txtNomeAutor = new JTextField();
        txtNomeAutor.setColumns(35);
        GridBagConstraints gbc_txtNomeAutor = new GridBagConstraints();
        gbc_txtNomeAutor.gridwidth = 4;
        gbc_txtNomeAutor.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNomeAutor.insets = new Insets(5, 5, 5, 5);
        gbc_txtNomeAutor.gridx = 1;
        gbc_txtNomeAutor.gridy = 0;
        pnlTop.add(txtNomeAutor, gbc_txtNomeAutor);
        
        JLabel lblDataIni = new JLabel("Data Início:");
        GridBagConstraints gbc_lblDataIni = new GridBagConstraints();
        gbc_lblDataIni.insets = new Insets(5, 5, 5, 5);
        gbc_lblDataIni.anchor = GridBagConstraints.EAST;
        gbc_lblDataIni.gridx = 5;
        gbc_lblDataIni.gridy = 0;
        pnlTop.add(lblDataIni, gbc_lblDataIni);
        
        try {
            txtDataIni = new JFormattedTextField(new MaskFormatter("##/##/####"));
        }
        catch (ParseException e2) {
            e2.printStackTrace();
        }
        txtDataIni.setColumns(15);
        GridBagConstraints gbc_txtDataIni = new GridBagConstraints();
        gbc_txtDataIni.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtDataIni.insets = new Insets(5, 5, 5, 5);
        gbc_txtDataIni.gridx = 6;
        gbc_txtDataIni.gridy = 0;
        pnlTop.add(txtDataIni, gbc_txtDataIni);
        
        JLabel lblDataFim = new JLabel("Data Fim:");
        GridBagConstraints gbc_lblDataFim = new GridBagConstraints();
        gbc_lblDataFim.insets = new Insets(5, 5, 5, 5);
        gbc_lblDataFim.anchor = GridBagConstraints.EAST;
        gbc_lblDataFim.gridx = 7;
        gbc_lblDataFim.gridy = 0;
        pnlTop.add(lblDataFim, gbc_lblDataFim);
        
        try {
            txtDataFim = new JFormattedTextField(new MaskFormatter("##/##/####"));
        }
        catch (ParseException e2) {
            e2.printStackTrace();
        }
        txtDataFim.setColumns(15);
        GridBagConstraints gbc_txtDataFim = new GridBagConstraints();
        gbc_txtDataFim.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtDataFim.insets = new Insets(5, 5, 0, 0);
        gbc_txtDataFim.gridx = 8;
        gbc_txtDataFim.gridy = 0;
        pnlTop.add(txtDataFim, gbc_txtDataFim);
                
        JLabel lblCelular = new JLabel("Celular:");
        GridBagConstraints gbc_lblCelular = new GridBagConstraints();
        gbc_lblCelular.insets = new Insets(5, 5, 5, 5);
        gbc_lblCelular.anchor = GridBagConstraints.EAST;
        gbc_lblCelular.gridx = 0;
        gbc_lblCelular.gridy = 1;
        pnlTop.add(lblCelular, gbc_lblCelular);
        
        try {
        	txtCelular = new JFormattedTextField(new MaskFormatter("(##)# ####-####"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        GridBagConstraints gbc_txtCelular = new GridBagConstraints();
        gbc_txtCelular.gridwidth = 2;
        gbc_txtCelular.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCelular.insets = new Insets(5, 5, 5, 5);
        gbc_txtCelular.gridx = 1;
        gbc_txtCelular.gridy = 1;
        pnlTop.add(txtCelular, gbc_txtCelular);
                
        JLabel lblFoneComercial = new JLabel("Telefone Comercial:");
        GridBagConstraints gbc_lblFoneComercial = new GridBagConstraints();
        gbc_lblFoneComercial.insets = new Insets(5, 5, 5, 5);
        gbc_lblFoneComercial.anchor = GridBagConstraints.EAST;
        gbc_lblFoneComercial.gridx = 3;
        gbc_lblFoneComercial.gridy = 1;
        pnlTop.add(lblFoneComercial, gbc_lblFoneComercial);
        
        try {
        	txtFoneComercial = new JFormattedTextField(new MaskFormatter("(##)####-####"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        GridBagConstraints gbc_txtFoneComercial = new GridBagConstraints();
        gbc_txtFoneComercial.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtFoneComercial.insets = new Insets(5, 5, 5, 5);
        gbc_txtFoneComercial.gridx = 4;
        gbc_txtFoneComercial.gridy = 1;
        pnlTop.add(txtFoneComercial, gbc_txtFoneComercial);
        
        
        JLabel lblTipo = new JLabel("Tipo:");
        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.insets = new Insets(5, 5, 5, 5);
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.gridx = 5;
        gbc_lblTipo.gridy = 1;
        pnlTop.add(lblTipo, gbc_lblTipo);
        
        
        (jcbTipo = new JComboBox<String>()).setToolTipText("Tipo");
        jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { Utils.Tipo.Busca.toString(), Utils.Tipo.Emêrgencia.toString() }));
        GridBagConstraints gbc_jcbTipo = new GridBagConstraints();
        gbc_jcbTipo.insets = new Insets(5, 5, 5, 5);
        gbc_jcbTipo.anchor = GridBagConstraints.EAST;
        gbc_jcbTipo.gridx = 6;
        gbc_jcbTipo.gridy = 1;
        pnlTop.add(jcbTipo, gbc_jcbTipo);
        
        btnIncluir = new JButton("Incluir");
        GridBagConstraints gbc_btnIncluir = new GridBagConstraints();
        gbc_btnIncluir.insets = new Insets(5, 5, 5, 5);
        gbc_btnIncluir.anchor = GridBagConstraints.EAST;
        gbc_btnIncluir.gridx = 7;
        gbc_btnIncluir.gridy = 1;
        pnlTop.add(btnIncluir, gbc_btnIncluir);
        
        btnExcluir = new JButton("Excluir");		
        GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
        gbc_btnExcluir.insets = new Insets(5, 5, 5, 0);
        gbc_btnExcluir.anchor = GridBagConstraints.WEST;
        gbc_btnExcluir.gridx = 8;
        gbc_btnExcluir.gridy = 1;
        pnlTop.add(btnExcluir, gbc_btnExcluir);
            
        
        ArrayList dadosAutor = new ArrayList();
        String[] colunasAutor = { "Nome", "Celular", "Telefone", "Tipo", "Data Início", "Data Fim", "idAutorizado" };
        boolean[] edicaoAutor = { true, true, true, false, true, true };
        
        
        modelo = new ModeloTabela(dadosAutor, colunasAutor, edicaoAutor);
        tabela = new JTable((TableModel)modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(2).setResizable(true);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(3).setResizable(true);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(4).setResizable(true);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(5).setResizable(true);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(10);
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(6)); //para remover da visualização da Jtable
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(3);
        tabela.setSelectionMode(0);
        JScrollPane rolagemTabelaAutor = new JScrollPane(tabela);
        this.add(rolagemTabelaAutor, "Center");

    }
}