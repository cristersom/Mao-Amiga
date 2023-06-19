package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;

import model.bo.CidadeBO;
import model.dao.CidadeDao;
import view.FrameCadastroCidade;
import view.FrameConsultaCidade;

public class ListernerConsultaCidade implements ActionListener {

	private FrameConsultaCidade pFormulario;
	CidadeDao cidDao = new CidadeDao();

	public ListernerConsultaCidade(FrameConsultaCidade pFormulario) {
		this.pFormulario = pFormulario;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Consultar")) {
			CidadeBO cidBO[] = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Código")) {
				try {
					cidBO = cidDao.consultaPorCodigo(Integer.parseInt(pFormulario.txtConsulta.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(pFormulario, "O código deve ser numérico", "Erro",
							JOptionPane.ERROR_MESSAGE);
					pFormulario.txtConsulta.selectAll();
					pFormulario.txtConsulta.requestFocus();
					return;
				}
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Cidade")) {
				cidBO = cidDao.consultaPorCidade(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("UF")) {
				cidBO = cidDao.consultaPorUF(pFormulario.txtConsulta.getText());
			}

			int indice = 0;
			do {
				try {
					pFormulario.modelo.addRow(new Object[] { new Integer(cidBO[indice].getCodigo()), cidBO[indice].getUf(),
							cidBO[indice].getCidade() });
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}

				indice++;
			} while (indice < cidBO.length);

		} else if (cmd.equals("Incluir")) {
			pFormulario.cidBO = new CidadeBO(); // inicializar a cidade, para pode passar o objeto formulário como parâmetro
			FrameCadastroCidade fr = new FrameCadastroCidade(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.txtCidade.requestFocus();

		} else if (cmd.equals("Excluir")) {

			if (pFormulario.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclusão?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (cidDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do banco
				pFormulario.cidBO = cidDao.consultaPorCodigo(Integer
						.parseInt(pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))[0];
				
				FrameCadastroCidade fr = new FrameCadastroCidade(this.pFormulario);
				fr.setVisible(true);
				pFormulario.getDesktopPane().add(fr);
				try {
					fr.setSelected(true);
				} catch (PropertyVetoException exc) {
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
		} else if (cmd.equals("Selecionar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o cidBO com o registro que está selecionado na Jtable, buscando do banco
				pFormulario.cidBO = cidDao.consultaPorCodigo(Integer
						.parseInt(pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString()))[0];
				
				try {
					pFormulario.cadCep.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				pFormulario.cadCep.txtCodigo.setText(String.valueOf(pFormulario.cidBO.getCodigo()));
				pFormulario.cadCep.txtCidade.setText(pFormulario.cidBO.getCidade());
				pFormulario.cadCep.txtUf.setText(pFormulario.cidBO.getUf());
				//pFormulario.cadCep.txtCodigo.setEditable(false);
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
