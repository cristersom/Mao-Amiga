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
        final ArrayList dadosAutor = new ArrayList();
        final String[] colunasAutor = { "Nome", "Telefone", "Tipo" };
        final boolean[] edicaoAutor = { true, true, true };
        this.modelo = new ModeloTabela(dadosAutor, colunasAutor, edicaoAutor);
        this.tabela = new JTable((TableModel)this.modelo);
        this.tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.tabela.getColumnModel().getColumn(0).setResizable(true);
        this.tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.tabela.getColumnModel().getColumn(1).setResizable(true);
        this.tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
        this.tabela.getColumnModel().getColumn(2).setResizable(true);
        this.tabela.getTableHeader().setReorderingAllowed(false);
        this.tabela.setAutoResizeMode(3);
        this.tabela.setSelectionMode(0);
        final JScrollPane rolagemTabelaAutor = new JScrollPane(this.tabela);
        this.add(rolagemTabelaAutor, "Center");
        final JPanel pnlBottomAutor = new JPanel();
        pnlBottomAutor.setBorder(BorderFactory.createBevelBorder(1));
        final JLabel lblNomeAutor = new JLabel("Nome:");
        pnlBottomAutor.add(lblNomeAutor);
        pnlBottomAutor.add(this.txtNomeAutor = new JTextField());
        this.txtNomeAutor.setColumns(35);
        final JLabel lblTelefoneAutor = new JLabel("Telefone:");
        pnlBottomAutor.add(lblTelefoneAutor);
        pnlBottomAutor.add(this.txtTelefoneAutor = new JTextField());
        this.txtTelefoneAutor.setColumns(15);
        (this.jcbTipo = new JComboBox<String>()).setToolTipText("Tipo");
        this.jcbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Busca", "Emerg\u00eancia" }));
        pnlBottomAutor.add(this.jcbTipo);
        pnlBottomAutor.add(this.btnIncluir = new JButton("Incluir"));
        pnlBottomAutor.add(this.btnExcluir = new JButton("Excluir"));
        this.add(pnlBottomAutor, "South");
    }
}