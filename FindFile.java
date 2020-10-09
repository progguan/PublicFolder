import java.util.*;

public class FileSystem {
    FileNode root;

    // I was confused about where can I get the root directory,
    // now let's assume we have the root directory fold file as an input
    public FileSystem(FileNode rootDirectory) {
        root = rootDirectory;
    }

    // solution 1, use bfs to find the file
    // After I have the root directory, I can do BFS search
    public FileNode findFileBFS(int targetSize, String suffix) {
        FileNode cur = root;
        Queue<FileNode> que = new ArrayDeque<>();
        que.offer(cur);

        while (!que.isEmpty()) {
            FileNode fn = que.poll();
            if (isTarget(fn, targetSize, suffix)) {
                return fn;
            }
            for (FileNode child : fn.subFilesOrFolders.values()) {
                if (child.isFolder && child.size > targetSize) {
                    que.offer(child);
                }
            }
        }

        return null;
    }

    // solution 2, find find using dfs, but it may cause stackoverflow issue when the number of files is large, so bfs is better
    public FileNode dfsFindFile(int targetSize, String suffix) {
        FileNode cur = root;
        return dfsHelper(targetSize, suffix, cur);
    }

    public FileNode dfsHelper(int targetSize, String suffix, FileNode cur) {
        for (FileNode child : cur.subFilesOrFolders.values()) {
            if (isTarget(child, targetSize, suffix)) {
                return child;
            }
            if (child.isFolder && child.size > targetSize) {
                dfsHelper(targetSize, suffix, child);
            }
        }
        return null;
    }

    // if the current file is a file and its name contains the suffix, or its size is < the target size, then we found it
    public boolean isTarget(FileNode fn, int targetSize, String suffix) {
        if (fn.isFile && (fn.name.contains(suffix) || fn.size < targetSize)) {
            return true;
        }
        return false;
    }
}

class FileNode {
    // file name including suffix, e.g. TargetFile.txt
    String name;
    int size;
    boolean isFile;
    boolean isFolder;
    /*
     I use Map because if in the future we want to have a feature finding a file / folder by name, then a
     HashMap can support this by children.get("name") in O(1) time complexity. But for this question we
     talked about today, we can just use a LinkedList here.
     */
    Map<String, FileNode> subFilesOrFolders;

    public FileNode(String name) {
        this.name = name;
        subFilesOrFolders = new HashMap<>();
    }
}
