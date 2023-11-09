package classes.itcompany;

import classes.developer.AbstractDeveloper;
import classes.developer.Developer;
import classes.initialiser.DeveloperInitialiser;
import classes.interfaces.Clearable;
import classes.project.Project;

import java.util.List;

public final class ITCompany implements Clearable {
    private String itCompanyName;
    private Project project;
    private List<Developer> developerList;

    public ITCompany (String itCompanyName) {
        this.itCompanyName = itCompanyName;
    }
    public ITCompany initialiseITCompany() {
        ITCompany itCompany = new ITCompany("ExampleLLC");
        itCompany.setDeveloperList(DeveloperInitialiser.initialiseDeveloperList(this));
        itCompany.setProject(null);
        return itCompany;
    }

    public String getItCompanyName() {
        return itCompanyName;
    }

    public void setItCompanyName(String itCompanyName) {
        this.itCompanyName = itCompanyName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Developer> getDeveloperList() {
        return developerList;
    }

    public void setDeveloperList(List<Developer> developerList) {
        this.developerList = developerList;
    }
    public void switchToNextMonth(){
        developerList.forEach(AbstractDeveloper::refreshTime);
    }
    public void optimiseForFactor(String factor) {
    }
    @Override
    public void clear() {
        this.getDeveloperList().clear();
        this.setProject(null);
    }
}
