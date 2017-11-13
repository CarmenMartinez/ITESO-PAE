package main.java.interfaces;

import main.java.model.Folder;

public interface FolderHandler {

	public void addFolder(Folder folder);
	public void onFolderSelected(Folder folder);
	public void onFolderDeleted(Folder folder);

}
