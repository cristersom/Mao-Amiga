package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import model.bo.AulaBO;
import model.bo.ColaboradorBO;
import model.bo.FrequenciaBO;
import model.bo.MatriculaBO;
import model.bo.TurmaBO;
import model.bo.TurmaColaboradorBO;
import model.dao.AulaDao;
import model.dao.MatriculaDao;
import model.dao.TurmaColaboradorDao;
import model.dao.TurmaDao;
import view.FrameCadastroAluno;
import view.FrameRegistraAula;
import view.Utils;

public class ListenerRegistraAula implements ActionListener {

	private FrameRegistraAula pFormulario;
	private MatriculaDao matriculaDao = new MatriculaDao();
	
	public ListenerRegistraAula(FrameRegistraAula pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		AulaBO aulaBO = new AulaBO();
		AulaDao aulaDao = new AulaDao();
		TurmaBO turmaBO = new TurmaBO();
		TurmaDao turmaDao = new TurmaDao();
		TurmaColaboradorDao turmaColaboradorDao = new TurmaColaboradorDao();
		
		if (origem == pFormulario.btnCancelar) {

			this.pFormulario.dispose();

		} else if (origem == pFormulario.jcbAnoLetivo) {
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);
			pFormulario.txtConteudoMinistrado.setText("");
			
			List<TurmaBO> turmaList;
			pFormulario.jcbTurma.removeAllItems();
	        try {
	        	turmaList = turmaDao.consultaPorAno(Integer.parseInt(pFormulario.jcbAnoLetivo.getSelectedItem().toString()));
	            for (int i = 0; i < turmaList.size(); i++)
	            	pFormulario.jcbTurma.addItem(turmaList.get(i));
	        } catch (NullPointerException e1){
	        	//se não existir turma cadastroda, não carrega nada no combobox
	        }
	        
		} else if (origem == pFormulario.jcbTurma) {
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);
			pFormulario.txtConteudoMinistrado.setText("");
			
