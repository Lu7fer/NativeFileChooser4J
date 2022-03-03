package cf.vbnm.filechooser4j;


public class Test {
    public static void main(String[] args) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setReadonly(true).setPathBufferLength(4096).setFilenameFilter("All Files 所有文件\0*.*\0\0")
                .setTitle("选择文件").setFlags(fileChooser.getFlags() | FileChooser.OFN_ALLOWMULTISELECT);
        System.out.println(fileChooser.openFileName() + "||" + fileChooser.getErrorCode());
        System.out.println(fileChooser);
//        System.out.println(System.getProperty(("file.encoding")));

    }
}
