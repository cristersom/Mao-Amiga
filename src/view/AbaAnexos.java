package view;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class AbaAnexos extends JPanel {
    public JTextField txtArquivo;
    public JTextField txtDescricao;
    public JButton btnBuscar;
    public JButton btnAdicionar;
    public JButton btnRemover;
    public JTable tabela;
    public ModeloTabela modelo;
    
    public AbaAnexos() {
        this.setLayout(new BorderLayout());
        
        ArrayList dados = new ArrayList();
        String[] colunas = { "Descrição", "Arquivo", "Data" };
        boolean[] edicao = { true, true, true };
        modelo = new ModeloTabela(dados, colunas, edicao);
        tabela = new JTable((TableModel)this.modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(2).setResizable(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(3);
        tabela.setSelectionMode(0);
        JScrollPane rolagemTabela = new JScrollPane(this.tabela);
        this.add(rolagemTabela, "Center");
        
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(BorderFactory.createBevelBorder(1));
        pnlBottom.add(txtArquivo = new JTextField());
        txtArquivo.setEditable(false);
        txtArquivo.setColumns(25);
        
        pnlBottom.add(btnBuscar = new JButton("Buscar"));
        JLabel lblDescricao = new JLabel("Descrição:");
        pnlBottom.add(lblDescricao);
        pnlBottom.add(this.txtDescricao = new JTextField());
        txtDescricao.setColumns(25);
        
        pnlBottom.add(this.btnAdicionar = new JButton("Adicionar"));
        pnlBottom.add(this.btnRemover = new JButton("Remover"));
        this.add(pnlBottom, "South");
    }
}