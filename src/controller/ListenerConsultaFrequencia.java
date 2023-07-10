package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.bo.AutorizadoBO;
import model.bo.FrequenciaBO;
import model.bo.TurmaBO;
import model.dao.FrequenciaDao;
import model.dao.TurmaDao;
import view.FrameConsultaFrequencia;

public class ListenerConsultaFrequencia implements ActionListener {
	private FrameConsultaFrequencia pFormulario;
	
	public ListenerConsultaFrequencia(FrameConsultaFrequencia pFormulario) {
		this.pFormulario = pFormulario;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		
		if (origem == pFormulario.btnCancelar) {

			this.pFormulario.dispose();

		} else if (origem == pFormulario.jcbAnoLetivo) {
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modeloTurma.getRowCount() - 1; i >= 0; i--)
				pFormulario.modeloTurma.removeRow(i);
			
			TurmaDao turmaDao = new TurmaDao();
			List<TurmaBO> turmaList;
			pFormulario.jcbTurma.removeAllItems();
	        try {
	        	turmaList = turmaDao.consultaPorAno(Integer.parseInt(pFormulario.jcbAnoLetivo.getSelectedItem().toString()));
	            for (int i = 0; i < turmaList.size(); i++)
	            	pFormulario.jcbTurma.addItem(turmaList.get(i));
	        } catch (NullPointerException e1){
	        	//se n�o existir turma cadastroda, n�o carrega nada no combobox
	        }
		} else if (origem == pFormulario.jcbTurma) {
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modeloTurma.getRowCount() - 1; i >= 0; i--)
				pFormulario.modeloTurma.removeRow(i);
			
		} else if (origem == pFormulario.btnConsultar) {
			// apaga todas as linhas da tabela
			for (int i = pFormulario.modeloTurma.getRowCount() - 1; i >= 0; i--)
				pFormulario.modeloTurma.removeRow(i);
			
			FrequenciaDao frequenciaDao = new FrequenciaDao();
			TurmaBO turmaBO = (TurmaBO)pFormulario.jcbTurma.getSelectedItem();
			ArrayList<FrequenciaBO> frequenciaBOList = null;
			
			frequenciaBOList = frequenciaDao.getFrequenciaTurma(turmaBO.getId());
			int indice = 0;
			if(frequenciaBOList != null) {
				do {
					pFormulario.modeloTurma.addRow(new Object[] {frequenciaBOList.get(indice).matriculaBO.alunoBO.getId()
							, frequenciaBOList.get(indice).matriculaBO.getIdMatricula(), frequenciaBOList.get(indice).matriculaBO.alunoBO.getNome()
							, frequenciaBOList.get(indice).getFaltasManha(), frequenciaBOList.get(indice).getFaltasTarde()
							, frequenciaBOList.get(indice).getTotalFaltas(), frequenciaBOList.get(indice).getTotalAulas()
							, frequenciaBOList.get(indice).getFrequencia()+"%"
					});
					indice++;
				} while(indice < frequenciaBOList.size());
			
			}
		}
	}
}
