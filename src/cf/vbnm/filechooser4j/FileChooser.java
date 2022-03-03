package cf.vbnm.filechooser4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class FileChooser {
    static {
        String libFullName = "libfilechooser.dll";

        String nativeTempDir = System.getProperty("java.io.tmpdir");

        InputStream in = null;
        BufferedInputStream reader;
        FileOutputStream writer = null;

        File extractedLibFile = new File(nativeTempDir + File.separator + libFullName);
        if (extractedLibFile.exists()) {
            extractedLibFile.delete();
        }
        try {
            in = FileChooser.class.getResourceAsStream(libFullName);
            if (in == null)
                in = FileChooser.class.getResourceAsStream(libFullName);
            FileChooser.class.getResource(libFullName);
            assert in != null;
            reader = new BufferedInputStream(in);
            writer = new FileOutputStream(extractedLibFile);

            byte[] buffer = new byte[1024];

            while (reader.read(buffer) > 0) {
                writer.write(buffer);
                buffer = new byte[1024];
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.load(extractedLibFile.toString());
        //System.load(Objects.requireNonNull(FileChooser.class.getResource("")).getPath() + "libfilechooser.dll");
    }


    /**
     * Flags from C source file COMMDLG_H
     */
    public static final int OFN_READONLY = 0x1;
    public static final int OFN_OVERWRITEPROMPT = 0x2;
    public static final int OFN_HIDEREADONLY = 0x4;
    public static final int OFN_NOCHANGEDIR = 0x8;
    public static final int OFN_SHOWHELP = 0x10;
    public static final int OFN_ENABLEHOOK = 0x20;
    public static final int OFN_ENABLETEMPLATE = 0x40;
    public static final int OFN_ENABLETEMPLATEHANDLE = 0x80;
    public static final int OFN_NOVALIDATE = 0x100;
    public static final int OFN_ALLOWMULTISELECT = 0x200;
    public static final int OFN_EXTENSIONDIFFERENT = 0x400;
    public static final int OFN_PATHMUSTEXIST = 0x800;
    public static final int OFN_FILEMUSTEXIST = 0x1000;
    public static final int OFN_CREATEPROMPT = 0x2000;
    public static final int OFN_SHAREAWARE = 0x4000;
    public static final int OFN_NOREADONLYRETURN = 0x8000;
    public static final int OFN_NOTESTFILECREATE = 0x10000;
    public static final int OFN_NONETWORKBUTTON = 0x20000;
    public static final int OFN_NOLONGNAMES = 0x40000;
    public static final int OFN_EXPLORER = 0x80000;
    public static final int OFN_NODEREFERENCELINKS = 0x100000;
    public static final int OFN_LONGNAMES = 0x200000;
    public static final int OFN_ENABLEINCLUDENOTIFY = 0x400000;
    public static final int OFN_ENABLESIZING = 0x800000;
    public static final int OFN_DONTADDTORECENT = 0x2000000;
    public static final int OFN_FORCESHOWHIDDEN = 0x10000000;
    public static final int OFN_EX_NOPLACESBAR = 0x1;
    public static final int OFN_SHAREFALLTHROUGH = 2;
    public static final int OFN_SHARENOWARN = 1;
    public static final int OFN_SHAREWARN = 0;
    /*
     * End of Flags from C source file COMMDLG_H
     * */
    /**
     * If file(s) succeed selected, return NO_ERROR
     */
    public static final short NO_ERROR = 0;
    /**
     * All possible error codes of file chooser
     */
    public static final short ERR_USERCANCELED = 0x3004;
    public static final short ERR_DIALOGFAILURE = -1;// The dialog box could not be created. The common dialog box function's call to the DialogBox function failed. For example, this error occurs if the common dialog box call specifies an invalid window handle.
    public static final short ERR_FINDRESFAILURE = 0x0006;// The common dialog box function failed to find a specified resource.
    public static final short ERR_INITIALIZATION = 0x0002;// The common dialog box function failed during initialization. This error often occurs when sufficient memory is not available.
    public static final short ERR_LOADRESFAILURE = 0x0007;// The common dialog box function failed to load a specified resource.
    public static final short ERR_LOADSTRFAILURE = 0x0005;// The common dialog box function failed to load a specified string.
    public static final short ERR_LOCKRESFAILURE = 0x0008;// The common dialog box function failed to lock a specified resource.
    public static final short ERR_MEMALLOCFAILURE = 0x0009;// The common dialog box function was unable to allocate memory for internal structures.
    public static final short ERR_MEMLOCKFAILURE = 0x000A;// The common dialog box function was unable to lock the memory associated with a handle.
    public static final short ERR_NOHINSTANCE = 0x0004;// The ENABLETEMPLATE flag was set in the Flags member of the initialization structure for the corresponding common dialog box, but you failed to provide a corresponding instance handle.
    public static final short ERR_NOHOOK = 0x000B;// The ENABLEHOOK flag was set in the Flags member of the initialization structure for the corresponding common dialog box, but you failed to provide a pointer to a corresponding hook procedure.
    public static final short ERR_NOTEMPLATE = 0x0003;// The ENABLETEMPLATE flag was set in the Flags member of the initialization structure for the corresponding common dialog box, but you failed to provide a corresponding template.
    public static final short ERR_REGISTERMSGFAIL = 0x000C;// The RegisterWindowMessage function returned an error code when it was called by the common dialog box function.
    public static final short ERR_STRUCTSIZE = 0x0001;// The lStructSize member of the initialization structure for the corresponding common dialog box is invalid.
    public static final short ERR_BUFFERTOOSMALL = 0x3003;// The buffer pointed to by the lpstrFile member of the OPENFILENAME structure is too small for the file name specified by the user. The first two bytes of the lpstrFile buffer contain an integer value specifying the size required to receive the full name, in characters.
    public static final short ERR_INVALIDFILENAME = 0x3002;// A file name is invalid.
    public static final short ERR_SUBCLASSFAILURE = 0x3001;// An attempt to subclass a list box failed because sufficient memory was not available.
    /*
     * End all possible error codes of file chooser
     */

    /**
     * Path initial length = 1024
     */
    private int pathBufferLength = 1024;

    /**
     * Path maxLength
     */
    public static final int PATH_MAX_LENGTH = 8192;
    /**
     * To storage path.
     */
    private byte[] path;

    /**
     * Title of file selection window
     */
    private String title;

    /**
     * Readonly flag
     */
    private boolean readonly = false;

    /**
     * Indicate the selected files number
     */
    private int files;

    /**
     * A string containing pairs of null-terminated filter strings.
     * The last string in the buffer must be terminated by two NULL characters.
     * <p>
     * The first string in each pair is a display string that describes the
     * filter (for example, "Text Files"), and the second string specifies
     * the filter pattern (for example, ".TXT"). To specify multiple filter patterns
     * for a single display string, use a semicolon to separate the patterns
     * (for example, ".TXT;.DOC;.BAK"). A pattern string can be a combination of
     * valid file name characters and the asterisk (*) wildcard character.
     * Do not include spaces in the pattern string.
     * <p>
     * The system does not change the order of the filters.
     * It displays them in the File Types combo box in the order specified in filenameFilter.
     * <p>
     * If Filter is null, the dialog box does not display any filters.
     * <p>
     * In the case of a shortcut, if no filter is set, GetOpenFileName and GetSaveFileName
     * retrieve the name of the .lnk file, not its target.
     * This behavior is the same as setting the OFN_NODEREFERENCELINKS flag
     * in the Flags member.
     * To retrieve a shortcut's target without filtering, use the string "All Files\0*.*\0\0".
     */
    private String filenameFilter = "All Files(*.*)\0*.*\0\0";


    /**
     * Initial directory
     */
    private String initialDir = null;

    private ArrayList<String> filePaths;

    private int flags = OFN_EXPLORER | OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST;

    private short errorCode = ERR_USERCANCELED;

    private Charset defaultCharset = Charset.forName("GBK");

    /**
     * Set the filename Extension of file chooser
     * eg:
     * "bitmap(*.bmp)\0*.bmp\0All Files(*.*)\0*.*\0\0"
     *
     * @see cf.vbnm.filechooser4j.FileChooser#filenameFilter
     */
    public FileChooser setFilenameFilter(String filenameFilter) {
        this.filenameFilter = filenameFilter;
        return this;
    }

    public FileChooser(boolean readonly) {
        this.readonly = readonly;
    }

    public FileChooser() {

    }

    /**
     * JNI method, to call the native file chooser.
     *
     * @param readonly       if false, call the GetSaveFileNameA(), if true, call the GetOpenFileNameA().
     * @param filenameFilter a byte array converted by a string contains the filename filter.
     * @param path           a byte array storage the selected file path and names.
     * @param flags          see {@link #flags}
     * @param initialDir     The directory opened to choose files.
     */
    private native short showOpenDialog0(boolean readonly, byte[] filenameFilter, byte[] path, byte[] initialDir, byte[] title, int flags);

    private short showOpenDialog() {
        return errorCode = showOpenDialog0(readonly,
                filenameFilter == null ? ("All Files(*.*)\0*.*\0\0" + '\0').getBytes(defaultCharset) : filenameFilter.getBytes(defaultCharset),
                path,
                initialDir == null ? "".getBytes() : (initialDir + '\0').getBytes(defaultCharset),
                title == null ? "".getBytes() : (title + '\0').getBytes(defaultCharset),
                flags);
    }

    /**
     * parse the files from path array.
     */
    public void parseResult() {
        if (filePaths == null) filePaths = new ArrayList<>();
        else filePaths.clear();
        int start = 0, len = 0;
        for (int i = 0; i < path.length; i++) {
            if (path[i] != 0) {
                len++;
            } else {
                filePaths.add(new String(path, start, len, defaultCharset));
                start = Math.min(i + 1, PATH_MAX_LENGTH);
                len = 0;
            }
            if (path[start] == 0)
                break;
        }

    }

    /**
     * return the first path of all select files.
     *
     * @return return a path.
     */
    public String getPath() {
        if (filePaths == null || filePaths.size() == 0)
            return null;
        if (filePaths.size() > 1)
            return filePaths.get(0) + File.separator + filePaths.get(1);
        return filePaths.get(0);
    }

    /**
     * Get all selected files' path.
     *
     * @return All paths of selected files, if no file selected, return null.
     */
    public String[] getPaths() {
        if (filePaths == null || filePaths.size() == 0)
            return null;
        if (filePaths.size() == 1)
            return new String[]{filePaths.get(0)};
        String[] ret = new String[filePaths.size() - 1];
        for (int i = 1; i < filePaths.size(); i++) {
            ret[i - 1] = filePaths.get(0) + File.separator + filePaths.get(i);
        }
        return ret;
    }

    /**
     * Use default setting to call file chooser or just call file chooser.
     *
     * @return Simply return success or not.
     */
    public boolean openFileName() {
        if (path == null)
            path = new byte[pathBufferLength];
        final boolean ret = showOpenDialog() == NO_ERROR;
        if (ret)
            parseResult();
        else
            filePaths.clear();
        return ret;
    }

    public FileChooser setReadonly(boolean readonly) {
        this.readonly = readonly;
        return this;
    }


    public FileChooser setInitialDir(String initialDir) {
        if (new File(initialDir).exists()) {
            this.initialDir = initialDir;
        }
        return this;
    }

    public FileChooser setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public FileChooser setPathBufferLength(int pathBufferLength) {
        this.pathBufferLength = Math.min(PATH_MAX_LENGTH, pathBufferLength);
        return this;
    }

    public FileChooser setTitle(String title) {
        this.title = title;
        return this;
    }

    public FileChooser setDefaultCharset(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
        return this;
    }

    public short getErrorCode() {
        return errorCode;
    }


    public int getFlags() {
        return flags;
    }

    @Override
    public String toString() {
        return "FileChooser{" + (getPaths() == null ? "[not select]" : Arrays.toString(getPaths())) +
                " }";
    }
}
