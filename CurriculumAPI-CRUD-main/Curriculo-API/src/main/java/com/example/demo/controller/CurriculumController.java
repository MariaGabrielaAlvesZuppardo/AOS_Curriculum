package com.example.demo.controller;

import com.example.demo.model.Curriculum;
import com.example.demo.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/curriculos")
public class CurriculumController {
    private final CurriculumRepository curriculumRepository;

    @Autowired
    public CurriculumController(CurriculumRepository curriculumRepository){
        this.curriculumRepository=curriculumRepository;
    }

    @GetMapping
    public ResponseEntity<List<Curriculum>> listarCurriculos() {
        List<Curriculum> curriculos = curriculumRepository.findAll();
        return new ResponseEntity<>(curriculos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curriculum> buscarCurriculoPorId(@PathVariable Long id) {
        Optional<Curriculum> curriculoOptional = curriculumRepository.findById(id);
        return curriculoOptional.map(curriculo -> new ResponseEntity<>(curriculo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Curriculum> criarCurriculo(@RequestBody Curriculum curriculum) {
        Curriculum novoCurriculo = curriculumRepository.save(curriculum);
        return new ResponseEntity<>(novoCurriculo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curriculum> atualizarCurriculo(@PathVariable Long id, @RequestBody Curriculum curriculoAtualizado) {
        Optional<Curriculum> curriculoOptional = curriculumRepository.findById(id);
        if (curriculoOptional.isPresent()) {
            Curriculum curriculoExistente = curriculoOptional.get();
            curriculoExistente.setNome(curriculoAtualizado.getNome());
            curriculoExistente.setExperiencia(curriculoAtualizado.getExperiencia());
            curriculoExistente.setCursos(curriculoAtualizado.getCursos());
            curriculoExistente.setEndereco(curriculoAtualizado.getEndereco());
            Curriculum curriculoAtualizadoBD = curriculumRepository.save(curriculoExistente);
            return new ResponseEntity<>(curriculoAtualizadoBD, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCurriculo(@PathVariable Long id) {
        Optional<Curriculum> curriculoOptional = curriculumRepository.findById(id);
        if (curriculoOptional.isPresent()) {
            curriculumRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
