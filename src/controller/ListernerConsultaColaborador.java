package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.ColaboradorBO;
import model.dao.ColaboradorDao;
import model.dao.TurmaDao;
import view.FrameCadastroColaborador;
import view.FrameConsultaColaborador;

public class ListernerConsultaColaborador implements ActionListener {

	private FrameConsultaColaborador pFormulario;
    private ColaboradorDao colaboradorDao = new ColaboradorDao();
    
	public ListernerConsultaColaborador(FrameConsultaColaborador pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Incluir")) {
			FrameCadastroColaborador fr = new FrameCadastroColaborador();
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.pnlColaborador.txtNome.requestFocus();

		} else if (cmd.equals("Consultar")) {
			ArrayList<ColaboradorBO> colaboradorBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					colaboradorBO = colaboradorDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("CPF")) {
				colaboradorBO = colaboradorDao.consultaPorCpf(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Nome")) {
				colaboradorBO = colaboradorDao.consultaPorNome(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					colaboradorBO = colaboradorDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			}

			int indice = 0;
			do {
				try {
					pFormulario.modelo.addRow(new Object[] { colaboradorBO.get(indice).getId(),
							colaboradorBO.get(indice).getNome(), colaboradorBO.get(indice).getTipo(), colaboradorBO.get(indice).getCpf(),
							colaboradorBO.get(indice).cep.getLogradouro() + ", " + colaboradorBO.get(indice).getNumero() + ", "
									+ colaboradorBO.get(indice).cep.getBairro() + ", "
									+ colaboradorBO.get(indice).cep.cidade.getCidade(),
							colaboradorBO.get(indice).cep.getCep() });
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}

				indice++;
			} while (indice < colaboradorBO.size());

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do
				// banco
				pFormulario.colaboradorBO = colaboradorDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				FrameCadastroColaborador fr = new FrameCadastroColaborador(this.pFormulario);
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
					if (colaboradorDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

		} else if (cmd.equals("Selecionar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.colaboradorBO = colaboradorDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				if(colaboradorDao.incluirColaborador(pFormulario.cadTurma.idTurma, pFormulario.colaboradorBO.getId())) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					pFormulario.cadTurma.modelo.addRow(new Object[] {
							pFormulario.colaboradorBO.getId(),
							pFormulario.colaboradorBO.getNome(),
							pFormulario.colaboradorBO.getTipo(),
							pFormulario.colaboradorBO.getCpf(),
							sdf.format(pFormulario.colaboradorBO.getDataNascimento().getTime()),
							pFormulario.colaboradorBO.getNomeMae()
					});
					pFormulario.dispose();
					try {
						pFormulario.cadTurma.setSelected(true);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
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
