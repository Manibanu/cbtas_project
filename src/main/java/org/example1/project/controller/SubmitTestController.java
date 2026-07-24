package org.example1.project.controller;


import org.example1.project.dto.SubmitAnswerDTO;
import org.example1.project.entity.Result;
import org.example1.project.service.SubmitTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:5173")
public class SubmitTestController {



    @Autowired
    private SubmitTestService submitTestService;



    @PostMapping("/submit/{testId}")
    public Result submitTest(

            @PathVariable Long testId,

            @RequestBody List<SubmitAnswerDTO> answers

    ){


        return submitTestService.submitTest(
                testId,
                answers
        );


    }


}