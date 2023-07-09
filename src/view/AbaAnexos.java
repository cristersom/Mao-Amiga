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
import javax.swing.JFileChooser;

public class AbaAnexos extends JPanel {
    public JTextField txtDescricao;
    public JButton btnAnexar, btnExcluir, btnAbrir;
    public JTable tabela;
    public ModeloTabela modelo;
    
    public AbaAnexos() {
        this.setLayout(new BorderLayout());
        
        ArrayList dados = new ArrayList();
        String[] colunas = { "Nome", "Descrição", "Data Inclusão", "idArquivo", "diretorio" };
        boolean[] edicao = { false, false, false, false, false };
        modelo = new ModeloTabela(dados, colunas, edicao);
        tabela = new JTable((TableModel)this.modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(2).setResizable(true);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(10);
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(3)); //para remover da visualização da Jtable
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(3));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(4);
        tabela.setSelectionMode(0);
        JScrollPane rolagemTabela = new JScrollPane(this.tabela);
        this.add(rolagemTabela, "Center");
        
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(BorderFactory.createBevelBorder(1));
        
        JLabel lblDescricao = new JLabel("Descrição:");
        pnlBottom.add(lblDescricao);
        pnlBottom.add(this.txtDescricao = new JTextField());
        txtDescricao.setColumns(25);
        
        pnlBottom.add(this.btnAnexar = new JButton("Anexar Arquivo"));
        pnlBottom.add(this.btnExcluir = new JButton("Excluir Arquivo"));
        pnlBottom.add(this.btnAbrir = new JButton("Abrir Arquivo"));
        this.add(pnlBottom, "South");
    }
}