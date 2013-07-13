package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import br.ufcg.ppgcc.compor.jcf.experimento.util.CalculoImpostoRenda;
import br.ufcg.ppgcc.compor.jcf.experimento.util.Validacao;

import net.compor.frameworks.jcf.api.Component;
import net.compor.frameworks.jcf.api.Service;



public class GerenteTitularesComFontePagadora extends Component{
	
	private HashMap<Titular,List<FontePagadora>> mapasTitulos = new HashMap<Titular,List<FontePagadora>>();
	private HashMap<Titular,List<Dependente>> mapasDependentes = new HashMap<Titular,List<Dependente>>();
	private CalculoImpostoRenda calculoImpostoDeRenda = new CalculoImpostoRenda();
	private Validacao validacao = new Validacao();
	private Resultado resultado = new Resultado();
	
	public GerenteTitularesComFontePagadora(String name){
		super(name);
	}
	
	@Service(name="AddTitularComFontePagadora")
	public void addTitularComFontePagadora(Titular titular, FontePagadora fonte){
		if((fonte.getNome() == null || fonte.getRendimentoRecebidos() == 0) || fonte.getRendimentoRecebidos() < 0){
			throw new ExcecaoImpostoDeRenda("O nome da fonte pagadora n�o foi setado");
		}
		
		if(mapasTitulos.get(titular) == null){
			
			mapasTitulos.put(titular, new ArrayList<FontePagadora>());
		}
		mapasTitulos.get(titular).add(fonte);
		
	}
	
	@Service(name="AddTitular")
	public void addTitular(Titular titular){
		if(!validacao.obrigatorio(titular.getNome()) || !validacao.obrigatorio(titular.getCpf()) ){
			throw new ExcecaoImpostoDeRenda("O nome e o cpf n�o foi setado");
		}
		mapasTitulos.put(titular, null);
	}
	
	@Service(name="ListarTitulares")
	public List<Titular> listarTitulares(){
		return new ArrayList<Titular>(mapasTitulos.keySet());
	}
	
	@Service(name="ListarFontes")
	public List<FontePagadora> listarFontes(Titular titular){
		return new ArrayList<FontePagadora>(mapasTitulos.get(titular));
	}
	
	@Service(name="AddDependente")
	public void addDependente(Titular titular, Dependente dependente){
		if(dependente.getCpf() == null || dependente.getNome() == null){
			throw new ExcecaoImpostoDeRenda("O nome e o cpf n�o foram setados");
		}
		if(mapasDependentes.get(titular) == null){
			mapasDependentes.put(titular, new ArrayList<Dependente>());
		}
		mapasDependentes.get(titular).add(dependente);
	}
	
	@Service(name="ListarDependentes")
	public List<Dependente> listarDependentes(Titular titular){
		if(mapasDependentes.get(titular) == null){
			mapasDependentes.put(titular, new ArrayList<Dependente>());
		}
		return new ArrayList<Dependente>(mapasDependentes.get(titular));
	}
	
	@Service(name="DeclaracaoCompleta")
	public Resultado declaracaoCompleta(Titular titular){
		double totalRecebido = calculoImpostoDeRenda.totalRecebido(listarFontes(titular));
		totalRecebido = calculoImpostoDeRenda.descontoDependentes(totalRecebido, listarDependentes(titular));
		resultado.setImpostoDevido(calculoImpostoDeRenda.impostoDevido(totalRecebido));
		return resultado;
	}	
	
	
}
