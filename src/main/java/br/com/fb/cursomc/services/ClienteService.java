package br.com.fb.cursomc.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fb.cursomc.domain.Cidade;
import br.com.fb.cursomc.domain.Cliente;
import br.com.fb.cursomc.domain.Endereco;
import br.com.fb.cursomc.domain.enums.TipoCliente;
import br.com.fb.cursomc.dto.ClienteDTO;
import br.com.fb.cursomc.dto.ClienteNewDTO;
import br.com.fb.cursomc.repositories.ClienteRepository;
import br.com.fb.cursomc.repositories.EnderecoRepository;
import br.com.fb.cursomc.services.exceptions.DataIntegrityException;
import br.com.fb.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository rep;
	
	@Autowired
	private EnderecoRepository enderecoRep;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = rep.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id +", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = rep.save(obj);
		enderecoRep.saveAll(obj.getEnderecos());
		return obj; 
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return rep.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			rep.deleteById(id);	// aqui pode lançar um DataIntegrityViolationException		
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return rep.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return rep.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cliente, cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDTO.getTelefone1());

		List<String> tels = Arrays.asList(objDTO.getTelefone2(), objDTO.getTelefone3());
		tels.forEach(t -> { if(t != null) cliente.getTelefones().add(t); });
				
		return cliente;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
