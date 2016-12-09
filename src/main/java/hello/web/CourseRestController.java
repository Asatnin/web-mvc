package hello.web;

import hello.service.CourseService;
import hello.service.StatisticsService;
import hello.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseRestController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CourseStatisticsResponse> getAllCourses(@RequestParam("page") int page,
                                                        @RequestParam("size") int size) {
        return courseService.getAllCourses(page, size)
                .stream()
                .map(CourseStatisticsResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{courseId}", method = RequestMethod.GET)
    @ResponseBody
    public CourseResponse getCourse(@PathVariable int courseId) {
        return new CourseResponse(courseService.getById(courseId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{courseId}/statistics", method = RequestMethod.POST)
    public void addStatisticsToCourse(@PathVariable int courseId,
                                      @RequestBody StatisticsRequest statisticsRequest,
                                      HttpServletResponse response) {
        response.addHeader(HttpHeaders.LOCATION,
                "/" + courseId + "/statistics/"
                        + courseService.addStatisticsToCourse(courseId, statisticsRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{courseId}/statistics/{statisticsId}", method = RequestMethod.DELETE)
    public void deleteStatisticsFromCourse(@PathVariable int courseId, @PathVariable int statisticsId) {
        courseService.deleteStatisticsFromCourse(courseId, statisticsId);
    }

    @RequestMapping(path = "/{courseId}/statistics/{statisticsId}", method = RequestMethod.PATCH)
    @ResponseBody
    public StatisticsResponse updateStatistics(@PathVariable int courseId, @PathVariable int statisticsId,
                                               @RequestBody StatisticsRequest statisticsRequest) {
        return new StatisticsResponse(statisticsService.updateStatistics(statisticsId, statisticsRequest));
    }

    @RequestMapping(path = "/{courseId}/statistics", method = RequestMethod.GET)
    @ResponseBody
    public List<StatisticsResponse> allStatistics(@PathVariable int courseId,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("size") int size) {
        return courseService.allStatistics(courseId, page, size)
                .stream()
                .map(StatisticsResponse::new)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{courseId}/instructors", method = RequestMethod.POST)
    public void addInstructorToCourse(@PathVariable int courseId,
                                      @RequestBody InstructorRequest instructorRequest,
                                      HttpServletResponse response) {
        response.addHeader(HttpHeaders.LOCATION,
                "/" + courseId + "/instructors/"
                        + courseService.addInstructorToCourse(courseId, instructorRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{courseId}/instructors/{instructorsId}", method = RequestMethod.DELETE)
    public void deleteInstructorFromCourse(@PathVariable int courseId, @PathVariable int instructorsId) {
        courseService.deleteInstructorFromCourse(courseId, instructorsId);
    }

    @RequestMapping(path = "/{courseId}/instructors", method = RequestMethod.GET)
    @ResponseBody
    public List<InstructorResponse> allInstructors(@PathVariable int courseId,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        return courseService.allInstructors(courseId, page, size)
                .stream()
                .map(InstructorResponse::new)
                .collect(Collectors.toList());
    }
}
