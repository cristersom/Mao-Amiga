package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import model.bo.BairroBO;
import model.dao.BairroDao;
import model.dao.CidadeDao;
import model.exceptions.StringVaziaException;
import view.FrameCadastroBairro;
import view.FrameConsultaCidade;

public class ListenerCadastroBairro implements ActionListener {

	private FrameCadastroBairro pFormulario;
	CidadeDao cidDao;
	
	public ListenerCadastroBairro(FrameCadastroBairro pFormulario) {
		this.pFormulario = pFormulario;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();

		if (origem == pFormulario.btnBuscaCidades) {
			FrameConsultaCidade fr = new FrameConsultaCidade(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		} else if (origem == pFormulario.btnOk) {
			BairroBO bairroBO = new BairroBO();
			BairroDao bairroDao = new BairroDao();
			
			try {
				bairroBO.setBairro(pFormulario.txtBairro.getText());

			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Bairro" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtBairro.selectAll();
				pFormulario.txtBairro.requestFocus();
				return;
			}

			bairroBO.setCodigo(pFormulario.codBairro);
			bairroBO.cidade.setCodigo(pFormulario.codCidade);

			// acesso ao dao
			// objetoDao.incluir(cidade);

			if (bairroBO.getCodigo() > 0) { // Neste caso o CEP deve ser alterado e não incluído
				if (bairroDao.alterar(bairroBO)) {
					bairroBO = bairroDao.consultaPorCodigo(bairroBO.getCodigo()).get(0);

					int linha = pFormulario.consBairro.tabela.getSelectedRow();
					pFormulario.consBairro.modelo.setValueAt(bairroBO.getBairro(), linha, 1);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consBairro != null) // para chamar o frame de origem
						this.pFormulario.dispose();
						try {
							this.pFormulario.consBairro.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
				}

			} else {
				if (bairroDao.incluir(bairroBO)) {
					JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					this.pFormulario.dispose();
				}
			}

		} else
			this.pFormulario.dispose();

	}
}