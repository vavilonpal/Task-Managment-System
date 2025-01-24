package org.combs.micro.taskmanagmentsystem.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class PaginationAndSortingService {
    private static final List<String> VALID_TASK_SORT_FIELDS = List.of("id", "title", "priority", "createdAt");
    private static final List<String> VALID_USER_SORT_FIELDS = List.of("id", "email");

    private static final List<String> VALID_SORT_DIRECTIONS = List.of("asc", "desc");
    public Pageable createTaskPageable(int page, int size, String sortBy, String sortDirection) {
        if (!VALID_TASK_SORT_FIELDS.contains(sortBy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort field");
        }

        if (!VALID_SORT_DIRECTIONS.contains(sortDirection.toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort direction");
        }

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }
    public Pageable createUserPageable(int page, int size, String sortBy, String sortDirection) {
        if (!VALID_USER_SORT_FIELDS.contains(sortBy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort field");
        }

        if (!VALID_SORT_DIRECTIONS.contains(sortDirection.toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort direction");
        }

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }
}
