package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
					
					if(indice== 1) {
						pFormulario.tabela.isRowSelected(indice);
						pFormulario.tabela.setSelectionBackground(Color.RED);
						/*pFormulario.tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
							
							public Component getTableCellRendererComponent(JTable table, Object value,
								boolean isSelected, boolean hasFocus, int row, int column) {
								
								super.getTableCellRendererComponent(table, value, isSelected,
									hasFocus, row, column);
								if (row == 2) {
									setBackground(Color.RED);
								} else {
									setBackground(null);
								}
								return this;
							}
						});*/
					}
					

						
						
					
					indice++;
				} while(indice < autorizadoBOList.size());
			}
		}
	}
}
