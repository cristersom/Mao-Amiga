package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import controller.ListenerConsultaFrequencia;
import model.bo.TurmaBO;
import model.dao.TurmaDao;

public class FrameConsultaFrequencia extends FrameCadastro {
	public JComboBox<Integer> jcbAnoLetivo;
	public JComboBox<TurmaBO> jcbTurma;
	public JButton btnConsultar;
	public JTabbedPane tabbedPane;
    public JTable tabelaTurma, tabelaAluno;
    public ModeloTabela modeloTurma, modeloAluno;
    public JPanel pnlAluno;
    public JTextField txtNome;
	
	public FrameConsultaFrequencia() {
        this.setTitle("Relatório de Frequência");
        pnlCenter.setLayout(new BorderLayout(0, 0));
              
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
        	//se não existir curso cadastrado, não carrega nada no combobox
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
        	//se não existir turma cadastrada, não carrega nada no combobox
        }
		
        btnConsultar = new JButton("Consultar");
		pnlTop.add(btnConsultar);
        
		//Painel com abas
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.CENTER;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		pnlCenter.add(tabbedPane);
        
		//Aba Turma
		JPanel pnlTurma = new JPanel();
		pnlTurma.setLayout(new BorderLayout());
        ArrayList dados = new ArrayList();
        String[] colunas = {"ID Aluno", "Matrícula", "Nome do Aluno", "Faltas Manhã", "Faltas Tarde", "Total Faltas", "Total Aulas", "Frequência",};
        boolean[] edicao = { false, false, false, false, false };
        modeloTurma = new ModeloTabela(dados, colunas, edicao);
        tabelaTurma = new JTable((TableModel)this.modeloTurma);
        tabelaTurma.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(0).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(1).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(1).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(2).setPreferredWidth(350);
        tabelaTurma.getColumnModel().getColumn(2).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(3).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(3).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(4).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(4).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(5).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(5).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(6).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(6).setResizable(true);
        tabelaTurma.getColumnModel().getColumn(7).setPreferredWidth(10);
        tabelaTurma.getColumnModel().getColumn(7).setResizable(true);
        tabelaTurma.getTableHeader().setReorderingAllowed(false);
        tabelaTurma.setAutoResizeMode(4);
        tabelaTurma.setSelectionMode(0);
        JScrollPane rolagemTabela = new JScrollPane(this.tabelaTurma);
        pnlTurma.add(rolagemTabela, "Center");
        tabbedPane.addTab("Turma", null, pnlTurma, null);

		//Aba Aluno
		pnlAluno = new JPanel();
		pnlAluno.setLayout(new BorderLayout());
		
        //Painel superior ###########################################################################
		JPanel pnlTopIndividual = new JPanel();
       // pnlTop.setBorder(BorderFactory.createBevelBorder(1));
        GridBagLayout gbl_pnlTopIndividual = new GridBagLayout();
        gbl_pnlTopIndividual.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_pnlTopIndividual.columnWeights = new double[]{0.0, 1.0};
        pnlTopIndividual.setLayout(gbl_pnlTopIndividual);
        pnlAluno.add(pnlTopIndividual, BorderLayout.NORTH);
        
		JLabel lblAluno = new JLabel("Aluno:");
		GridBagConstraints gbc_lblAluno = new GridBagConstraints();
		gbc_lblAluno.insets = new Insets(5, 5, 5, 5);
		gbc_lblAluno.anchor = GridBagConstraints.WEST;
		gbc_lblAluno.gridheight = 5;
		gbc_lblAluno.gridx = 0;
		gbc_lblAluno.gridy = 0;
		pnlTopIndividual.add(lblAluno,gbc_lblAluno);
		
        txtNome = new JTextField();
        GridBagConstraints gbc_txtNome = new GridBagConstraints();
        gbc_txtNome.fill = 2;
        gbc_txtNome.gridwidth = 4;
        gbc_txtNome.insets = new Insets(5, 5, 5, 5);
        gbc_txtNome.gridx = 1;
        gbc_txtNome.gridy = 1;
        txtNome.setEditable(false);
        pnlTopIndividual.add(txtNome, gbc_txtNome);
		
        ArrayList dadosAluno = new ArrayList();
        String[] colunasAluno = {"","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        boolean[] edicaoAluno = { false, false, false, false, false };
        modeloAluno = new ModeloTabela(dadosAluno, colunasAluno, edicaoAluno);
        tabelaAluno = new JTable((TableModel)this.modeloAluno);
        
    	tabelaAluno.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabelaAluno.getColumnModel().getColumn(0).setResizable(true);
        for(int i=1; i<=31; i++) {
        	tabelaAluno.getColumnModel().getColumn(i).setPreferredWidth(2);
            tabelaAluno.getColumnModel().getColumn(i).setResizable(true);
        }
        tabelaAluno.setAutoResizeMode(4);
        tabelaAluno.setSelectionMode(0);
        JScrollPane rolagemTabela2 = new JScrollPane(this.tabelaAluno);
        pnlAluno.add(rolagemTabela2, "Center");
                
        tabbedPane.addTab("Aluno", null, pnlAluno, null);
  
        btnOk.setVisible(false);
        btnCancelar.setText("Sair");
        //Seta os listeners do formulário
        ListenerConsultaFrequencia listener = new ListenerConsultaFrequencia(this);
        jcbAnoLetivo.addActionListener((ActionListener)listener);
        jcbTurma.addActionListener((ActionListener)listener);
        btnConsultar.addActionListener((ActionListener)listener);
        tabbedPane.addChangeListener((ChangeListener)listener);
	}
}
