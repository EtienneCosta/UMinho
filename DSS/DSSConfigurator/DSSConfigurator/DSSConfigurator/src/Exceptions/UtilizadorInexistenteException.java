/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Joana Marta Cruz
 */
public class UtilizadorInexistenteException extends Exception{
    public UtilizadorInexistenteException() {
        super();
    }
    
    public UtilizadorInexistenteException(String s) {
        super(s);
    }
}
