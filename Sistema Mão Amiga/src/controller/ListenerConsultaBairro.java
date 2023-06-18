package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import model.bo.BairroBO;
import model.dao.BairroDao;
import view.FrameCadastroBairro;
import view.FrameConsultaBairro;

public class ListenerConsultaBairro implements ActionListener {

	private FrameConsultaBairro pFormulario;
	BairroDao bairroDao = new BairroDao();

	public ListenerConsultaBairro(FrameConsultaBairro pFormulario) {
		this.pFormulario = pFormulario;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Consultar")) {
			ArrayList<BairroBO> bairroBO = null;

			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);

			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Bairro")) {
				bairroBO = bairroDao.consultaPorBairro(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Cidade")) {
				bairroBO = bairroDao.consultaPorCidade(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("UF")) {
				bairroBO = bairroDao.consultaPorUF(pFormulario.txtConsulta.getText());
			}

			int indice = 0;
			do {
				try {
					pFormulario.modelo.addRow(new Object[] { bairroBO.get(indice).getCodigo(), bairroBO.get(indice).getBairro(), bairroBO.get(indice).cidade.getCidade()
											, bairroBO.get(indice).cidade.getUf(), bairroBO.get(indice).cidade.getCodigo() });
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					break;
				}

				indice++;
			} while (indice < bairroBO.size());

		} else if (cmd.equals("Incluir")) {
			pFormulario.bairroBO = new BairroBO(); // inicializar a bairro, para pode passar o objeto formulário como parâmetro
			FrameCadastroBairro fr = new FrameCadastroBairro(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//fr.txtBairro.requestFocus();

		} else if (cmd.equals("Excluir")) {

			if (pFormulario.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclusão?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (bairroDao.excluir(Integer.parseInt(
							pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
						pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

		} else if (cmd.equals("Alterar")) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				// popular o bairroBO com o registro que está selecionado na Jtable, buscando do
				// banco
				pFormulario.bairroBO = bairroDao.consultaPorCodigo(Integer
						.parseInt(pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 5).toString())).get(0);
				
				FrameCadastroBairro fr = new FrameCadastroBairro(this.pFormulario);
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
				// popular o bairroBO com o registro que está selecionado na Jtable, buscando do banco
				pFormulario.bairroBO = bairroDao.consultaPorCodigo(Integer
						.parseInt(pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 5).toString())).get(0);
				
				try {
					pFormulario.cadBairro.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				pFormulario.cadBairro.codBairro = pFormulario.bairroBO.getCodigo();
				pFormulario.cadBairro.txtBairro.setText(pFormulario.bairroBO.getBairro());
				pFormulario.cadBairro.txtCidade.setText(pFormulario.bairroBO.cidade.getCidade());
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
