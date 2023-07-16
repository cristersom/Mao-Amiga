package view;

import java.awt.Color;
import java.awt.Component;
import java.util.*;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ModeloTabela extends AbstractTableModel {
	private ArrayList linhas = null;
	private String[] colunas = null;
	private boolean[] colEditavel;

	public ModeloTabela(ArrayList lin, String[] col, boolean[] editavel) {
		setLinhas(lin);
		setColunas(col);
		this.colEditavel = editavel;
	}

	public ArrayList getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList dados) {
		linhas = dados;
	}

	public String[] getColunas() {
		return colunas;
	}

	public void setColunas(String[] nomes) {
		colunas = nomes;
	}

	public int getColumnCount() {
		return colunas.length;
	}

	public int getRowCount() {
		return linhas.size();
	}

	public String getColumnName(int numCol) {
		return colunas[numCol];
	}

	public boolean isCellEditable(int numCol) {
		return this.colEditavel[numCol];
	}
	
	public boolean isCellEditable(int row, int col) {
	    return this.colEditavel[col];
	}
	
	public Object getValueAt(int numLin, int numCol) {
		Object[] linha = (Object[]) getLinhas().get(numLin);
		return linha[numCol];
	}

	public void setValueAt(Object dado, int numLin, int numCol) {
		if (isCellEditable(numCol)) {
			Object[] linha = (Object[]) getLinhas().get(numLin);
			linha[numCol] = dado;
			fireTableDataChanged();
		}
	}

	public void addRow(Object[] dados) {
		getLinhas().add(dados);
		fireTableDataChanged();
	}

	public void removeRow(int numLin) {
		getLinhas().remove(numLin);
		fireTableDataChanged();
	}

	public Class getColumnClass(int numCol) {
		Object[] linha = (Object[]) getLinhas().get(0);
		return linha[numCol].getClass();
	}
	
	public DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             comp.setForeground(Color.WHITE);
             comp.setBackground(Color.BLACK);
             return comp;
        }
    };
}
