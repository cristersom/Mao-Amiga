package view;

import controller.ListenerCadastro;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.GridBagLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

@SuppressWarnings("serial")
public class FrameCadastro extends JInternalFrame {
	private JPanel pnlCadastro;
	protected JPanel pnlCenter;
	public JButton btnCancelar;
	public JButton btnOk;
	
	
	
    public FrameCadastro() {
        super("titulo", true, true, true, true);
        super.setBounds(50, 20, 800, 600);
        try {
            this.setMaximum(true);
        }
        catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        
		pnlCadastro = new JPanel();
		pnlCadastro.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlCadastro);
		pnlCadastro.setLayout(new BorderLayout(0, 5));
        
	    pnlCenter =  new JPanel();
	    pnlCenter.setBorder(new LineBorder(Color.GRAY));
	    //pnlCadastro.add(pnlCenter, BorderLayout.CENTER);
	    pnlCadastro.add(pnlCenter, BorderLayout.NORTH);
	    GridBagLayout gridCentral = new GridBagLayout();
	    gridCentral.columnWidths = new int[]{0};
	    gridCentral.rowHeights = new int[]{0};
	    gridCentral.columnWeights = new double[]{Double.MIN_VALUE};
	    gridCentral.rowWeights = new double[]{Double.MIN_VALUE};
	    pnlCenter.setLayout(gridCentral);
	    
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlCadastro.add(pnlBottom, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlBottom.add(btnOk);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlBottom.add(btnCancelar);
		
		// aqui é setado o controller desse frame
		ListenerCadastro listener = new ListenerCadastro(this);
		btnCancelar.addActionListener(listener);
        
    }
}