package model.exceptions;

public class StringVaziaException extends Exception {

	@Override
	public String toString() {
		return " não pode ser vazio";
	}

}
