package fr.ocr.sql;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AbstractTableModelVehicule extends AbstractTableModel {

	private List<Object[]> data = new ArrayList<Object[]>();
	private String[] title;

	public AbstractTableModelVehicule(Object[][] data, String[] title) {
		this.title = title;
		for (Object[] vehicule : data) {
			this.data.add(vehicule);
		}
	}

	public String getColumnName(int col) {
		return this.title[col];
	}

	@Override
	public int getColumnCount() {
		return this.title.length;
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!this.getColumnName(columnIndex).equals("ACTION") && !this.getColumnName(columnIndex).equals("DETAIL"))
			return this.data.get(rowIndex)[columnIndex];
		else
			return null;
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (!this.getColumnName(columnIndex).equals("ACTION") && !this.getColumnName(columnIndex).equals("DETAIL"))
			this.data.get(rowIndex)[columnIndex] = value;
	}

	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}

	public void removeRow(int position) {
		this.data.remove(position);
		this.fireTableDataChanged();
	}

	public void addRow(Object[] vehicule) {
		this.data.add(vehicule);
		this.fireTableDataChanged();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}