package view;

import java.awt.event.ActionListener;
import controller.ListenerCadastroTurma;
import javax.swing.JScrollPane;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JTabbedPane;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.beans.PropertyVetoException;

import model.bo.CursoBO;
import model.bo.TurmaBO;
import model.dao.CursoDao;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class FrameCadastroTurma extends JInternalFrame
{
    private JPanel pnlTurma, pnlAlunos;
    protected JPanel pnlCenter;
    public JButton btnCancelar, btnOk, btnSelecionar;
    public JTextField txtTurma, txtDescricao, txtDataIni, txtDataFim;
    public JComboBox<Integer> jcbAno;
    public JComboBox<String> jcbCurso;
    public List<CursoBO> listCursoDao;
    public int codTurma;
    int i;
    private JLabel lblCurso, lblTurma, lblDataIni, lblDataFim;
    protected JButton btnConsultar, btnIncluir, btnAlterar, btnExlcuir, btnSair;
    public JTable tabela;
    public ModeloTabela modelo;
    
    public TurmaBO turmaBO;
    public FrameConsultaTurma consTurma = null;
    
    public FrameCadastroTurma(FrameConsultaTurma consTurma) {
        this();
        this.consTurma = consTurma;
        //this.jcbCurso.setSelectedIndex(consTurma.turmaBO.curso.getCodigo());
        this.codTurma = consTurma.turmaBO.getCodigo();
        this.jcbAno.setSelectedItem(consTurma.turmaBO.getAno());
        this.txtTurma.setText(consTurma.turmaBO.getTurma());
        this.txtDescricao.setText(consTurma.turmaBO.getDescricao());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // HH:mm:ss
        this.txtDataIni.setText(sdf.format(consTurma.turmaBO.getDataInicio().getTime()));
        this.txtDataFim.setText(sdf.format(consTurma.turmaBO.getDataFim().getTime()));
        //busca o indice do combobox
        int i;
        CursoDao cursoDao = new CursoDao();
        List<CursoBO> list = cursoDao.consultaPorDescricao("");
        for (i = 0; i < list.size(); i++) {
        	if(list.get(i).getCodigo() == consTurma.turmaBO.curso.getCodigo())
        	break;
        }
        this.jcbCurso.setSelectedIndex(i);
        
    }
    
    public FrameCadastroTurma() {
        super("Cadastro de Turma", true, true, true, true);
        this.consTurma = null;
        try {
            this.setMaximum(true);
        }
        catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        super.setBounds(100, 40, 600, 400);
        
        JPanel pnlMain = new JPanel();
        pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(pnlMain);
        pnlMain.setLayout(new BorderLayout(0, 5));
        
        JTabbedPane tabbedPane = new JTabbedPane(1);
        pnlMain.add(tabbedPane, "Center");
        
        (pnlTurma = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        pnlTurma.setLayout(new BorderLayout(0, 5));
        tabbedPane.addTab("Turma", null, pnlTurma, null);
        (pnlCenter = new JPanel()).setBorder(new LineBorder(Color.GRAY));
        pnlTurma.add(pnlCenter, "Center");
        
        GridBagLayout gridCentral = new GridBagLayout();
        gridCentral.columnWidths = new int[2];
        gridCentral.rowHeights = new int[5];
        gridCentral.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.2, 0.0, 0.2 };
        gridCentral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        pnlCenter.setLayout(gridCentral);
        
        lblCurso = new JLabel("Curso:");
        GridBagConstraints gbc_lblCurso = new GridBagConstraints();
        gbc_lblCurso.anchor = 13;
        gbc_lblCurso.insets = new Insets(5, 5, 5, 5);
        gbc_lblCurso.gridx = 0;
        gbc_lblCurso.gridy = 0;
        pnlCenter.add(lblCurso, gbc_lblCurso);
        
        CursoDao cursoDao = new CursoDao();
        jcbCurso = new JComboBox<String>();
        
        try {
            listCursoDao = cursoDao.consultaPorDescricao("");
            for (int i = 0; i < listCursoDao.size(); i++)
            	jcbCurso.addItem(listCursoDao.get(i).getCurso());
        } catch (NullPointerException e){
        	//se não existir curso cadastrodo, não carrega nada no combobox
        }
        
        GridBagConstraints gbc_jcbCurso = new GridBagConstraints();
        gbc_jcbCurso.insets = new Insets(5, 5, 5, 5);
        gbc_jcbCurso.fill = 2;
        gbc_jcbCurso.gridx = 1;
        gbc_jcbCurso.gridy = 0;
        pnlCenter.add(jcbCurso, gbc_jcbCurso);
        
        lblDataIni = new JLabel("Data Início:");
        GridBagConstraints gbc_lblDataIni = new GridBagConstraints();
        gbc_lblDataIni.anchor = 13;
        gbc_lblDataIni.insets = new Insets(5, 5, 5, 5);
        gbc_lblDataIni.gridx = 2;
        gbc_lblDataIni.gridy = 0;
        pnlCenter.add(lblDataIni, gbc_lblDataIni);
        try {
            txtDataIni = new JFormattedTextField(new MaskFormatter("##/##/####"));
        }
        catch (ParseException e2) {
            e2.printStackTrace();
        }
        GridBagConstraints gbc_textDataIni = new GridBagConstraints();
        gbc_textDataIni.insets = new Insets(5, 5, 5, 0);
        gbc_textDataIni.fill = 2;
        gbc_textDataIni.gridx = 3;
        gbc_textDataIni.gridy = 0;
        pnlCenter.add(txtDataIni, gbc_textDataIni);
        
        lblDataFim = new JLabel("Data Fim:");
        GridBagConstraints gbc_lblDataFim = new GridBagConstraints();
        gbc_lblDataFim.insets = new Insets(5, 5, 5, 5);
        gbc_lblDataFim.gridx = 4;
        gbc_lblDataFim.gridy = 0;
        pnlCenter.add(lblDataFim, gbc_lblDataFim);
        try {
            txtDataFim = new JFormattedTextField(new MaskFormatter("##/##/####"));
        }
        catch (ParseException e3) {
            e3.printStackTrace();
        }
        GridBagConstraints gbc_textDataFim = new GridBagConstraints();
        gbc_textDataFim.insets = new Insets(5, 5, 5, 0);
        gbc_textDataFim.fill = 2;
        gbc_textDataFim.gridx = 5;
        gbc_textDataFim.gridy = 0;
        pnlCenter.add(txtDataFim, gbc_textDataFim);
        
        lblTurma = new JLabel("Turma:");
        GridBagConstraints gbc_lblTurma = new GridBagConstraints();
        gbc_lblTurma.anchor = 13;
        gbc_lblTurma.insets = new Insets(5, 5, 5, 5);
        gbc_lblTurma.gridx = 0;
        gbc_lblTurma.gridy = 1;
        pnlCenter.add(lblTurma, gbc_lblTurma);
        
        txtTurma = new JTextField();
        GridBagConstraints gbc_txtTurma = new GridBagConstraints();
        gbc_txtTurma.gridwidth = 4;
        gbc_txtTurma.anchor = 11;
        gbc_txtTurma.insets = new Insets(5, 5, 5, 5);
        gbc_txtTurma.fill = 2;
        gbc_txtTurma.gridx = 1;
        gbc_txtTurma.gridy = 1;
        pnlCenter.add(txtTurma, gbc_txtTurma);
        txtTurma.setColumns(10);
        
        JLabel lblAno = new JLabel("Ano:");
        GridBagConstraints gbc_lblAno = new GridBagConstraints();
        gbc_lblAno.insets = new Insets(5, 5, 5, 5);
        gbc_lblAno.anchor = 13;
        gbc_lblAno.gridx = 5;
        gbc_lblAno.gridy = 1;
        pnlCenter.add(lblAno, gbc_lblAno);
        
        jcbAno = new JComboBox<Integer>();
        GridBagConstraints gbc_jcbAno = new GridBagConstraints();
        gbc_jcbAno.insets = new Insets(5, 5, 5, 0);
        gbc_jcbAno.fill = 2;
        gbc_jcbAno.gridx = 6;
        gbc_jcbAno.gridy = 1;
        i = 2000;
        while (i < 2100) {
            jcbAno.addItem(i);
            ++i;
        }
        jcbAno.setSelectedIndex(23);
        pnlCenter.add(jcbAno, gbc_jcbAno);
        
        JLabel lblDescrio = new JLabel("Descrição:");
        GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
        gbc_lblDescrio.insets = new Insets(5, 5, 5, 5);
        gbc_lblDescrio.anchor = 13;
        gbc_lblDescrio.gridx = 0;
        gbc_lblDescrio.gridy = 2;
        pnlCenter.add(lblDescrio, gbc_lblDescrio);
        txtDescricao = new JTextField();
        GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
        gbc_txtDescricao.gridwidth = 6;
        gbc_txtDescricao.insets = new Insets(5, 5, 5, 0);
        gbc_txtDescricao.fill = 2;
        gbc_txtDescricao.gridx = 1;
        gbc_txtDescricao.gridy = 2;
        pnlCenter.add(txtDescricao, gbc_txtDescricao);
        txtDescricao.setColumns(10);
        
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(new BevelBorder(1, null, null, null, null));
        pnlTurma.add(pnlBottom, "South");
        
        (btnOk = new JButton("OK")).setFont(new Font("Tahoma", 0, 12));
        pnlBottom.add(btnOk);
        
        (btnCancelar = new JButton("Cancelar")).setFont(new Font("Tahoma", 0, 12));
        pnlBottom.add(btnCancelar);
        
        (pnlAlunos = new JPanel(new BorderLayout())).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Alunos", null, pnlAlunos, null);
        
        ArrayList dados = new ArrayList();
        String[] colunas = { "Código", "Nome", "CPF", "Nascimento", "Tipo", "Endereço" };
        boolean[] edicao = { true, true, true, true };
        
        modelo = new ModeloTabela(dados, colunas, edicao);
        (tabela = new JTable((TableModel)modelo)).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnAlterar.doClick();
                }
            }
        });
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(0).setResizable(true);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(1).setResizable(true);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(2).setResizable(true);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(3).setResizable(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(3);
        tabela.setSelectionMode(0);
        JScrollPane rolagemTabela = new JScrollPane(tabela);
        
        pnlAlunos.add(rolagemTabela, "Center");
        pnlBottom = new JPanel();
        pnlBottom.setBorder(BorderFactory.createBevelBorder(1));
        pnlBottom.add(btnSelecionar = new JButton("Selecionar"));
        pnlBottom.add(btnIncluir = new JButton("Incluir"));
        pnlBottom.add(btnAlterar = new JButton("Alterar"));
        pnlBottom.add(btnExlcuir = new JButton("Excluir"));
        pnlAlunos.add(pnlBottom, "South");
        
        ListenerCadastroTurma listener = new ListenerCadastroTurma(this);
        btnOk.addActionListener((ActionListener)listener);
        btnCancelar.addActionListener((ActionListener)listener);
        btnSelecionar.addActionListener((ActionListener)listener);
        btnAlterar.addActionListener((ActionListener)listener);
        btnIncluir.addActionListener((ActionListener)listener);
        btnExlcuir.addActionListener((ActionListener)listener);
        //jcbCurso.addActionListener((ActionListener)listener);
    }
    
}