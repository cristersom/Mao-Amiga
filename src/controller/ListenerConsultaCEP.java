package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.CepBO;
import model.dao.CepDao;
import view.FrameCadastroCEP;
import view.FrameConsultaCEP;

public class ListenerConsultaCEP implements ActionListener {

	private FrameConsultaCEP pFormulario;

	public ListenerConsultaCEP(FrameConsultaCEP pFormulario) {
		this.pFormulario = pFormulario;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		CepDao cepDao = new CepDao();

		if (cmd.equals("Consultar")) {
			ArrayList<CepBO> cepBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("CEP")) {
				cepBO = cepDao.consultaPorCep(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Cidade")) {
				cepBO = cepDao.consultaPorCidade(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Endereço")) {
				cepBO = cepDao.consultaPorLogradouro(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Bairro")) {
				cepBO = cepDao.consultaPorBairro(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("UF")) {
				cepBO = cepDao.consultaPorUF(pFormulario.txtConsulta.getText());
			}

			int indice = 0;
			do {
				try {
					pFormulario.modelo
							.addRow(new Object[] { cepBO.get(indice).getCep(), cepBO.get(indice).cidade.getCidade(),
									cepBO.get(indice).getLogradouro(), cepBO.get(indice).getBairro(),
									cepBO.get(indice).cidade.getUf(), cepBO.get(indice).getCodigo() });
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}
				indice++;
			} while (indice < cepBO.size());

		} else if (cmd.equals("Incluir")) {
			FrameCadastroCEP fr = new FrameCadastroCEP();
			// this.pFormulario.setVisible(false);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do
				// banco
				pFormulario.cepBO = cepDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 5).toString()))
						.get(0);

				FrameCadastroCEP fr = new FrameCadastroCEP(this.pFormulario);
				fr.setVisible(true);
				pFormulario.getDesktopPane().add(fr);
				try {
					fr.setSelected(true);
				} catch (PropertyVetoException exc) {
				}
			}
		} else if (cmd.equals("Excluir")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclusão?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (cepDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 5).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			
		} else if (cmd.equals("Selecionar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.cepBO = cepDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 5).toString()))
						.get(0);
				if(pFormulario.cadAluno != null) {
					try {
						pFormulario.cadAluno.setSelected(true);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
					pFormulario.cadAluno.codCep = pFormulario.cepBO.getCodigo();
					pFormulario.cadAluno.pnlEndereco.txtCep.setText(pFormulario.cepBO.getCep());
					pFormulario.cadAluno.pnlEndereco.txtCidade.setText(pFormulario.cepBO.cidade.getCidade());
					pFormulario.cadAluno.pnlEndereco.txtLogradouro.setText(pFormulario.cepBO.getLogradouro());
					pFormulario.cadAluno.pnlEndereco.txtBairro.setText(pFormulario.cepBO.getBairro());
					pFormulario.dispose();
				}
				
				if(pFormulario.cadColaborador != null) {
					try {
						pFormulario.cadColaborador.setSelected(true);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
					pFormulario.cadColaborador.codCep = pFormulario.cepBO.getCodigo();
					pFormulario.cadColaborador.txtCep.setText(pFormulario.cepBO.getCep());
					pFormulario.cadColaborador.txtCidade.setText(pFormulario.cepBO.cidade.getCidade());
					pFormulario.cadColaborador.txtLogradouro.setText(pFormulario.cepBO.getLogradouro());
					pFormulario.cadColaborador.txtBairro.setText(pFormulario.cepBO.getBairro());
					pFormulario.dispose();
				}

			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
		} else {
			pFormulario.txtConsulta.setText("");
			pFormulario.txtConsulta.requestFocus();

		}
	}
}