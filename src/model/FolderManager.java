package model;

import interfaces.FolderHandler;

public class FolderManager {

	private FolderHandler folderHandler;
	private Folder folder;
	private User user;

	public FolderHandler getFolderHandler() {
		return folderHandler;
	}

	public FolderManager setFolderHandler(FolderHandler folderHandler) {
		this.folderHandler = folderHandler;
		return this;
	}

	public Folder getFolder() {
		return folder;
	}

	public FolderManager setFolder(Folder folder) {
		this.folder = folder;
		return this;
	}

	public User getUser() {
		return user;
	}

	public FolderManager setUser(User user) {
		this.user = user;
		return this;
	}

}
