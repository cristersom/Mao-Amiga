package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controller.ListernerConsultaColaborador;
import model.bo.ColaboradorBO;

public class FrameConsultaColaborador extends FrameConsulta {
	public JTable tabela;
	public ModeloTabela modelo;
	public ColaboradorBO colaboradorBO;
	public FrameCadastroTurma cadTurma;
	
	public FrameConsultaColaborador(FrameCadastroTurma cadTurma) {
		this();
		this.cadTurma = cadTurma;
	}
	
	public FrameConsultaColaborador() {
		jcbconsultaPor.setModel(new DefaultComboBoxModel(new String[] { "Nome", "CPF", "Código" }));
		setTitle("Consulta Colaboradores");
		
		ArrayList dados = new ArrayList();
		String[] colunas = new String[] { "Código", "Nome", "CPF", "Endereço", "CEP" };
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
		tabela.getColumnModel().getColumn(0).setResizable(false);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(1).setResizable(false);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(2).setResizable(false);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(3).setResizable(false);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(4).setResizable(true);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rolagemTabela = new JScrollPane(tabela);
		pnlMain.add(rolagemTabela, BorderLayout.CENTER);

		// aqui é setado o controller desse frame
		ListernerConsultaColaborador listener = new ListernerConsultaColaborador(this);
		btnSelecionar.addActionListener(listener);
		btnConsultar.addActionListener(listener);
		btnAlterar.addActionListener(listener);
		btnIncluir.addActionListener(listener);
		btnExlcuir.addActionListener(listener);
	}
}
