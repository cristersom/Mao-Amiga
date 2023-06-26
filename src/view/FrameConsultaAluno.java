package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controller.ListernerConsultaAluno;
import model.bo.AlunoBO;

public class FrameConsultaAluno extends FrameConsulta {
	public JTable tabela;
	public ModeloTabela modelo;
	public AlunoBO alunoBO;
	public FrameCadastroTurma cadTurma = null;
	
	public FrameConsultaAluno(FrameCadastroTurma cadTurma) {
		this();
		this.cadTurma = cadTurma;
	}
	
	public FrameConsultaAluno() {
		jcbconsultaPor.setModel(new DefaultComboBoxModel(new String[] { "Nome", "CPF", "C�digo" }));
		setTitle("Consulta Alunos");
		
		ArrayList dados = new ArrayList();
		String[] colunas = new String[] { "C�digo", "Nome", "CPF", "Nascimento", /*"Tipo",*/ "Endere�o" };
		boolean[] edicao = { true, true, true, true, true };

		modelo = new ModeloTabela(dados, colunas, edicao);
		tabela = new JTable(modelo);
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					btnAlterar.doClick();
			}
		});
		tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(0).setResizable(true);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(1).setResizable(true);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(2).setResizable(true);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(3).setResizable(true);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(4).setResizable(true);
		//tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
		//tabela.getColumnModel().getColumn(5).setResizable(true);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rolagemTabela = new JScrollPane(tabela);
		pnlMain.add(rolagemTabela, BorderLayout.CENTER);

		// aqui � setado o controller desse frame
		ListernerConsultaAluno listener = new ListernerConsultaAluno(this);
		btnSelecionar.addActionListener(listener);
		btnConsultar.addActionListener(listener);
		btnAlterar.addActionListener(listener);
		btnIncluir.addActionListener(listener);
		btnExlcuir.addActionListener(listener);
	}

}
