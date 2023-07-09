package view;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controller.ListenerFormulario;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

public class SistemaMatricula extends JFrame
{
    public JDesktopPane DPane;
    public JButton btnAluno, btnCidade, btnResponsavel, btnCep, btnTurma, btnColaborador, btnAutorizado, btnCurso, btnAula;
    private JMenuBar menuBar;
    private JMenu menuConsulta, menuCadastro, menuSistema;
    private JToolBar toolBar;
    private JButton btnSair;
    public JMenuItem itemSair, itemConsultaAluno, itemConsultaCidade, itemConsultaResponsavel, itemConsultaCep, itemCadastroAluno
         	       , itemCadastroResponsavel, itemCadastroCidade, itemCadastroCep, itemCadastroTurma, itemConsultaTurma
         	       , itemConsultaAutorizado, itemCadastroAutorizado, itemConsultaColaborador, itemCadastroColaborador, itemConsultaCurso
         	       , itemCadastroCurso, itemConsultaAula, itemRegistraAula;
    
    public SistemaMatricula() {
    	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.showConfirmDialog(DPane,"Deseja encerrar a aplicação?",
						"Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
			}
		});
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
			
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1024, 768);
        setTitle("Escola Mão Amiga - Bento Gonçalves");
        
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuSistema = new JMenu("Sistema");
		menuSistema.setMnemonic('s');
		menuBar.add(menuSistema);

		int width = 20, height = 20, hints = 100; //para redimensionar as imagens
		ImageIcon imgsair = new ImageIcon("sair.png");
		imgsair.setImage(imgsair.getImage().getScaledInstance(width, height, hints)); 
		itemSair = new JMenuItem("Sair");
		itemSair.setIcon(imgsair);
		menuSistema.add(itemSair);
		itemSair.setMnemonic('r');
		itemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
		         InputEvent.ALT_MASK));
		
		menuConsulta = new JMenu("Consulta");
		menuConsulta.setMnemonic('a');
		menuBar.add(menuConsulta);
		
		
        ImageIcon imgAluno = new ImageIcon("aluno.png");
        imgAluno.setImage(imgAluno.getImage().getScaledInstance(width, height, hints));
        itemConsultaAluno = new JMenuItem("Aluno");
        itemConsultaAluno.setIcon(imgAluno);
        menuConsulta.add(itemConsultaAluno);
        
        ImageIcon imgAula = new ImageIcon("frequencia.png");
        imgAula.setImage(imgAula.getImage().getScaledInstance(width, height, hints));
        itemConsultaAula = new JMenuItem("Frequência");
        itemConsultaAula.setIcon(imgAula);
        menuConsulta.add(itemConsultaAula);
        
        ImageIcon imgTurma = new ImageIcon("turma.png");
        imgTurma.setImage(imgTurma.getImage().getScaledInstance(width, height, hints));
        (itemConsultaTurma = new JMenuItem("Turma")).setIcon(imgTurma);
        menuConsulta.add(itemConsultaTurma);
        
        ImageIcon imgCurso = new ImageIcon("curso.png");
        imgCurso.setImage(imgCurso.getImage().getScaledInstance(width, height, hints));
        (itemConsultaCurso = new JMenuItem("Curso")).setIcon(imgCurso);
        menuConsulta.add(itemConsultaCurso);
        
        ImageIcon imgResponsavel = new ImageIcon("responsavel.png");
        imgResponsavel.setImage(imgResponsavel.getImage().getScaledInstance(width, height, hints));
        (itemConsultaResponsavel = new JMenuItem("Responsável")).setIcon(imgResponsavel);
        menuConsulta.add(itemConsultaResponsavel);
        
        ImageIcon imgAutorizado = new ImageIcon("autorizado.png");
        imgAutorizado.setImage(imgAutorizado.getImage().getScaledInstance(width, height, hints));
        (itemConsultaAutorizado = new JMenuItem("Autorizado")).setIcon(imgAutorizado);
        menuConsulta.add(itemConsultaAutorizado);
        
        ImageIcon imgColaborador = new ImageIcon("colaborador.png");
        imgColaborador.setImage(imgColaborador.getImage().getScaledInstance(width, height, hints));
        (itemConsultaColaborador = new JMenuItem("Colaborador")).setIcon(imgColaborador);
        menuConsulta.add(itemConsultaColaborador);
        
        ImageIcon imgCep = new ImageIcon("cep.png");
        imgCep.setImage(imgCep.getImage().getScaledInstance(width, height, hints));
        (itemConsultaCep = new JMenuItem("CEP")).setIcon(imgCep);
        menuConsulta.add(itemConsultaCep);
        
        ImageIcon imgCidade = new ImageIcon("cidade.png");
        imgCidade.setImage(imgCidade.getImage().getScaledInstance(width, height, hints));
        (itemConsultaCidade = new JMenuItem("Cidade")).setIcon(imgCidade);
        menuConsulta.add(itemConsultaCidade);
        
		menuCadastro = new JMenu("Cadastro");
		menuCadastro.setMnemonic('o');
		menuBar.add(menuCadastro);

        
        (itemCadastroAluno = new JMenuItem("Aluno")).setIcon(imgAluno);
        menuCadastro.add(itemCadastroAluno);
        (itemRegistraAula = new JMenuItem("Frequência")).setIcon(imgAula);
        menuCadastro.add(itemRegistraAula);
        (itemCadastroTurma = new JMenuItem("Turma")).setIcon(imgTurma);
        menuCadastro.add(itemCadastroTurma);
        (itemCadastroCurso = new JMenuItem("Curso")).setIcon(imgCurso);
        menuCadastro.add(itemCadastroCurso);
        (itemCadastroResponsavel = new JMenuItem("Responsável")).setIcon(imgResponsavel);
        menuCadastro.add(itemCadastroResponsavel);
        (itemCadastroAutorizado = new JMenuItem("Autorizado")).setIcon(imgAutorizado);
        menuCadastro.add(itemCadastroAutorizado);
        (itemCadastroColaborador = new JMenuItem("Colaborador")).setIcon(imgColaborador);
        menuCadastro.add(itemCadastroColaborador);
        (itemCadastroCep = new JMenuItem("CEP")).setIcon(imgCep);
        menuCadastro.add(itemCadastroCep);
        (itemCadastroCidade = new JMenuItem("Cidade")).setIcon(imgCidade);
        menuCadastro.add(itemCadastroCidade);
        
        
		//Elementos do JToolBar
		toolBar = new JToolBar();
		
        (btnAluno = new JButton()).setToolTipText("Consulta Aluno");
        btnAluno.setIcon(new ImageIcon("aluno.png"));
        toolBar.add(btnAluno);
        (btnAula = new JButton()).setToolTipText("Consulta Aula");
        btnAula.setIcon(new ImageIcon("frequencia.png"));
        toolBar.add(btnAula);
        (btnTurma = new JButton()).setToolTipText("Consulta Turma");
        btnTurma.setIcon(new ImageIcon("turma.png"));
        toolBar.add(btnTurma);
        (btnCurso = new JButton()).setToolTipText("Consulta Curso");
        btnCurso.setIcon(new ImageIcon("curso.png"));
        toolBar.add(btnCurso);
        (btnResponsavel = new JButton()).setToolTipText("Consulta Respons\u00e1vel");
        btnResponsavel.setIcon(new ImageIcon("responsavel.png"));
        toolBar.add(btnResponsavel);
        (btnAutorizado = new JButton()).setToolTipText("Consulta Autorizado");
        btnAutorizado.setIcon(new ImageIcon("autorizado.png"));
        toolBar.add(btnAutorizado);
        (btnColaborador = new JButton()).setToolTipText("Consulta Colaborador");
        btnColaborador.setIcon(new ImageIcon("colaborador.png"));
        toolBar.add(btnColaborador);
        (btnCep = new JButton()).setToolTipText("Consulta CEP");
        btnCep.setIcon(new ImageIcon("cep.png"));
        toolBar.add(btnCep);
        (btnCidade = new JButton()).setToolTipText("Consulta Cidade");
        btnCidade.setIcon(new ImageIcon("cidade.png"));
        toolBar.add(btnCidade);
        toolBar.addSeparator();
        (btnSair = new JButton()).setToolTipText("Sair do sistema");
        btnSair.setIcon(new ImageIcon("sair.png"));
        toolBar.add(btnSair);
        
	    Container ct = getContentPane();
	    ct.setLayout(new BorderLayout());
	    ct.add(toolBar,BorderLayout.NORTH);
	    
	    // criação da área para inserção de frames internos
	    DPane = new JDesktopPane();
	    //DPane.setLayout(null);
	   // DPane.setBounds(0,0,1000,1000);
	    DPane.putClientProperty("JDesktopPane.dragMode", "outline");
	    ct.add(DPane, BorderLayout.CENTER);
        
	    // aqui é setado o controller desse frame
        ListenerFormulario listener = new ListenerFormulario(this);
        btnSair.addActionListener((ActionListener)listener);
        itemSair.addActionListener((ActionListener)listener);
        btnAluno.addActionListener((ActionListener)listener);
        itemConsultaAluno.addActionListener((ActionListener)listener);
        itemCadastroAluno.addActionListener((ActionListener)listener);
        btnAula.addActionListener((ActionListener)listener);
        itemConsultaAula.addActionListener((ActionListener)listener);
        itemRegistraAula.addActionListener((ActionListener)listener);
        btnTurma.addActionListener((ActionListener)listener);
        itemConsultaTurma.addActionListener((ActionListener)listener);
        itemCadastroTurma.addActionListener((ActionListener)listener);
        btnCurso.addActionListener((ActionListener)listener);
        itemConsultaCurso.addActionListener((ActionListener)listener);
        itemCadastroCurso.addActionListener((ActionListener)listener);
        btnResponsavel.addActionListener((ActionListener)listener);
        itemConsultaResponsavel.addActionListener((ActionListener)listener);
        itemCadastroResponsavel.addActionListener((ActionListener)listener);
        btnAutorizado.addActionListener((ActionListener)listener);
        itemConsultaAutorizado.addActionListener((ActionListener)listener);
        itemCadastroAutorizado.addActionListener((ActionListener)listener);
        btnColaborador.addActionListener((ActionListener)listener);
        itemConsultaColaborador.addActionListener((ActionListener)listener);
        itemCadastroColaborador.addActionListener((ActionListener)listener);
        btnCep.addActionListener((ActionListener)listener);
        itemConsultaCep.addActionListener((ActionListener)listener);
        itemCadastroCep.addActionListener((ActionListener)listener);
        btnCidade.addActionListener((ActionListener)listener);
        itemConsultaCidade.addActionListener((ActionListener)listener);
        itemCadastroCidade.addActionListener((ActionListener)listener);
    }
    
    public static void main(String[] args) {
        SistemaMatricula sv = new SistemaMatricula();
        sv.setVisible(true);
    }
}