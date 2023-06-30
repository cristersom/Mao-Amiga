package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bo.TurmaBO;
import model.dao.TurmaDao;
import view.FrameCadastroTurma;
import view.FrameConsultaTurma;

public class ListernerConsultaTurma implements ActionListener {

	private FrameConsultaTurma pFormulario;

	public ListernerConsultaTurma(FrameConsultaTurma pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		TurmaDao turmaDao = new TurmaDao();

		if (cmd.equals("Incluir")) {
			FrameCadastroTurma fr = new FrameCadastroTurma();
			// this.pFormulario.setVisible(false);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.txtTurma.requestFocus();
		} else if (cmd.equals("Consultar")) {
			ArrayList<TurmaBO> turmaBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					turmaBO = turmaDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Turma")) {
				turmaBO = turmaDao.consultaPorTurma(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Ano")) {
				try {
					turmaBO = turmaDao.consultaPorAno(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Descrição")) {
				turmaBO = turmaDao.consultaPorDescricao(pFormulario.txtConsulta.getText());
			}

			int indice = 0;
			do {
				try {
					pFormulario.modelo
							.addRow(new Object[] { turmaBO.get(indice).getId(), turmaBO.get(indice).getTurma(),
									turmaBO.get(indice).getDescricao(), turmaBO.get(indice).getAno() });
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}

				indice++;
			} while (indice < turmaBO.size());

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do
				// banco
				pFormulario.turmaBO = turmaDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				FrameCadastroTurma fr = new FrameCadastroTurma(this.pFormulario);
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
					if (turmaDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			
		} else if (cmd.equals("Selecionar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.turmaBO = turmaDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				// pFormulario.cadCep.txtCodigo.setEditable(false);
				pFormulario.dispose();
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
		} else {
			pFormulario.txtConsulta.setText("");
			pFormulario.txtConsulta.requestFocus();

		}
	}
}
