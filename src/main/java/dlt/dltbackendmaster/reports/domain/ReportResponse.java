package dlt.dltbackendmaster.reports.domain;

/**
 * Report Response - if rows size is zero the file is not generated, so this
 * entity is used to determine that not created file cannot be called to
 * download, note: calling empty
 * 
 * @author Francisco Macuacua
 *
 */
public class ReportResponse {
	private String fileName;
	private int fileSize;
	private int nextIndex;

	public ReportResponse(String fileName, int fileSize, int nextIndex) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.nextIndex = nextIndex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}
}
