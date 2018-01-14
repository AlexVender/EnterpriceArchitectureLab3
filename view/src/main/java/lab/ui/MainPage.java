package lab.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import lab.dao.interfaces.ProjectsDAO;
import lab.dao.interfaces.TasksDAO;
import lab.dao.interfaces.UsersDAO;
import lab.datatypes.Priority;
import lab.datatypes.Status;
import lab.entities.ProjectEntity;
import lab.entities.TaskEntity;
import lab.entities.UserEntity;
import lab.filters.TasksFilter;
import lab.utils.EnumItemCaptionGenerator;
import lab.utils.EnumRenderer;
import lab.utils.LocalDateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import java.util.Arrays;
import java.util.Set;


@Title("Tasks Manager")
@Theme("custom-valo")
@SpringUI
public class MainPage extends UI {
    public static final float WIDTH = 980;
    public static final float PRIORITY_COLUMN_WIDTH = 70;
    public static final float STATUS_COLUMN_WIDTH = 90;
    public static final float DUE_DATE_COLUMN_WIDTH = 75;
    
    public static final String DATE_FORMAT = "dd.MM.YY";
    
    private final LocalDateRenderer localDateRenderer = new LocalDateRenderer(DATE_FORMAT);
    
    private final GridLayout wrapPane = new GridLayout(1, 1);
    private final HorizontalLayout mainLayout = new HorizontalLayout();
    private final VerticalLayout rightPane = new VerticalLayout();
    private final HorizontalLayout buttonsPane = new HorizontalLayout();
    private final FormLayout filterFormLayout = new FormLayout();
    private final Grid<TaskEntity> tasksGrid = new Grid<>();
    
    private final Button btnDelete = new Button("Delete");
    private final Button btnNew = new Button("New");
    private final Button btnSearchSwitch = new Button("Search");
    private final Button btnFind = new Button("Find");
    
    private final TextField filterText = new TextField("Search by text");
    private final ComboBox<Priority> filterPriority = new ComboBox<>("Priority");
    private final ComboBox<Status> filterStatus = new ComboBox<>("Status");
    private final DateField filterStartDateFrom = new DateField();
    private final DateField filterStartDateTo = new DateField();
    private final DateField filterDueDateFrom = new DateField();
    private final DateField filterDueDateTo = new DateField();
    private final ComboBox<UserEntity> filterAssignee = new ComboBox<>("Assignee");
    private final ComboBox<ProjectEntity> filterProject = new ComboBox<>("Project");
    
    @Autowired
    private TasksDAO tasksDAO;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private ProjectsDAO projectsDAO;
    
    @Autowired
    private TasksFilter filter;
    
