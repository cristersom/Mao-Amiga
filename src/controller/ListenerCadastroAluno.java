package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.bo.AlunoBO;
import model.bo.AnexoBO;
import model.bo.AutorizadoBO;
import model.bo.ParametrosBO;
import model.dao.AlunoDao;
import model.dao.AnexoDao;
import model.dao.AutorizadoDao;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;
import view.FrameCadastroAluno;
import view.FrameConsultaCEP;
import view.Utils;

public class ListenerCadastroAluno implements ActionListener, KeyListener, ChangeListener {

	private FrameCadastroAluno pFormulario;
	private AutorizadoBO autorizadoBO = new AutorizadoBO();
	private AutorizadoDao autorizadoDao = new AutorizadoDao();
	private AnexoBO anexoBO = new AnexoBO();
	private AnexoDao anexoDao = new AnexoDao();
	private ParametrosBO parametrosBO = Utils.getParametros();

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
			
		} else if (origem == pFormulario.pnlAnexos.btnAnexar) {//adicionar anexo
			String dirArquivo = "";
			
			JFileChooser file = new JFileChooser(); 
		    file.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result = file.showSaveDialog(pFormulario);
		    if (result == JFileChooser.CANCEL_OPTION){
		    	return;
		    } else {
		        File arquivo = file.getSelectedFile();
		        dirArquivo = arquivo.getPath();
		    }
		    
			String[] dirSeparado = dirArquivo.split("\\\\"); //separa o caminho para pegar o nome do arquivo
			anexoBO.setNome(dirSeparado[dirSeparado.length-1]);	//pega o nome do arquivo
			anexoBO.dataInclusao.setData(Calendar.getInstance()); //obtém a data atual
			anexoBO.setIdAluno(pFormulario.idAluno);
			anexoBO.setDiretorio("/" + String.valueOf(pFormulario.idAluno) + "/" + anexoBO.getNome());
			
			//busca a configuração no arquivo config.ini
			String diretorioRaiz = parametrosBO.getServidorArquivos();
			File source = new File(dirArquivo);
			File dest = new File(diretorioRaiz + "/" + String.valueOf(pFormulario.idAluno));
			
			if(!dest.exists()) {//verifica se o diretório existe
				dest.mkdirs(); //cria se não existir
			}
			
			dest = new File(diretorioRaiz + anexoBO.getDiretorio()); //cria o arquivo
			
			try {
				if(anexoDao.incluir(anexoBO)) {
					Utils.copyFile(source, dest);
					 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					pFormulario.pnlAnexos.modelo.addRow(new Object[] {anexoBO.getNome()
							, sdf.format(anexoBO.dataInclusao.getData().getTime()), anexoBO.getIdArquivo(), anexoBO.getDiretorio()});										
					JOptionPane.showMessageDialog(pFormulario, "Arquivo incluido!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);

				}

			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this.pFormulario, "Não foi possível copiar o arquivo!\n" + e1.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (origem == pFormulario.pnlAnexos.btnExcluir) {//excluir anexo
			if (pFormulario.pnlAnexos.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclusão?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					anexoBO.setIdArquivo(Integer.parseInt(pFormulario.pnlAnexos.modelo.getValueAt(pFormulario.pnlAnexos.tabela.getSelectedRow(), 2).toString()));
					anexoBO.setDiretorio(pFormulario.pnlAnexos.modelo.getValueAt(pFormulario.pnlAnexos.tabela.getSelectedRow(), 3).toString());
					
					if(anexoDao.excluir(anexoBO.getIdArquivo(), anexoBO.getDiretorio())) {
						File file = new File(parametrosBO.getServidorArquivos()+anexoBO.getDiretorio());
						file.delete();
						pFormulario.pnlAnexos.modelo.removeRow(pFormulario.pnlAnexos.tabela.getSelectedRow());
					}
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			
		} else if (origem == pFormulario.pnlAnexos.btnAbrir) {//abre anexo
			if (pFormulario.pnlAnexos.tabela.getSelectedRow() >= 0) {
				anexoBO.setDiretorio(pFormulario.pnlAnexos.modelo.getValueAt(pFormulario.pnlAnexos.tabela.getSelectedRow(), 3).toString());
				File arquivo = new File(parametrosBO.getServidorArquivos() + anexoBO.getDiretorio());
				
					try {
						Desktop.getDesktop().open(arquivo);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(this.pFormulario, "Não foi possível abrir o arquivo\n"+e1.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			
		} else if (origem == pFormulario.pnlAutorizados.btnIncluir) {
			autorizadoBO.setIdAluno(pFormulario.idAluno);
			if(pFormulario.pnlAutorizados.txtNomeAutor.getText().isEmpty()) {
				JOptionPane.showMessageDialog(pFormulario, "Informe um nome para o autorizado!", "Mensagem", JOptionPane.WARNING_MESSAGE);
				return;
			}
			autorizadoBO.setNome(pFormulario.pnlAutorizados.txtNomeAutor.getText());
			autorizadoBO.setCelular(pFormulario.pnlAutorizados.txtCelular.getText());
			autorizadoBO.setFoneComercial(pFormulario.pnlAutorizados.txtFoneComercial.getText());
			try {
				autorizadoBO.dataInicio.setData(pFormulario.pnlAutorizados.txtDataIni.getText());
				autorizadoBO.dataFim.setData(pFormulario.pnlAutorizados.txtDataFim.getText());
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.pFormulario, "Data inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			autorizadoBO.setTipo(pFormulario.pnlAutorizados.jcbTipo.getSelectedItem().toString());
			if(autorizadoDao.incluir(autorizadoBO)){
				// apaga todas as linhas da tabela
				for (int i = pFormulario.pnlAutorizados.modelo.getRowCount() - 1; i >= 0; i--)
					pFormulario.pnlAutorizados.modelo.removeRow(i);
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<AutorizadoBO> autorizadoBOList = autorizadoDao.consultaPorIdAluno(pFormulario.idAluno);
				int indice = 0;
				do {
					pFormulario.pnlAutorizados.modelo.addRow(new Object[] {autorizadoBOList.get(indice).getNome(), autorizadoBOList.get(indice).getCelular()
							, autorizadoBOList.get(indice).getFoneComercial(), autorizadoBOList.get(indice).getTipo()
							, sdf.format(autorizadoBOList.get(indice).dataInicio.getData().getTime())
							, sdf.format(autorizadoBOList.get(indice).dataFim.getData().getTime())
							, autorizadoBOList.get(indice).getIdAutorizado()
					});
					indice++;
				} while(indice < autorizadoBOList.size());
		
				JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			}
			
		} else if (origem == pFormulario.pnlAutorizados.btnExcluir) {
			if (pFormulario.pnlAutorizados.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclusão?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					int idAutorizado = Integer.parseInt(pFormulario.pnlAutorizados.modelo.getValueAt(pFormulario.pnlAutorizados.tabela.getSelectedRow(), 6).toString());
					if(autorizadoDao.excluir(idAutorizado)) {
						pFormulario.pnlAutorizados.modelo.removeRow(pFormulario.pnlAutorizados.tabela.getSelectedRow());
					}
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			
		} else if (origem == pFormulario.btnOk) {
			
			alunoBO.setTipo(pFormulario.pnlAluno.jcbTipo.getSelectedItem().toString());
			alunoBO.setAutorUsoImagem(pFormulario.pnlAluno.checkboxAutorImatem.isSelected() ? 1 : 0);		
			
			dia = String.valueOf(pFormulario.pnlAluno.jcbDia.getSelectedIndex() + 1);
			mes = String.valueOf(pFormulario.pnlAluno.jcbMes.getSelectedIndex() + 1);
			ano = String.valueOf(pFormulario.pnlAluno.jcbAno.getSelectedIndex() + 1900);

			dataNasc = dia + '/' + mes + '/' + ano;
			
			try {
				alunoBO.setDataNascimento(dataNasc);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.pFormulario, "Data inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlAluno);
				return;
			}

			try {
				alunoBO.setNome(pFormulario.pnlAluno.txtNome.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Nome" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtNome.selectAll();
				pFormulario.pnlAluno.txtNome.requestFocus();
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlAluno);
				return;
			}

			try {
				alunoBO.setCpf(pFormulario.pnlAluno.txtCpf.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtCpf.requestFocus();
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlAluno);
				return;
			} catch (CpfInvalidoException e1) {
				JOptionPane.showMessageDialog(pFormulario, e1.toString(), "Mensagem", JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtCpf.selectAll();
				pFormulario.pnlAluno.txtCpf.requestFocus();
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlAluno);
				return;
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "CPF deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlAluno.txtCpf.selectAll();
				pFormulario.pnlAluno.txtCpf.requestFocus();
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlAluno);
				return;
			}

			//pFormulario.pnlAluno.txtCpf.setText(alunoBO.getCpf());
			
			try {
				alunoBO.setNomeMae(pFormulario.pnlAluno.txtNomeMae.getText());
			} catch (StringVaziaException e1) {
			}
			alunoBO.setRg(pFormulario.pnlAluno.txtRG.getText());
			alunoBO.setNomePai(pFormulario.pnlAluno.txtNomePai.getText());
			alunoBO.setNacionalidade(pFormulario.pnlAluno.txtNacionalidade.getText());
			alunoBO.setCertNascimento(pFormulario.pnlAluno.txtCertNascimento.getText());
			alunoBO.setSexo(pFormulario.pnlAluno.jcbSexo.getSelectedItem().toString());

			if (pFormulario.idCep > 0)
				alunoBO.cep.setId(pFormulario.idCep);
			else {
				JOptionPane.showMessageDialog(pFormulario, "Informe um CEP!", "Mensagem", JOptionPane.WARNING_MESSAGE);
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlEndereco);
				return;
			}

			try {
				alunoBO.setNumero(Integer.parseInt(pFormulario.pnlEndereco.txtNumero.getText()));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Campo Número deve ser numérico!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.pnlEndereco.txtNumero.selectAll();
				pFormulario.pnlEndereco.txtNumero.requestFocus();
				pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlEndereco);
				return;
			}

			alunoBO.setComplemento(pFormulario.pnlEndereco.txtComplemento.getText());
			alunoBO.setCelular(pFormulario.pnlEndereco.txtCelular.getText());
			alunoBO.setFoneComercial(pFormulario.pnlEndereco.txtFoneComercial.getText());
			alunoBO.setEmail(pFormulario.pnlEndereco.txtEmail.getText());
			alunoBO.setIdiomaMaterno(pFormulario.pnlAluno.txtIdiomaMaterno.getText());
			alunoBO.setFichaSaude(pFormulario.pnlAluno.txtFichaSaude.getText());
			alunoBO.setInfoEducacao(pFormulario.pnlAluno.txtInfEducacao.getText());
			alunoBO.setObservacoes(pFormulario.pnlAluno.txtObservacoes.getText());
			alunoBO.setId(pFormulario.idAluno);

			// acesso ao dao
			if (alunoBO.getId() > 0) { // Neste o aluno deve ser alterado e não incluído
				if (alunoDao.alterar(alunoBO)) {
					alunoBO = alunoDao.consultaPorCodigo(alunoBO.getId()).get(0);

					int linha = pFormulario.consAluno.tabela.getSelectedRow();
					pFormulario.consAluno.modelo.setValueAt(alunoBO.getId(), linha, 0);
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

		} //else
			//this.pFormulario.dispose();

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
	
	public void stateChanged(ChangeEvent e) {
		
		
		if (pFormulario.tabbedPane.getSelectedComponent() == pFormulario.pnlAutorizados || pFormulario.tabbedPane.getSelectedComponent() == pFormulario.pnlAnexos) {
			if(pFormulario.idAluno == 0) {
				JOptionPane.showMessageDialog(pFormulario, "Salve o cadastro do aluno antes!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);

					pFormulario.tabbedPane.setSelectedComponent(pFormulario.pnlAluno);

				return;
			}
			
			// apaga todas as linhas da tabela de autorizados
			for (int i = pFormulario.pnlAutorizados.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.pnlAutorizados.modelo.removeRow(i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ArrayList<AutorizadoBO> autorizadoBOList = autorizadoDao.consultaPorIdAluno(pFormulario.idAluno);
			int indice = 0;
			if(autorizadoBOList != null) {
				do {
					pFormulario.pnlAutorizados.modelo.addRow(new Object[] {autorizadoBOList.get(indice).getNome(), autorizadoBOList.get(indice).getCelular()
							, autorizadoBOList.get(indice).getFoneComercial(), autorizadoBOList.get(indice).getTipo()
							, sdf.format(autorizadoBOList.get(indice).dataInicio.getData().getTime())
							, sdf.format(autorizadoBOList.get(indice).dataFim.getData().getTime())
							, autorizadoBOList.get(indice).getIdAutorizado()
					});
					indice++;
				} while(indice < autorizadoBOList.size());
			}

			
			// apaga todas as linhas da tabela de anexos
			for (int i = pFormulario.pnlAnexos.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.pnlAnexos.modelo.removeRow(i);
			ArrayList<AnexoBO> anexoBOList = anexoDao.consultaPorIdAluno(pFormulario.idAluno);

			if(anexoBOList != null) {
				indice = 0;
				do {
					pFormulario.pnlAnexos.modelo.addRow(new Object[] {anexoBOList.get(indice).getNome()
							, sdf.format(anexoBOList.get(indice).dataInclusao.getData().getTime()), anexoBOList.get(indice).getIdArquivo()
							, anexoBOList.get(indice).getDiretorio()});
					indice++;
				} while(indice < anexoBOList.size());
			}
		}
	}
}