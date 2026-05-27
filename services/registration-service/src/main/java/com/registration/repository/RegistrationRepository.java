package com.registration.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registration.entity.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {



        boolean existsByStudentIdAndCourseIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                int studentId,
                int courseId,
                LocalDate endDate,
                LocalDate startDate
        );

        boolean existsByStudentIdAndSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                int studentId,
                String slot,
                LocalDate endDate,
                LocalDate startDate
        );

        List<Registration> findByStudentId(int studentId);

        List<Registration> findByCourseId(int courseId);

        boolean existsByStudentIdAndCourseIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndRegistrationIdNot(
                int studentId,
                int courseId,
                LocalDate startDate,
                LocalDate endDate,
                int registrationId
        );

        boolean existsByStudentIdAndSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndRegistrationIdNot(
                int studentId,
                String slot,
                LocalDate endDate,
                LocalDate startDate,
                int registrationId
        );


}