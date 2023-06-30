package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import model.bo.ResponsavelBO;
import model.dao.ResponsavelDao;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;
import view.FrameCadastroResponsavel;
import view.FrameConsultaCEP;

public class ListenerCadastroResponsavel implements ActionListener, KeyListener {

	private FrameCadastroResponsavel pFormulario;

	public ListenerCadastroResponsavel(FrameCadastroResponsavel pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		String dia, mes, ano, dataNasc;
		ResponsavelBO responsavelBO = new ResponsavelBO();
		ResponsavelDao responsavelDao = new ResponsavelDao();

		if (origem == pFormulario.pnlEndereco.btnBuscaCep) {
			FrameConsultaCEP fr = new FrameConsultaCEP(this.pFormulario);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);

		} else if (origem == pFormulario.btnOk) {
			
			responsavelBO.setTipo(pFormulario.pnlResponsavel.jcbTipo.getSelectedItem().toString());
			responsavelBO.setAutorUsoImagem(pFormulario.pnlResponsavel.checkboxAutorImatem.isSelected() ? 1 : 0);		
			
			dia = String.valueOf(pFormulario.pnlResponsavel.jcbDia.getSelectedIndex() + 1);
			mes = String.valueOf(pFormulario.pnlResponsavel.jcbMes.getSelectedIndex() + 1);
			ano = String.valueOf(pFormulario.pnlResponsavel.jcbAno.getSelectedIndex() + 1900);

			dataNasc = dia + '/' + mes + '/' + ano;
			
			try {
				responsavelBO.setDataNascimento(dataNasc);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.pFormulario, "Data inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				responsavelBO.setNome(pFormulario.pnlResponsavel.txtNome.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Nome" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlResponsavel.txtNome.selectAll();
				pFormulario.pnlResponsavel.txtNome.requestFocus();
				return;
			}

			try {
				responsavelBO.setCpf(pFormulario.pnlResponsavel.txtCpf.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlResponsavel.txtCpf.requestFocus();
				return;
			} catch (CpfInvalidoException e1) {
				JOptionPane.showMessageDialog(pFormulario, e1.toString(), "Mensagem", JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlResponsavel.txtCpf.selectAll();
				pFormulario.pnlResponsavel.txtCpf.requestFocus();
				return;
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlResponsavel.txtCpf.selectAll();
				pFormulario.pnlResponsavel.txtCpf.requestFocus();
				return;
			}

			//pFormulario.pnlResponsavel.txtCpf.setText(responsavelBO.getCpf());
			
			try {
				responsavelBO.setNomeMae(pFormulario.pnlResponsavel.txtNomeMae.getText());
			} catch (StringVaziaException e1) {
			}
			responsavelBO.setRg(pFormulario.pnlResponsavel.txtRG.getText());
			responsavelBO.setNomePai(pFormulario.pnlResponsavel.txtNomePai.getText());
			responsavelBO.setNacionalidade(pFormulario.pnlResponsavel.txtNacionalidade.getText());
			responsavelBO.setCertNascimento(pFormulario.pnlResponsavel.txtCertNascimento.getText());
			responsavelBO.setSexo(pFormulario.pnlResponsavel.jcbSexo.getSelectedItem().toString());

			if (pFormulario.idCep > 0)
				responsavelBO.cep.setId(pFormulario.idCep);
			else {
				JOptionPane.showMessageDialog(pFormulario, "Informe um CEP!", "Mensagem", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				responsavelBO.setNumero(Integer.parseInt(pFormulario.pnlEndereco.txtNumero.getText()));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Campo \"Número\" deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlEndereco.txtNumero.selectAll();
				pFormulario.pnlEndereco.txtNumero.requestFocus();
				return;
			}

			responsavelBO.setComplemento(pFormulario.pnlEndereco.txtComplemento.getText());
			responsavelBO.setCelular(pFormulario.pnlEndereco.txtCelular.getText());
			responsavelBO.setFoneComercial(pFormulario.pnlEndereco.txtFoneComercial.getText());
			responsavelBO.setEmail(pFormulario.pnlEndereco.txtEmail.getText());
			responsavelBO.setId(pFormulario.idResponsavel);

			// acesso ao dao
			if (responsavelBO.getId() > 0) { // Neste o responsavel deve ser alterado e não incluído
				if (responsavelDao.alterar(responsavelBO)) {
					responsavelBO = responsavelDao.consultaPorCodigo(responsavelBO.getId()).get(0);

					int linha = pFormulario.consResponsavel.tabela.getSelectedRow();
					pFormulario.consResponsavel.modelo.setValueAt(responsavelBO.getId(), linha, 0);
					pFormulario.consResponsavel.modelo.setValueAt(responsavelBO.getNome(), linha, 1);
					pFormulario.consResponsavel.modelo.setValueAt(responsavelBO.getCpf(), linha, 2);
					pFormulario.consResponsavel.modelo.setValueAt(responsavelBO.cep.getLogradouro() + ", " + responsavelBO.getNumero()
							+ ", " + responsavelBO.cep.getBairro() + ", " + responsavelBO.cep.cidade.getCidade(), linha, 3);
					pFormulario.consResponsavel.modelo.setValueAt(responsavelBO.cep.getCep(), linha, 4);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consResponsavel != null) // para chamar o frame de origem
						this.pFormulario.dispose();
					try {
						this.pFormulario.consResponsavel.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			} else {
				if (responsavelDao.incluir(responsavelBO)) {
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
			pFormulario.pnlResponsavel.jcbDia.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}