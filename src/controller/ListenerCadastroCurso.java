package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;

import model.bo.CursoBO;
import model.dao.CursoDao;
import model.exceptions.StringVaziaException;
import view.FrameCadastroCurso;

public class ListenerCadastroCurso implements ActionListener {

	private FrameCadastroCurso pFormulario;

	public ListenerCadastroCurso(FrameCadastroCurso pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		CursoBO cursoBO = new CursoBO();
		CursoDao cursoDao = new CursoDao();

		if (origem == pFormulario.btnCancelar) { // para poder chamar o formulário por outro que não é o principal
			this.pFormulario.dispose();

		} else {

			try {
				cursoBO.setCurso(pFormulario.txtCurso.getText());
			} catch (StringVaziaException e1) {
				JOptionPane.showMessageDialog(pFormulario, "Curso" + e1.toString(), "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				pFormulario.txtCurso.requestFocus();
				return;
			}

			cursoBO.setDescricao(pFormulario.txtDescricao.getText());
			cursoBO.setCodigo(pFormulario.codCurso);

			// acesso ao dao
			// objetoDao.incluir(cidade);
			if (cursoBO.getCodigo() > 0) {
				if (cursoDao.alterar(cursoBO)) {
					cursoBO = cursoDao.consultaPorCodigo(cursoBO.getCodigo()).get(0);

					int linha = pFormulario.consCurso.tabela.getSelectedRow();
					pFormulario.consCurso.modelo.setValueAt(cursoBO.getCurso(), linha, 1);
					pFormulario.consCurso.modelo.setValueAt(cursoBO.getDescricao(), linha, 2);
					JOptionPane.showMessageDialog(pFormulario, "Registro alterado!", "Mensagem",
							JOptionPane.WARNING_MESSAGE);
					if (this.pFormulario.consCurso != null) // para chamar o frame de origem
						try {
							this.pFormulario.consCurso.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					this.pFormulario.dispose();
				}
			} else if (cursoDao.incluir(cursoBO)) {
				JOptionPane.showMessageDialog(pFormulario, "Registro incluido!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				this.pFormulario.dispose();
			}

		}

	}

}