    private Set<TaskEntity> selectedItems;
    
    
    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }
    
    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initLayout();
        tasksGridInit();
        rightPaneInit();
        
        wrapPane.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        wrapPane.setSizeFull();
        wrapPane.addComponent(mainLayout);
        
        mainLayout.setWidth(WIDTH, Unit.PIXELS);
        mainLayout.setHeight("100%");
        mainLayout.setMargin(new MarginInfo(true, false, false, false));
        
        rightPane.setMargin(true);
    }
    
    private void initLayout() {
        setContent(wrapPane);
        
        mainLayout.addComponents(tasksGrid, rightPane);
        rightPane.addComponents(buttonsPane, filterFormLayout);
        buttonsPane.addComponents(btnNew, btnDelete, btnSearchSwitch);
    
        HorizontalLayout filterStartDate = new HorizontalLayout(filterStartDateFrom, new Label(" – "), filterStartDateTo);
        HorizontalLayout filterDueDate = new HorizontalLayout(filterDueDateFrom, new Label(" – "), filterDueDateTo);
        filterStartDate.setCaption("Start Date");
        filterDueDate.setCaption("Due Date");
        filterFormLayout.addComponents(filterText, filterPriority, filterStatus, filterStartDate,
                filterDueDate, filterAssignee, filterProject, btnFind);
    }
    
    public void tasksGridInit() {
        tasksGrid.setSizeFull();
        tasksGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        
        tasksGrid.addColumn(TaskEntity::getName)
                .setCaption("Name")
                .setResizable(false)
                .setExpandRatio(2);
        tasksGrid.addColumn(TaskEntity::getPriority, new EnumRenderer<Priority>())
                .setCaption("Priority")
                .setWidth(PRIORITY_COLUMN_WIDTH)
                .setResizable(false);
        tasksGrid.addColumn(TaskEntity::getStatus, new EnumRenderer<Status>())
                .setCaption("Status")
                .setWidth(STATUS_COLUMN_WIDTH)
                .setResizable(false);
        tasksGrid.addColumn(TaskEntity::getDueDate, localDateRenderer)
                .setCaption("DueDate")
                .setWidth(DUE_DATE_COLUMN_WIDTH)
                .setResizable(false);
        
        tasksGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                TaskEntity task = event.getItem();
                TaskEditWindow taskEditWindow = new TaskEditWindow(task);
                taskEditWindow.addCloseListener(e -> refreshTasks());
                getUI().getUI().addWindow(taskEditWindow);
            }
        });
        
        refreshTasks();
    }
    
    private void rightPaneInit() {
        filterFormLayout.setSizeFull();
        filterFormLayout.setVisible(false);
        
        filterText.setWidth("100%");
        filterPriority.setItems(Arrays.asList(Priority.values()));
        filterPriority.setItemCaptionGenerator(new EnumItemCaptionGenerator<>());
        filterStatus.setItems(Arrays.asList(Status.values()));
        filterStatus.setItemCaptionGenerator(new EnumItemCaptionGenerator<>());
        filterAssignee.setItemCaptionGenerator(item -> item.getFirstName() + " " + item.getLastName());
        filterAssignee.setItems(usersDAO.readAll(0, 0));
        filterProject.setItemCaptionGenerator(ProjectEntity::getName);
        filterProject.setItems(projectsDAO.readAll(0, 0));
        
        btnNew.addClickListener(event -> {
            TaskCreateWindow taskCreateWindow = new TaskCreateWindow();
            taskCreateWindow.addCloseListener(e -> refreshTasks());
            getUI().getUI().addWindow(taskCreateWindow);
        });
        
        btnDelete.addClickListener(event -> {
            selectedItems = tasksGrid.getSelectedItems();
            int cnt = selectedItems.size();
            if (cnt > 0) {
                for (TaskEntity taskEntity : selectedItems) {
                    tasksDAO.delete(taskEntity);
                }
                String message = (cnt > 1) ? "Successfully deleted " + cnt + " tasks" : "Task Successfully deleted";
                Notification successDeleteNotification = new Notification(message, Notification.Type.HUMANIZED_MESSAGE);
                successDeleteNotification.show(Page.getCurrent());
                
                refreshTasks();
            }
        });
        
        btnSearchSwitch.addClickListener(event -> {
            if (filterFormLayout.isVisible()) {
                btnSearchSwitch.setCaption("Search");
                filterFormLayout.setVisible(false);
            } else {
                btnSearchSwitch.setCaption("Show all");
                filterFormLayout.setVisible(true);
            }
            refreshTasks();
        });
        
        btnFind.addClickListener(event -> {
            filter.setName(filterText.getValue());
            filter.setPriority(filterPriority.getValue());
            filter.setStatus(filterStatus.getValue());
            filter.setStartDateFrom(filterStartDateFrom.getValue());
            filter.setStartDateTo(filterStartDateTo.getValue());
            filter.setDueDateFrom(filterDueDateFrom.getValue());
            filter.setDueDateTo(filterDueDateTo.getValue());
            filter.setAssignee(filterAssignee.getValue());
            filter.setProject(filterProject.getValue());
            refreshTasks();
        });
    }
    
    public void refreshTasks() {
        if (filterFormLayout.isVisible()) {
            tasksGrid.setItems(filter.find());
        } else {
            tasksGrid.setItems(tasksDAO.readAll(0, 0));
        }
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainPage.class, productionMode = false)
    public static class MyUIServlet extends SpringVaadinServlet {
    }
}
