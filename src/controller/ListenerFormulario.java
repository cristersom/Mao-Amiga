package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import view.FrameCadastroCEP;
import view.FrameCadastroCidade;
import view.FrameCadastroResponsavel;
import view.FrameCadastroTurma;
import view.FrameConsultaAluno;
import view.FrameCadastroAluno;
import view.FrameConsultaCEP;
import view.FrameConsultaCidade;
import view.FrameConsultaResponsavel;
import view.FrameConsultaTurma;
import view.SistemaMatricula;

public class ListenerFormulario implements ActionListener {
	private SistemaMatricula pFormulario;

	public ListenerFormulario(SistemaMatricula pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();

		if (origem == pFormulario.itemConsultaAluno || origem == pFormulario.btnAluno) {
			FrameConsultaAluno fr = new FrameConsultaAluno();
			fr.setVisible(true);
			fr.btnSelecionar.setVisible(false);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtConsulta.requestFocus();
			
		} else if (origem == pFormulario.itemCadastroAluno) {
			FrameCadastroAluno fr = new FrameCadastroAluno();
			fr.setVisible(true);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.pnlAluno.requestFocus();

		} else if (origem == pFormulario.itemConsultaCidade || origem == pFormulario.btnCidade) {
			FrameConsultaCidade fr = new FrameConsultaCidade();
			fr.setVisible(true);
			fr.btnSelecionar.setVisible(false);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtConsulta.requestFocus();

		} else if (origem == pFormulario.itemCadastroCidade) {
			FrameCadastroCidade fr = new FrameCadastroCidade();
			fr.setVisible(true);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtCidade.requestFocus();

		} else if (origem == pFormulario.itemConsultaCep || origem == pFormulario.btnCep) {
			FrameConsultaCEP fr = new FrameConsultaCEP();
			fr.setVisible(true);
			fr.btnSelecionar.setVisible(false);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtConsulta.requestFocus();

		} else if (origem == pFormulario.itemCadastroCep) {
			FrameCadastroCEP fr = new FrameCadastroCEP();
			fr.setVisible(true);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtCep.requestFocus();

		} else if (origem == pFormulario.itemConsultaTurma || origem == pFormulario.btnTurma) {
			FrameConsultaTurma fr = new FrameConsultaTurma();
			fr.setVisible(true);
			fr.btnSelecionar.setVisible(false);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtConsulta.requestFocus();

		} else if (origem == pFormulario.itemCadastroTurma) {
			FrameCadastroTurma fr = new FrameCadastroTurma();
			fr.setVisible(true);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtTurma.requestFocus();

		} else if (origem == pFormulario.itemConsultaResponsavel || origem == pFormulario.btnResponsavel) {
			FrameConsultaResponsavel fr = new FrameConsultaResponsavel();
			fr.setVisible(true);
			fr.btnSelecionar.setVisible(false);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			fr.txtConsulta.requestFocus();

		} else if (origem == pFormulario.itemCadastroResponsavel) {
			FrameCadastroResponsavel fr = new FrameCadastroResponsavel();
			fr.setVisible(true);
			pFormulario.DPane.add(fr);
			try {
				fr.setSelected(true);
			} catch (PropertyVetoException exc) {
			}
			//fr.txtResponsavel.requestFocus();

		} else {
			if (JOptionPane.showConfirmDialog(this.pFormulario, "Deseja encerrar a aplicação?", "Confirmação",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}
}
