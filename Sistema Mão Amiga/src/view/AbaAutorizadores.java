package view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class AbaAutorizadores extends JPanel {
    public JTextField txtNomeAutor;
    public JTextField txtTelefoneAutor;
    public JComboBox<String> jcbTipo;
    public JButton btnIncluir;
    public JButton btnExcluir;
    public JTable tabela;
    public ModeloTabela modelo;
    
    public AbaAutorizadores() {
        this.setLayout(new BorderLayout());
        
        ArrayList dadosAutor = new ArrayList();
        String[] colunasAutor = { "Nome", "Telefone", "Tipo" };
        boolean[] edicaoAutor = { true, true, true };
        modelo = new ModeloTabela(dadosAutor, colunasAutor, edicaoAutor);
        tabela = new JTable((TableModel)modelo);
        
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(2).setResizable(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(3);
        tabela.setSelectionMode(0);
        
        JScrollPane rolagemTabelaAutor = new JScrollPane(tabela);
        this.add(rolagemTabelaAutor, "Center");
        JPanel pnlBottomAutor = new JPanel();
        pnlBottomAutor.setBorder(BorderFactory.createBevelBorder(1));
        JLabel lblNomeAutor = new JLabel("Nome:");
        pnlBottomAutor.add(lblNomeAutor);
        pnlBottomAutor.add(txtNomeAutor = new JTextField());
        txtNomeAutor.setColumns(35);
        
        JLabel lblTelefoneAutor = new JLabel("Telefone:");
        pnlBottomAutor.add(lblTelefoneAutor);
        pnlBottomAutor.add(txtTelefoneAutor = new JTextField());
        txtTelefoneAutor.setColumns(15);
        (jcbTipo = new JComboBox<String>()).setToolTipText("Tipo");
        jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Busca", "Emergência" }));
        pnlBottomAutor.add(jcbTipo);
        pnlBottomAutor.add(btnIncluir = new JButton("Incluir"));
        pnlBottomAutor.add(btnExcluir = new JButton("Excluir"));
        this.add(pnlBottomAutor, "South");
    }
}
