import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListDisplayManager {
	List<TaskList> taskLists = new ArrayList<TaskList>();

	public ListDisplayManager(List<TaskList> taskLists) {
		this.taskLists = taskLists;
	}

	public void showListAll() {
		for (TaskList taskList : taskLists) {
			System.out.println(taskList);
		}
	}

	public void showListByDate(LocalDate date) {
		TaskList taskList = null;

		for (TaskList list : taskLists) {
			if (list.getListDate().equals(date)) {
				taskList = list;
				break;
			}
		}

		if (taskList != null) {
			System.out.println(taskList);
		} else {
			System.out.println("There is no list for this date");
		}
	}

	public void showYesterdaysList() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		showListByDate(yesterday);
	}

	public void showListFromToDate(LocalDate fromDate, LocalDate toDate) {
		for (TaskList taskList : taskLists) {
			if (taskList.getListDate().compareTo(fromDate) >= 0 && taskList.getListDate().compareTo(toDate) <= 0) {
				System.out.println(taskList);
			}
		}
	}

	public void showListMonthWise(int month, int year) {
		for (TaskList taskList : taskLists) {
			if (taskList.getListDate().getYear() == year && taskList.getListDate().getMonthValue() == month) {
				System.out.println(taskList);
			}
		}
	}
}
