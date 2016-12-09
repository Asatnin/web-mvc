package hello.web;

import hello.domain.Student;
import hello.service.StudentService;
import hello.web.model.CourseRequest;
import hello.web.model.StudentRequest;
import hello.web.model.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentRestController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<StudentResponse> getStudentList(@RequestParam("page") int page,
                                                @RequestParam("size") int size) {
        return studentService.getAllStudents(page, size)
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{studentId}", method = RequestMethod.GET)
    @ResponseBody
    public StudentResponse getStudent(@PathVariable int studentId) {
        return new StudentResponse(studentService.getById(studentId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createStudent(@RequestBody StudentRequest studentRequest, HttpServletResponse response) {
        Student student = studentService.save(studentRequest);
        response.addHeader(HttpHeaders.LOCATION, "/students/" + student.getId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{studentId}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable int studentId) {
        studentService.delete(studentId);
    }

    @RequestMapping(path = "/{studentId}", method = RequestMethod.PATCH)
    @ResponseBody
    public StudentResponse updateStudent(@PathVariable int studentId,
                                         @RequestBody StudentRequest studentRequest) {
        return new StudentResponse(studentService.update(studentId, studentRequest));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{studentId}/courses", method = RequestMethod.POST)
    public void addCourseToStudent(@PathVariable int studentId, @RequestBody CourseRequest courseRequest,
                                   HttpServletResponse response) {
        int courseId = studentService.addCourseToStudent(studentId, courseRequest);
        response.addHeader(HttpHeaders.LOCATION, "/courses/" + courseId);
    }
}
