package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.CursoBO;
import model.dao.CursoDao;
import view.FrameCadastroCurso;
import view.FrameConsultaCurso;

public class ListernerConsultaCurso implements ActionListener {

	private FrameConsultaCurso pFormulario;

	public ListernerConsultaCurso(FrameConsultaCurso pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		CursoDao cursoDao = new CursoDao();

		if (cmd.equals("Consultar")) {
			ArrayList<CursoBO> cursoBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("ID")) {
				cursoBO = cursoDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Curso")) {
				cursoBO = cursoDao.consultaPorCurso(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Descrição")) {
				cursoBO = cursoDao.consultaPorDescricao(pFormulario.txtConsulta.getText());
			}

			int indice = 0;
			do {
				try {
					pFormulario.modelo.addRow(new Object[] { cursoBO.get(indice).getId(),
							cursoBO.get(indice).getCurso(), cursoBO.get(indice).getDescricao() });
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}
				indice++;
			} while (indice < cursoBO.size());

		} else if (cmd.equals("Incluir")) {
			FrameCadastroCurso fr = new FrameCadastroCurso();
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
			fr.txtCurso.requestFocus();

		} else if (cmd.equals("Excluir")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclusão?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (cursoDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.cursoBO = cursoDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				FrameCadastroCurso fr = new FrameCadastroCurso(this.pFormulario);
				fr.setVisible(true);
				pFormulario.getDesktopPane().add(fr);
				try {
					fr.setSelected(true);
				} catch (PropertyVetoException exc) {
				}
			}
		} else if (cmd.equals("Selecionar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.cursoBO = cursoDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				try {
					pFormulario.cadTurma.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				//pFormulario.cadTurma.idCurso = pFormulario.cursoBO.getId();
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
