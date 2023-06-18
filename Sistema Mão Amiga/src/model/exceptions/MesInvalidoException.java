package model.exceptions;

public class MesInvalidoException extends Exception {

	@Override
	public String toString() {
		return "Mês não têm 31 dias";
	}

}
