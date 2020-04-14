package com.dev.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dev.webapp.document.Cliente;
import com.dev.webapp.repository.ClienteRepository;
import com.dev.webapp.service.ClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	@Qualifier("clienteRepository")
	private ClienteRepository clienteRepository;
	
	@Override
	public Flux<Cliente> findAll() {
		Flux<Cliente> fluxCliente = clienteRepository.findAll();
		return fluxCliente;
	}

	@Override
	public Mono<Cliente> selByCodfid(String codFid) {
		Mono<Cliente> monoCliente = clienteRepository.findByCodfid(codFid);
		return monoCliente;
	}
	
	@Override
	public Flux<Cliente> selByNominativo(String description) {
		Flux<Cliente> fluxCliente = clienteRepository.findByNominativoLike(description);
		return fluxCliente;
	}
	
	@Override
	public Flux<Cliente> selByBollini(Integer bollini) {
		Flux<Cliente> fluxCliente = clienteRepository.selectByBollini(bollini);
		return fluxCliente;
	}

	@Override
	public Mono<Cliente> save(Cliente cliente) {
		Mono<Cliente> saveCliente = clienteRepository.save(cliente);
		return saveCliente;
	}

	@Override
	public Mono<Void> delete(String id) {
		return clienteRepository.deleteById(id);
	}

	@Override
	public Mono<Void> deleteByCodFid(String codFid) {
		Mono<Cliente> monoCliente = clienteRepository.findByCodfid(codFid);
		return monoCliente.flatMap((cliente) -> {
			return clienteRepository.delete(cliente);
		});
	}
}