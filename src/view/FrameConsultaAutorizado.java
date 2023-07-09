package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import controller.ListernerConsultaAutorizado;
import model.bo.AutorizadoBO;

public class FrameConsultaAutorizado extends FrameConsulta {
	public JTable tabela;
	public ModeloTabela modelo;
	public AutorizadoBO autorizadoBO;
	public FrameCadastroTurma cadTurma;
	
	public FrameConsultaAutorizado(FrameCadastroTurma cadTurma) {
		this();
		this.cadTurma = cadTurma;
	}
	
	public FrameConsultaAutorizado() {
		jcbconsultaPor.setModel(new DefaultComboBoxModel(new String[] { "Nome Autorizado", "Nome Aluno"}));
		setTitle("Consulta Responsável");
		
		ArrayList dados = new ArrayList();
		String[] colunas = new String[] { "Nome Aluno", "Nome Autorizado", "Celular", "Telefone", "Tipo", "Data Início", "Data Fim"};
		boolean[] edicao = { false, false, false, false, false  };

		modelo = new ModeloTabela(dados, colunas, edicao);
		tabela = new JTable(modelo);
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					btnAlterar.doClick();
			}
		});
		tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(0).setResizable(true);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(1).setResizable(true);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(2).setResizable(true);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(3).setResizable(true);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(4).setResizable(true);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(5).setResizable(true);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(6).setResizable(true);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rolagemTabela = new JScrollPane(tabela);
		pnlMain.add(rolagemTabela, BorderLayout.CENTER);

		btnSelecionar.setVisible(false);
		btnAlterar.setVisible(false);
		btnIncluir.setVisible(false);
		btnExlcuir.setVisible(false);
		
		// aqui é setado o controller desse frame
		ListernerConsultaAutorizado listener = new ListernerConsultaAutorizado(this);
		btnConsultar.addActionListener(listener);
	}
}
