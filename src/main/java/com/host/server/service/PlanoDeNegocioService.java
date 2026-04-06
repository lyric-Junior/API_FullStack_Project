package com.host.server.service;

import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.entitys.PlanoDeNegocio;
import com.host.server.repository.PlanoDeNegocioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoDeNegocioService {

    @Autowired
    private PlanoDeNegocioRepository planoRepo;

    private ValidationService validationService;

    public List<PlanoDeNegocio> listarPlanos() {
        return planoRepo.findAll();
    }

    public PlanoDeNegocio cadastrarPlanoDeNegocio(PlanoDeNegocio plano) {
        PlanoDeNegocio novoPlano = new PlanoDeNegocio();

        validationService.validatePlanoDeNegocioToCreate(plano);

        novoPlano.setTipoDePlano(plano.getTipoDePlano());
        novoPlano.setDescricao(plano.getDescricao());
        novoPlano.setProdutos(plano.getProdutos());
        novoPlano.setValor(plano.getValor());

        return planoRepo.save(novoPlano);
    }

    public void deletarPlano(Long id) {
        planoRepo.deleteById(id);
    }

    public void editarPlano(PlanoDeNegocio plano) {
        PlanoDeNegocio planoExistente = planoRepo.findById(plano.getId())
                .orElseThrow(()-> new EntityNotFoundException("The plan selected doesn't exists!"));

        planoExistente.setDescricao(plano.getDescricao());
        planoExistente.setProdutos(plano.getProdutos());
        planoExistente.setValor(plano.getValor());
    }
}
