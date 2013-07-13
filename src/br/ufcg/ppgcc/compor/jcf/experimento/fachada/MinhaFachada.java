package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.List;

import net.compor.frameworks.jcf.api.ComporFacade;

public class MinhaFachada extends ComporFacade implements FachadaExperimento {
	
	public void addComponents(){
		add(new GerenteTitularesComFontePagadora("GerenteTitularesComFontePagadoraComponent"));
	}
	
	public void criarNovoTitular(Titular titular) {
		requestService("AddTitular", titular);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Titular> listarTitulares() {
		return (List<Titular>) requestService("ListarTitulares");
	}

	@Override
	public void criarFontePagadora(Titular titular, FontePagadora fonte) {
		requestService("AddTitularComFontePagadora", titular, fonte);
		
	}

	@Override
	public Resultado declaracaoCompleta(Titular titular) {
		return (Resultado) requestService("DeclaracaoCompleta", titular);
	}

	@Override
	public void criarDependente(Titular titular, Dependente dependente) {
		requestService("AddDependente", titular, dependente);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FontePagadora> listarFontes(Titular titular) {
		return (List<FontePagadora>) requestService("ListarFontes", titular);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dependente> listarDependentes(Titular titular) {
		return (List<Dependente>) requestService("ListarDependentes", titular);
	}

	@Override
	public void criarGastoDedutivel(Titular titular, Pessoa realizador,
			GastoDedutivel gastoDedutivel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GastoDedutivel> listarGastosDedutiveis(Titular titular,
			Pessoa realizador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado relatorioSimplificado(Titular titular) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
