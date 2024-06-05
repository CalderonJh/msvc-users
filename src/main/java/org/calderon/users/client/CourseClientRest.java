package org.calderon.users.client;

import org.calderon.users.model.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "courses", url = "localhost:8081")
public interface CourseClientRest {
	@PutMapping("/courses/buy/{id}")
	Course buyCourse(@PathVariable String id);
}