import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	private ObjectInputStream objectInputStream;
	private FileInputStream fileInputStream;

	public FileReader() {

	}

	public FileReader(String filenameString) {
		try {
			this.fileInputStream = new FileInputStream(filenameString);
			this.objectInputStream = new ObjectInputStream(this.fileInputStream);
		} catch (FileNotFoundException e) {
			System.err.println("File does not exist with file name: " + filenameString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<TaskList> readLists() {
		List<TaskList> addressBookList = new ArrayList<TaskList>();

		try {
			addressBookList = (List<TaskList>) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	public TaskList readList() {
		TaskList taskList = new TaskList();

		try {
			taskList = (TaskList) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;
	}

	public void close() {
		try {
			this.fileInputStream.close();
			this.objectInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
