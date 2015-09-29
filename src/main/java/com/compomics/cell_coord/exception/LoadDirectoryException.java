/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.exception;

/**
 * Exception to be thrown if a wrong directory is loaded (for example empty).
 *
 * @author Paola
 */
public class LoadDirectoryException extends Exception {

    public LoadDirectoryException() {
    }

    public LoadDirectoryException(String message) {
        super(message);
    }

    public LoadDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadDirectoryException(Throwable cause) {
        super(cause);
    }

    public LoadDirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
