package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import model.bo.AnexoBO;
import model.bo.ParametrosBO;
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
		Aluno,
		Monitor,
		Professor,
		Responsável_Pedagógico,
		Responsável_Financeiro,
		Ambos,
		Busca,
		Emêrgencia
	}
	
	public static enum Sexo {
		Masculino,
		Feminino
	}
	
	public static enum Presente {
		Sim,
		Não
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
	
	public static void copyFile(File source, File dest) throws IOException {
	    FileChannel sourceChannel = null;
	    FileChannel destChannel = null;
	    try {
	        sourceChannel = new FileInputStream(source).getChannel();
	        destChannel = new FileOutputStream(dest).getChannel();
	        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	       }finally{
	           sourceChannel.close();
	           destChannel.close();
	   }
	}
	
	public static ParametrosBO getParametros() {
		ParametrosBO parametrosBO = new ParametrosBO();
		BufferedReader buffRead;
		try {
			buffRead = new BufferedReader(new FileReader("config.ini"));
			ArrayList<String> linhaList = new ArrayList<String>();
			String linha = "";
			while (true) {
				linha = buffRead.readLine();
				
				if (linha != null) {
					//System.out.println(linha);
					String[] linhaSplit = linha.split(":=");
					linhaList.add(linhaSplit[1]);
				} else
					break;
			}
			buffRead.close();

		parametrosBO.setServidorArquivos(linhaList.get(0));
		parametrosBO.setServidorBancoDados(linhaList.get(1));
		parametrosBO.setUsuarioBanco(linhaList.get(2));
		parametrosBO.setSenhaBanco(linhaList.get(3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parametrosBO;
	}
}
