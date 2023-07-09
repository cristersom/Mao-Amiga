package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import controller.ListenerRegistraAula;
import model.bo.TurmaBO;
import model.bo.TurmaColaboradorBO;
import model.dao.TurmaColaboradorDao;
import model.dao.TurmaDao;
import javax.swing.JTextArea;

public class FrameRegistraAula extends FrameCadastro {
	public int idAula;
	public JButton btnBuscar;
	public JComboBox<Integer> jcbAnoLetivo, jcbDia, jcbAno;
	public JComboBox<TurmaBO> jcbTurma;
	public JComboBox<TurmaColaboradorBO> jcbProfessor;
	public JComboBox<String> jcbMes;
	public JTextArea txtConteudoMinistrado;
    public JTable tabela;
    public ModeloTabela modelo;
	
    public FrameRegistraAula() {
    	pnlCenter.setLayout(new BorderLayout(0, 0));
        this.setTitle("Registro de Aula");
        
        //Painel Superior #######################################################################################
		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlCenter.add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblAnoLetivo = new JLabel("Ano Letivo:");
        pnlTop.add(lblAnoLetivo);
        jcbAnoLetivo = new JComboBox<Integer>();
		pnlTop.add(jcbAnoLetivo);
		
		TurmaDao turmaDao = new TurmaDao();
		List<Integer> anoList;
        try {
        	anoList = turmaDao.consultaAnoTurmas();
            for (int i = 0; i < anoList.size(); i++)
            	jcbAnoLetivo.addItem(anoList.get(i));
        } catch (NullPointerException e){
        	//se não existir curso cadastrodo, não carrega nada no combobox
        }
		
		JLabel lblTurma = new JLabel("Turma:");
        pnlTop.add(lblTurma);
        jcbTurma = new JComboBox<TurmaBO>();
		pnlTop.add(jcbTurma);
		
		List<TurmaBO> turmaList;
        try {
        	turmaList = turmaDao.consultaPorAno(Integer.parseInt(jcbAnoLetivo.getSelectedItem().toString()));
            for (int i = 0; i < turmaList.size(); i++)
            	jcbTurma.addItem(turmaList.get(i));
        } catch (NullPointerException e1){
        	//se não existir turma cadastroda, não carrega nada no combobox
        }
				
		JLabel lblProfessor = new JLabel("Professor:");
        pnlTop.add(lblProfessor);
        jcbProfessor = new JComboBox<TurmaColaboradorBO>();
		pnlTop.add(jcbProfessor);
		
		jcbProfessor.addItem(null);//para obrigar a seleção de professor para registro de aula
		TurmaColaboradorDao turmaColaboradorDao = new TurmaColaboradorDao();
		List<TurmaColaboradorBO> professorList;
        try {
        	TurmaBO turmaBO = (TurmaBO)jcbTurma.getSelectedItem();
        	professorList = turmaColaboradorDao.consultaProfessores(turmaBO.getId());
            for (int i = 0; i < professorList.size(); i++)
            	jcbProfessor.addItem(professorList.get(i));
        } catch (NullPointerException e1){
        	//se não existir turma cadastroda, não carrega nada no combobox
        }
		
		JLabel lblData = new JLabel("Data:");
        pnlTop.add(lblData);
        
        jcbDia = new JComboBox<Integer>();
        int i = 1;
        while (i < 32) {
            jcbDia.addItem(i);
            ++i;
        }
        pnlTop.add(jcbDia);
        
        jcbMes = new JComboBox<String>();
		jcbMes.setModel(new DefaultComboBoxModel<String>(new String[] { "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL",
				"MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO" }));
		pnlTop.add(jcbMes);
        
		jcbAno = new JComboBox<Integer>();
		for (i = 1900; i < 2100; i++)
			jcbAno.addItem(i);
		jcbAno.setSelectedIndex(123);
		pnlTop.add(jcbAno);
		
		//Obtem a data atual
		Calendar dataAtual = Calendar.getInstance();
		Integer ano = dataAtual.get(Calendar.YEAR);
		Integer mes = dataAtual.get(Calendar.MONTH);
		Integer diaDoMes = dataAtual.get(Calendar.DAY_OF_MONTH);
		//seta o dia com a data atual
		jcbDia.setSelectedIndex(diaDoMes - 1);
		jcbMes.setSelectedIndex(mes);
		jcbAno.setSelectedIndex(ano-1900);
		
        btnBuscar = new JButton("Buscar Alunos");
		pnlTop.add(btnBuscar);
        
		//Painel Central ##########################################################################################
		JPanel pnlAlunos = new JPanel(new BorderLayout());
        pnlAlunos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        pnlCenter.add(pnlAlunos, BorderLayout.CENTER);
                
        ArrayList dados = new ArrayList();
        //dados = (ArrayList) Arrays.asList("","","","Boolean","Boolena","");
        String[] colunas = { "Matricula", "ID", "Nome do Aluno", "Presente Manhã?", "Presente Tarde?", "idTurma"};
        boolean[] edicao = { false, false, false, true, true, false };
        modelo = new ModeloTabela(dados, colunas, edicao);
        tabela = new JTable(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(0).setResizable(false);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setResizable(false);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(2).setResizable(false);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(1);
        tabela.getColumnModel().getColumn(3).setResizable(false);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(1);
        tabela.getColumnModel().getColumn(4).setResizable(false);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(10);
        tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(5)); //para remover da visualização da Jtable
        tabela.getTableHeader().setReorderingAllowed(false);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane rolagemTabela = new JScrollPane(tabela);
        pnlAlunos.add(rolagemTabela, BorderLayout.CENTER);
        
        //Painel inferior ###########################################################################
		JPanel pnlBottom = new JPanel();
       // pnlTop.setBorder(BorderFactory.createBevelBorder(1));
        GridBagLayout gbl_pnlBottom = new GridBagLayout();
        gbl_pnlBottom.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_pnlBottom.columnWeights = new double[]{0.0, 1.0};
        pnlBottom.setLayout(gbl_pnlBottom);
		pnlCenter.add(pnlBottom, BorderLayout.SOUTH);
		
		
		JLabel lblConteudo = new JLabel("Conteúdo Ministrado:");
		GridBagConstraints gbc_lblConteudo = new GridBagConstraints();
		gbc_lblConteudo.insets = new Insets(5, 5, 5, 5);
		gbc_lblConteudo.anchor = GridBagConstraints.WEST;
		gbc_lblConteudo.gridheight = 5;
		gbc_lblConteudo.gridx = 0;
		gbc_lblConteudo.gridy = 0;
		pnlBottom.add(lblConteudo,gbc_lblConteudo);
        
        		
        		txtConteudoMinistrado = new JTextArea();	
        		txtConteudoMinistrado.setRows(5);
        		txtConteudoMinistrado.setLineWrap(true);
        		GridBagConstraints gbc_txtConteudoMinistrado = new GridBagConstraints();
        		gbc_txtConteudoMinistrado.insets = new Insets(5, 5, 5, 5);
        		gbc_txtConteudoMinistrado.fill = GridBagConstraints.BOTH;
        		gbc_txtConteudoMinistrado.gridx = 1;
        		gbc_txtConteudoMinistrado.gridy = 0;
        		pnlBottom.add(txtConteudoMinistrado, gbc_txtConteudoMinistrado);

        
        btnOk.setText("Registrar Aula");
        //Seta os listeners do formulário
        ListenerRegistraAula listener = new ListenerRegistraAula(this);
        btnOk.addActionListener((ActionListener)listener);
        btnCancelar.addActionListener((ActionListener)listener);
        jcbAnoLetivo.addActionListener((ActionListener)listener);
        jcbTurma.addActionListener((ActionListener)listener);
        jcbDia.addActionListener((ActionListener)listener);
        jcbMes.addActionListener((ActionListener)listener);
        jcbAno.addActionListener((ActionListener)listener);
        btnBuscar.addActionListener((ActionListener)listener);
    }
}