package redt.app.repository;

import org.springframework.data.repository.CrudRepository;
import redt.app.model.Feedback;
import redt.app.model.Reaction;

public interface ReactionRepository extends CrudRepository<Reaction, Long> {
}
