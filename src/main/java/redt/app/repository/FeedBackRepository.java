package redt.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import redt.app.model.Feedback;

public interface FeedBackRepository extends PagingAndSortingRepository<Feedback, Long> {
}
