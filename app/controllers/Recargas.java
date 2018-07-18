package controllers;

import models.Recarga;
import play.data.validation.Valid;
import play.mvc.Controller;

  public class Recargas extends Controller{
	
	public static void salvar(@Valid Recarga recarga) {		
		recarga.save();
		render(recarga);
				
		flash.success("Recarga salva com sucesso!");
		
	}
	
	public static void detalhesRecarga(Recarga recarga) {
		render(recarga); 
	}

}
