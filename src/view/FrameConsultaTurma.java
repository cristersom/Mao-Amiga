package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.ListernerConsultaTurma;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import model.bo.TurmaBO;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class FrameConsultaTurma extends FrameConsulta
{
    public JTable tabela;
    public ModeloTabela modelo;
    public TurmaBO turmaBO = null;
    public FrameCadastroTurma cadTurma = null;
    
	public FrameConsultaTurma(FrameCadastroTurma cadTurma){
		this();
		this.cadTurma = cadTurma;
	}
    
    public FrameConsultaTurma() {
    	
		jcbconsultaPor.setModel(new DefaultComboBoxModel<String>(new String[] {"Turma", "ID", "Ano", "Descrição"}));
		setTitle("Consulta Turma");
	    // criação de um arranjo sem tamanho definido para inserção dinâmica de objetos
	    ArrayList dados = new ArrayList();
	    
	    // criação de um arranjo para os títulos no cabeçalho
	    String[] colunas = new String[] {"ID", "Turma", "Descrição", "Ano"};
	    
	    // criação de um arranjo para identificar se a célula é editável ou não
	    boolean[] edicao = {true, true, true, true};
	    
		modelo = new ModeloTabela(dados, colunas, edicao);
		tabela = new JTable(modelo);
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
					btnAlterar.doClick();
			}
		});
    	
        tabela.getColumnModel().getColumn(0).setPreferredWidth(20);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(350);
        tabela.getColumnModel().getColumn(2).setResizable(true);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(3).setResizable(true);
        tabela.getTableHeader().setReorderingAllowed(false); 
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane rolagemTabela = new JScrollPane(tabela);
	    pnlMain.add(rolagemTabela, BorderLayout.CENTER);
        
	    // aqui é setado o controller desse frame
        ListernerConsultaTurma listener = new ListernerConsultaTurma(this);
        btnSelecionar.addActionListener((ActionListener)listener);
        btnConsultar.addActionListener((ActionListener)listener);
        btnAlterar.addActionListener((ActionListener)listener);
        btnIncluir.addActionListener((ActionListener)listener);
        btnExlcuir.addActionListener((ActionListener)listener);
    }
}