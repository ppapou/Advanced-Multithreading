import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

public class FileCounterTask extends RecursiveTask<Long> {

    /**
     * Current processed file
     */
    protected final File mFile;
    /**
     * All documents in folder
     */
    protected final AtomicLong mDocumentCount;
    /**
     * Directories count
     */
    protected final AtomicLong mFolderCount;
    FileCounterTask(File file, AtomicLong mDocumentCount, AtomicLong mFolderCount) {
        this.mFile = file;
        this.mDocumentCount = new AtomicLong(0);
        this.mFolderCount = new AtomicLong(0);
    }

    /**
     *
     * @return files number
     */
    public AtomicLong documentCount() {
        return mDocumentCount;
    };

    /**
     *
     * @return directories number
     */
    public AtomicLong folderCount() {
        return mFolderCount;
    }

    @Override
    protected Long compute() {
        if (mFile.isFile()) {
            mDocumentCount.incrementAndGet();
        } else {
            mFolderCount.incrementAndGet();
        }

        List<ForkJoinTask<Long>> forkJoinTaskList = new ArrayList<>();
        for(File file: Objects.requireNonNull(mFile.listFiles())) {
            //the task for each File
            forkJoinTaskList.add(
                    new FileCounterTask(file, mDocumentCount, mFolderCount).fork());
        }

        long sum = 0;

        for (ForkJoinTask<Long> forkJoinTask: forkJoinTaskList) {
            sum += forkJoinTask.join();
        }

        return sum;
    }
}
