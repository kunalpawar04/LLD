package org.example;

import java.util.ArrayList;

/*
 Composite Pattern:
 - Treats individual objects and compositions of objects uniformly
 - Represents part-whole hierarchies using a tree structure
 - Allows clients to work with single objects and groups in the same way
 - Simplifies recursive operations on complex object structures
  ---------------------------------------
  Both File (leaf) and Folder (composite) implement this interface.
  This allows clients to treat files and folders uniformly.
 */

interface FileObject {

    // List the contents (similar to `ls` command)
    // indent controls how deep we are in the folder hierarchy
    void ls(Integer indent);

    // Recursively open / print everything under this node
    void openAll(Integer indent);

    // Returns size in MB
    // For File → fixed size
    // For Folder → sum of children sizes
    double getSizeInMB();

    // Navigate into a child folder by name
    // Returns the folder if found, otherwise null
    FileObject cd(String target);

    // Name of file or folder
    String getName();

    // Helps distinguish Leaf vs Composite at runtime
    Boolean isFolder();
}

/*
 * Leaf class (File)
 * -----------------
 * Represents an individual file.
 * A file cannot contain other FileObjects.
 */
class File implements FileObject {

    private double size;
    private String name;

    public File(double fileSize, String name) {
        this.size = fileSize;
        this.name = name;
    }

    // Prints only itself (no children)
    @Override
    public void ls(Integer indent) {
        String space = " ".repeat(indent);
        System.out.println(space + name);
    }

    // Same as ls for a file (no recursion)
    @Override
    public void openAll(Integer indent) {
        String space = " ".repeat(indent);
        System.out.println(space + name);
    }

    // File has a fixed size
    @Override
    public double getSizeInMB() {
        return this.size;
    }

    // cd is not applicable for files
    @Override
    public FileObject cd(String target) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    // Leaf node → not a folder
    @Override
    public Boolean isFolder() {
        return false;
    }
}

/*
 * Composite class (Folder)
 * ------------------------
 * A folder can contain files and other folders.
 * Implements recursive behavior.
 */
class Folder implements FileObject {

    private String name;
    private double size;

    // Children: can be File or Folder
    private ArrayList<FileObject> items;

    public Folder(String name) {
        this.name = name;
        this.size = 0;
        this.items = new ArrayList<>();
    }

    /*
     * Lists only immediate children (like `ls`)
     * Does NOT recurse.
     */
    @Override
    public void ls(Integer indent) {
        for (FileObject item : items) {
            if (item.isFolder()) {
                System.out.println("+" + item.getName());
            } else {
                System.out.println(item.getName());
            }
        }
    }

    /*
     * Recursively prints the entire folder tree
     * Uses indentation to represent hierarchy
     */
    @Override
    public void openAll(Integer indent) {
        String space = " ".repeat(indent);

        // Folder is marked with +
        System.out.println(space + "+" + name);

        // Recursively open children
        for (FileObject item : items) {
            item.openAll(indent + 4);
        }
    }

    /*
     * Folder size = sum of sizes of all children
     * Computed dynamically to avoid stale values
     */
    @Override
    public double getSizeInMB() {
        double totalSize = 0;

        for (FileObject item : items) {
            totalSize += item.getSizeInMB();
        }

        this.size = totalSize;
        return this.size;
    }

    /*
     * Navigate into a child folder by name
     * Only folders are valid cd targets
     */
    @Override
    public FileObject cd(String target) {
        for (FileObject item : items) {
            if (item.isFolder() && item.getName().equals(target)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    // Composite node → folder
    @Override
    public Boolean isFolder() {
        return true;
    }

    // Adds a File or Folder to this folder
    public void add(FileObject item) {
        this.items.add(item);
    }
}

/*
 * Client code
 * -----------
 * Demonstrates usage of Composite pattern.
 * Client never cares whether it's dealing with File or Folder.
 */
public class Main {

    public static void main(String[] args) {

        // Root folder
        Folder root = new Folder("root");

        // Files under root
        File file1 = new File(1, "Config.xml");
        File file2 = new File(3, "Setup.pdf");

        // Subfolder
        Folder codingFolder = new Folder("Coding");

        root.add(file1);
        root.add(file2);
        root.add(codingFolder);

        // Files under Coding folder
        File file3 = new File(2.44, "Composite_Design_Pattern.java");
        File file4 = new File(1.78, "mergeSort.cpp");
        File file5 = new File(10, "index.html");

        codingFolder.add(file3);
        codingFolder.add(file4);
        codingFolder.add(file5);

        // Try different behaviors:

        // 1. Print entire tree
        // root.openAll(0);

        // 2. Get total size of root
        // System.out.println("Size of root folder is: " + root.getSizeInMB() + "MB");

        // 3. Navigate into a folder
        // FileObject internalFolder = root.cd("Coding");

        // 4. List immediate contents
        // codingFolder.ls(0);

        // 5. Recursive open from Coding folder
        // codingFolder.openAll(0);
    }
}