package com.host.server.service;

import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.Entitys.PlanoDeNegocio;
import com.host.server.repository.PlanoDeNegocioRepository;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanoDeNegocioService {

    @Autowired
    private PlanoDeNegocioRepository planoRepo;

    @Autowired
    private PatternValidationService patternValidationService;

    @Autowired
    private UsuarioRepository userRepo;

    public PlanoDeNegocio cadastrarPlanoDeNegocio(PlanoDeNegocio plano, UsuarioDTO user) {
        PlanoDeNegocio novoPlano = new PlanoDeNegocio();
        //Validations
        patternValidationService.validateUser(user);
        patternValidationService.validatePlanoDeNegocioToCreate(plano);

        novoPlano.setTipoDePlano(plano.getTipoDePlano());
        novoPlano.setDescricao(plano.getDescricao());
        novoPlano.setProdutos(plano.getProdutos());
        novoPlano.setValor(plano.getValor());

        return planoRepo.save(novoPlano);
    }
}
