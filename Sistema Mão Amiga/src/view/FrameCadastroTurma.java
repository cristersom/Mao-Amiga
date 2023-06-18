package view;

import java.awt.event.ActionListener;
import controller.ListenerCadastroTurma;
import javax.swing.JScrollPane;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.text.ParseException;
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
import model.bo.TurmaBO;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class FrameCadastroTurma extends JInternalFrame
{
    private JPanel pnlTurma;
    private JPanel pnlAlunos;
    protected JPanel pnlCenter;
    public JButton btnCancelar;
    public JButton btnOk;
    public JTextField txtTurma;
    public JTextField txtDescricao;
    public JTextField txtDataIni;
    public JTextField txtDataFim;
    public JComboBox<Integer> jcbAno;
    public JComboBox<String> jcbCurso;
    public int codTurma;
    int i;
    private JLabel lblCurso;
    private JLabel lblTurma;
    private JLabel lblDataIni;
    private JLabel lblDataFim;
    protected JButton btnConsultar;
    protected JButton btnIncluir;
    protected JButton btnAlterar;
    protected JButton btnExlcuir;
    protected JButton btnSair;
    public JButton btnSelecionar;
    public JTable tabela;
    public ModeloTabela modelo;
    public TurmaBO turmaBO;
    public FrameConsultaTurma consTurma;
    
    public FrameCadastroTurma(final FrameConsultaTurma consTurma) {
        this();
        this.consTurma = consTurma;
        this.codTurma = consTurma.turmaBO.getCodigo();
        this.jcbAno.setSelectedItem(consTurma.turmaBO.getAno());
        this.txtTurma.setText(consTurma.turmaBO.getTurma());
        this.txtDescricao.setText(consTurma.turmaBO.getDescricao());
    }
    
    public FrameCadastroTurma() {
        super("T\u00edtulo", true, true, true, true);
        this.consTurma = null;
        try {
            this.setMaximum(true);
        }
        catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        super.setBounds(100, 40, 600, 400);
        final JPanel pnlMain = new JPanel();
        pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(pnlMain);
        pnlMain.setLayout(new BorderLayout(0, 5));
        final JTabbedPane tabbedPane = new JTabbedPane(1);
        pnlMain.add(tabbedPane, "Center");
        (this.pnlTurma = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.pnlTurma.setLayout(new BorderLayout(0, 5));
        tabbedPane.addTab("Turma", null, this.pnlTurma, null);
        (this.pnlCenter = new JPanel()).setBorder(new LineBorder(Color.GRAY));
        this.pnlTurma.add(this.pnlCenter, "Center");
        final GridBagLayout gridCentral = new GridBagLayout();
        gridCentral.columnWidths = new int[2];
        gridCentral.rowHeights = new int[5];
        gridCentral.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.2, 0.0, 0.2 };
        gridCentral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        this.pnlCenter.setLayout(gridCentral);
        this.lblCurso = new JLabel("Curso:");
        final GridBagConstraints gbc_lblCurso = new GridBagConstraints();
        gbc_lblCurso.anchor = 13;
        gbc_lblCurso.insets = new Insets(5, 5, 5, 5);
        gbc_lblCurso.gridx = 0;
        gbc_lblCurso.gridy = 0;
        this.pnlCenter.add(this.lblCurso, gbc_lblCurso);
        this.jcbCurso = new JComboBox<String>();
        final GridBagConstraints gbc_jcbCurso = new GridBagConstraints();
        gbc_jcbCurso.insets = new Insets(5, 5, 5, 5);
        gbc_jcbCurso.fill = 2;
        gbc_jcbCurso.gridx = 1;
        gbc_jcbCurso.gridy = 0;
        this.pnlCenter.add(this.jcbCurso, gbc_jcbCurso);
        this.lblDataIni = new JLabel("Data In\u00edcio:");
        final GridBagConstraints gbc_lblDataIni = new GridBagConstraints();
        gbc_lblDataIni.anchor = 13;
        gbc_lblDataIni.insets = new Insets(5, 5, 5, 5);
        gbc_lblDataIni.gridx = 2;
        gbc_lblDataIni.gridy = 0;
        this.pnlCenter.add(this.lblDataIni, gbc_lblDataIni);
        try {
            this.txtDataIni = new JFormattedTextField(new MaskFormatter("##/##/####"));
        }
        catch (ParseException e2) {
            e2.printStackTrace();
        }
        final GridBagConstraints gbc_textDataIni = new GridBagConstraints();
        gbc_textDataIni.insets = new Insets(5, 5, 5, 0);
        gbc_textDataIni.fill = 2;
        gbc_textDataIni.gridx = 3;
        gbc_textDataIni.gridy = 0;
        this.pnlCenter.add(this.txtDataIni, gbc_textDataIni);
        this.lblDataFim = new JLabel("Data Fim:");
        final GridBagConstraints gbc_lblDataFim = new GridBagConstraints();
        gbc_lblDataFim.insets = new Insets(5, 5, 5, 5);
        gbc_lblDataFim.gridx = 4;
        gbc_lblDataFim.gridy = 0;
        this.pnlCenter.add(this.lblDataFim, gbc_lblDataFim);
        try {
            this.txtDataFim = new JFormattedTextField(new MaskFormatter("##/##/####"));
        }
        catch (ParseException e3) {
            e3.printStackTrace();
        }
        final GridBagConstraints gbc_textDataFim = new GridBagConstraints();
        gbc_textDataFim.insets = new Insets(5, 5, 5, 0);
        gbc_textDataFim.fill = 2;
        gbc_textDataFim.gridx = 5;
        gbc_textDataFim.gridy = 0;
        this.pnlCenter.add(this.txtDataFim, gbc_textDataFim);
        this.lblTurma = new JLabel("Turma:");
        final GridBagConstraints gbc_lblTurma = new GridBagConstraints();
        gbc_lblTurma.anchor = 13;
        gbc_lblTurma.insets = new Insets(5, 5, 5, 5);
        gbc_lblTurma.gridx = 0;
        gbc_lblTurma.gridy = 1;
        this.pnlCenter.add(this.lblTurma, gbc_lblTurma);
        this.txtTurma = new JTextField();
        final GridBagConstraints gbc_txtTurma = new GridBagConstraints();
        gbc_txtTurma.gridwidth = 4;
        gbc_txtTurma.anchor = 11;
        gbc_txtTurma.insets = new Insets(5, 5, 5, 5);
        gbc_txtTurma.fill = 2;
        gbc_txtTurma.gridx = 1;
        gbc_txtTurma.gridy = 1;
        this.pnlCenter.add(this.txtTurma, gbc_txtTurma);
        this.txtTurma.setColumns(10);
        final JLabel lblAno = new JLabel("Ano:");
        final GridBagConstraints gbc_lblAno = new GridBagConstraints();
        gbc_lblAno.insets = new Insets(5, 5, 5, 5);
        gbc_lblAno.anchor = 13;
        gbc_lblAno.gridx = 5;
        gbc_lblAno.gridy = 1;
        this.pnlCenter.add(lblAno, gbc_lblAno);
        this.jcbAno = new JComboBox<Integer>();
        final GridBagConstraints gbc_jcbAno = new GridBagConstraints();
        gbc_jcbAno.insets = new Insets(5, 5, 5, 0);
        gbc_jcbAno.fill = 2;
        gbc_jcbAno.gridx = 6;
        gbc_jcbAno.gridy = 1;
        this.i = 2000;
        while (this.i < 2100) {
            this.jcbAno.addItem(this.i);
            ++this.i;
        }
        this.jcbAno.setSelectedIndex(23);
        this.pnlCenter.add(this.jcbAno, gbc_jcbAno);
        final JLabel lblDescrio = new JLabel("Descri\u00e7\u00e3o:");
        final GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
        gbc_lblDescrio.insets = new Insets(5, 5, 5, 5);
        gbc_lblDescrio.anchor = 13;
        gbc_lblDescrio.gridx = 0;
        gbc_lblDescrio.gridy = 2;
        this.pnlCenter.add(lblDescrio, gbc_lblDescrio);
        this.txtDescricao = new JTextField();
        final GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
        gbc_txtDescricao.gridwidth = 6;
        gbc_txtDescricao.insets = new Insets(5, 5, 5, 0);
        gbc_txtDescricao.fill = 2;
        gbc_txtDescricao.gridx = 1;
        gbc_txtDescricao.gridy = 2;
        this.pnlCenter.add(this.txtDescricao, gbc_txtDescricao);
        this.txtDescricao.setColumns(10);
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(new BevelBorder(1, null, null, null, null));
        this.pnlTurma.add(pnlBottom, "South");
        (this.btnOk = new JButton("OK")).setFont(new Font("Tahoma", 0, 12));
        pnlBottom.add(this.btnOk);
        (this.btnCancelar = new JButton("Cancelar")).setFont(new Font("Tahoma", 0, 12));
        pnlBottom.add(this.btnCancelar);
        (this.pnlAlunos = new JPanel(new BorderLayout())).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Alunos", null, this.pnlAlunos, null);
        final ArrayList dados = new ArrayList();
        final String[] colunas = { "C\u00f3digo", "Nome", "CPF", "Nascimento", "Tipo", "Endere\u00e7o" };
        final boolean[] edicao = { true, true, true, true };
        this.modelo = new ModeloTabela(dados, colunas, edicao);
        (this.tabela = new JTable((TableModel)this.modelo)).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    FrameCadastroTurma.this.btnAlterar.doClick();
                }
            }
        });
        this.tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.tabela.getColumnModel().getColumn(0).setResizable(true);
        this.tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
        this.tabela.getColumnModel().getColumn(1).setResizable(true);
        this.tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        this.tabela.getColumnModel().getColumn(2).setResizable(true);
        this.tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
        this.tabela.getColumnModel().getColumn(3).setResizable(true);
        this.tabela.getTableHeader().setReorderingAllowed(false);
        this.tabela.setAutoResizeMode(3);
        this.tabela.setSelectionMode(0);
        final JScrollPane rolagemTabela = new JScrollPane(this.tabela);
        this.pnlAlunos.add(rolagemTabela, "Center");
        pnlBottom = new JPanel();
        pnlBottom.setBorder(BorderFactory.createBevelBorder(1));
        pnlBottom.add(this.btnSelecionar = new JButton("Selecionar"));
        pnlBottom.add(this.btnIncluir = new JButton("Incluir"));
        pnlBottom.add(this.btnAlterar = new JButton("Alterar"));
        pnlBottom.add(this.btnExlcuir = new JButton("Excluir"));
        this.pnlAlunos.add(pnlBottom, "South");
        final ListenerCadastroTurma listener = new ListenerCadastroTurma(this);
        this.btnOk.addActionListener((ActionListener)listener);
        this.btnCancelar.addActionListener((ActionListener)listener);
        this.btnSelecionar.addActionListener((ActionListener)listener);
        this.btnAlterar.addActionListener((ActionListener)listener);
        this.btnIncluir.addActionListener((ActionListener)listener);
        this.btnExlcuir.addActionListener((ActionListener)listener);
    }
}