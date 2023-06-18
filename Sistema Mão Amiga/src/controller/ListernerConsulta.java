package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.FrameConsulta;

public class ListernerConsulta implements ActionListener {

	private FrameConsulta pFormulario;

	public ListernerConsulta(FrameConsulta pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Sair")) {
			this.pFormulario.dispose();
		}
	}
}
