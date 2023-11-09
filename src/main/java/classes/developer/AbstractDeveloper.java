package classes.developer;

import classes.project.Task;

import java.util.Objects;

public abstract class AbstractDeveloper {
    private final int TIME_NORM = 180;
    protected Grade grade;
    protected String developerName;
    protected Integer timeAmount = TIME_NORM;
    public AbstractDeveloper(Grade grade, String developerName) {
        this.grade = grade;
        this.developerName = developerName;
    }
    public abstract void completeTask(Task task);

    public void refreshTime () {
        this.timeAmount = TIME_NORM;
    }

    public Integer getTimeAmount() {
        return timeAmount;
    }

    public void setTimeAmount(Integer timeAmount) {
        this.timeAmount = timeAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDeveloper that = (AbstractDeveloper) o;
        return grade == that.grade && Objects.equals(developerName, that.developerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, developerName);
    }
}