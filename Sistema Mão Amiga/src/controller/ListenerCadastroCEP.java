package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;

import model.bo.CepBO;
import model.dao.CepDao;
import model.dao.CidadeDao;
import model.exceptions.CepInvalidoException;
import model.exceptions.StringVaziaException;
import view.FrameCadastroCEP;
import view.FrameConsultaBairro;
import view.FrameConsultaCidade;

public class ListenerCadastroCEP implements ActionListener, KeyListener, FocusListener {

	private FrameCadastroCEP pFormulario;
	CidadeDao cidDao = new CidadeDao();

	public ListenerCadastroCEP(FrameCadastroCEP pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		CepBO cepBO = new CepBO();
		CepDao cepDao = new CepDao();

		if (origem == pFormulario.btnBuscaCidades) {
			FrameConsultaCidade fr = new FrameConsultaCidade(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		} else if (origem == pFormulario.btnBuscaBairro) {
			FrameConsultaBairro fr = new FrameConsultaBairro(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		} else if (origem == pFormulario.btnOk) {

			try {
				cepBO.setCep(pFormulario.txtCep.getText());

			} catch (StringVaziaException | CepInvalidoException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CEP" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtCep.selectAll();
				pFormulario.txtCep.requestFocus();
				return;
			}

			/*try {
				cepBO.cidade.setCodigo(Integer.parseInt(pFormulario.txtCodigo.getText()));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Código deve ser numérico", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtCodigo.selectAll();
				pFormulario.txtCodigo.requestFocus();
				return;
			}*/
			cepBO.setCodigo(pFormulario.codCep);
			cepBO.setLogradouro(pFormulario.txtLogradouro.getText());
			cepBO.setBairro(pFormulario.txtBairro.getText());

			// acesso ao dao
			// objetoDao.incluir(cidade);

			if (cepBO.getCodigo() > 0) { // Neste caso o CEP deve ser alterado e não incluído
				if (cepDao.alterar(cepBO)) {
					cepBO = cepDao.consultaPorCodigo(cepBO.getCodigo()).get(0);

					int linha = pFormulario.consCep.tabela.getSelectedRow();
					pFormulario.consCep.modelo.setValueAt(cepBO.getCep(), linha, 0);
					pFormulario.consCep.modelo.setValueAt(cepBO.cidade.getCidade(), linha, 1);
					pFormulario.consCep.modelo.setValueAt(cepBO.getLogradouro(), linha, 2);
					pFormulario.consCep.modelo.setValueAt(cepBO.getBairro(), linha, 3);
					pFormulario.consCep.modelo.setValueAt(cepBO.cidade.getUf(), linha, 4);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consCep != null) // para chamar o frame de origem
						this.pFormulario.dispose();
						try {
							this.pFormulario.consCep.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
				}

			} else {
				if (cepDao.incluir(cepBO)) {
					JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					this.pFormulario.dispose();
				}
			}

		} else
			this.pFormulario.dispose();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB)
			if (e.getSource() == pFormulario.txtCep)
				pFormulario.txtCodigo.requestFocus();
			else
				buscaCidade();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		Object origem = e.getSource();

		if (origem == pFormulario.txtCep) {
			CepBO cepBO = new CepBO();
			try {
				cepBO.setCep(pFormulario.txtCep.getText());
				pFormulario.txtCep.setText(cepBO.getCep());
			} catch (CepInvalidoException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CEP" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtCep.selectAll();
				pFormulario.txtCep.requestFocus();
				return;
			} catch (StringVaziaException e1) {
			}

		} else
			buscaCidade();

	}

	public void buscaCidade() {
		if (!pFormulario.txtCodigo.getText().equals(""))
			try {
				pFormulario.cidBO = cidDao.consultaPorCodigoUnico(Integer.parseInt(pFormulario.txtCodigo.getText()));
				if (pFormulario.cidBO.getCodigo() != 0) {
					pFormulario.txtUf.setText(pFormulario.cidBO.getUf());
					pFormulario.txtCidade.setText(pFormulario.cidBO.getCidade());
					pFormulario.txtLogradouro.requestFocus();
				} else {
					pFormulario.txtUf.setText("");
					pFormulario.txtCidade.setText("");
					pFormulario.txtCodigo.setText("");
					pFormulario.txtCodigo.requestFocus();
				}

			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
						JOptionPane.ERROR_MESSAGE);
				pFormulario.txtCodigo.setText("");
				pFormulario.txtCodigo.requestFocus();
				return;
			}
	}

}
