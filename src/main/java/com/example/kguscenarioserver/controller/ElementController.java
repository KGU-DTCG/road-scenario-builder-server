package com.example.kguscenarioserver.controller;

import com.example.kguscenarioserver.dto.element.ElementRequest;
import com.example.kguscenarioserver.dto.element.UpdateElementRequest;
import com.example.kguscenarioserver.entity.Element;
import com.example.kguscenarioserver.entity.Type;
import com.example.kguscenarioserver.service.ElementService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ElementController {

    private final ElementService elementService;

    //element 저장
    @PostMapping("/save_element")
    public void saveElement(@RequestBody @Valid ElementRequest request,
                            HttpServletResponse response) throws IOException {
        elementService.saveElement(convertToElement(request));
        response.sendRedirect("/scenario_list");
    }

    //element 삭제
    @DeleteMapping("/delete_element/{id}")
    public void deleteElement(@PathVariable Long id,
                              HttpServletResponse response) throws IOException{
        try{
            Element element = elementService.getElement(id);
            elementService.deleteElement(element);
            response.sendRedirect("/scenario_list");
        } catch (NoSuchElementException e){
            response.sendError(response.SC_NOT_FOUND,"해당 시나리오가 없습니다.");
        } catch (Exception e){
            response.sendError(response.SC_INTERNAL_SERVER_ERROR,"에러가 발생했습니다.");
        }
    }

    //element 수정
    @PostMapping("/update_element")
    public void updateElement(@RequestBody @Valid UpdateElementRequest request,
                              HttpServletResponse response) throws IOException{
        elementService.updateElement(request.getId(), request.getUpdateName());
        response.sendRedirect("/scenario_list");
    }

    //dto -> entity 변환
    private Element convertToElement(ElementRequest request) {
        Element element = new Element();
        element.setType(request.getType());
        element.setName(request.getName());

        if(request.getType() != Type.LAYER){
            element.setParent(elementService.getElement(request.getParentId()));
        }

        return element;
    }
}
