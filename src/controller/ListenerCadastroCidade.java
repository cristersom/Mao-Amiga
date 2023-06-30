package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;

import model.bo.CidadeBO;
import model.dao.CidadeDao;
import model.exceptions.StringVaziaException;
import view.FrameCadastroCidade;

public class ListenerCadastroCidade implements ActionListener {

	private FrameCadastroCidade pFormulario;

	public ListenerCadastroCidade(FrameCadastroCidade pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();

		if (origem == pFormulario.btnCancelar) {

			this.pFormulario.dispose();

		} else if (origem == pFormulario.jcbUf) {
			pFormulario.txtEstado.setText(pFormulario.estados[pFormulario.jcbUf.getSelectedIndex()]);

		} else {
			CidadeBO cidBO = new CidadeBO();
			CidadeDao cidDao = new CidadeDao();

			try {
				cidBO.setCidade(pFormulario.txtCidade.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Cidade" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtCidade.selectAll();
				pFormulario.txtCidade.requestFocus();
				return;
				// JOptionPane.showMessageDialog(pFormulario,"Esta é uma caixa de diálogo de
				// Mensagem", "Mensagem", JOptionPane.WARNING_MESSAGE);
			}

			cidBO.setUf((String) pFormulario.jcbUf.getSelectedItem());
			cidBO.setId(pFormulario.idCidade);

			// acesso ao dao
			// objetoDao.incluir(cidade);
			if (cidBO.getId() > 0) { // Se for "0" é uma cidade nova
				if (cidDao.alterar(cidBO)) {
					cidBO = cidDao.consultaPorCodigoUnico(cidBO.getId());
					int linha = pFormulario.consCid.tabela.getSelectedRow();
					pFormulario.consCid.modelo.setValueAt(cidBO.getUf(), linha, 1);
					pFormulario.consCid.modelo.setValueAt(cidBO.getCidade(), linha, 2);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consCid != null) //para chamar o frame de origem
						try {
							this.pFormulario.consCid.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					this.pFormulario.dispose();
				}
			} else if (cidDao.incluir(cidBO)) {
				JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				this.pFormulario.dispose();
			}

		}
	}
}