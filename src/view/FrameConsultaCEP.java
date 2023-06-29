package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controller.ListenerConsultaCEP;
import model.bo.CepBO;

public class FrameConsultaCEP extends FrameConsulta {
	public JTable tabela;
	public ModeloTabela modelo;
	public CepBO cepBO;
	public FrameCadastroCEP cadCep = null;
	public FrameCadastroAluno cadAluno = null;
	public FrameCadastroColaborador cadColaborador = null;
	public FrameCadastroResponsavel cadResponsavel = null;

	public FrameConsultaCEP(FrameCadastroCEP cadCep){
		this();
		this.cadCep = cadCep;
	}
	
	public FrameConsultaCEP(FrameCadastroAluno cadAluno){
		this();
		this.cadAluno = cadAluno;
	}

	public FrameConsultaCEP(FrameCadastroColaborador cadColaborador) {
		this();
		this.cadColaborador = cadColaborador;
	}
	
	public FrameConsultaCEP(FrameCadastroResponsavel cadResponsavel) {
		this();
		this.cadResponsavel = cadResponsavel;
	}
	
	public FrameConsultaCEP() {
		jcbconsultaPor.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Cidade", "CEP", "Logradouro", "Bairro", "UF" }));
		setTitle("Consulta de CEP");
		// criação de um arranjo sem tamanho definido para inserção dinâmica de objetos
		ArrayList dados = new ArrayList();

		// criação de um arranjo para os títulos no cabeçalho
		String[] colunas = new String[] { "CEP", "Cidade", "Logradouro", "Bairro", "UF", "Código"};

		// criação de um arranjo para identificar se a célula é editável ou não
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
		tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(0).setResizable(false);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
		tabela.getColumnModel().getColumn(1).setResizable(false);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(250);
		tabela.getColumnModel().getColumn(2).setResizable(false);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(3).setResizable(false);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(20);
		tabela.getColumnModel().getColumn(4).setResizable(false);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(20);
		tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(5)); //para remover da visualização da Jtable
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rolagemTabela = new JScrollPane(tabela);
		pnlMain.add(rolagemTabela, BorderLayout.CENTER);

		// aqui é setado o controller desse frame
		ListenerConsultaCEP listener = new ListenerConsultaCEP(this);
		btnSelecionar.addActionListener(listener);
		btnConsultar.addActionListener(listener);
		btnAlterar.addActionListener(listener);
		btnIncluir.addActionListener(listener);
		btnExlcuir.addActionListener(listener);
	}
}
