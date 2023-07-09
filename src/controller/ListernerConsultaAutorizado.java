package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.bo.AutorizadoBO;
import model.dao.AutorizadoDao;
import view.FrameConsultaAutorizado;

public class ListernerConsultaAutorizado implements ActionListener  {
	
	private FrameConsultaAutorizado pFormulario;
	private AutorizadoDao autorizadoDao = new AutorizadoDao();

	public ListernerConsultaAutorizado(FrameConsultaAutorizado pFormulario) {
		this.pFormulario = pFormulario;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("Consultar")) {
			ArrayList<AutorizadoBO> autorizadoBOList = null;
			
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modelo.getRowCount() - 1; i >= 0; i--)
				pFormulario.modelo.removeRow(i);
			
			if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Nome Autorizado")) {
				autorizadoBOList = autorizadoDao.consultaPorNome(pFormulario.txtConsulta.getText());
			} else if (pFormulario.jcbconsultaPor.getSelectedItem().equals("Nome Aluno")) {
				autorizadoBOList = autorizadoDao.consultaPorNomeAluno(pFormulario.txtConsulta.getText());
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			int indice = 0;
			if(autorizadoBOList != null) {
				do {
					pFormulario.modelo.addRow(new Object[] {autorizadoBOList.get(indice).getNomeAluno(), autorizadoBOList.get(indice).getNome()
							, autorizadoBOList.get(indice).getCelular(), autorizadoBOList.get(indice).getFoneComercial()
							, autorizadoBOList.get(indice).getTipo()
							, sdf.format(autorizadoBOList.get(indice).dataInicio.getData().getTime())
							, sdf.format(autorizadoBOList.get(indice).dataFim.getData().getTime())
							, autorizadoBOList.get(indice).getIdAutorizado()
					});
					indice++;
				} while(indice < autorizadoBOList.size());
			}
		}
	}
}
