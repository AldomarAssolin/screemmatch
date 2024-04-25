package br.com.alura.screenmatch.excesao;

public class ErrorDeCoversaoDeAnoException extends RuntimeException {
    private String message;
    public ErrorDeCoversaoDeAnoException(String errMessage){
        this.message = errMessage;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