			List<TurmaColaboradorBO> professorList;
			pFormulario.jcbProfessor.removeAllItems();
			pFormulario.jcbProfessor.addItem(null);//para obrigar a seleção de professor para registro de aula
	        try {
	        	turmaBO = (TurmaBO)pFormulario.jcbTurma.getSelectedItem();
	        	professorList = turmaColaboradorDao.consultaProfessores(turmaBO.getId());
	            for (int i = 0; i < professorList.size(); i++)
	            	pFormulario.jcbProfessor.addItem(professorList.get(i));
	        } catch (NullPointerException e1){
	        	//se não existir turma cadastroda, não carrega nada no combobox
	        }
		} else if (origem == pFormulario.jcbDia || origem == pFormulario.jcbMes|| origem == pFormulario.jcbAno) {
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);
			pFormulario.txtConteudoMinistrado.setText("");
		} else if (origem == pFormulario.btnBuscar) {
			
			turmaBO = (TurmaBO)pFormulario.jcbTurma.getSelectedItem();
			aulaBO.setIdTurma(turmaBO.getId());
			
			String dia = String.valueOf(pFormulario.jcbDia.getSelectedIndex() + 1);
			String mes = String.valueOf(pFormulario.jcbMes.getSelectedIndex() + 1);
			String ano = String.valueOf(pFormulario.jcbAno.getSelectedIndex() + 1900);
			try {
				aulaBO.setDataAula(dia + '/' + mes + '/' + ano);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			aulaBO = aulaDao.consulta(aulaBO.getIdTurma(), aulaBO.getDataAula());
			
			pFormulario.idAula = aulaBO.getIdAula();
			
			if(pFormulario.idAula > 0) {
				if (JOptionPane.showConfirmDialog(pFormulario, "Já registro de aula para este dia!\n Deseja atualizar?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					pFormulario.txtConteudoMinistrado.setText(aulaBO.getConteudo_ministrado());
					
					// apaga todas as linhas da tabela
					for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
						pFormulario.modelo.removeRow(i);
					
					int indice = 0;
					do {
						try {//carrega com todas as presenças marcadas
							pFormulario.modelo.addRow(new Object[] {aulaBO.frequenciaBOList.get(indice).matriculaBO.getIdMatricula()
									, aulaBO.frequenciaBOList.get(indice).matriculaBO.alunoBO.getId()
									, aulaBO.frequenciaBOList.get(indice).matriculaBO.alunoBO.getNome()
									, (aulaBO.frequenciaBOList.get(indice).getPresenteManha().toString().equals(Utils.Presente.Sim.toString()) ? true : false)
									, (aulaBO.frequenciaBOList.get(indice).getPresenteTarde().toString().equals(Utils.Presente.Sim.toString()) ? true : false)
									, aulaBO.getIdTurma()
							});
						} catch (Exception e1) {
							break;
						}
						indice++;
					} while (indice < aulaBO.frequenciaBOList.size());	
			        pFormulario.btnOk.setText("Atualizar Aula");
			        return;
				} else return;
			}
			
			
			// se não tem aula registrada, carrega todos os alunos da turma
			ArrayList<MatriculaBO> matriculaBOList = new ArrayList<MatriculaBO>();
			matriculaBOList = matriculaDao.consultaPorTurma(turmaBO.getId());
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);
			
			int indice = 0;
			do {
				try {//carrega com todas as presenças marcadas
					pFormulario.modelo.addRow(new Object[] {matriculaBOList.get(indice).getIdMatricula(), matriculaBOList.get(indice).alunoBO.getId()
							, matriculaBOList.get(indice).alunoBO.getNome(),true, true, matriculaBOList.get(indice).getIdTurma()
					});
				} catch (Exception e1) {
					break;
				}
				indice++;
			} while (indice < matriculaBOList.size());	
	       // pFormulario.btnOk.setText("Atualizar Aula");
			
		} else {//botão ok
			
			if(pFormulario.jcbProfessor.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(pFormulario, "Selecione o Professor!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(pFormulario.txtConteudoMinistrado.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(pFormulario, "Informe o conteúdo ministrado!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
						
			turmaBO = (TurmaBO)pFormulario.jcbTurma.getSelectedItem();
			aulaBO.setIdTurma(turmaBO.getId());
			String dia = String.valueOf(pFormulario.jcbDia.getSelectedIndex() + 1);
			String mes = String.valueOf(pFormulario.jcbMes.getSelectedIndex() + 1);
			String ano = String.valueOf(pFormulario.jcbAno.getSelectedIndex() + 1900);
			try {
				aulaBO.setDataAula(dia + '/' + mes + '/' + ano);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			TurmaColaboradorBO turmaColaboradorBO = (TurmaColaboradorBO)pFormulario.jcbProfessor.getSelectedItem();
			aulaBO.setIdColaborador(turmaColaboradorBO.colaboradorBO.getId());
			
			aulaBO.setConteudo_ministrado(pFormulario.txtConteudoMinistrado.getText().toString());
			
			//buscar frequencia alunos
			
			for(int i=0; i < pFormulario.tabela.getRowCount(); i++) {
				FrequenciaBO frequenciaBO = new FrequenciaBO();
				frequenciaBO.matriculaBO.setIdMatricula(Integer.parseInt(pFormulario.modelo.getValueAt(i, 0).toString()));
				if((boolean) pFormulario.modelo.getValueAt(i, 3))
					frequenciaBO.setPresenteManha(Utils.Presente.Sim.toString());
				if((boolean) pFormulario.modelo.getValueAt(i, 4))
					frequenciaBO.setPresenteTarde(Utils.Presente.Sim.toString());
				aulaBO.frequenciaBOList.add(frequenciaBO);
			}
			
			//int idAula = aulaDao.getAula(aulaBO.getIdTurma(), aulaBO.getDataAula()).getIdAula();
			
			if(pFormulario.idAula > 0) {
				aulaBO.setIdAula(pFormulario.idAula);
				if (JOptionPane.showConfirmDialog(pFormulario, "Já registro de aula para este dia!\n Deseja atualizar?", "Confirmacao",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if(aulaDao.alterar(aulaBO)) {
						JOptionPane.showMessageDialog(pFormulario, "Registro Atualizado!", "Mensagem",
								JOptionPane.WARNING_MESSAGE);
						this.pFormulario.dispose();
					}
						
				}
			}else if(aulaDao.incluir(aulaBO)) {
				JOptionPane.showMessageDialog(pFormulario, "Aula Registrada!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				this.pFormulario.dispose();
			}

		}
	}
}