package model.exceptions;

public class AnoInvalidoException extends Exception {

	@Override
	public String toString() {
		return "Ano não é bissexto";
	}

}
