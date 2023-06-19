package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

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
}
