package main.java.interfaces;

import javafx.collections.ObservableList;
import main.java.model.Folder;

public interface FolderHandler {

	public void addFolder(Folder folder);
	public void onFolderSelected(Folder folder);
	public void onFolderDeleted(Folder folder);
	public void onFolderRemoved(Folder folder);
	public void onFolderAdded(Folder folder);
	public ObservableList<Folder> getFoldersFFH();

}
