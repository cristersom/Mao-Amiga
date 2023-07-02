package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import controller.ListernerConsultaCurso;
import model.bo.CursoBO;


public class FrameConsultaCurso extends FrameConsulta {
	public JTable tabela;
	public ModeloTabela modelo;
	public CursoBO cursoBO;
	public FrameCadastroTurma cadTurma = null;

	public FrameConsultaCurso (FrameCadastroTurma cadTurma) {
		this();
		this.cadTurma = cadTurma;
	}
	
	public FrameConsultaCurso() {
		jcbconsultaPor.setModel(new DefaultComboBoxModel<String>(new String[] {"Curso", "ID", "Descrição"}));
		setTitle("Consulta Curso");
		
		ArrayList dados = new ArrayList();
		String[] colunas = new String[] {"ID", "Curso", "Descrição"};
		boolean[] edicao = {false, false, false};
		
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
	    tabela.getColumnModel().getColumn(0).setResizable(false);
	    tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
	    tabela.getColumnModel().getColumn(1).setResizable(false);
	    tabela.getColumnModel().getColumn(2).setPreferredWidth(400);
	    tabela.getColumnModel().getColumn(2).setResizable(true);
	    tabela.getTableHeader().setReorderingAllowed(false); 
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane rolagemTabela = new JScrollPane(tabela);
	    pnlMain.add(rolagemTabela, BorderLayout.CENTER);

		// aqui é setado o controller desse frame
		ListernerConsultaCurso listener = new ListernerConsultaCurso(this);
		btnSelecionar.addActionListener(listener);
		btnConsultar.addActionListener(listener);
		btnAlterar.addActionListener(listener);
		btnIncluir.addActionListener(listener);
		btnExlcuir.addActionListener(listener);
	}
}
