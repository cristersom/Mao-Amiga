package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import model.bo.ColaboradorBO;
import model.dao.ColaboradorDao;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;
import view.FrameCadastroColaborador;
import view.FrameConsultaCEP;

public class ListenerCadastroColaborador implements ActionListener, KeyListener {

	private FrameCadastroColaborador pFormulario;

	public ListenerCadastroColaborador(FrameCadastroColaborador pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		String dia, mes, ano, dataNasc;
		ColaboradorBO colaboradorBO = new ColaboradorBO();
		ColaboradorDao colaboradorDao = new ColaboradorDao();

		if (origem == pFormulario.pnlEndereco.btnBuscaCep) {
			FrameConsultaCEP fr = new FrameConsultaCEP(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);

		} else if (origem == pFormulario.btnOk) {
			
			colaboradorBO.setTipo(pFormulario.pnlColaborador.jcbTipo.getSelectedItem().toString());
			colaboradorBO.setAutorUsoImagem(pFormulario.pnlColaborador.checkboxAutorImatem.isSelected() ? 1 : 0);		
			
			dia = String.valueOf(pFormulario.pnlColaborador.jcbDia.getSelectedIndex() + 1);
			mes = String.valueOf(pFormulario.pnlColaborador.jcbMes.getSelectedIndex() + 1);
			ano = String.valueOf(pFormulario.pnlColaborador.jcbAno.getSelectedIndex() + 1900);

			dataNasc = dia + '/' + mes + '/' + ano;
			
			try {
				colaboradorBO.setDataNascimento(dataNasc);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.pFormulario, "Data inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				colaboradorBO.setNome(pFormulario.pnlColaborador.txtNome.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Nome" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlColaborador.txtNome.selectAll();
				pFormulario.pnlColaborador.txtNome.requestFocus();
				return;
			}

			try {
				colaboradorBO.setCpf(pFormulario.pnlColaborador.txtCpf.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlColaborador.txtCpf.requestFocus();
				return;
			} catch (CpfInvalidoException e1) {
				JOptionPane.showMessageDialog(pFormulario, e1.toString(), "Mensagem", JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlColaborador.txtCpf.selectAll();
				pFormulario.pnlColaborador.txtCpf.requestFocus();
				return;
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlColaborador.txtCpf.selectAll();
				pFormulario.pnlColaborador.txtCpf.requestFocus();
				return;
			}

			//pFormulario.pnlColaborador.txtCpf.setText(colaboradorBO.getCpf());
			
			try {
				colaboradorBO.setNomeMae(pFormulario.pnlColaborador.txtNomeMae.getText());
			} catch (StringVaziaException e1) {
			}
			colaboradorBO.setRg(pFormulario.pnlColaborador.txtRG.getText());
			colaboradorBO.setNomePai(pFormulario.pnlColaborador.txtNomePai.getText());
			colaboradorBO.setNacionalidade(pFormulario.pnlColaborador.txtNacionalidade.getText());
			colaboradorBO.setCertNascimento(pFormulario.pnlColaborador.txtCertNascimento.getText());
			colaboradorBO.setSexo(pFormulario.pnlColaborador.jcbSexo.getSelectedItem().toString());

			if (pFormulario.codCep > 0)
				colaboradorBO.cep.setCodigo(pFormulario.codCep);
			else {
				JOptionPane.showMessageDialog(pFormulario, "Informe um CEP!", "Mensagem", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				colaboradorBO.setNumero(Integer.parseInt(pFormulario.pnlEndereco.txtNumero.getText()));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Campo \"Número\" deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlEndereco.txtNumero.selectAll();
				pFormulario.pnlEndereco.txtNumero.requestFocus();
				return;
			}

			colaboradorBO.setComplemento(pFormulario.pnlEndereco.txtComplemento.getText());
			colaboradorBO.setCelular(pFormulario.pnlEndereco.txtCelular.getText());
			colaboradorBO.setFoneComercial(pFormulario.pnlEndereco.txtFoneComercial.getText());
			colaboradorBO.setEmail(pFormulario.pnlEndereco.txtEmail.getText());
			colaboradorBO.setCodigo(pFormulario.codColaborador);

			// acesso ao dao
			if (colaboradorBO.getCodigo() > 0) { // Neste o colaborador deve ser alterado e não incluído
				if (colaboradorDao.alterar(colaboradorBO)) {
					colaboradorBO = colaboradorDao.consultaPorCodigo(colaboradorBO.getCodigo()).get(0);

					int linha = pFormulario.consColaborador.tabela.getSelectedRow();
					pFormulario.consColaborador.modelo.setValueAt(colaboradorBO.getCodigo(), linha, 0);
					pFormulario.consColaborador.modelo.setValueAt(colaboradorBO.getNome(), linha, 1);
					pFormulario.consColaborador.modelo.setValueAt(colaboradorBO.getCpf(), linha, 2);
					pFormulario.consColaborador.modelo.setValueAt(colaboradorBO.cep.getLogradouro() + ", " + colaboradorBO.getNumero()
							+ ", " + colaboradorBO.cep.getBairro() + ", " + colaboradorBO.cep.cidade.getCidade(), linha, 3);
					pFormulario.consColaborador.modelo.setValueAt(colaboradorBO.cep.getCep(), linha, 4);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consColaborador != null) // para chamar o frame de origem
						this.pFormulario.dispose();
					try {
						this.pFormulario.consColaborador.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			} else {
				if (colaboradorDao.incluir(colaboradorBO)) {
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
			pFormulario.pnlColaborador.jcbDia.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}