package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import model.bo.TurmaBO;
import model.dao.CursoDao;
import model.dao.TurmaDao;
import model.exceptions.StringVaziaException;
import view.FrameCadastroTurma;

public class ListenerCadastroTurma implements ActionListener {

	private FrameCadastroTurma pFormulario;
	CursoDao cursoDao = new CursoDao();

	public ListenerCadastroTurma(FrameCadastroTurma pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		TurmaBO turmaBO = new TurmaBO();
		TurmaDao turmaDao = new TurmaDao();;

		if (origem == pFormulario.btnCancelar) { // para poder chamar o formulário por outro que não é o principal
			this.pFormulario.dispose();
		} else {//botão ok

			turmaBO.setCodigo(pFormulario.codTurma);
			turmaBO.curso.setCodigo(pFormulario.listCursoDao.get(pFormulario.jcbCurso.getSelectedIndex()).getCodigo());
			turmaBO.setAno((int) pFormulario.jcbAno.getSelectedItem());
			turmaBO.setDescricao(pFormulario.txtDescricao.getText());
			
			try {
				turmaBO.setTurma(pFormulario.txtTurma.getText());
			} catch (StringVaziaException e3) {
				JOptionPane.showMessageDialog(pFormulario, "Informe uma turma!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			}
			
			try {
				
				turmaBO.setDataInicio(pFormulario.txtDataIni.getText());
				turmaBO.setDataFim(pFormulario.txtDataFim.getText());
			} catch (ParseException e2) {
				JOptionPane.showMessageDialog(pFormulario, "Data inválida!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			}

			// acesso ao Dao
			if (turmaBO.getCodigo() > 0) {
				if (turmaDao.alterar(turmaBO)) {
					turmaBO = turmaDao.consultaPorCodigo(turmaBO.getCodigo()).get(0);
					int linha = pFormulario.consTurma.tabela.getSelectedRow();
					pFormulario.consTurma.modelo.setValueAt(turmaBO.getCodigo(), linha, 0);
					pFormulario.consTurma.modelo.setValueAt(turmaBO.getTurma(), linha, 1);
					pFormulario.consTurma.modelo.setValueAt(turmaBO.getDescricao(), linha, 2);
					pFormulario.consTurma.modelo.setValueAt(turmaBO.getAno(), linha, 3);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					
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
				JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				this.pFormulario.dispose();
			}

		}

	}

}
