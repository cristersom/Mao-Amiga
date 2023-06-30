 package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controller.ListernerConsultaCidade;
import model.bo.CidadeBO;


public class FrameConsultaCidade extends FrameConsulta {
	public JTable tabela;
	public ModeloTabela modelo;
	public CidadeBO cidBO;
	public FrameCadastroCEP cadCep = null;

	
	public FrameConsultaCidade(FrameCadastroCEP cadCep) {
		this(); // invoca o construtor principal
		this.cadCep = cadCep;
	}
	
	public FrameConsultaCidade() {
		jcbconsultaPor.setModel(new DefaultComboBoxModel<String>(new String[] {"Cidade", "ID", "UF"}));
		setTitle("Consulta de Cidade");
	    // criação de um arranjo sem tamanho definido para inserção dinâmica de objetos
	    ArrayList dados = new ArrayList();
	    
	    // criação de um arranjo para os títulos no cabeçalho
	    String[] colunas = new String[] {"ID", "UF", "Cidade"};
	    
	    // criação de um arranjo para identificar se a célula é editável ou não
	    boolean[] edicao = {true, true, true};
	    
		modelo = new ModeloTabela(dados, colunas, edicao);
		tabela = new JTable(modelo);
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
					btnAlterar.doClick();
			}
		});
	    tabela.getColumnModel().getColumn(0).setPreferredWidth(25);
	    tabela.getColumnModel().getColumn(0).setResizable(true);
	    tabela.getColumnModel().getColumn(1).setPreferredWidth(25);
	    tabela.getColumnModel().getColumn(1).setResizable(true);
	    tabela.getColumnModel().getColumn(2).setPreferredWidth(600);
	    tabela.getColumnModel().getColumn(2).setResizable(true);
	    tabela.getTableHeader().setReorderingAllowed(false); 
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane rolagemTabela = new JScrollPane(tabela);
	    pnlMain.add(rolagemTabela, BorderLayout.CENTER);
	    
		// aqui é setado o controller desse frame
		ListernerConsultaCidade listener = new ListernerConsultaCidade(this);
		btnSelecionar.addActionListener(listener);
		btnConsultar.addActionListener(listener);
		btnAlterar.addActionListener(listener);
		btnIncluir.addActionListener(listener);
		btnExlcuir.addActionListener(listener);
		jcbconsultaPor.addActionListener(listener);
	}

}
