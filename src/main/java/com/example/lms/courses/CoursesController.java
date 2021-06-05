package com.example.lms.courses;

import javax.validation.Valid;

import com.example.lms.courses.dto.AssignCourseDto;
import com.example.lms.courses.dto.AssignTeacherDto;
import com.example.lms.courses.dto.CreateCourseDto;
import com.example.lms.courses.dto.CreateLessonDto;
import com.example.lms.courses.dto.StudentCourseDto;
import com.example.lms.courses.dto.StudentCoursesDto;
import com.example.lms.courses.entities.Course;
import com.example.lms.lessons.LessonsService;
import com.example.lms.security.UserDetailsImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CoursesController {
  private final CoursesService coursesService;
  private final LessonsService lessonsService;

  @GetMapping("/courses/teacher/{id}/create/lesson")
  @PreAuthorize("hasAuthority('TEACHER')")
  public String getCreateLessonPage(CreateLessonDto createLessonDto) {
    return "teacherCreateLesson";
  }

  @PostMapping("/courses/teacher/{id}/create/lesson")
  @PreAuthorize("hasAuthority('TEACHER')")
  public String createLesson(@PathVariable("id") Long id, @Valid CreateLessonDto createLessonDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "teacherCreateLesson";
    }

    try {
      lessonsService.create(createLessonDto, id);
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "teacherCreateLesson";
    }

    model.addAttribute("success", "Урок был создан успешно");
    return "teacherCreateLesson";
  }

  @GetMapping("/courses/teacher/{id}")
  @PreAuthorize("hasAuthority('TEACHER')")
  public String getTeacherCourse(@PathVariable("id") Long id, Model model) {
    Course teacherCourse = coursesService.getTeacherCourse(id);

    model.addAttribute("teacherCourse", teacherCourse);
    return "teacherCourse";
  }

  @GetMapping("/courses/teacher")
  @PreAuthorize("hasAuthority('TEACHER')")
  public String getTeacherCourses(@AuthenticationPrincipal UserDetailsImpl principal, Pageable pageable, Model model) {
    Long teacherId = principal.getUserData().getId();
    Page<Course> teacherCoursesPage = coursesService.getTeacherCourses(teacherId, pageable);

    model.addAttribute("currentPage", pageable.getPageNumber() + 1);
    model.addAttribute("totalPages", teacherCoursesPage.getTotalPages());
    model.addAttribute("totalElements", teacherCoursesPage.getTotalElements());
    model.addAttribute("teacherCourses", teacherCoursesPage.getContent());
    return "teacherCourses";
  }

  @GetMapping("/courses/{id}")
  @PreAuthorize("hasAuthority('STUDENT')")
  public String getStudentCourse(@PathVariable("id") Long id, Model model) {
    StudentCourseDto studentCourseDto = coursesService.getStudentCourse(id);

    model.addAttribute("studentCourseDto", studentCourseDto);
    return "studentCourse";
  }

  @GetMapping("/courses")
  @PreAuthorize("hasAuthority('STUDENT')")
  public String getStudentCourses(@AuthenticationPrincipal UserDetailsImpl principal, Pageable pageable, Model model) {
    Long studentId = principal.getUserData().getId();
    StudentCoursesDto studentCoursesDto = coursesService.getStudentCourses(studentId, pageable);

    model.addAttribute("currentPage", studentCoursesDto.getCurrentPage());
    model.addAttribute("totalPages", studentCoursesDto.getTotalPages());
    model.addAttribute("totalElements", studentCoursesDto.getTotalElements());
    model.addAttribute("studentCoursesDto", studentCoursesDto.getStudentCourseDtos());
    return "studentCourses";
  }

  @GetMapping("/courses/create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String getCreatePage(CreateCourseDto createCourseDto) {
    return "adminCreateCourse";
  }

  @PostMapping("/courses/create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String create(@Valid CreateCourseDto createCourseDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "adminCreateCourse";
    }

    try {
      coursesService.create(createCourseDto);
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "adminCreateCourse";
    }

    model.addAttribute("success", "Курс был создан успешно");
    return "adminCreateCourse";
  }

  @GetMapping("/courses/assign")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String getAssignPage(AssignCourseDto assignCourseDto) {
    return "adminAssignCourse";
  }

  @PostMapping("/courses/assign")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String assign(@Valid AssignCourseDto assignCourseDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "adminAssignCourse";
    }

    try {
      coursesService.assign(assignCourseDto);
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "adminAssignCourse";
    }

    model.addAttribute("success", "Курс был назначен успешно");
    return "adminAssignCourse";
  }

  @GetMapping("/courses/assign/teacher")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String getAssignTeacherPage(AssignTeacherDto assignTeacherDto) {
    return "adminAssignTeacher";
  }

  @PostMapping("/courses/assign/teacher")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String assignTeacher(@Valid AssignTeacherDto assignTeacherDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "adminAssignTeacher";
    }

    try {
      coursesService.assignTeacher(assignTeacherDto);
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "adminAssignTeacher";
    }

    model.addAttribute("success", "Преподаватель был назначен успешно");
    return "adminAssignTeacher";
  }
}