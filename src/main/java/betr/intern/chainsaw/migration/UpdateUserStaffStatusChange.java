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
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void execute(Database database) throws CustomChangeException {
        try {
            MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

            Query pitchTechQuery = new Query(Criteria.where("email").regex("@pitchtech\\.com$", "i"));
            Update setStaffTrue = new Update().set("isStaff", true);

            UpdateResult resultTrue = mongoTemplate.updateMulti(pitchTechQuery, setStaffTrue, "users");

            Query othersQuery = new Query(Criteria.where("email").not().regex("@pitchtech\\.com$", "i"));
            Update setStaffFalse = new Update().set("isStaff", false);

            UpdateResult resultFalse = mongoTemplate.updateMulti(othersQuery, setStaffFalse, "users");

            System.out.println(String.format(
                    "updated %d staff and %d regular users.",
                    resultTrue.getModifiedCount(), resultFalse.getModifiedCount()));

        } catch (Exception e) {
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
    public ValidationErrors validate(Database database) {
        return null;
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {}
}
