package com.cartel;

import java.io.File;

import javax.smartcardio.CardException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.cartel.model.Client;
import com.cartel.nfc.ReaderException;

public class Test {

	public static void main(String[] args){
		String s="bonsoivgdffgdfgfddgdgf";
		System.out.println(s.substring(0,Math.min(s.length(), 16)));
	}
}
