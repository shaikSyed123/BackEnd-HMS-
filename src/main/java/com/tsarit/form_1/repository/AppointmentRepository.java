//package com.tsarit.form_1.repository;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.tsarit.form_1.model.Appointment;
//
//@Repository
//public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
//
//	  public Optional<Appointment>  findByid(int id);
//    
//
////	  List<Map<String, Object>> findBydate(LocalDate date);
//       
//	    
////	    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.date BETWEEN :startOfDay AND :endOfDay")
////	    long countByDateBetween(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);
//
//	    long countBydate(LocalDate date);
//	
//}
