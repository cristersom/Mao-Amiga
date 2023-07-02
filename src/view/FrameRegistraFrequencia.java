package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

public class FrameRegistraFrequencia extends FrameCadastro {    
	public JComboBox<String> jcbTurma, jcbProfessor;
    public JTable tabela;
    public ModeloTabela modelo;
	
    public FrameRegistraFrequencia() {
    	pnlCenter.setLayout(new BorderLayout(0, 0));
        this.setTitle("Registro de Presença");
        
        //Painel Superior
		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlCenter.add(pnlTop, BorderLayout.WEST);

		JLabel lblTurma = new JLabel("Turma:");
        pnlTop.add(lblTurma);
        jcbTurma = new JComboBox<String>();
		pnlTop.add(jcbTurma);
		
		JLabel lblProfessor = new JLabel("Professor:");
        pnlTop.add(lblProfessor);
        jcbProfessor = new JComboBox<String>();
		pnlTop.add(jcbProfessor);
		
		//Painel Central
		JPanel pnlAlunos = new JPanel(new BorderLayout());
        pnlAlunos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlCenter.add(pnlAlunos, BorderLayout.SOUTH);
        
        ArrayList dados = new ArrayList();
        String[] colunas = { "Matricula", "ID", "Aluno", "Manhã", "Tarde", "idTurma"};
        boolean[] edicao = { true, true, true, true };
        
        modelo = new ModeloTabela(dados, colunas, edicao);
        tabela = new JTable(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(0).setResizable(false);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setResizable(false);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(2).setResizable(false);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(5);
        tabela.getColumnModel().getColumn(3).setResizable(false);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(5);
        tabela.getColumnModel().getColumn(4).setResizable(false);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(10);
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(5)); //para remover da visualização da Jtable
        tabela.getTableHeader().setReorderingAllowed(false);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane rolagemTabela = new JScrollPane(tabela);
        pnlAlunos.add(rolagemTabela, "Center");

    }
}