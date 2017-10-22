package br.com.xp5.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.xp5.cobranca.model.StatusTitulo;
import br.com.xp5.cobranca.model.Titulo;
import br.com.xp5.cobranca.repository.Titulos;
import br.com.xp5.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {
	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválida!");
		}
	}

	public void excluir(Long codigo) {
		titulos.delete(codigo);
		
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
		
	}
	
	public List<Titulo> filtar(TituloFilter filtro){
		
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return  titulos.findByDescricaoContaining(descricao);
		
		
	}
}
