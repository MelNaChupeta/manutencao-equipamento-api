package br.com.tads.manutencaoequipamentoapi.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.models.projection.VwReceitaCategoriaProjection;
import br.com.tads.manutencaoequipamentoapi.models.projection.VwReceitaPeriodoProjection;
import br.com.tads.manutencaoequipamentoapi.repositories.VwReceitaRepository;

@Service
public class ReceitaService {

    @Autowired
    private VwReceitaRepository repository;

    public List<VwReceitaCategoriaProjection> findByCategoria(Long idCategoria) {
        //Specification<VwReceita> specification = new ReceitaSpecification("CATEGORIA", idCategoria);
        return repository.findCategoria(idCategoria);
    }
    
    public List<VwReceitaPeriodoProjection> findByPeriodo(LocalDate dataInicial , LocalDate dataFinal) {
        if(dataInicial != null && dataFinal != null) {
            return repository.findPeriodo(dataInicial,dataFinal);
        }else{
            return repository.findPeriodo();
        }
    }
    
    
}
