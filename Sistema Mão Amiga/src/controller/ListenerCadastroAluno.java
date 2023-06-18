package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import model.bo.AlunoBO;
import model.dao.AlunoDao;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;
import view.FrameCadastroAluno;
import view.FrameConsultaCEP;

public class ListenerCadastroAluno implements ActionListener, KeyListener {

	private FrameCadastroAluno pFormulario;

	public ListenerCadastroAluno(FrameCadastroAluno pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		String dia, mes, ano, dataNasc;
		AlunoBO alunoBO = new AlunoBO();
		AlunoDao alunoDao = new AlunoDao();

		if (origem == pFormulario.pnlEndereco.btnBuscaCep) {
			FrameConsultaCEP fr = new FrameConsultaCEP(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);

		} else if (origem == pFormulario.btnOk) {

			dia = String.valueOf(pFormulario.pnlAluno.jcbDia.getSelectedIndex() + 1);
			mes = String.valueOf(pFormulario.pnlAluno.jcbMes.getSelectedIndex() + 1);
			ano = String.valueOf(pFormulario.pnlAluno.jcbAno.getSelectedIndex() + 1900);

			dataNasc = dia + '/' + mes + '/' + ano;

			try {
				alunoBO.setDataNascimento(dataNasc);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.pFormulario, "Data inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				alunoBO.setNome(pFormulario.pnlAluno.txtNome.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Nome" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtNome.selectAll();
				pFormulario.pnlAluno.txtNome.requestFocus();
				return;
			}

			try {
				alunoBO.setCpf(pFormulario.pnlAluno.txtCpf.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtCpf.requestFocus();
				return;
			} catch (CpfInvalidoException e1) {
				JOptionPane.showMessageDialog(pFormulario, e1.toString(), "Mensagem", JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtCpf.selectAll();
				pFormulario.pnlAluno.txtCpf.requestFocus();
				return;
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtCpf.selectAll();
				pFormulario.pnlAluno.txtCpf.requestFocus();
				return;
			}

			pFormulario.pnlAluno.txtCpf.setText(alunoBO.getCpf());

			if (pFormulario.codCep > 0)
				alunoBO.cep.setCodigo(pFormulario.codCep);
			else {
				JOptionPane.showMessageDialog(pFormulario, "Informe um CEP!", "Mensagem", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				alunoBO.setNumero(Integer.parseInt(pFormulario.pnlEndereco.txtNumero.getText()));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Campo \"Número\" deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlEndereco.txtNumero.selectAll();
				pFormulario.pnlEndereco.txtNumero.requestFocus();
				return;
			}

			alunoBO.setComplemento(pFormulario.pnlEndereco.txtComplemento.getText());
			alunoBO.setCodigo(pFormulario.codAluno);

			// acesso ao dao
			if (alunoBO.getCodigo() > 0) { // Neste caso a Chapa deve ser alterada e não incluída
				if (alunoDao.alterar(alunoBO)) {
					alunoBO = alunoDao.consultaPorCodigo(alunoBO.getCodigo()).get(0);

					int linha = pFormulario.consAluno.tabela.getSelectedRow();
					pFormulario.consAluno.modelo.setValueAt(alunoBO.getCodigo(), linha, 0);
					pFormulario.consAluno.modelo.setValueAt(alunoBO.getNome(), linha, 1);
					pFormulario.consAluno.modelo.setValueAt(alunoBO.getCpf(), linha, 2);
					pFormulario.consAluno.modelo.setValueAt(alunoBO.cep.getLogradouro() + ", " + alunoBO.getNumero()
							+ ", " + alunoBO.cep.getBairro() + ", " + alunoBO.cep.cidade.getCidade(), linha, 3);
					pFormulario.consAluno.modelo.setValueAt(alunoBO.cep.getCep(), linha, 4);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consAluno != null) // para chamar o frame de origem
						this.pFormulario.dispose();
					try {
						this.pFormulario.consAluno.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			} else {
				if (alunoDao.incluir(alunoBO)) {
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
			pFormulario.pnlAluno.jcbDia.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}