package ApiTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;

public class ApiTests {
    private UsersSettings usersSettings;

    @BeforeClass(alwaysRun = true)
    public void initPages(){
        usersSettings = new UsersSettings();
    }

        @Test (priority = 1)
        public void editUser () throws IOException {
            usersSettings.checkApiTest();
        }

        @Test (priority = 2)
         public void createCorrectUsers () throws IOException {
            usersSettings.createCorrectUser();
        }
        @Test (priority = 3)
        public void createIncorrectUser () throws IOException {
           usersSettings.createIncorrectUser();

         }
        @Test (priority = 4)
        public void deleteUser () throws IOException {
           usersSettings.deleteExistUser();
        }
      @Test (priority = 5)
        public void deleteNonExistUser() throws IOException {
          usersSettings.deleteNonExistUser();
  }
}