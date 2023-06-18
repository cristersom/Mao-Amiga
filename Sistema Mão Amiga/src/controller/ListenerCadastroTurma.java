package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;

import model.bo.TurmaBO;
import model.dao.TurmaDao;
import model.exceptions.StringVaziaException;
import view.FrameCadastroTurma;

public class ListenerCadastroTurma implements ActionListener {

	private FrameCadastroTurma pFormulario;

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
		} else {

			turmaBO.setAno((int) pFormulario.jcbAno.getSelectedItem());

			try {
				turmaBO.setTurma(pFormulario.txtTurma.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Turma" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtTurma.requestFocus();
				return;
			}

			turmaBO.setDescricao(pFormulario.txtDescricao.getText());
			turmaBO.setCodigo(pFormulario.codTurma);

			// acesso ao Dao
			if (turmaBO.getCodigo() > 0) {
				if (turmaDao.alterar(turmaBO)) {
					turmaBO = turmaDao.consultaPorCodigo(turmaBO.getCodigo()).get(0);
					int linha = pFormulario.consTurma.tabela.getSelectedRow();
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
