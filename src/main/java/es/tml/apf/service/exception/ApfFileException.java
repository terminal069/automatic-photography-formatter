package es.tml.apf.service.exception;

public class ApfFileException extends Exception{

	private static final long serialVersionUID = 1955373695880225089L;

	public ApfFileException() {
		
		super();
	}
	
	public ApfFileException(String message) {
		
        super(message);
    }
	
	public ApfFileException(String message, Throwable cause) {
		
        super(message, cause);
    }
}
