package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bo.ResponsavelBO;
import model.dao.ResponsavelDao;
import view.FrameCadastroResponsavel;
import view.FrameConsultaResponsavel;

public class ListernerConsultaResponsavel implements ActionListener {

	private FrameConsultaResponsavel pFormulario;
    private ResponsavelDao responsavelDao = new ResponsavelDao();
    
	public ListernerConsultaResponsavel(FrameConsultaResponsavel pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Incluir")) {
			FrameCadastroResponsavel fr = new FrameCadastroResponsavel();
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.pnlResponsavel.txtNome.requestFocus();

		} else if (cmd.equals("Consultar")) {
			ArrayList<ResponsavelBO> responsavelBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					responsavelBO = responsavelDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("CPF")) {
				responsavelBO = responsavelDao.consultaPorCpf(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Nome")) {
				responsavelBO = responsavelDao.consultaPorNome(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					responsavelBO = responsavelDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
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
					pFormulario.modelo.addRow(new Object[] { responsavelBO.get(indice).getId(),
							responsavelBO.get(indice).getNome(), responsavelBO.get(indice).getTipo(), responsavelBO.get(indice).getCpf(),
							responsavelBO.get(indice).cep.getLogradouro() + ", " + responsavelBO.get(indice).getNumero() + ", "
									+ responsavelBO.get(indice).cep.getBairro() + ", "
									+ responsavelBO.get(indice).cep.cidade.getCidade(),
							responsavelBO.get(indice).cep.getCep() });
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}

				indice++;
			} while (indice < responsavelBO.size());

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do
				// banco
				pFormulario.responsavelBO = responsavelDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				FrameCadastroResponsavel fr = new FrameCadastroResponsavel(this.pFormulario);
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
					if (responsavelDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

		} else if (cmd.equals("Selecionar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				pFormulario.responsavelBO = responsavelDao
						.consultaPorCodigo(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))
						.get(0);

				if(responsavelDao.incluirResponsavel(pFormulario.cadTurma.idTurma, pFormulario.responsavelBO.getId())) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					pFormulario.cadTurma.modelo.addRow(new Object[] {
							pFormulario.responsavelBO.getId(),
							pFormulario.responsavelBO.getNome(),
							pFormulario.responsavelBO.getTipo(),
							pFormulario.responsavelBO.getCpf(),
							sdf.format(pFormulario.responsavelBO.getDataNascimento().getTime()),
							pFormulario.responsavelBO.getNomeMae()
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
