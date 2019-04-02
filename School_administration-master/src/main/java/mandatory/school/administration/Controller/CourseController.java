package mandatory.school.administration.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mandatory.school.administration.Model.Course;
import mandatory.school.administration.Model.LocalCourse;
import mandatory.school.administration.Model.Teacher;
import mandatory.school.administration.Model.TeacherCourse;
import mandatory.school.administration.Services.course.CourseService;
import mandatory.school.administration.Services.student.StudentService;
import mandatory.school.administration.Services.teacher.TeacherService;
import mandatory.school.administration.Utilities.CourseUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseController
{
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseUtilities courseUtilities;

    @GetMapping()
    public String overview(Model model)
    {
        boolean isTeacherOrAdmin = courseService.getIsTeacherOrAdmin();
        courseService.updateCourses();
        List<Course> courses = courseService.getFullCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("count", courses.size());
        model.addAttribute("course", new Course());
        model.addAttribute("isTeacherOrAdmin", isTeacherOrAdmin);
        return "/course/CourseOverview/overview";
    }

    @GetMapping("/details")
    public String details(Model model, @RequestParam int id)
    {
        boolean isTeacherOrAdmin = courseService.getIsTeacherOrAdmin();
        boolean isAdmin = courseService.getIsAdmin();
        courseService.updateCourses();
        List<Teacher> teachersInCourse = teacherService.getAllByCourseId(id);
        model.addAttribute("course", courseService.getFullCourseById(id));
        model.addAttribute("studentsInCourse", studentService.getAllByCourseId(id));
        model.addAttribute("teachersInCourse", teachersInCourse);
        model.addAttribute("isTeacherOrAdmin", isTeacherOrAdmin);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("availableTeachers", courseUtilities.getTeachersThatAreNotInCourse(teacherService.getAllTeachers(), teachersInCourse));
        return "/course/details";
    }

    @ResponseBody
    @GetMapping("/ajaxdetails")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<String> getCourseAjax(@RequestParam int id) throws JsonProcessingException
    {
        Course response = courseService.getFullCourseById(id);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();

        map.put("nameDanish", response.getNameDanish());
        map.put("nameEnglish", response.getNameEnglish());
        map.put("description", response.getDescription());
        map.put("semester", String.valueOf(response.getSemester()));
        map.put("numberOfTeachers", String.valueOf(response.getNumberOfTeachers()));
        map.put("studyProgramme", response.getStudyProgramme());
        map.put("mandatory", response.getMandatory());
        map.put("ects", String.valueOf(response.getECTS()));
        map.put("language", response.getLanguage());
        map.put("minimumStudents", String.valueOf(response.getLocalCourse().getMinimumStudents()));
        map.put("expectedStudents", String.valueOf(response.getLocalCourse().getExpectedStudents()));
        map.put("maximumStudents", String.valueOf(response.getLocalCourse().getMaximumStudents()));
        map.put("prerequisites", response.getLocalCourse().getPrerequisites());
        map.put("learningOutcome", response.getLocalCourse().getLearningOutcome());
        map.put("content", response.getLocalCourse().getContent());
        map.put("learningActivities", response.getLocalCourse().getLearningActivities());
        map.put("examForm", response.getLocalCourse().getExamForm());

        return new ResponseEntity<>(mapper.writeValueAsString(map), HttpStatus.OK);
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Course course, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "/course/create";
        }
        courseService.updateCourses();
        int id = courseService.createCourseLegacy(course).getId();
        course.getLocalCourse().setId(id);
        courseService.createLocalCourse(course.getLocalCourse());
        return "redirect:/course/details?id=" + id;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam int courseId)
    {
        model.addAttribute("course", courseService.getFullCourseById(courseId));
        return "/course/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Course course, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "redirect:/course/edit?id=" + course.getId();
        }
        courseService.editCourse(course.getLocalCourse());
        courseService.createCourseLegacy(course);
        return "redirect:/course/details?id=" + course.getId();
    }

    @GetMapping("/removeStudent")
    public String removeStudent (@RequestParam int courseId, @RequestParam int studentId)
    {
        courseService.removeStudentFromCourse(courseId, studentId);
        return "redirect:/course/details?id=" + courseId;
    }

    @GetMapping("/addTeacher")
    public String addTeacher (@RequestParam("courseId") int courseId, @RequestParam("teacherId") int teacherId)
    {
        Teacher teacher = teacherService.getOne(teacherId);
        LocalCourse course = courseService.findCourseById(courseId);
        TeacherCourse tc = new TeacherCourse();
        tc.setTeacher(teacher);
        tc.setCourse(course);
        course.getTeacherCourses().add(tc);
        teacherService.editTeacher(teacher);
        courseService.editCourse(course);
        return "redirect:/course/details?id=" + courseId;
    }

    @GetMapping("/removeTeacher")
    public String removeTeacher (@RequestParam int courseId, @RequestParam int teacherId)
    {
        courseService.removeTeacherFromCourse(courseId, teacherId);
        return "redirect:/course/details?id=" + courseId;
    }
}
