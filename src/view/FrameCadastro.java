package view;

import java.awt.event.ActionListener;
import controller.ListenerCadastro;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

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
        (this.pnlCadastro = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.pnlCadastro);
        this.pnlCadastro.setLayout(new BorderLayout(0, 5));
        (this.pnlCenter = new JPanel()).setBorder(new LineBorder(Color.GRAY));
        this.pnlCadastro.add(this.pnlCenter, "Center");
        final GridBagLayout gridCentral = new GridBagLayout();
        gridCentral.columnWidths = new int[1];
        gridCentral.rowHeights = new int[1];
        gridCentral.columnWeights = new double[] { Double.MIN_VALUE };
        gridCentral.rowWeights = new double[] { Double.MIN_VALUE };
        this.pnlCenter.setLayout(gridCentral);
        final JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(new BevelBorder(1, null, null, null, null));
        this.pnlCadastro.add(pnlBottom, "South");
        (this.btnOk = new JButton("OK")).setFont(new Font("Tahoma", 0, 12));
        pnlBottom.add(this.btnOk);
        (this.btnCancelar = new JButton("Cancelar")).setFont(new Font("Tahoma", 0, 12));
        pnlBottom.add(this.btnCancelar);
        final ListenerCadastro listener = new ListenerCadastro(this);
        this.btnCancelar.addActionListener((ActionListener)listener);
    }
}