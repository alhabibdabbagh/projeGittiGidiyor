package com.gittigidiyor.homework2;

import com.gittigidiyor.homework2.model.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author habib
 * @on 29.08.2021 07:59
 */
@RestController
public class HelloController {

    java.util.List<Student> studentList = new ArrayList<>();

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StringRespone seyHello(@RequestParam String name, @PathParam("salary") double salary) {
        return new StringRespone("Hello From " + name + "and salary  " + salary);
    }

    @RequestMapping(value = "/hello11", method = RequestMethod.GET, produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public StringRespone seyHelloWithXml(@RequestParam String name, @PathParam("salary") double salary) {
        return new StringRespone("Hello From " + name + "and salary  " + salary);
    }

    @GetMapping(value = "/hello/{name}/{years}")
    public String info(@PathVariable String name, @PathVariable Integer years) {
        return "hello " + name + " and ur age " + years;
    }

    @GetMapping(value = "/hello1")
    public ResponseEntity<String> info2(@RequestParam String name, @PathParam("years") Integer years) {
        if (years > 100) {
            //  return  ResponseEntity.badRequest().body(" ur age cann't be yonger");// we dont use new
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ur age is failed");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("habibwww", "haderValue");
        return new ResponseEntity<String>("hello " + name + " and ur age " + years, httpHeaders, HttpStatus.CREATED);// headers
    }

    //@RequestParam  @PathParam @PathVariable
    @GetMapping(value = "/hello2")
    public ResponseEntity<String> info3(@RequestHeader("Cookie") String cookie, @RequestParam String name, @PathParam("years") Integer years) {
        if (years > 100) {
            //  return  ResponseEntity.badRequest().body(" ur age cann't be yonger");// we dont use new
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ur age is failed");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("habibwww", "haderValue");
        // return new ResponseEntity<String>("hello "+name+" and ur age "+", cookie  "+ cookie+" "+years,httpHeaders, HttpStatus.CREATED) ;// headers

        return ResponseEntity.ok().headers(httpHeaders).body("cookie " + cookie);// with different way
    }

    @GetMapping(value = "/hello3")
    public void http(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String Cookie = httpServletRequest.getHeader("Cookie");
        httpServletResponse.setHeader("habib-value", "habibNAMe");
        httpServletResponse.setHeader("Cookie", Cookie);
        httpServletResponse.setStatus(201);
        httpServletResponse.getWriter().println("hello from new http ");
    }

    @GetMapping(value = "/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        if (studentList.isEmpty()) {
            Student student = new Student(1, "habib", "istanbul");
            Student student2 = new Student(2, "mahmut", "antalya");
            Student student3 = new Student(3, "hasan", "ankara");
            studentList.add(student);
            studentList.add(student2);
            studentList.add(student3);
        }


        return ResponseEntity.ok().body(studentList);
    }

    @PostMapping(value = "/students")
    public ResponseEntity<List<Student>> addStudent(@RequestBody Student student) {
        studentList.add(student);
        return ResponseEntity.ok().body(studentList);

    }

    @GetMapping(value = "*")
    public ResponseEntity<String> fallBackendPoint() {
        return new ResponseEntity<>("there is no endPoint here", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/calculate/{opreation}")
    public StringRespone calculate(@PathVariable String opreation, @RequestParam("sum1") int sum1, @RequestParam("sum2") int sum2) {
        switch (opreation) {
            case "div":
                return new StringRespone(" " + sum1 + " / " + sum2 + " = " + (sum1 / sum2));
            case "sum":
                return new StringRespone(" " + sum1 + " + " + sum2 + " = " + (sum1 + sum2));
            case "multi":
                return new StringRespone(" " + sum1 + " * " + sum2 + " = " + (sum1 * sum2));
            case "sub":
                return new StringRespone(" " + sum1 + " - " + sum2 + " = " + (sum1 - sum2));
            default:
                return new StringRespone("raon opereation");
        }

    }
}
