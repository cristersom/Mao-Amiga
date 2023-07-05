package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bo.AlunoBO;
import model.dao.AlunoDao;
import model.dao.MatriculaDao;
import view.FrameCadastroAluno;
import view.FrameConsultaAluno;
import view.Utils;

public class ListernerConsultaAluno implements ActionListener {

	private FrameConsultaAluno pFormulario;
	private AlunoDao alunoDao = new AlunoDao();

	public ListernerConsultaAluno(FrameConsultaAluno pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("Incluir")) {
			FrameCadastroAluno fr = new FrameCadastroAluno();
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.pnlAluno.txtNome.requestFocus();

		} else if (cmd.equals("Consultar")) {
			ArrayList<AlunoBO> alunoBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					alunoBO = alunoDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("CPF")) {
				alunoBO = alunoDao.consultaPorCpf(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Nome")) {
				alunoBO = alunoDao.consultaPorNome(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					alunoBO = alunoDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
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
					pFormulario.modelo.addRow(new Object[] { alunoBO.get(indice).getId(),
							alunoBO.get(indice).getNome(), alunoBO.get(indice).getCpf(),
							alunoBO.get(indice).cep.getLogradouro() + ", " + alunoBO.get(indice).getNumero() + ", "
									+ alunoBO.get(indice).cep.getBairro() + ", "
									+ alunoBO.get(indice).cep.cidade.getCidade(),
							alunoBO.get(indice).cep.getCep() });
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}

				indice++;
			} while (indice < alunoBO.size());

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do
				// banco
				pFormulario.alunoBO = alunoDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				FrameCadastroAluno fr = new FrameCadastroAluno(this.pFormulario);
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
					if (alunoDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

		} else if (cmd.equals("Selecionar")) {
			MatriculaDao matriculaDao = new MatriculaDao();
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.alunoBO = alunoDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);
								
				if (matriculaDao.incluir(pFormulario.cadTurma.idTurma, pFormulario.alunoBO.getId())) {
					
					
					int idMatricula = matriculaDao.consultaMatricula(pFormulario.cadTurma.idTurma, pFormulario.alunoBO.getId()).get(0).getIdMatricula();
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					pFormulario.cadTurma.modelo.addRow(new Object[] {idMatricula, pFormulario.alunoBO.getId(),  pFormulario.alunoBO.getNome(), Utils.Tipo.Aluno.toString()
							, pFormulario.alunoBO.getCpf(), sdf.format(pFormulario.alunoBO.getDataNascimento().getTime()), pFormulario.alunoBO.getNomeMae()
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
