
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TaskList implements Serializable {
	private List<TaskEntry> taskEntryList = new ArrayList<>();
	private LocalDate listDate = LocalDate.now();

	public TaskList() {

	}

	public TaskList(List<TaskEntry> taskEntryList, LocalDate listDate) {
		super();
		this.taskEntryList = taskEntryList;
		this.listDate = listDate;
	}

	public boolean add(TaskEntry taskEntry) {
		return taskEntryList.add(taskEntry);
	}

	public TaskEntry update(int index, TaskEntry taskEntry) {
		return taskEntryList.set(index, taskEntry);
	}

	public TaskEntry delete(int index) {
		return taskEntryList.remove(index);
	}

	public String toString() {
		StringBuilder str = new StringBuilder("Date: " + listDate);
		str.append(new StringBuilder(
				"\n********************************************************************************\n\n"));
		str.append(new StringBuilder(
				String.format("%7s %10s %10s   %-15s %s", "Sr. No.", "Start Time", "End Time", "Type", "Description")));
		str.append(new StringBuilder(
				"\n\n********************************************************************************\n"));
		int i = 1;
		for (TaskEntry taskEntry : taskEntryList) {
			str.append(String.format("\n%7d ", i));
			str.append(taskEntry);
			i++;
		}
		str.append(new StringBuilder(
				"\n\n********************************************************************************\n"));
		return str.toString();
	}
	
	public boolean equals(Object obj) {
		TaskList taskList = (TaskList)obj;
		
		if(taskList==null) {
			return false;
		}
		if(!(taskList instanceof TaskList)) {
			return false;
		}
		if(taskList.listDate.equals(this.listDate)) {
			return true;
		} else {
			return false;
		}
	}

	public List<TaskEntry> getTaskEntryList() {
		return taskEntryList;
	}

	public void setTaskEntryList(List<TaskEntry> taskEntryList) {
		this.taskEntryList = taskEntryList;
	}

	public LocalDate getListDate() {
		return listDate;
	}

	public void setListDate(LocalDate listDate) {
		this.listDate = listDate;
	}
}
