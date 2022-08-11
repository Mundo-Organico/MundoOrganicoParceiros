package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.exception.UserInvalid;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.exception.UserNonexistentException;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Salesman;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories.SalesmanDAO;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.services.SalesmanService;

@Controller
public class SalesmanController {

	@Autowired
	private SalesmanDAO salesmanDAO;

	@Autowired
	private SalesmanService salesmanService;

	@GetMapping("/")
	public String viewLogin() {

		return "index";
	}

	@PostMapping("/logar")
	public String logarSalesman(Model model, @RequestParam String email, @RequestParam String password,
			HttpSession session, RedirectAttributes red) {

		try {
			Salesman logado = this.salesmanService.login(email, password);
			session.setAttribute("logado", logado);
			return "redirect:/main-center";

		} catch (UserNonexistentException e) {
			model.addAttribute("msgErro", e.getMessage());
		} catch (UserInvalid e) {
			model.addAttribute("msgErro", e.getMessage());
		}

		return "redirect:/";
	}

	@GetMapping("/cadastro")
	public String viewSalesman() {
		return "register";
	}

	@GetMapping("/esqueceu-senha")
	public String viewPassword() {
		return "password1";
	}

	@PostMapping("/salvarUser")
	public String createUser(Salesman salesman, String passwordValid, RedirectAttributes red) {

		if (salesman.getFantasy_name().trim().isEmpty() || salesman.getCnpj().trim().isEmpty()
				|| salesman.getEmail().trim().isEmpty() || salesman.getPassword().trim().isEmpty()) {
			// red.addFlashAttribute(); mensagem
			return "redirect:/cadastro";
		}

		if (this.salesmanDAO.existsByEmail(salesman.getEmail()) && !salesman.getPassword().equals(passwordValid)) {
			// red.addFlashAttribute(); mensagem
			// red.addFlashAttribute("", salesman); Retornar o resto
			return "redirect:/cadastro";

		} else if (this.salesmanDAO.existsByEmail(salesman.getEmail())) {
			// red.addFlashAttribute(); mensagem
			// red.addFlashAttribute("", salesman); Retornar o resto
			return "redirect:/cadastro";

		} else if (!salesman.getPassword().equals(passwordValid)) {
			// red.addFlashAttribute(); mensagem
			// red.addFlashAttribute("", salesman); Retornar o resto
			return "redirect:/cadastro";
		}

		salesman.setPassword(salesmanService.criptografarPassword(salesman));
		salesmanService.save(salesman);
//		salesmanService.emailSend(salesman);

		return "redirect:/";
	}

	@GetMapping("/sair")
	public String sair(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/main-center")
	public String viewCenter() {
		return "main-center";
	}

	@GetMapping("/meus-dados")
	public String viewDados() {
		return "settings";
	}

	@GetMapping("/info-pessoa/{id}")
	public String viewInfo(Model model, @PathVariable Integer id) {
		Salesman salesman = salesmanService.findById(id);
		System.out.println(salesman);
		model.addAttribute("salesman", salesman);

		return "settings_personal_information";
	}

	@GetMapping("/credencial/{id}")
	public String viewCred(Model model, @PathVariable Integer id) {
		Salesman salesman = salesmanService.findById(id);
		System.out.println(salesman);
		model.addAttribute("salesman", salesman);

		return "settings_credentials";
	}

	@PostMapping("/updateuser-info")
	public String updateUser(@ModelAttribute Salesman salesman, Model model) {
		model.addAttribute("salesman", salesman);
		salesmanService.updateDate(salesman);

		return "redirect:/meus-dados";
	}

	@PostMapping("/updateuser-credencial")
	public String updateC(@ModelAttribute Salesman salesman, Model model, String passwordValid) {
		
		if (this.salesmanDAO.existsByEmail(salesman.getEmail()) && !salesman.getPassword().equals(passwordValid)) {
			// red.addFlashAttribute(); mensagem
			// red.addFlashAttribute("", salesman); Retornar o resto
			return "redirect:/credencial/{id}";

		} else if (this.salesmanDAO.existsByEmail(salesman.getEmail())) {
			// red.addFlashAttribute(); mensagem
			// red.addFlashAttribute("", salesman); Retornar o resto
			return "redirect:/credencial/{id}";

		} else if (!salesman.getPassword().equals(passwordValid)) {
			// red.addFlashAttribute(); mensagem
			// red.addFlashAttribute("", salesman); Retornar o resto
			return "redirect:/credencial/{id}";
		}

		model.addAttribute("salesman", salesman);
		salesmanService.updateDateC(salesman);

		return "redirect:/meus-dados";
	}
}
