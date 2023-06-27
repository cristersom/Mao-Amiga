package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import model.exceptions.AnoInvalidoException;
import model.exceptions.DiaInvalidoException;
import model.exceptions.MesInvalidoException;

public class Utils {
	public static void addGridBag(JPanel pnl, JComponent component, int x, int y, GridBagConstraints gbc) {
		gbc.gridx = x;
		gbc.gridy = y;

		pnl.add(component, gbc);
	}

	public static JLabel CreateLabel(JLabel label, String text) {
		label = new JLabel(text);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		return label;
	}

	public static String formatString(String value, String pattern) {
		MaskFormatter mf;
		try {
			mf = new MaskFormatter(pattern);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (Exception ex) {
			return value;
		}
	}
	
	public static enum Tipo {
		ALUNO,
		MONITOR,
		PROFESSOR
	}
	
	public void validaData(int dia, int mes, int ano)
			throws DiaInvalidoException, MesInvalidoException, AnoInvalidoException {
		if (dia == 31 && mes != 1 && mes != 3 && mes != 5 && mes != 7 && mes != 8 && mes != 10 && mes != 12)
			throw new MesInvalidoException();

		else if (dia == 30 && mes == 2)
			throw new DiaInvalidoException();

		else if (dia == 29 && mes == 2 && ano % 4 != 0)
			throw new AnoInvalidoException();

		//this.nascimento = String.valueOf(dia) + '/' + String.valueOf(mes) + '/' + String.valueOf(ano);
	}
}
