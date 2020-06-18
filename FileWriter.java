import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileWriter {
	private ObjectOutputStream objectOutputStream;
	private FileOutputStream fileOutputStream;

	public FileWriter() {

	}

	public FileWriter(String fileName) {
		try {
			this.fileOutputStream = new FileOutputStream(fileName);
			this.objectOutputStream = new ObjectOutputStream(fileOutputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File does not exist with file name: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void writeLists(List<TaskList> taskLists) {
		try {
			objectOutputStream.writeObject(taskLists);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeList(TaskList taskList) {
		try {
			objectOutputStream.writeObject(taskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.fileOutputStream.close();
			this.objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
