package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.exception.UserInvalid;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.exception.UserNonexistentException;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Salesman;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories.SalesmanDAO;

@Service
public class SalesmanService {

	@Autowired
	private SalesmanDAO salesmanDAO;
	
	private static final int complexidadeSenha = 10;
	
	public Salesman save(Salesman salesman) {
		return salesmanDAO.saveAndFlush(salesman);
	}
	
	public void validSaveSalesman(Salesman salesman, String passwordValid) throws UserInvalid {

		if (salesman.getFantasy_name().trim().isEmpty() || salesman.getCnpj().trim().isEmpty()
				|| salesman.getEmail().trim().isEmpty() || salesman.getPassword().trim().isEmpty()) {

			throw new UserInvalid("Os campos obrigatórios não podem estar vazio.");
		}

		if (this.salesmanDAO.existsByEmail(salesman.getEmail()) && !salesman.getPassword().equals(passwordValid)) {
			throw new UserInvalid("Email já cadastrado.");

		} else if (this.salesmanDAO.existsByEmail(salesman.getEmail())) {
			throw new UserInvalid("Email já cadastrado.");

		} else if (!salesman.getPassword().equals(passwordValid)) {
			throw new UserInvalid("As senhas não coincidem.");
		}

	}
	
	public Salesman login(String email, String password) throws UserNonexistentException, UserInvalid {
		
		if (email.isBlank()) {
			throw new UserInvalid("Insira o email.");
		}

		if (password.isBlank()) {
			throw new UserInvalid("Insira a senha.");
		}
		
		Optional<Salesman> salesman = salesmanDAO.findByEmail(email);
		
		if (salesman.isPresent()) {
			if (BCrypt.checkpw(password, salesman.get().getPassword())) {
				return salesman.get();
			} else {
				throw new UserNonexistentException("Email e/ou senha inválidos");
			}
		} else {
			throw new UserNonexistentException("Email e/ou senha inválidos");
		}
	}
	
	public String criptografarPassword(Salesman salesman) {
		return BCrypt.hashpw(salesman.getPassword(), BCrypt.gensalt(complexidadeSenha));
	}

	public Salesman findById(Integer id){
		Optional<Salesman> obj = salesmanDAO.findById(id);
		return obj.get();
	}

	public void updateDate(Salesman salesman) {
		Salesman entity = findById(salesman.getId());
		entity.setFantasy_name(salesman.getFantasy_name());
		entity.setCnpj(salesman.getCnpj());
		salesmanDAO.save(entity);
	}
	
	public void updateDateC(Salesman salesman) {
		Salesman entity = findById(salesman.getId());
		entity.setEmail(salesman.getEmail());
		entity.setPassword(BCrypt.hashpw(salesman.getPassword(), BCrypt.gensalt(complexidadeSenha)));
		salesmanDAO.save(entity);
	}
	
}
