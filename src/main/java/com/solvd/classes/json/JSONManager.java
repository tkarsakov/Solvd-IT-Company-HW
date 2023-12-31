package com.solvd.classes.json;

import com.solvd.classes.exceptions.IncorrectJSONFormatException;
import com.solvd.classes.exceptions.IncorrectProjectNameException;
import com.solvd.classes.project.Project;
import com.solvd.classes.project.Task;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.solvd.classes.json.JSONFields.*;


public final class JSONManager implements IGetJSONObject {
    private static final Logger LOGGER = LogManager.getLogger(JSONManager.class);

    public static Project loadProject(String filename) {
        // Read json into string
        String projectstring;
        try (FileInputStream inputStream = new FileInputStream(filename + ".json")) {
            projectstring = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.info("File not found. Try again?");
            return null;
        }
        // Create JSONobject from said string
        JSONObject projectJSON;
        try {
            projectJSON = new JSONObject(projectstring);
        } catch (IncorrectJSONFormatException e) {
            LOGGER.info("Incorrect json formatting");
            LOGGER.error(e.getMessage());
            throw e;
        }
        // Get task objects array from json
        JSONArray tasksJSONarray = projectJSON.getJSONArray("tasks");
        // Set up variables for Project
        Project project = new Project(projectJSON.getString(PROJECT_NAME.getFieldName()));
        // Iterate over JSONarray to get individual tasks
        List<Task> taskList = JSONStream.of(tasksJSONarray)
                .map(taskJSON -> new Task(
                        taskJSON.getString(TASK_NAME.getFieldName()),
                        project,
                        taskJSON.getInt(TIME_REQUIRED.getFieldName()),
                        new BigDecimal(taskJSON.getString(REWARD.getFieldName()))))
                .toList();
        project.setTaskList(new ArrayList<>(taskList));
        return project;
    }

    public static boolean saveProject(Project project) throws IncorrectProjectNameException {
        if (project == null) {
            LOGGER.info("Can't save an unopened project!");
            return false;
        }
        JSONObject projectJSON = IGetJSONObject.getJSONObject(project);
        if (project.getProjectName().matches(".*\\s.*")) {
            throw new IncorrectProjectNameException("Project name cannot contain whitespace.");
        }
        try (FileWriter fileWriter = new FileWriter(project.getProjectName() + ".json")) {
            fileWriter.write(projectJSON.toString(2));
            return true;
        } catch (IOException e) {
            LOGGER.info("Cannot write to file. Check file/project name and try again.");
            return false;
        }
    }

    public static boolean saveProject(Project project, String filename) {
        if (project == null) {
            LOGGER.info("Can't save an unopened project!");
            return false;
        }
        JSONObject projectJSON = IGetJSONObject.getJSONObject(project);
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(projectJSON.toString(2));
            return true;
        } catch (IOException e) {
            LOGGER.info("Cannot write to file. Check file/project name and try again.");
            return false;
        }
    }

    public static boolean saveProject(Project project, boolean removeWhitespace) {
        if (project == null) {
            LOGGER.info("Can't save an unopened project!");
            return false;
        }
        JSONObject projectJSON = IGetJSONObject.getJSONObject(project);
        try (FileWriter fileWriter = new FileWriter(project.getProjectName().replace(" ", "_") + ".json")) {
            fileWriter.write(projectJSON.toString(2));
            return true;
        } catch (IOException e) {
            LOGGER.info("Cannot write to file. Check file/project name and try again.");
            return false;
        }
    }
}