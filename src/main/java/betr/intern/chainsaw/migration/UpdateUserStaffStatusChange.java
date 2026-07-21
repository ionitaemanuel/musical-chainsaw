package betr.intern.chainsaw.migration;

import com.mongodb.client.result.UpdateResult;
import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserStaffStatusChange implements CustomTaskChange, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void execute(final Database database) throws CustomChangeException {
        try {
            final MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

            final Query pitchTechQuery = new Query(Criteria.where("email").regex("@pitchtech\\.com$", "i"));
            final Update setStaffTrue = new Update().set("isStaff", true);

            final UpdateResult resultTrue = mongoTemplate.updateMulti(pitchTechQuery, setStaffTrue, "users");

            final Query othersQuery = new Query(Criteria.where("email").not().regex("@pitchtech\\.com$", "i"));
            final Update setStaffFalse = new Update().set("isStaff", false);

            final UpdateResult resultFalse = mongoTemplate.updateMulti(othersQuery, setStaffFalse, "users");

            System.out.println(String.format(
                    "updated %d staff and %d regular users.",
                    resultTrue.getModifiedCount(), resultFalse.getModifiedCount()));

        } catch (final Exception e) {
            throw new CustomChangeException("w h y", e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "successfully updated isStaff";
    }

    @Override
    public void setUp() throws SetupException {}

    @Override
    public ValidationErrors validate(final Database database) {
        return null;
    }

    @Override
    public void setFileOpener(final ResourceAccessor resourceAccessor) {}
}
