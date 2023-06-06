package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller//@RestController
@RequestMapping("/students")//http://localhost:8080/SpringMVC/students
//class:tüm methodlar için
// method level :sadece o method için mapping yapar
public class StudentController {

    @Autowired
    private StudentService service;

    //controllerdan requeste göre geriye ModelAndView(data+view name) veya
    //String tipinde view name döndürülür.
      //@RequestMapping("/students/hi")
      @GetMapping("/hi")
      public ModelAndView sayHi(){
          ModelAndView mav=new ModelAndView();
          mav.addObject("message","Hi, ");
          mav.addObject("messagebody","I am a Student Management System");
          mav.setViewName("hi");//hi.jsp
          return mav;
      }
      //view resolver mav içindeki view name e göre hi.jsp dosyasını bulur.
      //mav içindeki model ı hi.jsp içerisine bind eder.

    //1-Student Creation
    //kullanıcıdan bilgileri almak için form göstrelim
    @GetMapping("/new")//http://localhost:8080/SpringMVC/students/new
    public String sendStudentForm(@ModelAttribute("student") Student student){
          return "studentForm";
    }
    //@ModelAttribute ann:Studentformdaki bilgilerle Student tipinde bir obje oluşturur,
    //daha sonra bu objenin kullanılmasını sağlar. view ile controller arasında data transferini sağlar

    //formun submit://http://localhost:8080/SpringMVC/students/saveStudent, method:POST
    //tüm listeyi gösterelim
//    @PostMapping("/saveStudent")//http://localhost:8080/SpringMVC/students/saveStudent+POST
//    public String createStudent(@Valid @ModelAttribute Student student){
//          //service in save metodu gerekli
//        service.saveStudent(student);
//      return "redirect:/students";//http://localhost:8080/SpringMVC/students
//    }

    //validation
    @PostMapping("/saveStudent")//http://localhost:8080/SpringMVC/students/saveStudent+POST
    public String createStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult){
          if(bindingResult.hasErrors()){
              return "studentForm";
          }
        service.saveStudent(student);
        return "redirect:/students";//http://localhost:8080/SpringMVC/students
    }



    //tüm studentları listeleyen request:http://localhost:8080/SpringMVC/students
    //2-read:tüm kayıtlar
    @GetMapping//http://localhost:8080/SpringMVC/students
    public ModelAndView listAllStudents(){
          List<Student> students=service.getAll();
          ModelAndView mav=new ModelAndView();
          mav.addObject("studentList",students);
          mav.setViewName("students");//students.jsp
        return mav;
    }

    //3-update:http://localhost:8080/SpringMVC/students/update?id=4
//    @GetMapping("/update")
//    public ModelAndView showFormForUpdate(@RequestParam("id") Long id){
//          Student foundStudent=service.getStudentById(id);
//          ModelAndView mav=new ModelAndView();
//          mav.addObject("student",foundStudent);//studentFormda student modelına foundStudent ı bind etme
//          mav.setViewName("studentForm");
//          return mav;
//    }

    //update için 2.yöntem
    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("id") Long id, Model model){
        Student foundStudent=service.getStudentById(id);
        model.addAttribute("student",foundStudent);
        return "studentForm";
    }



    //4-delete:http://localhost:8080/SpringMVC/students/delete/1
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
          service.deleteStudent(id);
          return "redirect:/students";
    }

    //5-Exception Handling
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(Exception ex){
          ModelAndView mav=new ModelAndView();
          mav.addObject("message",ex.getMessage());
          mav.setViewName("notFound");
          return mav;
    }
    //ExceptionHandler: belirtilen exception sınıfı için bir metod belirlememizi sağlar
    //bu metod exceptionı yakalar ve özel bir işlem(notFound.jsp gösterilmesi) uygular...


    //restful service:tüm kayıtları döndüren:http://localhost:8080/SpringMVC/students/restAll
    @GetMapping("/restAll")
    @ResponseBody//responseun doğrudan HTTPye json olarak yazılmasını sağlıyor.
    public List<Student> getAllStudents(){
          List<Student> studentList=service.getAll();
          return studentList;
    }



}
