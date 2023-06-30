package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.bo.ColaboradorBO;
import model.bo.MatriculaBO;
import model.bo.TurmaBO;
import model.dao.ColaboradorDao;
import model.dao.MatriculaDao;
import model.dao.TurmaDao;
import model.exceptions.StringVaziaException;
import view.FrameCadastroTurma;
import view.FrameConsultaAluno;
import view.FrameConsultaColaborador;
import view.Utils;

public class ListenerCadastroTurma implements ActionListener, ChangeListener {

	private FrameCadastroTurma pFormulario;
	private TurmaBO turmaBO = new TurmaBO();
	private TurmaDao turmaDao = new TurmaDao();
	private MatriculaDao matriculaDao = new MatriculaDao();
	private ColaboradorBO colaboradorBO = new ColaboradorBO();
	private ColaboradorDao colaboradorDao = new ColaboradorDao();
	
	//CursoDao cursoDao = new CursoDao();

	public ListenerCadastroTurma(FrameCadastroTurma pFormulario) {
		this.pFormulario = pFormulario;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		
		if (origem == pFormulario.btnCancelar || origem == pFormulario.btnSair) { // para poder chamar o formul�rio por outro que n�o � o principal
			this.pFormulario.dispose();
		} else if (origem == pFormulario.btnIncluirAluno) {
			FrameConsultaAluno fr = new FrameConsultaAluno(this.pFormulario);
			//this.pFormulario.setVisible(false);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.txtConsulta.requestFocus();
		} else if (origem == pFormulario.btnIncluirColaborador) {
			FrameConsultaColaborador fr = new FrameConsultaColaborador(this.pFormulario);
			//this.pFormulario.setVisible(false);
			fr.setVisible(true);
			pFormulario.getDesktopPane().add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr.txtConsulta.requestFocus();
		} else if (origem == pFormulario.btnExlcuir) {
			if (pFormulario.tabela.getSelectedRow() >= 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Confirma exclus�o?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString() == Utils.Tipo.Aluno.toString()) {
						if (matriculaDao.excluir(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
							pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
					} else {
						if (colaboradorDao.excluirColaboradorTurma(Integer.parseInt(
								pFormulario.modelo.getValueAt(pFormulario.tabela.getSelectedRow(), 0).toString())) == true)
							pFormulario.modelo.removeRow(pFormulario.tabela.getSelectedRow());
					}
				}
			} else
				JOptionPane.showMessageDialog(pFormulario, "Escolha um registro!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);			
		} else {//bot�o ok

			turmaBO.setId(pFormulario.idTurma);
			turmaBO.cursoBO.setId(pFormulario.listCursoDao.get(pFormulario.jcbCurso.getSelectedIndex()).getId());
			turmaBO.setAno((int) pFormulario.jcbAno.getSelectedItem());
			turmaBO.setDescricao(pFormulario.txtDescricao.getText());
			
			try {
				turmaBO.setTurma(pFormulario.txtTurma.getText());
				turmaBO.setDataInicio(pFormulario.txtDataIni.getText());
				turmaBO.setDataFim(pFormulario.txtDataFim.getText());
				

				// acesso ao Dao
				if (turmaBO.getId() > 0) {
					if (turmaDao.alterar(turmaBO)) {
						turmaBO = turmaDao.consultaPorCodigo(turmaBO.getId()).get(0);
						try {
							int linha = pFormulario.consTurma.tabela.getSelectedRow();
							pFormulario.consTurma.modelo.setValueAt(turmaBO.getId(), linha, 0);
							pFormulario.consTurma.modelo.setValueAt(turmaBO.getTurma(), linha, 1);
							pFormulario.consTurma.modelo.setValueAt(turmaBO.getDescricao(), linha, 2);
							pFormulario.consTurma.modelo.setValueAt(turmaBO.getAno(), linha, 3);
							JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
									JOptionPane.WARNING_MESSAGE);
						} catch (NullPointerException n) {//no caso de um registro novo que � alterado durante a cria��o
							/*
							pFormulario.consTurma.modelo.addRow(new Object[] {turmaBO.getId(), turmaBO.getTurma()
									, turmaBO.getDescricao(), turmaBO.getAno()
							});
							*/
						}

						if (this.pFormulario.consTurma != null) // para chamar o frame de origem
							try {
								this.pFormulario.consTurma.setSelected(true);
							} catch (PropertyVetoException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						this.pFormulario.dispose();
					}
				} else if (turmaDao.incluir(turmaBO)) {
					JOptionPane.showMessageDialog(pFormulario, "Registros incluido!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					this.pFormulario.dispose();
				}				
			} catch (StringVaziaException e3) {
				JOptionPane.showMessageDialog(pFormulario, "Informe uma turma!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			} catch (ParseException e2) {
				JOptionPane.showMessageDialog(pFormulario, "Data inv�lida!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}
	
	    public void stateChanged(ChangeEvent e) {
	    	
	        if (pFormulario.tabbedPane.getSelectedComponent() == pFormulario.pnlPessoas) {
	        	turmaBO.setId(pFormulario.idTurma);
    			turmaBO.cursoBO.setId(pFormulario.listCursoDao.get(pFormulario.jcbCurso.getSelectedIndex()).getId());
    			turmaBO.setAno((int) pFormulario.jcbAno.getSelectedItem());
    			turmaBO.setDescricao(pFormulario.txtDescricao.getText());
    			
    			try {
    				turmaBO.setTurma(pFormulario.txtTurma.getText());
    				turmaBO.setDataInicio(pFormulario.txtDataIni.getText());
    				turmaBO.setDataFim(pFormulario.txtDataFim.getText());
    				pFormulario.turmaBO = turmaBO;

    			} catch (StringVaziaException e3) {
    				JOptionPane.showMessageDialog(pFormulario, "Informe os dados da turma!", "Mensagem",
    						JOptionPane.WARNING_MESSAGE);   				
    			} catch (ParseException e2) {
    				JOptionPane.showMessageDialog(pFormulario, "Informe as datas inv�lida!", "Mensagem",
    						JOptionPane.WARNING_MESSAGE);
    			}
	        		        	
	        	if(pFormulario.turmaBO.getId() > 0 ) {
		        	ArrayList<MatriculaBO> matriculaBOList = null;
		        	ArrayList<ColaboradorBO> colaboradorBOList = null;
		        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // HH:mm:ss
		        	
		        	matriculaBOList = matriculaDao.consultaPorTurma(pFormulario.idTurma);
		        	colaboradorBOList = colaboradorDao.consultaPorTurma(pFormulario.idTurma);
		        	
					// apaga todas as linhas da tabela
					for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
						pFormulario.modelo.removeRow(i);
					
					int indice = 0;
					do {
						try {
							pFormulario.modelo.addRow(new Object[] { colaboradorBOList.get(indice).getId()
									, colaboradorBOList.get(indice).getNome(), colaboradorBOList.get(indice).getTipo()
									, colaboradorBOList.get(indice).getCpf(), sdf.format(colaboradorBOList.get(indice).getDataNascimento().getTime())
									, colaboradorBOList.get(indice).getNomeMae()
							});
						} catch (Exception e1) {
							break;
						}
						indice++;
					} while (indice < matriculaBOList.size());					
					
					indice = 0;
					do {
						try {
							pFormulario.modelo.addRow(new Object[] { matriculaBOList.get(indice).getAluno()
									, matriculaBOList.get(indice).turmaBO.alunoBO.getNome(), "Aluno", matriculaBOList.get(indice).turmaBO.alunoBO.getCpf()
									, sdf.format(matriculaBOList.get(indice).turmaBO.alunoBO.getDataNascimento().getTime())
									, matriculaBOList.get(indice).turmaBO.alunoBO.getNomeMae()
							});
						} catch (Exception e1) {
							break;
						}
						indice++;
					} while (indice < matriculaBOList.size());
	        	} else {
	    			if (turmaDao.incluir(turmaBO)) {
	    				turmaBO.setId(turmaDao.consultaPorTurmaAno(turmaBO.getTurma(), turmaBO.getAno()));
	    				pFormulario.idTurma = turmaBO.getId();
	    			}
				}
	        }
	    }
}
