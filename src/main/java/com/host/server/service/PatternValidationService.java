package com.host.server.service;


import com.host.server.model.DTO.ClienteDTO;
import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.Entitys.PlanoDeNegocio;
import com.host.server.repository.ClienteRepository;
import com.host.server.repository.PlanoDeNegocioRepository;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.password.CompromisedPasswordException;

import java.util.regex.Pattern;

public class PatternValidationService {

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private PlanoDeNegocioRepository planoRepo;

    public static final Pattern NOME_USUARIO_PATTERN = Pattern.compile("^[\\p{L} _-]+$");

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    public static final Pattern NOME_PRODUTO_PATTERN = Pattern.compile("^[A-Za-z0-9_#()]+$");

    public static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%&*\\[\\]{}()?/\\\\|_\\-+=])[A-Za-z\\d!@#$%&*\\[\\]{}()?/\\\\|_\\-+=]{8,}$");

    public void validateUser(UsuarioDTO user) {
        //repo validations
        if (!userRepo.existsById(user.getId())) {
            throw new RuntimeException("O usuário não existe");
        }
        //Patterns
        else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("The user Email is not valid!");
        } else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("The userName is not valid! Check Patterns");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("The username is too big! max = 20");
        } if (!user.isAdmin()) {
            throw new RuntimeException("The user is not an admin!");
        }

        else if (user.getId() == null) {
            throw new RuntimeException("The user id is null!");
        }
    }

    public void validateCliente(ClienteDTO cliente) {
        //Client Validations
        if(cliente.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do cliente é inválido. max = 100 caracteres");
        } else if(cliente.getCpf().length() > 11 ) {
            throw new IllegalArgumentException("O CPF do cliente é inválido. max = 11 caracteres ");
        } else if(clienteRepo.existsById(cliente.getId())) {
            throw new RuntimeException("O cliente já está cadastrado dentro do sistema");
        } else if (cliente.getSenha().length() < 8) {
            throw new CompromisedPasswordException("A senha do cliente não é válida. min = 8");
        } else if (cliente.getEmail() == null){
            throw new IllegalArgumentException("The email can't be empty!");
        }
        //Patterns
        else if (!PASSWORD_PATTERN.matcher(cliente.getSenha()).matches()) {
            throw new RuntimeException("The password is not valid!. Check password patterns.");
        } else if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            throw new IllegalArgumentException("The email is not valid!. Check email PATTERNs.");
        }
    }
    public void validatePlanoDeNegocioToCreate(PlanoDeNegocio plano) {
        if (planoRepo.existsById(plano.getId())) {
            throw new RuntimeException("This plan already exists!");
        } else if (plano.getProdutos() == null || plano.getProdutos().size() == 0) {
            throw new RuntimeException("The products list can't be empty!");
        } else if (!NOME_PRODUTO_PATTERN.matcher(plano.getTipoDePlano()).matches()) {
            throw new IllegalArgumentException("The plan type is not valid!");
        }
    }
}
