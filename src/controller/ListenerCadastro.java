package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.FrameCadastro;


public class ListenerCadastro implements ActionListener {

	private FrameCadastro pFormulario;

	public ListenerCadastro(FrameCadastro pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();

		if (origem == pFormulario.btnCancelar) {
			pFormulario.dispose();

		}

	}
}