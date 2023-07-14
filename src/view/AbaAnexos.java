package view;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AbaAnexos extends JPanel {
    public JButton btnAnexar, btnExcluir, btnAbrir;
    public JTable tabela;
    public ModeloTabela modelo;
    
    public AbaAnexos() {
        this.setLayout(new BorderLayout());
        
        ArrayList dados = new ArrayList();
        String[] colunas = { "Arquivo", "Data Inclusão", "idArquivo", "diretorio" };
        boolean[] edicao = { false, false, false, false };
        modelo = new ModeloTabela(dados, colunas, edicao);
        tabela = new JTable((TableModel)this.modelo);
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					btnAbrir.doClick();
			}
		});
        tabela.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(20);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(20);
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(2)); //para remover da visualização da Jtable
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(2));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(4);
        tabela.setSelectionMode(0);
        JScrollPane rolagemTabela = new JScrollPane(this.tabela);
        this.add(rolagemTabela, "Center");
        
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(BorderFactory.createBevelBorder(1));
        
        pnlBottom.add(this.btnAnexar = new JButton("Anexar Arquivo"));
        pnlBottom.add(this.btnExcluir = new JButton("Excluir Arquivo"));
        pnlBottom.add(this.btnAbrir = new JButton("Abrir Arquivo"));
        this.add(pnlBottom, "South");
    }
}