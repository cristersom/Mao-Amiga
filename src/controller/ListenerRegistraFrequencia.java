package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import model.bo.MatriculaBO;
import model.bo.TurmaBO;
import model.bo.TurmaColaboradorBO;
import model.dao.MatriculaDao;
import model.dao.TurmaColaboradorDao;
import model.dao.TurmaDao;
import view.FrameCadastroAluno;
import view.FrameRegistraFrequencia;

public class ListenerRegistraFrequencia implements ActionListener {

	private FrameRegistraFrequencia pFormulario;
	private MatriculaDao matriculaDao = new MatriculaDao();
	
	public ListenerRegistraFrequencia(FrameRegistraFrequencia pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();

		if (origem == pFormulario.btnCancelar) {

			this.pFormulario.dispose();

		} else if (origem == pFormulario.jcbAnoLetivo) {
			TurmaDao turmaDao = new TurmaDao();
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
			TurmaColaboradorDao turmaColaboradorDao = new TurmaColaboradorDao();
			List<TurmaColaboradorBO> professorList;
			pFormulario.jcbProfessor.removeAllItems();
	        try {
	        	TurmaBO turmaBO = (TurmaBO)pFormulario.jcbTurma.getSelectedItem();
	        	professorList = turmaColaboradorDao.consultaProfessores(turmaBO.getId());
	            for (int i = 0; i < professorList.size(); i++)
	            	pFormulario.jcbProfessor.addItem(professorList.get(i));
	        } catch (NullPointerException e1){
	        	//se não existir turma cadastroda, não carrega nada no combobox
	        }
	    
		} else if (origem == pFormulario.btnBuscar) {
			
			ArrayList<MatriculaBO> matriculaBOList = new ArrayList<MatriculaBO>();
			TurmaBO turmaBO = (TurmaBO)pFormulario.jcbTurma.getSelectedItem();
			
			matriculaBOList = matriculaDao.consultaPorTurma(turmaBO.getId());
			
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);
			
			int indice = 0;
			do {
				try {//carrega com todas as presenças marcadas
					pFormulario.modelo.addRow(new Object[] {matriculaBOList.get(indice).getMatricula(), matriculaBOList.get(indice).alunoBO.getId()
							, matriculaBOList.get(indice).alunoBO.getNome(),true, true, matriculaBOList.get(indice).getIdTurma()
					});
				} catch (Exception e1) {
					break;
				}
				indice++;
			} while (indice < matriculaBOList.size());	
	        
		} else {//botão ok
/*
			try {
				cidBO.setCidade(pFormulario.txtCidade.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Cidade" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtCidade.selectAll();
				pFormulario.txtCidade.requestFocus();
				return;
				// JOptionPane.showMessageDialog(pFormulario,"Esta é uma caixa de diálogo de
				// Mensagem", "Mensagem", JOptionPane.WARNING_MESSAGE);
			}

			cidBO.setUf((String) pFormulario.jcbUf.getSelectedItem());
			cidBO.setId(pFormulario.idCidade);

			// acesso ao dao
			// objetoDao.incluir(cidade);
			if (cidBO.getId() > 0) { // Se for "0" é uma cidade nova
				if (cidDao.alterar(cidBO)) {
					cidBO = cidDao.consultaPorCodigoUnico(cidBO.getId());
					int linha = pFormulario.consCid.tabela.getSelectedRow();
					pFormulario.consCid.modelo.setValueAt(cidBO.getUf(), linha, 1);
					pFormulario.consCid.modelo.setValueAt(cidBO.getCidade(), linha, 2);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consCid != null) //para chamar o frame de origem
						try {
							this.pFormulario.consCid.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					this.pFormulario.dispose();
				}
			} else if (cidDao.incluir(cidBO)) {
				JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				this.pFormulario.dispose();
			}
*/
		}
	}
}